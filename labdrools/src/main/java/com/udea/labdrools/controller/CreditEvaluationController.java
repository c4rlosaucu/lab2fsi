package com.udea.labdrools.controller;

import com.udea.labdrools.ai.AIExplanationService;
import com.udea.labdrools.model.CreditRequest;
import com.udea.labdrools.model.CreditResponse;
import com.udea.labdrools.service.CreditEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit")
public class CreditEvaluationController {
    @Autowired
    private CreditEvaluationService evaluationService;
    @Autowired
    private AIExplanationService aiService;

    //Endpoint REST
    @PostMapping("/api/evaluate")
    public CreditResponse evaluateCreditApi(@Valid @RequestBody CreditRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Errores de validacion");
            return new CreditResponse(false, 0.0, 0.0, "Error de Validacion: " + errorMessage);
        }
        return evaluationService.evaluateCredit(request);
    }

    @PostMapping("/evaluateCredit")
    public Object evaluate(@RequestBody CreditRequest customer) {
        CreditResponse decision = evaluationService.evaluateCredit(customer);
        String explanation = aiService.explain(decision.getMessage());
        return new Object() {
            public final CreditResponse result = decision;
            public final String aiExplanation = explanation;
        };
    }
}

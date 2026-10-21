package com.udea.labdrools.controller;

import com.udea.labdrools.model.CreditRequest;
import com.udea.labdrools.model.CreditResponse;
import com.udea.labdrools.model.Customer;
import com.udea.labdrools.service.CreditEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador de las vistas web (Thymeleaf). El controlador REST
 * (CreditEvaluationController) se mantiene aparte para no mezclar JSON con HTML.
 */
@Controller
@RequestMapping("/credit")
public class CreditWebController {

    @Autowired
    private CreditEvaluationService evaluationService;

    // Mostrar el formulario web
    @GetMapping("/form")
    public String showCreditForm(Model model) {
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setCustomer(new Customer());
        model.addAttribute("creditRequest", creditRequest);
        return "credit_form";
    }

    // Procesar la solicitud desde el formulario web
    @PostMapping("/evaluate")
    public String evaluateCreditWeb(@Valid @ModelAttribute("creditRequest") CreditRequest creditRequest,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "credit_form";
        }
        CreditResponse creditResponse = evaluationService.evaluateCredit(creditRequest);
        model.addAttribute("creditResponse", creditResponse);
        return "credit_result";
    }
}

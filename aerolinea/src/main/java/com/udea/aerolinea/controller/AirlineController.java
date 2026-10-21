package com.udea.aerolinea.controller;

import com.udea.aerolinea.model.FlightRequest;
import com.udea.aerolinea.model.ServiceResult;
import com.udea.aerolinea.service.AirlineEvaluationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineEvaluationService evaluationService;

    public AirlineController(AirlineEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/api/evaluate")
    public ResponseEntity<Object> evaluate(@Valid @RequestBody FlightRequest request,
                                           BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Map.of("error", "Error de validacion: " + errors));
        }
        ServiceResult serviceResult =
                evaluationService.evaluate(request.getPassenger(), request.getFlight());
        return ResponseEntity.ok(serviceResult);
    }
}

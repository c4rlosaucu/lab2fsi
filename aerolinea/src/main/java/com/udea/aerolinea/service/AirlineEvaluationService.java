package com.udea.aerolinea.service;

import com.udea.aerolinea.model.Flight;
import com.udea.aerolinea.model.Passenger;
import com.udea.aerolinea.model.ServiceResult;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class AirlineEvaluationService {

    private final KieContainer kieContainer;

    public AirlineEvaluationService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public ServiceResult evaluate(Passenger passenger, Flight flight) {
        ServiceResult result = new ServiceResult();
        KieSession kieSession = kieContainer.newKieSession();
        try {
            // Los hechos que entran a la memoria de trabajo
            kieSession.insert(passenger);
            kieSession.insert(flight);
            kieSession.insert(result);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        return result;
    }
}

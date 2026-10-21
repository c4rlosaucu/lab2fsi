package com.udea.labdrools.service;

import com.udea.labdrools.model.CreditRequest;
import com.udea.labdrools.model.CreditResponse;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditEvaluationService {

    @Autowired
    private KieContainer kieContainer;

    public CreditResponse evaluateCredit(CreditRequest request) {
        //Crear una respuesta oficial
        CreditResponse response = new CreditResponse();
        //crear la sesion de drools
        KieSession kieSession = kieContainer.newKieSession();
        try{
            //insertar los hechos en la sesion
            kieSession.insert(request);
            kieSession.insert(response);
            //ejecutamos todas las reglas
            kieSession.fireAllRules();

        }finally {
            //libera la sesion
            kieSession.dispose();
        }
        return response;
    }


}

package com.udea.labdrools.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIExplanationService {
    private final ChatClient chatClient;
    public AIExplanationService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String explain(String decison) {
        String prompt = """
                Eres un experto en reglas bancarias
                Explica el siguiente resultado generado por Drools.
                
                Resultado: 
                %s
                
                Explica:
                - Qué regla se activó
                - Porque se activó
                - Que significa para el cliente
                """.formatted(decison);
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            // Si la IA falla (sin saldo, sin clave, sin red), la decision de Drools no se pierde
            return "Explicacion de IA no disponible: " + e.getClass().getSimpleName();
        }
    }

}
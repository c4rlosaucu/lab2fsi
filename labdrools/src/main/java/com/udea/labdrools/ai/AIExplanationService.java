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
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

}
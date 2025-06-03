package com.rapidforge.service.projectGen;/*
package com.rapidforge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ai.mistralai.MistralAiChatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class MistralService {

    @Autowired
    private MistralAiChatModel mistralAiChatModel;

    public String sendPrompt(String prompt) {
        //PromptTemplate promptTemplate = new PromptTemplate("{prompt}");
       // Prompt promptRequest = promptTemplate.create(Map.of("prompt", prompt));
        Long startTime = System.currentTimeMillis();
        Prompt promptRequest =   new Prompt(prompt);
        String response =  mistralAiChatModel.call(promptRequest).getResult().getOutput().getText();
        log.info("response from Mistral AI: {}", response);
        Long totalTimeTaken =  (System.currentTimeMillis() - startTime);
        log.info("TotalTimeTaken : {}", totalTimeTaken);
        return response;
    }

}*/

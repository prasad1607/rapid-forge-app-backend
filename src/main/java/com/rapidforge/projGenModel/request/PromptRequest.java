package com.rapidforge.projGenModel.request;

import lombok.Data;

@Data
public class PromptRequest {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}


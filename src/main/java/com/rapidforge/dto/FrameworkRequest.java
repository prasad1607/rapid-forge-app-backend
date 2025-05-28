package com.rapidforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkRequest {
    private String frameCode;
    private String frameName;
    private String langCode;
    private Boolean isDefaultFrame;
    private String createdBy;
}

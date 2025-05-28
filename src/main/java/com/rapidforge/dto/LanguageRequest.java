package com.rapidforge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageRequest {
    private String langCode;
    private String langName;
    private String langExtension;
    private String createdBy;

    private Integer usageCount = 0;
}

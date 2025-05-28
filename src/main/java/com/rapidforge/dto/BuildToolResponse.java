package com.rapidforge.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildToolResponse {
    private Long id;
    private String buildToolsCode;
    private String buildToolsName;
    private String buildToolsType;
    private String langCode;
    private String frameCode;
    private Boolean isDefaultFrame;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<BuildToolVersionResponse> versions;
}

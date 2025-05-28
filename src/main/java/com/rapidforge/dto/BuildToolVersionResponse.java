package com.rapidforge.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildToolVersionResponse {
    private String buildToolVerCode;
    private String buildToolsCode;
    private String buildVer;
    private String buildVersion;
    private Boolean isDefaultFrameVer;
    private String createdBy;
    private LocalDateTime createdAt;
}

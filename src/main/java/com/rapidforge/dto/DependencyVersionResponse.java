package com.rapidforge.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DependencyVersionResponse {
    private String dependVerCode;
    private String dependCode;
    private String frameVerCode;
    private String dependVer;
    private String dependVersion;
    private Boolean isDefaultDependVer;
    private String createdBy;
    private LocalDateTime createdAt;
}

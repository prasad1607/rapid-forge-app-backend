package com.rapidforge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrameworkVersionResponse {
    private String frameVerCode;
    private String frameVer;
    private String frameVersion;
    private Boolean isDefaultFrameVer;
    private LocalDateTime createdAt;
    private String createdBy;
}
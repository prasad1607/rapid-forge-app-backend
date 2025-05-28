package com.rapidforge.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LanguageVersionResponse {
    private String langVerCode;
    private String langVer;
    private String langVersion;
    private Boolean isDefaultLangVer;
    private LocalDateTime createdAt;
}

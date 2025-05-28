package com.rapidforge.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LanguageResponse {
    private Long id;
    private String langCode;
    private String langName;
    private String langExtension;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<LanguageVersionResponse> versions;
}

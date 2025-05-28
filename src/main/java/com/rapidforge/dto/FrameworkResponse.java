package com.rapidforge.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameworkResponse {
    private Long id;
    private String frameCode;
    private String frameName;
    private String langCode;
    private Boolean isDefaultFrame;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<FrameworkVersionResponse> versions;
}

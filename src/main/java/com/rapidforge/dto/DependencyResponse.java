package com.rapidforge.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DependencyResponse {
    private String dependCode;
    private String dependName;
    private String groupId;
    private String artifactId;
    private String version;
    private String scope;
    private String dependCategory;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<DependencyVersionResponse> versions;
}

package com.rapidforge.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkVersionRequest {
    private String frameVerCode;
    private String frameCode;
    private String frameVer;
    private String frameVersion;
    private Boolean isDefaultFrameVer;
    private String createdBy;
}

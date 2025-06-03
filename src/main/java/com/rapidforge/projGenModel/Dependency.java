package com.rapidforge.projGenModel;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dependency {

    @NotBlank(message = "groupId is required")
    private String groupId;

    @NotBlank(message = "artifactId is required")
    private String artifactId;

    @NotBlank(message = "version is required")
    private String version;
}

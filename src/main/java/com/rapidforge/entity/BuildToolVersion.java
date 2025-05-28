package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"BUILD_TOOL_VERSIONS\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildToolVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"BUILD_TOOL_VER_CODE\"", nullable = false, unique = true)
    private String buildToolVerCode;

    @Column(name = "\"BUILD_TOOLS_CODE\"", nullable = false)
    private String buildToolsCode;

    @Column(name = "\"BUILDVER\"", nullable = false)
    private String buildVer;

    @Column(name = "\"BUILD_VERSION\"", nullable = false)
    private String buildVersion;

    @Column(name = "\"IS_DEFAULT_FRAME_VER\"")
    private Boolean isDefaultFrameVer = true;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"BUILD_TOOLS\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"BUILD_TOOLS_CODE\"", nullable = false, unique = true)
    private String buildToolsCode;

    @Column(name = "\"BUILD_TOOLS_NAME\"", nullable = false)
    private String buildToolsName;

    @Column(name = "\"BUILD_TOOLS_TYPE\"", nullable = false)
    private String buildToolsType;

    @Column(name = "\"LANG_CODE\"", nullable = false)
    private String langCode;

    @Column(name = "\"FRAME_CODE\"", nullable = false)
    private String frameCode;

    @Column(name = "\"IS_DEFAULT_FRAME\"")
    private Boolean isDefaultFrame = true;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"DEPENDENCIES\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"DEPEND_CODE\"", nullable = false, unique = true)
    private String dependCode;

    @Column(name = "\"LANG_CODE\"", nullable = false)
    private String langCode;

    @Column(name = "\"FRAME_CODE\"")
    private String frameCode;

    @Column(name = "\"FRAME_VER_CODE\"")
    private String frameVerCode;

    @Column(name = "\"CATEGORY\"")
    private String category;

    @Column(name = "\"COMPATIBLE_WITH\"")
    private String compatibleWith;

    @Column(name = "\"DEPEND_NAME\"", nullable = false)
    private String dependName;

    @Column(name = "\"GROUP_ID\"", nullable = false)
    private String groupId;

    @Column(name = "\"ARTIFACT_ID\"", nullable = false)
    private String artifactId;

    @Column(name = "\"VERSION\"", nullable = false)
    private String version;

    @Column(name = "\"SCOPE\"", nullable = false)
    private String scope;

    @Column(name = "\"DEPEND_CATEGORY\"", nullable = false)
    private String dependCategory;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

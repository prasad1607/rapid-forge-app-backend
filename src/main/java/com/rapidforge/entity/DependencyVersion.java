package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"DEPENDENCY_VERSIONS\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DependencyVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"DEPEND_VER_CODE\"", nullable = false, unique = true)
    private String dependVerCode;

    @Column(name = "\"DEPEND_CODE\"", nullable = false)
    private String dependCode;

    @Column(name = "\"FRAME_VER_CODE\"", nullable = false)
    private String frameVerCode;

    @Column(name = "\"DEPENDVER\"", nullable = false)
    private String dependVer;

    @Column(name = "\"DEPEND_VERSION\"", nullable = false)
    private String dependVersion;

    @Column(name = "\"IS_DEFAULT_DEPEND_VER\"")
    private Boolean isDefaultDependVer;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

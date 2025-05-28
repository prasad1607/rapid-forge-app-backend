package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"FRAMEWORK_VERSIONS\"")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"FRAME_VER_CODE\"", nullable = false, unique = true)
    private String frameVerCode;

    @Column(name = "\"FRAME_CODE\"", nullable = false)
    private String frameCode;

    @Column(name = "\"FRAMEVER\"", nullable = false)
    private String frameVer;

    @Column(name = "\"FRAME_VERSION\"", nullable = false)
    private String frameVersion;

    @Column(name = "\"IS_DEFAULT_FRAME_VER\"")
    private Boolean isDefaultFrameVer = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"FRAME_CODE\"", referencedColumnName = "\"FRAME_CODE\"", insertable = false, updatable = false)
    private Framework framework;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"LANGUAGE_VERSIONS\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="\"ID\"")
    private Long id;

    @Column(name = "\"LANG_VER_CODE\"", nullable = false, unique = true)
    private String langVerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"LANG_CODE\"", referencedColumnName = "\"LANG_CODE\"", insertable = false, updatable = false)
    private Language language;

    @Column(name = "\"LANG_CODE\"", nullable = false)
    private String langCode;

    @Column(name = "\"LANG_NAME\"", nullable = false)
    private String langName;

    @Column(name = "\"LANGVER\"", nullable = false)
    private String langVer;

    @Column(name = "\"LANG_VERSION\"", nullable = false)
    private String langVersion;

    @Column(name = "\"IS_DEFAULT_LANG_VER\"")
    private Boolean isDefaultLangVer;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt;
}

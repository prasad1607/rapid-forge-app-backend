package com.rapidforge.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "\"LANGUAGES\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="\"ID\"")
    private Long id;

    @Column(name = "\"LANG_CODE\"", nullable = false, unique = true)
    private String langCode;

    @Column(name = "\"LANG_NAME\"", nullable = false)
    private String langName;

    @Column(name = "\"LANG_EXTENSION\"", nullable = false)
    private String langExtension;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt;

    @Column(name = "\"USAGE_COUNT\"")
    private Integer usageCount = 0;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LanguageVersion> versions;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Framework> frameworks;

}


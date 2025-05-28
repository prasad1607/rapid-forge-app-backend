package com.rapidforge.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"FRAMEWORKS\"")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Framework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"FRAME_CODE\"", nullable = false, unique = true)
    private String frameCode;

    @Column(name = "\"FRAME_NAME\"", nullable = false)
    private String frameName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"LANG_CODE\"", referencedColumnName = "LANG_CODE", nullable = false)
    private Language language;

    @Column(name = "\"IS_DEFAULT_FRAME\"")
    private Boolean isDefaultFrame = true;

    @Column(name = "\"CREATED_BY\"", nullable = false)
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private LocalDateTime createdAt = LocalDateTime.now();
}

package com.rapidforge.projGenModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"FILE_TEMPLATE_PLACEHOLDERS\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileTemplatePlaceholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name =  "\"FILE_TEMPLATE_ID\"", unique = true,nullable = false)
    private String fileTemplateId;

    @Column(nullable = false, name = "\"PLACEHOLDER\"")
    private String placeholder;

    @Column(name = "\"DEFAULT_VALUE\"")
    private String defaultValue;
}
package com.rapidforge.projGenModel;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "\"FRAMEWORKS\"")
@Data
public class Framework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"FRAME_CODE\"", unique = true)
    private String frameCode;

    @Column(name = "\"FRAME_NAME\"")
    private String frameName;

    @Column(name = "\"LANG_CODE\"")
    private String langCode;

    @Column(name = "\"IS_DEFAULT_FRAME\"")
    private Boolean isDefaultFrame = true;

    @Column(name = "\"CREATED_BY\"")
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private Timestamp createdAt;
}
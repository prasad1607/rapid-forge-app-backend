package com.rapidforge.projGenModel;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "\"FILE_TEMPLATE\"")
@Data
public class FileTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"FILE_TEMPLATE_ID\"", unique = true)
    private String fileTemplateId;

    @Column(name = "\"FILE_VERSION\"")
    private String fileVersion;

    @Column(name = "\"FILE_NAME\"")
    private String fileName;

    @Column(name = "\"FILE_CONTENT\"")
    private String fileContent;

    @Column(name = "\"FRAME_CODE\"")
    private String frameCode;

    @Column(name = "\"CREATED_BY\"")
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private Timestamp createdAt;

}
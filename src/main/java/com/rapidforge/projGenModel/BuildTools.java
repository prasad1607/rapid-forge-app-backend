package com.rapidforge.projGenModel;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "\"BUILD_TOOLS\"")
@Data
public class BuildTools {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"BUILD_TOOLS_CODE\"", unique = true)
    private String buildToolsCode;

    @Column(name = "\"BUILD_TOOLS_NAME\"")
    private String buildToolsName;

    @Column(name = "\"BUILD_TOOLS_TYPE\"")
    private String buildToolsType;

    @Column(name = "\"LANG_CODE\"")
    private String langCode;

    @Column(name = "\"FRAME_CODE\"")
    private String frameCode;

    @Column(name = "\"IS_DEFAULT_FRAME\"")
    private Boolean isDefaultFrame = true;

    @Column(name = "\"CREATED_BY\"")
    private String createdBy;

    @Column(name = "\"CREATED_AT\"")
    private Timestamp createdAt;
}
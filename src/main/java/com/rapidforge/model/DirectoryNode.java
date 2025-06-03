package com.rapidforge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"DIRECTORY_NODES\"")
@Data
public class DirectoryNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"FRAME_CODE\"", referencedColumnName = "\"FRAME_CODE\"")
    private Framework framework;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"BUILD_TOOLS_CODE\"", referencedColumnName = "\"FRAME_CODE\"")
    private BuildTools buildTools;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PARENT_ID\"")
    private DirectoryNode parent;

    @Column(name = "\"NAME\"")
    private String name;

    @Column(name = "\"TYPE\"")
    private String type; // 'file' or 'directory'

    @Column(name = "\"IS_DYNAMIC\"")
    private Boolean isDynamic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"FILE_TEMPLATE_ID\"", referencedColumnName = "\"FILE_TEMPLATE_ID\"")
    private FileTemplate fileTemplate;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Use lazy loading for children
    private List<DirectoryNode> children = new ArrayList<>();
}


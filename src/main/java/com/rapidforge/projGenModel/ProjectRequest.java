package com.rapidforge.projGenModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRequest {
    private String projectName;
    private String groupId;
    private String artifactId;
    private String version;
    private String description;
    private String author;

    private Language language;
    private FrameworkMeta framework;
    private String projectType;
    private BuildTool buildTool;
    private String packaging;
    private String apiType;
    private Database database;
    private List<Dependency> dependencies;
    private Authentication authentication;
    private TestFramework testFramework;
    private LinterTool linterTool;
    private DocumentationTool documentationTool;
    private Boolean dockerSupport;
    private CiCdTool ciCdTool;
    private Boolean envSupport;
    private FileStorage fileStorage;
    private LoggingFramework loggingFramework;
    private String license;
    private String theme;
    private String createdAt;

    @Data public static class Language { private String name; private String version; }
    @Data public static class FrameworkMeta { private String name; private String version; }
    @Data public static class BuildTool { private String name; private String version; }
    @Data public static class Database { private String name; private String version; }
    @Data public static class Authentication { private String type; private boolean enabled; }
    @Data public static class TestFramework { private String name; private String version; }
    @Data public static class LinterTool { private String name; private String version; }
    @Data public static class DocumentationTool { private String name; private String version; }
    @Data public static class CiCdTool { private String name; private String version; }
    @Data public static class FileStorage { private String type; }
    @Data public static class LoggingFramework { private String name; private String version; }
}

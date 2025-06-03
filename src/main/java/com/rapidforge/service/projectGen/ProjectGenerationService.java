package com.rapidforge.service.projectGen;

import com.rapidforge.exception.ResourceNotFoundException;
import com.rapidforge.model.*;
import com.rapidforge.repository.projectGen.BuildRepository;
import com.rapidforge.repository.projectGen.FileTemplatePlaceholderRepository;
import com.rapidforge.repository.projectGen.FrameworkProjectGenRepository;
import com.rapidforge.util.ZipUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectGenerationService {

    @Autowired
    private FileTemplatePlaceholderRepository fileTemplatePlaceholderRepository;

    @Autowired
    private ZipUtil zipUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FrameworkProjectGenRepository frameworkProjectGenRepository;
    private  final BuildRepository buildRepo;
    private final DirectoryTreeBuilder directoryTreeBuilder;
    private final FileSystemWriter fileSystemWriter;

    public File generateProject(ProjectRequest request) throws IOException {
        log.info("Starting project generation for: {}", request.getProjectName());

        Framework framework = frameworkProjectGenRepository.findByframeName(request.getFramework().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Framework not found: " + request.getFramework().getName()));

        BuildTools buildTools = buildRepo.findByBuildName(request.getBuildTool().getName())
                .orElseThrow(() -> new ResourceNotFoundException("build not found: " + request.getBuildTool().getName()));
        DirectoryNode rootNode = directoryTreeBuilder.buildTree(framework,buildTools);

        Map<String, Object> placeholders = buildPlaceholderMap(request, framework.getFrameCode(), buildTools.getBuildToolsCode());


        String outputPath = Files.createTempDirectory("generated-project").toString();
        fileSystemWriter.writeToFileSystem(rootNode, placeholders, outputPath);

        File zipFile = new File(outputPath + ".zip");
        ZipUtil.zipDirectory(new File(outputPath), zipFile);
        log.info("Project successfully generated at: {}", outputPath);
        return zipFile;
    }

    private Map<String, Object> buildPlaceholderMap(ProjectRequest request, String frameCode, String buildToolCode) {
        Map<String, Object> placeholders = new HashMap<>();

        //Load defaults from DB
        List<FileTemplatePlaceholder> dbPlaceholders = fileTemplatePlaceholderRepository.findAllByFrameworkId(frameCode, buildToolCode);
        Map<String, String> defaultMap = dbPlaceholders.stream()
                .collect(Collectors.toMap(
                        FileTemplatePlaceholder::getPlaceholder,
                        FileTemplatePlaceholder::getDefaultValue,
                        (existing, replacement) -> existing
                ));

        // Step 2: Reflect fields
        Field[] fields = ProjectRequest.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                String key = field.getName();

                if (value == null) {
                    // Try fallback from DB if available
                    if (defaultMap.containsKey(key)) {
                        placeholders.put(key, defaultMap.get(key));
                    } else {
                        log.warn("No value or default found for placeholder: {}", key);
                    }
                    continue;
                }

                if (value instanceof String || value instanceof Number || value instanceof Boolean) {
                    placeholders.put(key, value);
                } else if (value instanceof List<?>) {
                    placeholders.put(key, value); // keep as-is (e.g., List<Dependency>)
                } else {
                    // Convert complex objects like Language, FrameworkMeta, etc. to maps
                    Map<String, Object> converted = objectMapper.convertValue(value, new TypeReference<>() {});
                    placeholders.put(key, converted);
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        }

        return placeholders;
    }

}
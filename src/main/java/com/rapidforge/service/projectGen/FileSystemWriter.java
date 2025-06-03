package com.rapidforge.service.projectGen;

import com.rapidforge.projGenModel.DirectoryNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSystemWriter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([\\w.]+)(?::([^}]+))?}}");

    private static final Pattern EACH_BLOCK_PATTERN = Pattern.compile("\\{\\{#each (\\w+)}}(.*?)\\{\\{/each}}", Pattern.DOTALL);

    public void writeToFileSystem(DirectoryNode rootNode, Map<String, Object> placeholderValues, String basePath) {
        writeNode(rootNode, placeholderValues, basePath, new HashSet<>());
    }


    private void writeNode(DirectoryNode node, Map<String, Object> placeholders, String currentPath, Set<Long> visitedIds) {
        try {
            if (node.getId() != null && !visitedIds.add(node.getId())) {
                log.warn("Detected and skipped cyclic node: {}", node.getName());
                return;
            }
            // Replace placeholders in directory or file name
            String nodeName = replaceNodeNamePlaceholders(node.getName(), placeholders);
            File fileOrDir = new File(currentPath, nodeName);

            if ("directory".equalsIgnoreCase(node.getType())) {
                if (!fileOrDir.exists() && !fileOrDir.mkdirs()) {
                    throw new IOException("Failed to create directory: " + fileOrDir.getAbsolutePath());
                }
            } else if ("file".equalsIgnoreCase(node.getType())) {
                if (!fileOrDir.exists() && !fileOrDir.createNewFile()) {
                    throw new IOException("Failed to create file: " + fileOrDir.getAbsolutePath());
                }

                if (node.getFileTemplate() != null) {
                    String contentTemplate = node.getFileTemplate().getFileContent();
                    if (contentTemplate != null) {
                        String renderedContent = renderTemplate(contentTemplate, placeholders);
                        try (FileWriter writer = new FileWriter(fileOrDir)) {
                            writer.write(renderedContent);
                        }
                    }
                }
            }

            // Recurse into children
            for (DirectoryNode child : node.getChildren()) {
                writeNode(child, placeholders, fileOrDir.getAbsolutePath(), visitedIds);
            }

        } catch (IOException e) {
            log.error("Error writing node {} to filesystem", node.getName(), e);
            throw new RuntimeException("Failed to write project structure: " + e.getMessage());
        }
    }

    private String replaceNodeNamePlaceholders(String nodeName, Map<String, Object> placeholders) {
        if (nodeName == null) return null;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(nodeName);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String defaultVal = matcher.group(2);
            Object replacement = placeholders.getOrDefault(key, defaultVal != null ? defaultVal : key);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    private String renderTemplate(String template, Map<String, Object> values) {
        // First handle loops
        template = processEachBlocks(template, values);
        // Then handle regular placeholders
        String rendered = replacePlaceholders(template, values);

        // Unescape \n, \t, etc.
        return unescapeJavaEscapes(rendered);
    }

    private String processEachBlocks(String template, Map<String, Object> values) {
        Matcher matcher = EACH_BLOCK_PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String listKey = matcher.group(1);
            String blockContent = matcher.group(2);
            List<Object> listObj = (List<Object>) values.get(listKey);

            StringBuilder rendered = new StringBuilder();
            if (listObj instanceof List<?>) {
                for (Object item : (List<?>) listObj) {
                    Map<String, Object> itemMap = convertObjectToMap(item);
                    if (itemMap != null) {
                        rendered.append(renderTemplate(blockContent, itemMap));
                    } else {
                        rendered.append(blockContent.replace("{{this}}", item.toString()));
                    }
                }
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(rendered.toString()));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    /*private String replacePlaceholders(String input, Map<String, Object> values) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String defaultValue = matcher.group(2);
            Object value = values.getOrDefault(key, defaultValue != null ? defaultValue : "");
            matcher.appendReplacement(result, Matcher.quoteReplacement(value.toString()));
        }

        matcher.appendTail(result);
        return result.toString();
    }*/
    private String replacePlaceholders(String input, Map<String, Object> values) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);             // e.g. framework.name
            String defaultValue = matcher.group(2);    // optional default
            Object value = resolveNestedValue(values, key);
            String replacement = value != null ? value.toString() : (defaultValue != null ? defaultValue : "");
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }
    private Object resolveNestedValue(Map<String, Object> map, String key) {
        String[] parts = key.split("\\.");
        Object current = map;

        for (String part : parts) {
            if (current instanceof Map<?, ?>) {
                current = ((Map<?, ?>) current).get(part);
            } else {
                return null;
            }
            if (current == null) return null;
        }

        return current;
    }


    private String unescapeJavaEscapes(String input) {
        return input
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\'", "'")
                .replace("\\\\", "\\");
    }
    private Map<String, Object> convertObjectToMap(Object obj) {
        try {
            return objectMapper.convertValue(obj, new TypeReference<>() {});
        } catch (IllegalArgumentException e) {
            log.warn("Failed to convert object to map: {}", obj.getClass().getSimpleName(), e);
            return null;
        }
    }


}

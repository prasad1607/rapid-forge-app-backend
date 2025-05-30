package com.rapidforge.controller;

import com.rapidforge.service.DependencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dependencies")
public class DependencyController {

    private static final Logger logger = LoggerFactory.getLogger(DependencyController.class);

    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDependencies(
            @RequestParam String langCode,
            @RequestParam String frameCode,
            @RequestParam(required = false) String buildToolsName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        logger.info("Fetching dependencies with langCode={}, frameCode={}, buildToolsName={}, page={}, size={}",
                langCode, frameCode, buildToolsName, page, size);
        Map<String, Object> result = dependencyService.getDependencies(
                langCode, frameCode, buildToolsName, page, size
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/scan")
    public ResponseEntity<String> scanPomDirectory(@RequestParam String path) {
        if (path == null || path.trim().isEmpty()) {
            logger.warn("Scan request with empty path");
            return ResponseEntity.badRequest().body("Path must not be empty");
        }
        logger.info("Received request to scan directory: {}", path);
        dependencyService.scanPomDirectory(path);
        return ResponseEntity.ok("Scan completed for: " + path);
    }
}

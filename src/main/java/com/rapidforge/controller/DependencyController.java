package com.rapidforge.controller;

import com.rapidforge.service.DependencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/dependencies")
public class DependencyController {

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
        Map<String, Object> result = dependencyService.getDependencies(
                langCode, frameCode, buildToolsName, page, size
        );
        return ResponseEntity.ok(result);
    }

}



package com.rapidforge.controller;

import com.rapidforge.service.FrameworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/frameworks")
public class FrameworkController {

    private final FrameworkService frameworkService;

    public FrameworkController(FrameworkService frameworkService) {
        this.frameworkService = frameworkService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFrameworksWithVersions(
            @RequestParam String langCode,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String versionSortBy,
            @RequestParam(defaultValue = "desc") String versionSortOrder) {

        Map<String, Object> response = frameworkService.getFrameworksWithVersionsByLangCode(
                langCode, page, size, versionSortBy, versionSortOrder);
        return ResponseEntity.ok(response);
    }

}

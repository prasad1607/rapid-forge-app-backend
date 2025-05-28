package com.rapidforge.controller;

import com.rapidforge.dto.LanguageRequest;
import com.rapidforge.dto.LanguageResponse;
import com.rapidforge.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> fetchLanguages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String langName) {

        logger.info("Received request to fetch languages. Page: {}, Search term: '{}'", page, langName);

        Map<String, Object> response = languageService.getPaginatedLanguagesWithVersions(langName, page);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/incrementUsage")
    public ResponseEntity<Void> incrementUsage(@PathVariable Long id) {
        logger.info("Incrementing usage count for language with ID: {}", id);
        languageService.incrementUsageCount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<LanguageResponse> addLanguage(@RequestBody LanguageRequest request) {
        logger.info("Received request to add language: {}", request.getLangName());
        LanguageResponse response = languageService.addLanguage(request);
        return ResponseEntity.ok(response);
    }
}


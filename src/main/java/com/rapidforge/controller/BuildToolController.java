package com.rapidforge.controller;

import com.rapidforge.dto.BuildToolResponse;
import com.rapidforge.service.BuildToolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/build-tools")
public class BuildToolController {

    private final BuildToolService buildToolService;

    public BuildToolController(BuildToolService buildToolService) {
        this.buildToolService = buildToolService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBuildTools(
            @RequestParam String langCode,
            @RequestParam String frameCode,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BuildToolResponse> toolPage = buildToolService.getBuildToolsWithVersions(langCode, frameCode, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", toolPage.getContent());
        response.put("page", toolPage.getNumber() + 1);
        response.put("size", toolPage.getSize());
        response.put("totalElements", toolPage.getTotalElements());
        response.put("totalPages", toolPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}


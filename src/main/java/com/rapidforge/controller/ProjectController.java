package com.rapidforge.controller;

import com.rapidforge.projGenModel.ProjectRequest;
import com.rapidforge.service.projectGen.ProjectGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectGenerationService projectGenerationService;
//    @Autowired
//    private final MistralService mistralService;



    @PostMapping("/generateProject")
    public ResponseEntity<FileSystemResource> generateProject(@RequestBody ProjectRequest request) throws Exception {
        File zipFile = projectGenerationService.generateProject(request);
        FileSystemResource resource = new FileSystemResource(zipFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


//    @PostMapping("/home")
//    public ResponseEntity<String> getHomeResponse(@RequestBody PromptRequest request) {
//        String promptBody = request.getPrompt();
//        if (promptBody == null || promptBody.isBlank()) {
//            return ResponseEntity.badRequest().body("Prompt must not be empty.");
//        }
//        log.info("Prompt Given : {}", promptBody);
//        String aiResponse = mistralService.sendPrompt(promptBody);
//        return ResponseEntity.ok(aiResponse);
//    }


}

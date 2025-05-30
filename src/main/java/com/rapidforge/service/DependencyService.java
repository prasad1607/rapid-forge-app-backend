package com.rapidforge.service;

import com.rapidforge.dto.DependencyResponse;
import com.rapidforge.dto.DependencyVersionResponse;
import com.rapidforge.entity.Dependency;
import com.rapidforge.entity.DependencyVersion;
import com.rapidforge.repository.DependencyRepository;
import com.rapidforge.repository.DependencyVersionRepository;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.*;

@Service
public class DependencyService {

    private static final Logger logger = LoggerFactory.getLogger(DependencyService.class);

    private final DependencyRepository dependencyRepository;
    private final DependencyVersionRepository dependencyVersionRepository;

    public DependencyService(DependencyRepository dependencyRepository,
                             DependencyVersionRepository dependencyVersionRepository) {
        this.dependencyRepository = dependencyRepository;
        this.dependencyVersionRepository = dependencyVersionRepository;
    }

    public Map<String, Object> getDependencies(String langCode, String frameCode, String buildToolsName, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Dependency> dependencyPage = dependencyRepository.findByFilters(
                langCode, frameCode, buildToolsName, pageable
        );

        List<DependencyResponse> responses = dependencyPage.getContent().stream()
                .map(dep -> {
                    List<DependencyVersion> versions = dependencyVersionRepository
                            .findByDependCodeOrderByCreatedAtDesc(dep.getDependCode());

                    List<DependencyVersionResponse> versionResponses = versions.stream()
                            .map(ver -> new DependencyVersionResponse(
                                    ver.getDependVerCode(),
                                    ver.getDependCode(),
                                    ver.getFrameVerCode(),
                                    ver.getDependVer(),
                                    ver.getDependVersion(),
                                    ver.getIsDefaultDependVer(),
                                    ver.getCreatedBy(),
                                    ver.getCreatedAt()
                            )).toList();

                    return DependencyResponse.builder()
                            .dependCode(dep.getDependCode())
                            .dependName(dep.getDependName())
                            .groupId(dep.getGroupId())
                            .artifactId(dep.getArtifactId())
                            .version(dep.getVersion())
                            .scope(dep.getScope())
                            .dependCategory(dep.getDependCategory())
                            .createdBy(dep.getCreatedBy())
                            .createdAt(dep.getCreatedAt())
                            .versions(versionResponses)
                            .build();
                })
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("content", responses);
        result.put("page", page);
        result.put("size", size);
        result.put("totalElements", dependencyPage.getTotalElements());
        result.put("totalPages", dependencyPage.getTotalPages());

        return result;
    }

    public void scanPomDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("Invalid directory: {}", directoryPath);
            return;
        }

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            logger.warn("Directory is empty or inaccessible: {}", directoryPath);
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanPomDirectory(file.getAbsolutePath());
            } else if (file.getName().equalsIgnoreCase("pom.xml")) {
                processPomFile(file);
            }
        }
    }

    private void processPomFile(File pomFile) {
        try (FileReader reader = new FileReader(pomFile)) {
            MavenXpp3Reader mavenReader = new MavenXpp3Reader();
            Model model = mavenReader.read(reader);

            List<org.apache.maven.model.Dependency> dependencies = model.getDependencies();

            if (dependencies == null || dependencies.isEmpty()) {
                logger.info("No dependencies found in {}", pomFile.getAbsolutePath());
                return;
            }

            for (org.apache.maven.model.Dependency dep : dependencies) {
                if (dep == null) continue;

                String groupId = dep.getGroupId();
                String artifactId = dep.getArtifactId();
                if (groupId == null || artifactId == null) {
                    logger.warn("Skipping dependency with null groupId or artifactId in {}", pomFile.getAbsolutePath());
                    continue;
                }

                String version = dep.getVersion() != null ? dep.getVersion() : "INHERITED";
                String scope = dep.getScope() != null ? dep.getScope() : "compile";

                String dependCode = groupId + ":" + artifactId + ":" + version + ":" + scope;

                boolean exists = dependencyRepository.existsByDependCode(dependCode);
                if (!exists) {
                    Dependency dependency = Dependency.builder()
                            .dependCode(dependCode)
                            .dependName(artifactId)
                            .groupId(groupId)
                            .artifactId(artifactId)
                            .version(version)
                            .scope(scope)
                            .dependCategory("dependency")
                            .langCode("en")
                            .frameCode("")
                            .category("library")
                            .compatibleWith("Java")
                            .createdBy("admin")
                            .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                            .build();

                    dependencyRepository.save(dependency);
                    logger.info("Saved dependency: {}", dependCode);
                } else {
                    logger.debug("Skipped existing dependency: {}", dependCode);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to process POM file: {}", pomFile.getAbsolutePath(), e);
        }
    }
}

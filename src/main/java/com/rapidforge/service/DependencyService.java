package com.rapidforge.service;

import com.rapidforge.dto.DependencyResponse;
import com.rapidforge.dto.DependencyVersionResponse;
import com.rapidforge.entity.Dependency;
import com.rapidforge.entity.DependencyVersion;
import com.rapidforge.repository.DependencyRepository;
import com.rapidforge.repository.DependencyVersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DependencyService {

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

}



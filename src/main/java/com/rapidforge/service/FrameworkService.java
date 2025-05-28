package com.rapidforge.service;

import com.rapidforge.dto.FrameworkResponse;
import com.rapidforge.dto.FrameworkVersionResponse;
import com.rapidforge.entity.Framework;
import com.rapidforge.entity.FrameworkVersion;
import com.rapidforge.repository.FrameworkRepository;
import com.rapidforge.repository.FrameworkVersionRepository;
import com.rapidforge.repository.LanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FrameworkService {

    private final FrameworkRepository frameworkRepository;
    private final FrameworkVersionRepository frameworkVersionRepository;
    private final LanguageRepository languageRepository;

    public FrameworkService(FrameworkRepository frameworkRepository,
                            FrameworkVersionRepository frameworkVersionRepository,
                            LanguageRepository languageRepository) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkVersionRepository = frameworkVersionRepository;
        this.languageRepository = languageRepository;
    }

    public Map<String, Object> getFrameworksWithVersionsByLangCode(
            String langCode, int page, int size,
            String versionSortBy, String versionSortOrder) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Framework> frameworkPage = frameworkRepository.findByLanguage_LangCode(langCode, pageable);

        List<FrameworkResponse> content = frameworkPage.getContent().stream().map(framework -> {
            List<FrameworkVersion> versions = frameworkVersionRepository
                    .findByFrameCodeOrderByCreatedAtDesc(framework.getFrameCode());

            // Apply sorting dynamically
            versions = sortVersions(versions, versionSortBy, versionSortOrder);

            // Limit to 10
            versions = versions.stream().limit(10).collect(Collectors.toList());

            List<FrameworkVersionResponse> versionResponses = versions.stream()
                    .map(version -> FrameworkVersionResponse.builder()
                            .frameVerCode(version.getFrameVerCode())
                            .frameVer(version.getFrameVer())
                            .frameVersion(version.getFrameVersion())
                            .isDefaultFrameVer(version.getIsDefaultFrameVer())
                            .createdBy(version.getCreatedBy())
                            .createdAt(version.getCreatedAt())
                            .build())
                    .collect(Collectors.toList());

            return FrameworkResponse.builder()
                    .id(framework.getId())
                    .frameCode(framework.getFrameCode())
                    .frameName(framework.getFrameName())
                    .langCode(framework.getLanguage().getLangCode())
                    .isDefaultFrame(framework.getIsDefaultFrame())
                    .createdBy(framework.getCreatedBy())
                    .createdAt(framework.getCreatedAt())
                    .versions(versionResponses)
                    .build();
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("page", page);
        result.put("size", size);
        result.put("totalElements", frameworkPage.getTotalElements());
        result.put("totalPages", frameworkPage.getTotalPages());

        return result;
    }

    private List<FrameworkVersion> sortVersions(List<FrameworkVersion> versions, String sortBy, String sortOrder) {
        Comparator<FrameworkVersion> comparator;

        switch (sortBy) {
            case "frameVer":
                comparator = Comparator.comparing(FrameworkVersion::getFrameVer, String.CASE_INSENSITIVE_ORDER);
                break;
            case "frameVersion":
                comparator = Comparator.comparing(FrameworkVersion::getFrameVersion, String.CASE_INSENSITIVE_ORDER);
                break;
            case "createdAt":
            default:
                comparator = Comparator.comparing(FrameworkVersion::getCreatedAt);
                break;
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        return versions.stream().sorted(comparator).collect(Collectors.toList());
    }

}

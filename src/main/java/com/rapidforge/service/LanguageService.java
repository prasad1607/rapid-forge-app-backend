package com.rapidforge.service;

import com.rapidforge.dto.LanguageRequest;
import com.rapidforge.dto.LanguageResponse;
import com.rapidforge.dto.LanguageVersionResponse;
import com.rapidforge.entity.Language;
import com.rapidforge.entity.LanguageVersion;
import com.rapidforge.repository.LanguageRepository;
import com.rapidforge.repository.LanguageVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private static final Logger logger = LoggerFactory.getLogger(LanguageService.class);

    private final LanguageRepository languageRepository;
    private final LanguageVersionRepository versionRepository;

    public LanguageService(LanguageRepository languageRepository,LanguageVersionRepository versionRepository) {
        this.languageRepository = languageRepository;
        this.versionRepository = versionRepository;
    }

    public Map<String, Object> getPaginatedLanguagesWithVersions(String langName, int page) {
        logger.info("Fetching languages with versions. Search: '{}', Page: {}", langName, page);
        int pageIndex = Math.max(page - 1, 0); // Adjust to 0-based index

        PageRequest pageable = PageRequest.of(pageIndex, 10);
        Page<Language> languagesPage;

        if (langName == null || langName.isEmpty()) {
            languagesPage = languageRepository.findAllByOrderByLangNameAsc(pageable);
        } else {
            languagesPage = languageRepository.findByLangNameIgnoreCaseOrderByLangNameAsc(langName, pageable);
        }

        List<LanguageResponse> languageResponses = languagesPage.stream().map(language -> {
            List<LanguageVersion> versions = versionRepository
                    .findByLanguage_LangCodeOrderByCreatedAtDesc(language.getLangCode());

            List<LanguageVersionResponse> versionDTOs = versions.stream().map(v -> new LanguageVersionResponse(
                    v.getLangVerCode(),
                    v.getLangVer(),
                    v.getLangVersion(),
                    v.getIsDefaultLangVer(),
                    v.getCreatedAt()
            )).collect(Collectors.toList());

            return new LanguageResponse(
                    language.getId(),
                    language.getLangCode(),
                    language.getLangName(),
                    language.getLangExtension(),
                    language.getCreatedBy(),
                    language.getCreatedAt(),
                    versionDTOs
            );
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", languageResponses);
        response.put("page", page); // Return the original 1-based page
        response.put("size", 10);
        response.put("totalElements", languagesPage.getTotalElements());
        response.put("totalPages", languagesPage.getTotalPages());

        return response;
    }


    public void incrementUsageCount(Long id) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found with id " + id));
        language.setUsageCount(language.getUsageCount() + 1);
        languageRepository.save(language);
    }

    public LanguageResponse addLanguage(LanguageRequest request) {
        logger.info("Adding new language: {}", request.getLangName());

        Language language = Language.builder()
                .langCode(request.getLangCode())
                .langName(request.getLangName())
                .langExtension(request.getLangExtension())
                .createdBy(request.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .usageCount(0)
                .build();

        Language saved = languageRepository.save(language);

        return LanguageResponse.builder()
                .id(saved.getId())
                .langCode(saved.getLangCode())
                .langName(saved.getLangName())
                .langExtension(saved.getLangExtension())
                .createdBy(saved.getCreatedBy())
                .createdAt(saved.getCreatedAt())
                .versions(List.of())
                .build();
    }

}

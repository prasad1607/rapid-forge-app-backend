package com.rapidforge.service;

import com.rapidforge.dto.BuildToolResponse;
import com.rapidforge.dto.BuildToolVersionResponse;
import com.rapidforge.entity.BuildTool;
import com.rapidforge.repository.BuildToolRepository;
import com.rapidforge.repository.BuildToolVersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildToolService {

    private final BuildToolRepository buildToolRepository;
    private final BuildToolVersionRepository buildToolVersionRepository;

    public BuildToolService(BuildToolRepository buildToolRepository, BuildToolVersionRepository buildToolVersionRepository) {
        this.buildToolRepository = buildToolRepository;
        this.buildToolVersionRepository = buildToolVersionRepository;
    }

    public Page<BuildToolResponse> getBuildToolsWithVersions(String langCode, String frameCode, Pageable pageable) {
        Page<BuildTool> buildTools = buildToolRepository.findByLangCodeAndFrameCode(langCode, frameCode, pageable);

        return buildTools.map(tool -> {
            List<BuildToolVersionResponse> versionResponses =
                    buildToolVersionRepository.findByBuildToolsCodeOrderByCreatedAtDesc(tool.getBuildToolsCode())
                            .stream()
                            .map(version -> BuildToolVersionResponse.builder()
                                    .buildToolVerCode(version.getBuildToolVerCode())
                                    .buildToolsCode(version.getBuildToolsCode())
                                    .buildVer(version.getBuildVer())
                                    .buildVersion(version.getBuildVersion())
                                    .isDefaultFrameVer(version.getIsDefaultFrameVer())
                                    .createdBy(version.getCreatedBy())
                                    .createdAt(version.getCreatedAt())
                                    .build())
                            .toList();

            return BuildToolResponse.builder()
                    .id(tool.getId())
                    .buildToolsCode(tool.getBuildToolsCode())
                    .buildToolsName(tool.getBuildToolsName())
                    .buildToolsType(tool.getBuildToolsType())
                    .langCode(tool.getLangCode())
                    .frameCode(tool.getFrameCode())
                    .isDefaultFrame(tool.getIsDefaultFrame())
                    .createdBy(tool.getCreatedBy())
                    .createdAt(tool.getCreatedAt())
                    .versions(versionResponses)
                    .build();
        });
    }

}


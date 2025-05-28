package com.rapidforge.repository;

import com.rapidforge.entity.BuildToolVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildToolVersionRepository extends JpaRepository<BuildToolVersion, Long> {
    List<BuildToolVersion> findByBuildToolsCodeOrderByCreatedAtDesc(String buildToolsCode);
}

package com.rapidforge.repository;

import com.rapidforge.entity.LanguageVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageVersionRepository extends JpaRepository<LanguageVersion, Long> {
    List<LanguageVersion> findByLanguage_LangCodeOrderByCreatedAtDesc(String langCode);
}
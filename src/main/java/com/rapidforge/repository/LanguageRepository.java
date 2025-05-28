package com.rapidforge.repository;

import com.rapidforge.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Page<Language> findAllByOrderByLangNameAsc(Pageable pageable);
    Page<Language> findByLangNameIgnoreCaseOrderByLangNameAsc(String langName, Pageable pageable);

}
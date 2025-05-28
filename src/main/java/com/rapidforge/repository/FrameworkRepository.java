package com.rapidforge.repository;

import com.rapidforge.entity.Framework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Long> {
    Page<Framework> findByLanguage_LangCode(String langCode, Pageable pageable);

}

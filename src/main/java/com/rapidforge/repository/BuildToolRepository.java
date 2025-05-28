package com.rapidforge.repository;

import com.rapidforge.entity.BuildTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildToolRepository extends JpaRepository<BuildTool, Long> {
    Page<BuildTool> findByLangCodeAndFrameCode(String langCode, String frameCode, Pageable pageable);
}

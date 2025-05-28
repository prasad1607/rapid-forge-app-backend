package com.rapidforge.repository;

import com.rapidforge.entity.Dependency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {

    @Query("""
    SELECT d FROM Dependency d
    WHERE d.langCode = :langCode
      AND d.frameCode = :frameCode
      AND (:buildToolsName IS NULL OR EXISTS (
        SELECT 1 FROM BuildTool bt
        WHERE bt.langCode = d.langCode
          AND bt.frameCode = d.frameCode
          AND bt.buildToolsName = :buildToolsName
    ))
""")
    Page<Dependency> findByFilters(
            @Param("langCode") String langCode,
            @Param("frameCode") String frameCode,
            @Param("buildToolsName") String buildToolsName,
            Pageable pageable
    );

}



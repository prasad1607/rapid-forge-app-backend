package com.rapidforge.repository;

import com.rapidforge.entity.DependencyVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependencyVersionRepository extends JpaRepository<DependencyVersion, Long> {
    List<DependencyVersion> findByDependCodeOrderByCreatedAtDesc(String dependCode);
}


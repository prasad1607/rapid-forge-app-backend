package com.rapidforge.repository;

import com.rapidforge.entity.FrameworkVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FrameworkVersionRepository extends JpaRepository<FrameworkVersion, Long> {
    List<FrameworkVersion> findByFrameCodeOrderByCreatedAtDesc(String frameCode);
}

package com.rapidforge.repository.projectGen;

import com.rapidforge.model.BuildTools;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildRepository extends JpaRepository<BuildTools, String> {
    Optional<BuildTools> findByBuildName(String name);
}

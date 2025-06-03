package com.rapidforge.repository.projectGen;

import com.rapidforge.projGenModel.BuildTools;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildRepository extends JpaRepository<BuildTools, String> {
    Optional<BuildTools> findByBuildToolsName(String name);
}

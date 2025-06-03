package com.rapidforge.repository.projectGen;

import com.rapidforge.model.Framework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrameworkProjectGenRepository extends JpaRepository<Framework, String> {
    Optional<Framework> findByframeName(String name);
}

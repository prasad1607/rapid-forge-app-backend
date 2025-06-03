package com.rapidforge.repository.projectGen;

import com.rapidforge.model.FileTemplatePlaceholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileTemplatePlaceholderRepository extends JpaRepository<FileTemplatePlaceholder, String> {

    @Query(value = """
    SELECT * FROM "FILE_TEMPLATE_PLACEHOLDERS" ftp
    WHERE ftp."FILE_TEMPLATE_ID" IN (
        SELECT "FILE_TEMPLATE_ID" FROM "DIRECTORY_NODES" WHERE "FRAME_CODE" = :frameCode AND "BUILD_TOOLS_CODE" = :buildToolCode
    )
""", nativeQuery = true)
    List<FileTemplatePlaceholder> findAllByFrameworkId(@Param("frameCode") String frameCode,
                                                       @Param("buildToolCode") String buildToolCode);
}
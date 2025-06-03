package com.rapidforge.repository.projectGen;

import com.rapidforge.model.DirectoryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectoryNodeRepository extends JpaRepository<DirectoryNode, String> {
    @Query("""
    SELECT n FROM DirectoryNode n
    LEFT JOIN FETCH n.children
    LEFT JOIN FETCH n.fileTemplate
    WHERE n.framework.frameCode = :frameCode
    AND n.buildTool.buildToolCode = :buildToolCode
""")
    List<DirectoryNode> fetchFullStructureByFramework(@Param("frameCode") String frameCode,
                                                      @Param("buildToolCode")String buildToolCode);


}

package com.rapidforge.repository.projectGen;

import com.rapidforge.projGenModel.DirectoryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectoryNodeRepository extends JpaRepository<DirectoryNode, String> {
//    @Query("""
//    SELECT n FROM DirectoryNode n
//    LEFT JOIN FETCH n.children
//    LEFT JOIN FETCH n.fileTemplate
//    WHERE n.framework.frameCode = :frameCode
//    AND n.buildTools.buildToolsCode = :buildToolCode
//""")
//    List<DirectoryNode> fetchFullStructureByFramework(@Param("frameCode") String frameCode,
//                                                      @Param("buildToolCode")String buildToolCode);

    @Query("""
    SELECT n FROM DirectoryNode n
    LEFT JOIN FETCH n.fileTemplate
    WHERE n.framework.frameCode = :frameCode
    AND n.buildTools.buildToolsCode = :buildToolCode
""")
    List<DirectoryNode> fetchFlatTree(@Param("frameCode") String frameCode,
                                      @Param("buildToolCode") String buildToolCode);



}


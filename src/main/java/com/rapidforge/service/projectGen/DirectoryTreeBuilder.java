package com.rapidforge.service.projectGen;

import com.rapidforge.projGenModel.BuildTools;
import com.rapidforge.projGenModel.DirectoryNode;
import com.rapidforge.projGenModel.Framework;
import com.rapidforge.repository.projectGen.DirectoryNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectoryTreeBuilder {

    private final DirectoryNodeRepository directoryNodeRepository;

//    public DirectoryNode buildTree(Framework framework, BuildTools buildTools) {
//        log.info("Building directory tree for framework: {}", framework.getFrameName());
//        List<DirectoryNode> nodes = directoryNodeRepository.fetchFullStructureByFramework(framework.getFrameCode(), buildTools.getBuildToolsCode());
//
//        if (nodes.isEmpty()) {
//            throw new IllegalStateException("No directory structure defined for framework: " + framework.getFrameName());
//        }
//
//        Map<Long, DirectoryNode> idToNode = nodes.stream()
//                .collect(Collectors.toMap(DirectoryNode::getId, node -> node));
//
//        DirectoryNode root = null;
//        for (DirectoryNode node : nodes) {
//            if (node.getParent() != null) {
//                DirectoryNode parent = idToNode.get(node.getParent().getId());
//                if (parent == null) {
//                    throw new IllegalStateException("Parent not found for node: " + node.getName());
//                }
//            } else {
//                root = node;
//            }
//        }
//
//        if (root == null) {
//            throw new IllegalStateException("No root node found for framework: " + framework.getFrameName());
//        }
//
//        return root;
//    }
public DirectoryNode buildTree(Framework framework, BuildTools buildTools) {
    log.info("Building directory tree for framework: {}", framework.getFrameName());

    List<DirectoryNode> flatNodes = directoryNodeRepository.fetchFlatTree(
            framework.getFrameCode(),
            buildTools.getBuildToolsCode()
    );

    if (flatNodes.isEmpty()) {
        throw new IllegalStateException("No directory structure defined for framework: " + framework.getFrameName());
    }

    // Step 1: Map ID to node
    Map<Long, DirectoryNode> idToNode = new HashMap<>();
    DirectoryNode root = null;
    for (DirectoryNode node : flatNodes) {
        node.setChildren(new ArrayList<>()); // clear any leftover data
        idToNode.put(node.getId(), node);
    }

    // Step 2: Link parent-child relationships
    for (DirectoryNode node : flatNodes) {
        if (node.getParent() != null) {
            DirectoryNode parent = idToNode.get(node.getParent().getId());
            if (parent != null) {
                parent.getChildren().add(node);
            }
        } else {
            root = node;
        }
    }

    if (root == null) {
        throw new IllegalStateException("No root node found for framework: " + framework.getFrameName());
    }

    return root;
}

}
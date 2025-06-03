package com.rapidforge.service.projectGen;

import com.rapidforge.model.BuildTools;
import com.rapidforge.model.DirectoryNode;
import com.rapidforge.model.Framework;
import com.rapidforge.repository.projectGen.DirectoryNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectoryTreeBuilder {

    private final DirectoryNodeRepository directoryNodeRepository;

    public DirectoryNode buildTree(Framework framework, BuildTools buildTools) {
        log.info("Building directory tree for framework: {}", framework.getFrameName());
        List<DirectoryNode> nodes = directoryNodeRepository.fetchFullStructureByFramework(framework.getFrameCode(), buildTools.getBuildToolsCode());

        if (nodes.isEmpty()) {
            throw new IllegalStateException("No directory structure defined for framework: " + framework.getFrameName());
        }

        Map<Long, DirectoryNode> idToNode = nodes.stream()
                .collect(Collectors.toMap(DirectoryNode::getId, node -> node));

        DirectoryNode root = null;
        for (DirectoryNode node : nodes) {
            if (node.getParent() != null) {
                DirectoryNode parent = idToNode.get(node.getParent().getId());
                if (parent == null) {
                    throw new IllegalStateException("Parent not found for node: " + node.getName());
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
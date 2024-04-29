package dev.mega.megacore.command;

import java.util.LinkedList;
import java.util.List;

public class CommandHandler {
    public void handle(CommandHappen commandHappen) {
        Tree tree = getTreeOrDefault(commandHappen.command().getName());

        for (String arg : commandHappen.args()) {
            Branch branch = getBranchOrDefault(tree, arg);
            return;
        }

    }

    private Tree getTreeOrDefault(String treeName) {
        List<Tree> registeredTrees = TreeRegistor.getTrees();
        for (Tree tree : registeredTrees) {
            if (tree.name().equals(treeName)) {
                return tree;
            }
        }
        return new Tree(treeName, List.of());
    }

    private Branch getBranchOrDefault(Tree tree, String argName) {
        List<Branch> branches = tree.branches();
        for (Branch branch : branches) {
            if (branch.name().equals(argName)) {
                return branch;
            }
        }
        return new Branch(argName, new LinkedList<>());
    }
}

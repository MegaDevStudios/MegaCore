package dev.mega.megacore.command;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class TreeRegistor {
    private static TreeRegistor instance;
    @Getter private static List<Tree> trees = new LinkedList<>();

    private TreeRegistor() {}

    public static void treeRegistre(Tree tree) {
        trees.add(tree);
    }

    public static TreeRegistor instance() {
        if (instance == null) {
            instance = new TreeRegistor();
        }

        return instance;
    }
}

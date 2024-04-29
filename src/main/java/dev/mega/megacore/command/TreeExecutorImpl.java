package dev.mega.megacore.command;

import java.util.List;

public class TreeExecutorImpl implements TreeExecutor {

    @Override
    public void execute(Tree tree) {
        List<Branch> branches = tree.branches();
    }
}

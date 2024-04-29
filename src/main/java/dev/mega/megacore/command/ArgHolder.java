package dev.mega.megacore.command;

import java.util.ArrayList;
import java.util.List;

public class ArgHolder {
    List<Arg> args = new ArrayList<>();

    public void injectTree(List<Arg> args) {
        this.args = args;
    }
}

package dev.mega.megacore.command;

import dev.mega.megacore.command.matcher.ArgumentMatcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestArg extends Argument {
    public TestArg(ArgumentMatcher matcher) {
        super(matcher);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        System.out.println("TEST ARG");
        return false;
    }
}

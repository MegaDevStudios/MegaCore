package dev.mega.megacore.command;

import lombok.Getter;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public abstract class Argument implements CommandExecutor, TabCompleter {
    private final ArgumentMatcher matcher;
    private final List<Argument> subcommands = new ArrayList<>();

    public Argument(ArgumentMatcher matcher) {
        this.matcher = matcher;
    }

    public void addArgument(String label, Argument argument) {
        subcommands.put(label, argument);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO: check if command args does matches for it's types
        if (matcher.matches(args[0])) {
            return false;
        }
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>(subcommands.keySet());
    }
}

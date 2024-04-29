package dev.mega.megacore.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Argument implements CommandExecutor, TabCompleter {
    private final String label;
    private final Map<String, Argument> subcommands = new HashMap<>();

    public Argument(String label) {
        this.label = label;
    }

    public void addArgument(String label, Argument argument) {
        subcommands.put(label, argument);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(String.valueOf(subcommands.keySet()));
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>(subcommands.keySet());
    }
}

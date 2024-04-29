package dev.mega.megacore.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Argument {
    String getName();

    String getPermission();

    List<String> getTabCompleters();

    List<Argument> getArguments();

    void executor(CommandSender sender, String[] args);
}

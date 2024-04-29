package dev.mega.megacore.command;

import org.bukkit.command.*;

import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private final List<Argument> subCommands = new ArrayList<>();

    // /mega give Dreaght 1000
    // /mega give all 1000
    // /mega withdraw Dreaght 1
    // /mega balance Dreaght(optional)
    // /mega reload
    // /mega menu create 1 "Test title" 15
    // /mega menu remove 1

    // Argument1: [give, withdraw, balance, reload]
    // GiveArg: [String, all]
    // AllArg: [Integer]
    // BalanceArg: [Optional<String>]
    // ReloadArg: []
    // Hard <====OOO=========================> Soft
    //            ^ <-- maintain quality

    public static void registerCommand(JavaPlugin plugin, Argument command) {
        CommandManager commandManager = new CommandManager();
        PluginCommand pluginCommand = plugin.getCommand(command.getName());

        if (pluginCommand != null)
            pluginCommand.setExecutor(commandManager);

        for (Argument subCommand : command.getArguments())
            commandManager.getSubCommands().add(subCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) return false;

        for (Argument subCommand : subCommands) {

            if (args[0].equals(subCommand.getName())) {

                if (!sender.hasPermission(subCommand.getPermission())) {
                    return false;
                } else {
                    subCommand.executor(sender, args);
                }
                return true;
            }
        }
        return true;
    }

    public List<Argument> getSubCommands() {
        return subCommands;
    }
}

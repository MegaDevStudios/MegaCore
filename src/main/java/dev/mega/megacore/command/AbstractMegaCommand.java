package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractMegaCommand implements MegaCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String s, @NotNull String[] args) {

    }

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                               @NotNull String alias, @NotNull String[] args);

    public abstract void sendUsageMessage(CommandSender commandSender, String commandName, String message);

    public abstract void sendUsageMessage(Player player);

    public abstract String getPermission();
}

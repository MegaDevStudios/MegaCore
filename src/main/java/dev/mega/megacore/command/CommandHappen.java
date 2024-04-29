package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public record CommandHappen(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {}

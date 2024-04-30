package dev.mega.megacore;

import dev.mega.megacore.command.CommandManager;
import dev.mega.megacore.command.MegaCommand;
import dev.mega.megacore.command.matcher.StringArg;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public abstract class MegaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandManager.getCommands().forEach(I -> {
            Objects.requireNonNull(getCommand(I.getMatcher().getValue())).setExecutor(I);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

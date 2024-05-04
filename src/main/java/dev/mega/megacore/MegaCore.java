package dev.mega.megacore;

import dev.mega.megacore.command.CommandManager;
import dev.mega.megacore.command.MegaCommand;
import dev.mega.megacore.command.matcher.StringArg;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public abstract class MegaCore extends JavaPlugin {
    @Getter
    private static MegaCore instance;

    @Override
    public void onEnable() {
        instance = this;

        CommandManager.getCommands().forEach(I -> {
            Objects.requireNonNull(getCommand(I.getMatcher().getValue())).setExecutor(I);
        });

        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public abstract void enable();

    public abstract void disable();
}

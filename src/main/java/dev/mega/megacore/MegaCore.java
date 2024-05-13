package dev.mega.megacore;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MegaCore extends JavaPlugin {
    @Getter
    private static MegaCore instance;

    @Override
    public void onEnable() {
        instance = this;

        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public abstract void enable();

    public abstract void disable();
}

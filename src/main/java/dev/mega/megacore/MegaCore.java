package dev.mega.megacore;

import dev.mega.megacore.manager.Reloadable;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MegaCore extends JavaPlugin implements Reloadable {

    @Override
    public void onLoad() {
        this.getLogger().info("Initializing MegaCore plugin.");
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }
}

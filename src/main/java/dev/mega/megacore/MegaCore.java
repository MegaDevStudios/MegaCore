package dev.mega.megacore;

import dev.mega.megacore.config.AbstractManager;
import dev.mega.megacore.config.Configurator;
import dev.mega.megacore.manager.Reloadable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MegaCore extends JavaPlugin implements Reloadable {

    private final AbstractManager configManager;

    protected MegaCore(AbstractManager configManager) {
        this.configManager = configManager;
    }

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
        saveConfigs();
    }

    private void saveConfigs() {
        if (configManager != null) {
            Set<Configurator> configs = configManager.getAllConfigs();

            for (Configurator configurator : configs) {
                HashMap<String, Object> data = configurator.getData();

                for (Map.Entry<String, Object> line : data.entrySet()) {
                    configurator.setConfigValue(line.getKey(), line.getValue());
                }
            }
        }
    }
}

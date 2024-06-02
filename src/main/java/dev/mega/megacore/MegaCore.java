package dev.mega.megacore;

import dev.mega.megacore.config.SubFolder;
import dev.mega.megacore.config.Configurator;
import dev.mega.megacore.manager.MegaManager;
import dev.mega.megacore.manager.Reloadable;
import dev.mega.megacore.util.MegaCoreUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MegaCore extends JavaPlugin implements Reloadable {

    private final SubFolder configManager;
    private final String managersPath;

    protected MegaCore(Class<? extends SubFolder> configManager, String managersPath) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MegaCoreUtil.getLogger().info(
                Arrays.toString(configManager.getMethods())
        );

        this.configManager = (SubFolder) configManager.getMethod("init", MegaCore.class).invoke(this, this);
        this.managersPath = managersPath;
    }

    @Override
    public void onLoad() {
        this.getLogger().info("Initializing MegaCore plugin.");
    }

    @Override
    public void onEnable() {
        enable();
        MegaManager.init(this, managersPath);
        MegaManager.getInstance().enable();
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

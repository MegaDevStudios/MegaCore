package dev.mega.megacore.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Manager to contain Config objects
 */
public abstract class Manager implements Config {
    @Getter private final Plugin plugin;
    @Getter private final String dataFolder;
    private final Map<Class<? extends Config>, Config> configMap = new HashMap<>();

    public Manager(Plugin plugin, String dataFolder) {
        this.plugin = plugin;
        this.dataFolder = dataFolder;
    }

    /**
     * Adds a Config object to map.
     * @param configClass Class to add.
     * @param config Config object to add.
     */
    public void addConfig(Class<? extends Config> configClass, Config config) {
        configMap.put(configClass, config);
    }

    /**
     * Gets a Config object by Class.
     * @param configClass Class of Config object.
     * @return The Config object if found, otherwise throws an exception.
     */
    public <T extends Config> T getConfig(Class<T> configClass) {
        T config = getConfigFromMap(configClass);
        if (config != null) return config;

        for (Config managerConfig : configMap.values()) {
            if (managerConfig instanceof Manager) {
                config = ((Manager) managerConfig).getConfig(configClass);
                if (config != null) return config;
            }
        }

        throw new IllegalArgumentException("No configuration found for class: " + configClass.getName());
    }

    /**
     * Gets the Manager object by Class.
     * @param managerClass Class of Manager object
     * @return The Config object if found, otherwise throws an exception.
     */
    public <V extends Config> V getManager(Class<V> managerClass) {
        for (Config managerConfig : configMap.values()) {
            if (managerConfig instanceof Manager) {
                return ((Manager) managerConfig).getManager(managerClass);
            }
        }

        throw new IllegalArgumentException("No manager found for class: " + managerClass.getName());
    }

    private <T extends Config> T getConfigFromMap(Class<T> configClass) {
        Config config = configMap.get(configClass);
        if (config instanceof Manager) return ((Manager) config).getConfig(configClass);
        else return configClass.cast(config);
    }
}


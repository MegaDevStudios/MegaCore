package dev.mega.megacore.config;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * Represents a Manager to contain Config objects
 */
@Getter
public abstract class SubFolder implements Config {
    private final Plugin plugin;
    private final String dataFolder;
    protected final Map<Class<? extends Config>, Config> configMap = new HashMap<>();

    public SubFolder(Plugin plugin, String dataFolder) {
        this.plugin = plugin;
        this.dataFolder = dataFolder;
    }

    /**
     * Clears the config map.
     */
    public void clearConfigs() {
        configMap.clear();
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
     * @param targetConfig Class of Config object.
     * @return The Config object if found, otherwise throws an exception.
     */
    public <T extends Config> T getConfig(Class<T> targetConfig) {
        Set<Class<?>> visitedClasses = new HashSet<>();

        Stack<Config> stack = new Stack<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            Config current = stack.pop();
            visitedClasses.add(current.getClass());

            if (targetConfig.isAssignableFrom(current.getClass())) {
                return targetConfig.cast(current);
            }

            else if (current instanceof SubFolder) {
                SubFolder manager = (SubFolder) current;
                for (Config config : manager.configMap.values()) {
                    stack.push(config);
                }
            }
        }

        throw new IllegalArgumentException(String.format("""
               No configuration found for class: %s!
               Ensure you have this class registered!
               
               All registered Config classes we found: %s
               """, targetConfig.getName(), visitedClasses));
    }

    public Set<Configurator> getAllConfigs() {
        Set<Configurator> allConfigs = new HashSet<>();
        Stack<Config> stack = new Stack<>();
        stack.push(this);

        while (!stack.isEmpty()) {
            Config current = stack.pop();

            if (current instanceof Configurator)
                allConfigs.add((Configurator) current);

            else if (current instanceof SubFolder) {
                SubFolder manager = (SubFolder) current;

                for (Config config : manager.configMap.values()) {
                    stack.push(config);
                }
            }
        }

        return allConfigs;
    }
}
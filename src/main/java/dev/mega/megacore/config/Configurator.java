package dev.mega.megacore.config;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Represents a configurable object that handles YAML configuration files.
 */
public abstract class Configurator implements Config {
    protected final Plugin plugin;
    @Getter
    protected FileConfiguration config;
    protected File configFile;

    @Getter private final HashMap<String, Object> data = new HashMap<>();

    /**
     * Constructs a Configurator object.
     *
     * @param plugin The plugin instance.
     * @param path   The path to the configuration file.
     */
    protected Configurator(@NotNull Plugin plugin, String... path) {
        this.plugin = plugin;
        initializeConfigFile(path);
        this.config = loadConfig();
    }

    /**
     * Gets the path of the configuration file.
     *
     * @return The path of the configuration file.
     */
    public String getPath() {
        return configFile != null ? configFile.getAbsolutePath() : "unknown";
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String path) {
        if (data.containsKey(path)) {
            return (T) data.get(path);
        } else {
            Object value = config.get(path);
            data.put(path, value);
            return (T) value;
        }
    }

    public void setValue(String path, Object value) {
        data.put(path, value);
    }

    public void setConfigValue(String path, Object value) {
        setValue(path, value);
        config.set(path, value);
        saveConfig();
    }

    /**
     * Deletes the configuration file.
     */
    protected void deleteConfig() {
        if (configFile != null && configFile.exists() && configFile.delete()) {
            plugin.getLogger().info("Deleted configuration file: " + configFile.getName());
        } else {
            plugin.getLogger().warning(
                    "Failed to delete configuration file: " + (configFile != null ? configFile.getName() : "unknown"));
        }
    }

    private void saveConfig() {
        if (configFile != null) {
            try {
                config.save(configFile);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to save configuration file: " + configFile.getName(), e);
            }
        }
    }

    private void createResource(String... path) {
        String filePath = String.join("/", path) + ".yml";
        File file = new File(plugin.getDataFolder(), filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                if (file.createNewFile()) {
                    plugin.getLogger().info("Created new configuration file: " + filePath);
                } else {
                    plugin.getLogger().warning("Failed to create new configuration file: " + filePath);
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Error while creating configuration file: " + filePath, e);
            }
        }
    }

    private void initializeConfigFile(String... path) {
        String filePath = String.join("/", path) + ".yml";
        configFile = new File(plugin.getDataFolder(), filePath);
        if (!configFile.exists()) {
            if (plugin.getResource(filePath) != null) {
                plugin.saveResource(filePath, false);
            } else {
                createResource(path);
            }
        }
    }

    public FileConfiguration loadConfig() {
        YamlConfiguration yamlConfig = new YamlConfiguration();
        if (configFile != null && configFile.exists()) {
            try {
                yamlConfig.load(configFile);
            } catch (IOException | InvalidConfigurationException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load configuration file: " + configFile.getName(), e);
            }
        } else {
            plugin.getLogger().warning("Configuration file does not exist: " + (configFile != null ? configFile.getName() : "unknown"));
        }
        return yamlConfig;
    }
}

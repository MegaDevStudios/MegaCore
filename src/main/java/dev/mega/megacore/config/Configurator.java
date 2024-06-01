package dev.mega.megacore.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Represents a configurable object that handles YAML configuration files.
 */
public abstract class Configurator implements Config {
    protected final Plugin plugin;
    protected FileConfiguration config;
    protected File configFile;

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

    /**
     * Retrieves a String value from the configuration.
     *
     * @param path The path to the value.
     * @return The string value, or empty string if not found.
     */
    public String getString(String path) {
        return config.getString(path, "");
    }

    /**
     * Retrieves a Boolean value from the configuration.
     *
     * @param path The path to the value.
     * @return The Boolean value, or false if not found.
     */
    public Boolean getBoolean(String path) {
        return config.getBoolean(path, false);
    }

    /**
     * Retrieves an Integer value from the configuration.
     *
     * @param path The path to the value.
     * @return The integer value, or zero if not found.
     */
    public Integer getInt(String path) {
        return config.getInt(path, 0);
    }

    /**
     * Retrieves a Double value from the configuration.
     *
     * @param path The path to the value.
     * @return The double value, or zero if not found.
     */
    public Double getDouble(String path) {
        return config.getDouble(path, 0);
    }

    /**
     * Retrieves an Object value from the configuration.
     *
     * @param path The path to the value.
     * @return The object value, or null if not found.
     */
    public Object getValue(String path) {
        return config.get(path);
    }

    /**
     * Returns a list of Strings from the configuration.
     *
     * @param path The path to the list.
     * @return The list of strings.
     */
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path  The path to the value.
     * @param value The value to set.
     */
    public void setValue(String path, Object value) {
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

    private FileConfiguration loadConfig() {
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

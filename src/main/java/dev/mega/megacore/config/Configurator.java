package dev.mega.megacore.config;

import dev.mega.megacore.util.Color;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Represents a configurable object that handles YAML configuration files.
 */
public abstract class Configurator implements Config {
    protected final Plugin plugin;
    @Getter
    protected FileConfiguration config;
    protected File configFile;

    /**
     * Constructs a Configurator object.
     *
     * @param plugin The plugin instance.
     * @param path   The path to the configuration file.
     */
    protected Configurator(Plugin plugin, String... path) {
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
     * Gets the value from path.
     * @param path Path to value.
     * @return Value.
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String path) {
        Object value = config.get(path);
        return (T) value;
    }

    /**
     * Gets the path of the configuration file with a default parameter.
     *
     * @param def default value you want returned
     * @return The path of the configuration file.
     */
    public <T> T getValue(String path, T def) {
        T value = getValue(path);
        return value == null ? def : value;
    }

    /**
     * Gets the colored text of the config file.
     */
    public String getColoredString(String path) {
        return getColoredString(path, "");
    }

    /**
     * Gets the colored text of the config file with a default value.
     * @param path Path to configuration value.
     * @param def Default value.
     * @return Value.
     */
    public String getColoredString(String path, String def) {
        return getColoredString(path, def, '&');
    }

    /**
     * Gets the colored text of the config file with a char symbol.
     * @param path Path to configuration value.
     * @param symbol Key for colored string.
     * @return Value.
     */
    public String getColoredString(String path, char symbol) {
        return getColoredString(path, "", symbol);
    }

    /**
     * Gets the colored text of the config file with default value and char symbol.
     * @param path Path to configuration value.
     * @param def Default value.
     * @param symbol Key for colored string.
     * @return Value.
     */
    public String getColoredString(String path, String def, char symbol) {
        return Color.getTranslated(getValue(path, def), symbol);
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

    /**
     * Saves all changes to file.
     */
    public void saveConfig() {
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

    /**
     * Loads the configuration.
     * @return Configuration.
     */
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

package dev.mega.megacore.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents a configurable object that handles YAML configuration files.
 */
public abstract class Configurable implements Config {
    protected Plugin plugin;
    protected FileConfiguration config;
    protected File configFile;
    protected File parentFolder;

    /**
     * Constructs a Configurable object.
     *
     * @param plugin The plugin instance.
     * @param path   The path to the configuration file.
     */
    protected Configurable(@NotNull Plugin plugin, String... path) {
        this.plugin = plugin;
        this.parentFolder = new File(plugin.getDataFolder(), String.join("/", path));
        saveResource(path);
        this.config = getConfig();
    }

    /**
     * Retrieves a String value from the configuration.
     *
     * @param path The path to the value.
     * @return The String value.
     */
    public String getString(String path) {
        return getConfig().getString(path);
    }

    /**
     * Retrieves an Object value from the configuration.
     *
     * @param path The path to the value.
     * @return The Object value.
     */
    public Object getValue(String path) {
        return getConfig().get(path);
    }

    /**
     * Returns a list of Strings from the configuration.
     *
     * @param path The path to the list.
     * @return The list of Strings.
     */
    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path  The path to the value.
     * @param value The value to set.
     */
    public void setValue(String path, Object value) {
        getConfig().set(path, value);
        saveConfig();
    }

    /**
     * Deletes the configuration file.
     */
    protected void deleteConfig() {
        if (configFile.exists()) {
            configFile.delete();
            plugin.getLogger().info("Deleted configuration file: " + configFile.getName());
        }
    }

    private void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save configuration file: " + configFile.getName());
        }
    }

    private void saveResource(String... path) {
        String filePath = String.join("/", path) + ".yml";
        File file = new File(plugin.getDataFolder(), filePath);
        if (!file.exists()) {
            plugin.saveResource(filePath, true);
        }
        this.configFile = file;
    }

    private FileConfiguration getConfig() {
        plugin.getConfig().options().copyDefaults(true);
        YamlConfiguration yamlConfig = new YamlConfiguration();
        try {
            yamlConfig.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning("Failed to load configuration file: " + configFile.getName());
        }
        return yamlConfig;
    }
}

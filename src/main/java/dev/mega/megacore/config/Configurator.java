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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     */
    public String getColoredString(String path, String def) {
        return getColoredString(path, def, '&');
    }

    public String getColoredString(String path, char symbol) {
        return getColoredString(path, "", symbol);
    }

    public String getColoredString(String path, String def, char symbol) {
        return Color.getTranslated(getValue(path, def), symbol);
    }

    // Choice one method or implement yourself version
    // 1.
    public String getCompletedString(String path, Object... objects) {
        String string = getValue(path, "");

        String[] strings = string.split("%");
        String[] finalStrings = new String[strings.length];

        int index = 0;
        if (string.startsWith("%")) {
            for (int i = 0; i < strings.length; i++) {
                if (i % 2 == 1) {
                    finalStrings[i] = strings[i];
                } else {
                    finalStrings[i] = objects[index].toString();
                    index++;
                }
            }
        } else {
            for (int i = 0; i < strings.length; i++) {
                if (i % 2 == 0) {
                    finalStrings[i] = strings[i];
                } else {
                    finalStrings[i] = objects[index].toString();
                    index++;
                }
            }
        }

        return String.join("", finalStrings);
    }

    // 2.
    public String getCompletedStringV2(String path, Object... objects) {
        String configString = getValue(path, "");

        Pattern pattern = Pattern.compile("%(.*?)%");
        Matcher matcher = pattern.matcher(configString);

        StringBuffer result = new StringBuffer();

        int index = 0;
        while (matcher.find() && index < objects.length) {
            matcher.appendReplacement(result, objects[index].toString());
            index++;
        }

        matcher.appendTail(result);

        return result.toString();
    }

    public void setValue(String path, Object value) {
        config.set(path, value);
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

package dev.mega.megacore.config.serializer;

import dev.mega.megacore.config.Configurator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SerializeUtil {
    /**
     * Deserializes the object from configuration section path.
     * @param configurator Object of class Configurator.
     * @param path Config file path.
     * @param sectionPath Section path at config.
     * @return Map with String and Object.
     */
    public static Map<String, Object> deserialize(Configurator configurator, String path, String sectionPath) {
        Map<String, Object> data = new HashMap<>();
        File file = new File(path);
        FileConfiguration configuration = new YamlConfiguration();

        return new HashMap<>();
    }

    public static void serialize(Map<String, Object> data) {

    }
}

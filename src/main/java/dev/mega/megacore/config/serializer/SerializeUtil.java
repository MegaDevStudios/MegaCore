package dev.mega.megacore.config.serializer;

import com.google.common.collect.Maps;
import dev.mega.megacore.config.Configurator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SerializeUtil {
    /**
     * Deserializes the object from configuration section path.
     * @param configurator Object of class Configurator.
     * @param sectionPath Section path at config.
     * @return Map with String and Object.
     */
    public static Map<String, Object> deserialize(Configurator configurator, String sectionPath) {
        Map<String, Object> data = Maps.newConcurrentMap();

        ConfigurationSection section = configurator.getConfigurationSection(sectionPath);
        if (section == null) return data;

        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            data.put(key, section.get(key));
        }

        return new HashMap<>();
    }

    public static void serialize(Configurator configurator, ConfigurationSerializable serializable) {
        Map<String, Object> data = serializable.serialize();

        for (Map.Entry<String, Object> line : data.entrySet()) {
            String path = line.getKey();
            Object value = line.getValue();

            configurator.set(path, value);
        }
    }
}

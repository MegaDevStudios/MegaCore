package dev.mega.megacore.config.serializer;

import dev.mega.megacore.config.Configurator;
import java.util.HashMap;
import java.util.Map;

public class SerializeUtil {
    /**
     * Deserializes the object from configuration section path.
     * @param configurator Object of class Configurator.
     * @param sectionPath Section path at config.
     * @return Map with String and Object.
     */
    public static Map<String, Object> deserialize(Configurator configurator, String sectionPath) {
        Map<String, Object> data = new HashMap<>();

        return new HashMap<>();
    }

    public static void serialize(Map<String, Object> data) {

    }
}

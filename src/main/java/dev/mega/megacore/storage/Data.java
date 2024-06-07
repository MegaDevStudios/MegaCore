package dev.mega.megacore.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class represents a wrapper over HashMap.
 * @param <T> Object as HashMap value.
 */
public class Data<T> {
    private Map<UUID, T> data = new HashMap<>();

    public Data() {
    }

    public Data(Map<UUID, T> data) {
        this.data = data;
    }

    public Map<UUID, T> getData() {
        return data;
    }

    public T getValue(UUID key) {
        return data.get(key);
    }

    public boolean contains(UUID key) {
        return data.containsKey(key);
    }

    public void addValue(UUID key, T value) {
        data.put(key, value);
    }

    public void remove(UUID key) {
        data.remove(key);
    }

    public void clear() {
        data.clear();
    }
}

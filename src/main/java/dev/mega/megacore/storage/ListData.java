package dev.mega.megacore.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class represents a wrapper over HashMap but with List as Map value.
 * @param <T> Object inside List as Map value.
 */
public class ListData<T> extends Data<List<T>> {
    public boolean contains(T value) {
        for (List<T> values : getData().values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;
    }

    public T getRegistered(UUID uuid, T value) {
        if (contains(uuid)) {
            return getValue(uuid).stream()
                    .filter(value1 -> value1.equals(value))
                    .findAny()
                    .orElse(null);
        }

        return null;
    }

    public void addValueForUuid(UUID uuid, T value) {
        if (contains(uuid)) {
            getValue(uuid).add(value);
        } else {
            List<T> values = new ArrayList<>();
            values.add(value);

            getData().put(uuid, values);
        }
    }

    public boolean removeValueForUuid(UUID uuid, T value) {
        if (contains(uuid)) {
            return getValue(uuid).remove(value);
        }
        return false;
    }

    public boolean removeValue(T value) {
        for (UUID uuid : getData().keySet()) {
            if (removeValueForUuid(uuid, value)) {
                return true;
            }
        }
        return false;
    }
}

package dev.mega.megacore.storage;

import java.util.List;
import java.util.UUID;

public class ListData<T> extends Data<List<T>> {
    public boolean contains(T value) {
        for (List<T> values : getData().values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;
    }

    public void addValueForUuid(UUID uuid, T value) {
        if (contains(uuid)) {
            getData().get(uuid).add(value);
        } else {
            List<T> values = getValue(uuid);
            values.add(value);

            getData().put(uuid, values);
        }
    }

    public boolean removeValueForUuid(UUID uuid, T value) {
        if (contains(uuid)) {
            return getData().get(uuid).remove(value);
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

package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;
import dev.mega.megacore.util.ClassUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MegaManager extends Manager {
    @Getter
    private static MegaManager instance;
    private final Map<String, Manager> managers = new HashMap<>();
    private final String managersPath;

    private MegaManager(MegaCore megaCore, String managersPath) {
        super(megaCore);
        this.managersPath = managersPath;
    }

    public static MegaManager init(MegaCore megaCore, String managersPath) {
        if (instance == null) {
            instance = new MegaManager(megaCore, managersPath);
        }
        return getInstance();
    }

    private void registerManagers() {
        List<Class<Manager>> managerClasses = ClassUtil.findSubclasses(megaCore, managersPath, Manager.class);
        for (Class<?> managerClass : managerClasses) {
            try {
                Manager manager = (Manager) managerClass.getDeclaredConstructor(MegaCore.class).newInstance(megaCore);
                managers.put(managerClass.getSimpleName(), manager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void enable() {
        registerManagers();
        for (Manager manager : managers.values()) {
            manager.enable();
        }
        setRunning(true);
    }

    @Override
    public void disable() {
        for (Manager manager : managers.values()) {
            manager.disable();
        }
        setRunning(false);
    }
}

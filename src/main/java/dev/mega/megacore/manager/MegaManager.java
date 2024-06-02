package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;
import dev.mega.megacore.util.ClassUtil;
import dev.mega.megacore.util.MegaCoreUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MegaManager extends Manager {

    @Getter private static MegaManager instance;
    private final Map<Class<? extends Manager>, Manager> managers = new HashMap<>();
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

    public static <T extends Manager> T getManager(Class<T> targetConfig) {
        MegaManager megaManager = getInstance();

        if (megaManager != null) {
            if (!megaManager.isRunning()) {
                throw new RuntimeException("MegaManager is disabled!");
            }
            Manager manager = getInstance().getManagers().get(targetConfig);
            if (targetConfig.isInstance(manager)) {
                return targetConfig.cast(manager);
            }
            throw new IllegalArgumentException("Class is not loaded: " + targetConfig.getName());
        } else {
            throw new RuntimeException("MegaManager is not initialized yet!");
        }

    }

    private void registerManagers() {
        List<Class<Manager>> managerClasses = ClassUtil.findSubclasses(megaCore, managersPath, Manager.class);
        for (Class<? extends Manager> managerClass : managerClasses) {
            try {
                Manager manager = managerClass.getDeclaredConstructor(MegaCore.class).newInstance(megaCore);
                managers.put(managerClass, manager);
            } catch (Exception e) {
                MegaCoreUtil.getLogger().severe(e.toString());
            }
        }
    }

    @Override
    public void enable() {
        setRunning(true);
        MegaCoreUtil.getLogger().info("MegaManager enabled!");
        registerManagers();
        for (Manager manager : managers.values()) {
            manager.enable();
        }
    }

    @Override
    public void disable() {
        setRunning(false);
        MegaCoreUtil.getLogger().info("MegaManager disabled!");
        for (Manager manager : managers.values()) {
            manager.disable();
        }
    }
}

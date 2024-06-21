package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;
import dev.mega.megacore.config.Configurator;
import dev.mega.megacore.config.SubFolder;
import dev.mega.megacore.listener.MegaListener;
import dev.mega.megacore.manager.priority.ManagerPriority;
import dev.mega.megacore.util.ClassUtil;
import dev.mega.megacore.util.MegaCoreUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class MegaManager extends Manager {
    @Getter private static MegaManager instance;
    private final Map<Class<? extends Manager>, Manager> managers = new HashMap<>();
    private final List<String> managersPath;
    private final List<String> listenersPath;

    List<Class<Manager>> managerClasses;
    List<Class<MegaListener>> listenerClasses;

    private MegaManager(MegaCore megaCore, List<String> managersPath, List<String> listenersPath) {
        super(megaCore);
        this.managersPath = managersPath;
        this.listenersPath = listenersPath;

        listenerClasses = listenersPath.stream()
                .flatMap(lp -> ClassUtil.findSubclasses(megaCore, lp, MegaListener.class).stream())
                .collect(Collectors.toList());

        managerClasses = managersPath.stream()
                .flatMap(mp -> ClassUtil.findSubclasses(megaCore, mp, Manager.class).stream())
                .collect(Collectors.toList());
    }

    public static MegaManager init(MegaCore megaCore, List<String> managersPath, List<String> listenersPath) {
        if (instance == null) {
            instance = new MegaManager(megaCore, managersPath, listenersPath);
        }
        return getInstance();
    }

    public static <T extends Manager> T getManager(Class<T> targetConfig) {
        if (instance != null) {
            if (!instance.isRunning()) {
                throw new RuntimeException("MegaManager is disabled!");
            }
            Manager manager = getInstance().getManagers().get(targetConfig);
            if (targetConfig.isInstance(manager)) {
                return targetConfig.cast(manager);
            }
            throw new IllegalArgumentException("Class is not loaded: " + targetConfig.getName());
        } else {
            throw new IllegalStateException("MegaManager is not initialized yet!");
        }

    }

    private void registerManagers() {
        for (Class<? extends Manager> managerClass : managerClasses) {
            try {
                Manager manager = managerClass.getDeclaredConstructor(MegaCore.class).newInstance(megaCore);
                managers.put(managerClass, manager);
            } catch (NoSuchMethodException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("[DEVELOPMENT ISSUE] %s\n");
                sb.append("Can't find public constructor has one (MegaCore plugin) argument of\n");
                sb.append("package: %s; class: %s\n");
                MegaCoreUtil.getLogger().severe(
                        String.format(sb.toString(), e.getCause(), managerClass.getPackage(), managerClass.getName()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("[DEVELOPMENT ISSUE] %s\n");
                sb.append("package: %s; class: %s\n");
                sb.append("Class cannot be instantiated, accessed or invoked!\n");
                sb.append("Ensure it's not abstract class that has constructor with one (MegaCore plugin) argument!\n");
                MegaCoreUtil.getLogger().severe(
                        String.format(sb.toString(), e.getCause(), managerClass.getPackage(), managerClass.getName()));
            }
        }
    }

    private void registerListeners() {
        for (Class<? extends MegaListener> managerClass : listenerClasses) {
            try {
                MegaListener listener = managerClass.getDeclaredConstructor(MegaCore.class).newInstance(megaCore);
                Bukkit.getPluginManager().registerEvents(listener, megaCore);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void enableManagers() {
        List<Manager> managerList = new ArrayList<>(managers.values());
        Collections.sort(managerList);

        for (Manager manager : managerList) {
            manager.enable();
        }
    }

    @Override
    public void enable() {
        setRunning(true);

        MegaCoreUtil.getLogger().info("MegaManager enabled!");
        registerManagers();
        registerListeners(); // IDK it does not work and I don't know why :/

        enableManagers();
    }

    @Override
    public void disable() {

        // Disabling managers
        MegaCoreUtil.getLogger().info("MegaManager disabled!");
        for (Manager manager : managers.values()) {
            manager.disable();
        }

        // Saving all configs
        SubFolder configManager = SubFolder.getConfigManager();

        if (configManager != null) {
            Set<Configurator> configs = configManager.getAllConfigs();
            configs.forEach(Configurator::saveConfig);
        }

        setRunning(false);
    }
}

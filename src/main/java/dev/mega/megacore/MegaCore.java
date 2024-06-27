package dev.mega.megacore;

import dev.mega.megacore.config.SubFolder;
import dev.mega.megacore.listener.server.MenuListener;
import dev.mega.megacore.manager.MegaManager;
import dev.mega.megacore.manager.Reloadable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class represents entrypoint of JavaPlugin instance.
 */
@Getter
public abstract class MegaCore extends JavaPlugin implements Reloadable {
    private final Class<? extends SubFolder> configManagerClass;

    private final List<String> managersPath;
    private final List<String> listenersPath;

    /**
     * Represents a constructor of MegaCore class.
     * @param configManagerClass ConfigManager class.
     * @param managersPath Managers package path. All managers extend Manager class.
     * @param listenersPath Listeners package path.
     */
    protected MegaCore(Class<? extends SubFolder> configManagerClass, List<String> managersPath, List<String> listenersPath) {
        this.configManagerClass = configManagerClass;

        this.managersPath = managersPath;
        this.listenersPath = listenersPath;
    }

    /**
     * Calls when plugin has loaded.
     */
    @Override
    public void onLoad() {
        this.getLogger().info("Initializing MegaCore plugin.");

        try {
            SubFolder.setConfigManager(configManagerClass.getConstructor(MegaCore.class).newInstance(this));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Logger.getGlobal().severe("Your config manager has no public constructor with MegaCore argument!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        MegaManager.init(this, managersPath, listenersPath);
        enableMegaManager();
    }

    /**
     * Calls when plugin has enabled.
     */
    @Override
    public void onEnable() {
        enable();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);

        registerCommands();
    }

    /**
     * Calls when plugin has disabled.
     */
    @Override
    public void onDisable() {
        disable();

        MegaManager.getInstance().disable();
    }

    /**
     * Registers all commands.
     */
    protected abstract void registerCommands();

    protected void enableMegaManager() {
        MegaManager.getInstance().enable();
    }
}

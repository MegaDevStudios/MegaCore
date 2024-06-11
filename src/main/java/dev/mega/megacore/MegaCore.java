package dev.mega.megacore;

import dev.mega.megacore.config.SubFolder;
import dev.mega.megacore.inventory.MegaInventory;
import dev.mega.megacore.manager.MegaManager;
import dev.mega.megacore.manager.Reloadable;
import dev.mega.megacore.util.MegaCoreUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

/**
 * Class represents entrypoint of JavaPlugin instance.
 */
public abstract class MegaCore extends JavaPlugin implements Reloadable {

    private SubFolder configManager = null;
    private String managersPath = null;
    private String listenersPath = null;

    /**
     * Represents a constructor of MegaCore class.
     * @param configManager Class that extends SubFolder. "init" method required.
     * @param managersPath Managers package path. All managers extend Manager class.
     * @param listenersPath Listeners package path.
     */
    protected MegaCore(Class<? extends SubFolder> configManager, String managersPath, String listenersPath) {
        try {
            this.configManager = (SubFolder) configManager.getMethod("init", MegaCore.class).invoke(this, this);
        } catch (NullPointerException e) {
            MegaCoreUtil.getLogger().warning(String.format("""
                    %s: ConfigManager is null! Do not call it if you're using/not using other ConfigManager!
                    """, e.getCause()));
        } catch (NoSuchMethodException e) {
            MegaCoreUtil.getLogger().severe(String.format("""
                    %s: ConfigManager class cannot be registered!
                    Config manager does not contain 'init(MegaCore.class)' method.
                    """, e.getCause()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            MegaCoreUtil.getLogger().severe(String.format("""
                    %s: Can't access `init(MegaCore.class)` method!
                    Ensure it is a public static method and it contains one (MegaCore megaCore) argument!
                    
                    Example of ConfigManager's "init" method:
                    public static void init(MegaCore plugin) {...}
                    """, e.getCause()));
        }
        this.managersPath = managersPath;
        this.listenersPath = listenersPath;
    }

    /**
     * Calls when plugin has loaded.
     */
    @Override
    public void onLoad() {
        this.getLogger().info("Initializing MegaCore plugin.");
    }

    /**
     * Calls when plugin has enabled.
     */
    @Override
    public void onEnable() {
        MegaManager.init(this, managersPath, listenersPath);
        MegaManager.getInstance().enable();

        registerCommands();

        enable();
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

    /**
     * Gets the SubFolder ConfigManager class.
     * @return ConfigManager.
     */
    public SubFolder getConfigManager() {
        if (configManager == null) {
            throw new NullPointerException("""
                    [DEVELOPMENT ISSUE] ConfigManager has not initialized yet!
                    
                    Sorry, but what should we return? You've no ConfigManager correctly registered.
                    Restart your plugin and register it correctly!
                    """);
        }

        return configManager;
    }

}

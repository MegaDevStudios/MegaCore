package dev.mega.megacore;

import dev.mega.megacore.config.SubFolder;
import dev.mega.megacore.manager.MegaManager;
import dev.mega.megacore.manager.Reloadable;
import dev.mega.megacore.util.MegaCoreUtil;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents entrypoint of JavaPlugin instance.
 */
@Getter
public abstract class MegaCore extends JavaPlugin implements Reloadable {

    private SubFolder configManager = null;
    private List<String> managersPath = new ArrayList<>();
    private List<String> listenersPath = new ArrayList<>();

    /**
     * Represents a constructor of MegaCore class.
     * @param configManager Class that extends SubFolder. "init" method required.
     * @param managersPath Managers package path. All managers extend Manager class.
     * @param listenersPath Listeners package path.
     */
    protected MegaCore(Class<? extends SubFolder> configManager, List<String> managersPath, List<String> listenersPath) {
        try {
            this.configManager = (SubFolder) configManager.getMethod("init", MegaCore.class).invoke(this, this);
        } catch (NullPointerException e) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(e.getCause())
                    .append(": ConfigManager is null! Do not call it if you're using/not using other ConfigManager!");
            MegaCoreUtil.getLogger().warning(errorMessage.toString());
        } catch (NoSuchMethodException e) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(e.getCause()).append(": ConfigManager class cannot be registered!\n");
            errorMessage.append("Config manager does not contain 'init(MegaCore.class)' method.");
            MegaCoreUtil.getLogger().severe(errorMessage.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(e.getCause()).append(": Can't access `init(MegaCore.class)` method!\n");
            errorMessage.append("Ensure it is a public static method and it contains one (MegaCore megaCore) argument!\n");
            errorMessage.append("\nExample of ConfigManager's \"init\" method:\n" +
                    "public static void init(MegaCore plugin) {...}");
            MegaCoreUtil.getLogger().severe(errorMessage.toString());
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

    public void enableMegaManager() {
        MegaManager.getInstance().enable();
    }

    /**
     * Gets the SubFolder ConfigManager class.
     * @return ConfigManager.
     */
    public SubFolder getConfigManager() {
        if (configManager == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[DEVELOPMENT ISSUE] ConfigManager has not initialized yet!\n\n");
            sb.append("Sorry, but what should we return? You've no ConfigManager correctly registered.\n");
            sb.append("Restart your plugin and register it correctly!\n");
            throw new NullPointerException(sb.toString());
        }

        return configManager;
    }

}

package dev.mega.megacore.util;

import org.bukkit.Bukkit;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the main util for interactions with megacore functionality.
 */
public final class MegaCoreUtil {

    private static Logger logger;

    /**
     * @return the Logger for MegaCore
     */
    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger("MegaCore", null) { };
            logger.setParent(Bukkit.getLogger());
            logger.setLevel(Level.ALL);
        }
        return logger;
    }
}

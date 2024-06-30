package dev.mega.megacore.api;

import dev.mega.megacore.manager.MegaManager;

/**
 * Represents the api handler interface.
 */
public interface MegaAPIHandler {
    default boolean isDisabled() {
        return MegaManager.getInstance() == null || !MegaManager.getInstance().isRunning();
    }
}

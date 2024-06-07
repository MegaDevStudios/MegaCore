package dev.mega.megacore.api;

import dev.mega.megacore.manager.MegaManager;

public interface MegaAPIHandler {
    default boolean isDisabled() {
        return MegaManager.getInstance() == null || !MegaManager.getInstance().isRunning();
    }
}

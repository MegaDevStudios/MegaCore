package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;

public abstract class Manager {
    protected final MegaCore megaCore;

    public Manager(MegaCore megaCore) {
        this.megaCore = megaCore;
    }

    /**
     * Reloads the Manager's settings
     */
    public abstract void reload();

    /**
     * Cleans up the Manager's resources
     */
    public abstract void disable();
}

package dev.mega.megacore.listener;

import dev.mega.megacore.MegaCore;
import org.bukkit.event.Listener;

/**
 * Represents the listener sketch.
 */
public class MegaListener implements Listener {
    protected final MegaCore megaCore;

    public MegaListener(MegaCore megaCore) {
        this.megaCore = megaCore;
    }
}

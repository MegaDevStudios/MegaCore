package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;
import lombok.Getter;
import lombok.Setter;

public abstract class Manager implements Reloadable {
    protected final MegaCore megaCore;
    @Getter @Setter
    private boolean isRunning = false;

    public Manager(MegaCore megaCore) {
        this.megaCore = megaCore;
    }
}

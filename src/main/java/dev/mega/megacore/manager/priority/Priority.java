package dev.mega.megacore.manager.priority;

import lombok.Getter;

/**
 * Represents the priority flag.
 */
@Getter
public enum Priority {
    HIGH(2),
    NORMAL(1),
    LOW(0);

    private final int slot;

    Priority(int priority) {
        this.slot = priority;
    }
}

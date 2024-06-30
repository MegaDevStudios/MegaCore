package dev.mega.megacore.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

/**
 * Represents the abstract cancellable event.
 */
@Getter @Setter
public abstract class CancellableEvent extends MegaEvent implements Cancellable {
    private boolean isCancelled = false;
    private String cancelMessage = "Sorry, this event was cancelled.";
}

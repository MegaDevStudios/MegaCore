package dev.mega.megacore.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@Getter @Setter
public abstract class CancellableEvent extends Event implements Cancellable {
    private boolean isCancelled = false;
    private String cancelMessage = "Sorry, this event was cancelled.";
}

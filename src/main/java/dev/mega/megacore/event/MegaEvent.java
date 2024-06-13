package dev.mega.megacore.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @Setter
public abstract class MegaEvent extends Event {
    private final HandlerList handlers = new HandlerList();
}

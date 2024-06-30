package dev.mega.megacore.inventory.menu;

import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Represents the handler of menu open event.
 */
public interface Openable {
    void handleOpen(InventoryOpenEvent event);
}

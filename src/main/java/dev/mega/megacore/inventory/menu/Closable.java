package dev.mega.megacore.inventory.menu;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Represents the handler of menu close event.
 */
public interface Closable {
    void handleClose(InventoryCloseEvent event);
}

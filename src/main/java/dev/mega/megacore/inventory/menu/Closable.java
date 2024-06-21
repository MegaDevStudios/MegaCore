package dev.mega.megacore.inventory.menu;

import org.bukkit.event.inventory.InventoryCloseEvent;


public interface Closable {
    void handleClose(InventoryCloseEvent event);
}

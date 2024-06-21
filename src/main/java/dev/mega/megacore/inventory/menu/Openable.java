package dev.mega.megacore.inventory.menu;

import org.bukkit.event.inventory.InventoryOpenEvent;

public interface Openable {
    void handleOpen(InventoryOpenEvent event);
}

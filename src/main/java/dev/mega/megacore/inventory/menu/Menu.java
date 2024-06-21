package dev.mega.megacore.inventory.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface Menu extends InventoryHolder {
    String getTitle();

    void handleClick(InventoryClickEvent event);

    void open();

    void close();
}

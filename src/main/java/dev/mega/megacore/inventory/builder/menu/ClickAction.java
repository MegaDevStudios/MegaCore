package dev.mega.megacore.inventory.builder.menu;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ClickAction {
    void execute(InventoryClickEvent event);
}

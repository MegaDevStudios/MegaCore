package dev.mega.megacore.listener.server;

import dev.mega.megacore.inventory.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Represent the listener of menu events.
 */
public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        Menu menu = (Menu) holder;

        if (event.getCurrentItem() == null) return;
        if (event.getClickedInventory() != event.getView().getTopInventory())
            return;

        event.setCancelled(true);

        menu.handleClick(event);
    }
}

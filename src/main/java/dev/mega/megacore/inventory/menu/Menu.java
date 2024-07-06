package dev.mega.megacore.inventory.menu;

import dev.mega.megacore.inventory.builder.menu.MenuItemBuilder;
import dev.mega.megacore.util.Color;

import lombok.Getter;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents an abstract menu. It provides the basic structure
 * and behavior that all menus in the application should have.
 *
 * <p>Subclasses of AbstractMenu should implement the abstract methods to provide
 * specific functionality.</p>
 *
 * @since 2024-06-20
 */
@Getter
public abstract class Menu implements InventoryHolder {
    private final Player player;
    private final InventoryType type;
    private MenuItemBuilder[] items;
    private Inventory inventory;
    @Setter protected String menuName;

    public Menu(Player player, int rows, String menuName) {
        this.player = player;
        this.items = new MenuItemBuilder[rows * 9];
        this.type = InventoryType.CHEST;
        this.menuName = menuName;
    }

    public Menu(Player player, InventoryType type, String menuName) {
        this.player = player;
        this.type = type;
        this.menuName = menuName;
    }

    @Override
    public @NotNull Inventory getInventory() {
        inventory = type == InventoryType.CHEST
                ? Bukkit.createInventory(this, getSize(), Color.getTranslated(getMenuName()))
                : Bukkit.createInventory(this, type, Color.getTranslated(getMenuName()));
        update();

        return inventory;
    }

    public void open() {
        player.openInventory(getInventory());
    }

    public void close() {
        player.closeInventory();
    }

    public void handleClick(InventoryClickEvent event) {
        MenuItemBuilder item = items[event.getSlot()];

        if (item == null) return;

        item.doClickActions(event);

        event.setCancelled(true);
    }

    /**
     * update menu items
     */
    public void update() {
        items = new MenuItemBuilder[this.items.length];
        setMenuItems();
        inventory.setContents(convertToItemStacks(items));
    }

    /**
     *
     * @return the number of items in the menu
     */
    public int getSize() {
        return items.length;
    }

    protected void setItems(MenuItemBuilder item, int... indexes) {
        for (int index : indexes) {
            setItem(item, index);
        }
    }

    /**
     * Put an item on the menu.
     *
     * @param item the item you want to insert.
     * @param index slot where the item will be.
     */
    protected void setItem(MenuItemBuilder item, int index) {
        if (index >= getSize()) return;
        items[index] = item;
    }

    /**
     * Put an items on the menu.
     *
     * @param item the item you want to insert.
     * @param indexes slots where the item will be.
     */
    protected void setItems(MenuItemBuilder item, List<Integer> indexes) {
        for (int index : indexes) {
            setItem(item, index);
        }
    }

    protected abstract void setMenuItems();

    private static ItemStack[] convertToItemStacks(MenuItemBuilder[] items) {
        return Arrays.stream(items)
                .map(item -> item == null ? new ItemStack(Material.AIR) : item.toItemStack())
                .toArray(ItemStack[]::new);
    }

}

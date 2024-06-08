package dev.mega.megacore.inventory.builder.menu;

import dev.mega.megacore.inventory.builder.MegaItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItemBuilder extends MegaItemBuilder<MenuItemBuilder> {
    List<ClickAction> clickActions = new ArrayList<>();

    public MenuItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public MenuItemBuilder(Material type, int amount) {
        super(type, amount);
    }

    public MenuItemBuilder(Material type) {
        super(type);
    }

    public MenuItemBuilder addClickAction(ClickAction action) {
        this.clickActions.add(action);
        return this;
    }

    public MenuItemBuilder addClickActions(ClickAction... actions) {
        return addClickActions(Arrays.asList(actions));
    }

    public MenuItemBuilder addClickActions(List<ClickAction> actions) {
        this.clickActions.addAll(actions);
        return this;
    }

    public MenuItemBuilder addLeftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isLeftClick()) return;
            action.execute(event);
        });
        return this;
    }

    public MenuItemBuilder addRightClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isRightClick()) return;
            action.execute(event);
        });
        return this;
    }

    public MenuItemBuilder addLeftShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isLeftClick() && !event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    public MenuItemBuilder addRightShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isRightClick() && !event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    public MenuItemBuilder addShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    public void doClickActions(InventoryClickEvent event) {
        clickActions.forEach(action -> action.execute(event));
    }

    @Override
    public MenuItemBuilder build() {
        return null;
    }
}

package dev.mega.megacore.inventory.builder.menu;

import dev.mega.megacore.inventory.builder.MegaItemBuilder;
import dev.mega.megacore.inventory.builder.object.MegaStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides the builder for menu items.
 */
public class MenuItemBuilder extends MegaItemBuilder<MenuItemBuilder> {
    List<ClickAction> clickActions = new ArrayList<>();

    public MenuItemBuilder(MegaStack megaStack) {
        super(megaStack);
    }

    public MenuItemBuilder(ItemStack stack) {
        super(stack);
    }

    /**
     * Adds the click action.
     * @param action Action.
     * @return Builder.
     */
    public MenuItemBuilder addClickAction(ClickAction action) {
        this.clickActions.add(action);
        return this;
    }

    /**
     * Adds the click action.
     * @param actions Actions.
     * @return Builder.
     */
    public MenuItemBuilder addClickActions(ClickAction... actions) {
        return addClickActions(Arrays.asList(actions));
    }

    /**
     * Adds the click action.
     * @param actions Actions.
     * @return Builder.
     */
    public MenuItemBuilder addClickActions(List<ClickAction> actions) {
        this.clickActions.addAll(actions);
        return this;
    }

    /**
     * Adds the left click action.
     * @param action Action.
     * @return Builder.
     */
    public MenuItemBuilder addLeftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isLeftClick()) return;
            action.execute(event);
        });
        return this;
    }

    /**
     * Adds the right click action.
     * @param action Actions.
     * @return Builder.
     */
    public MenuItemBuilder addRightClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isRightClick()) return;
            action.execute(event);
        });
        return this;
    }

    /**
     * Adds the left shift click action.
     * @param action Actions.
     * @return Builder.
     */
    public MenuItemBuilder addLeftShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isLeftClick() && !event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    /**
     * Adds the right shift click action.
     * @param action Actions.
     * @return Builder.
     */
    public MenuItemBuilder addRightShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isRightClick() && !event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    /**
     * Adds the shift click action.
     * @param action Actions.
     * @return Builder.
     */
    public MenuItemBuilder addShiftClickAction(ClickAction action) {
        this.clickActions.add((event) -> {
            if (!event.isShiftClick()) return;
            action.execute(event);
        });
        return this;
    }

    /**
     * Calls all actions of this item.
     * @param event Event.
     */
    public void doClickActions(InventoryClickEvent event) {
        clickActions.forEach(action -> action.execute(event));
    }

    /**
     * Builds the item.
     * @return Instance.
     */
    @Override
    public MenuItemBuilder build() {
        return this;
    }
}

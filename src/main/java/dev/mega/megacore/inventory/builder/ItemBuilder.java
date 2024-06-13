package dev.mega.megacore.inventory.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder extends MegaItemBuilder<ItemBuilder> {
    public ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public ItemBuilder(Material type, int amount) {
        super(type, amount);
    }

    public ItemBuilder(Material type) {
        super(type);
    }

    @Override
    public ItemBuilder build() {
        return this;
    }
}

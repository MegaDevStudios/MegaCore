package dev.mega.megacore.inventory.builder;

import dev.mega.megacore.inventory.builder.object.BukkitItemStack;
import dev.mega.megacore.inventory.builder.object.MegaStack;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the item stack builder.
 */
public class ItemBuilder extends MegaItemBuilder<ItemBuilder> {
    public ItemBuilder(MegaStack itemStack) {
        super(itemStack);
    }

    public ItemBuilder(ItemStack stack) {
        super(stack);
    }

    @Override
    public ItemBuilder build() {
        return this;
    }
}

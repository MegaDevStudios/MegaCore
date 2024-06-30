package dev.mega.megacore.inventory.builder;

import dev.mega.megacore.inventory.builder.object.MegaStack;

/**
 * Represents the item stack builder.
 */
public class ItemBuilder extends MegaItemBuilder<ItemBuilder> {
    public ItemBuilder(MegaStack itemStack) {
        super(itemStack);
    }

    @Override
    public ItemBuilder build() {
        return this;
    }
}

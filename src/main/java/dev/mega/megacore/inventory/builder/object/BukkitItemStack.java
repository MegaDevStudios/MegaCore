package dev.mega.megacore.inventory.builder.object;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public class BukkitItemStack implements MegaStack {
    private final ItemStack itemStack;

    public BukkitItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public BukkitItemStack(Material type, int amount) {
        this(new ItemStack(type, amount));
    }

    public BukkitItemStack(Material type) {
        this(type, 1);
    }
}

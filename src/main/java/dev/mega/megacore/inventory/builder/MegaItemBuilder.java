package dev.mega.megacore.inventory.builder;

import dev.mega.megacore.inventory.builder.menu.MenuItemBuilder;
import dev.mega.megacore.util.Color;
import dev.mega.megacore.util.MegaCoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MegaItemBuilder<T extends MegaItemBuilder<T>> {
    protected final ItemStack itemStack;

    public MegaItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public MegaItemBuilder(Material type, int amount) {
        this(new ItemStack(type, amount));
    }

    public MegaItemBuilder(Material type) {
        this(type, 1);
    }

    @SuppressWarnings("unchecked")
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            MegaCoreUtil.getLogger().warning("This item doesn't support cloning. This item has been returned");
            return build();
        }
    }

    public T setType(Material type) {
        itemStack.setType(type);
        return build();
    }

    public T setAmount(int amount) {
        itemStack.setAmount(amount);
        return build();
    }

    public T addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return build();
    }

    public T addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return build();
    }

    public T removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return build();
    }

    public T setName(String name) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(Color.getTranslated(name));
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T setLore(List<String> lore) {
        ItemMeta itemMeta = getItemMeta();

        Color.getTranslated(lore);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return build();
    }

    public T removeLoreLine(String line) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.remove(line);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T removeLoreLine(int index) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());

        lore.remove(index);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T addLoreLine(String line) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = itemMeta.hasLore()
                ? new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()))
                : new ArrayList<>();

        lore.add(line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T addLoreLine(int index, String line) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = itemMeta.hasLore()
                ? new ArrayList<>(itemMeta.getLore())
                : new ArrayList<>();

        lore.add(index, line);
        Color.getTranslated(lore);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T setLoreLine(int index, String line) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = itemMeta.hasLore()
                ? new ArrayList<>(itemMeta.getLore())
                : new ArrayList<>();

        lore.set(index, line);

        Color.getTranslated(lore);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T addFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T removeFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.removeItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T resetFlags() {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.removeItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]));
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T setArmorColor(org.bukkit.Color color) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.setColor(color);
        itemStack.setItemMeta(itemMeta);
        return build();
    }

    public T makeGlow() {
        return addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                .addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemStack toItemStack() {
        return itemStack;
    }

    public MenuItemBuilder toMenuItem() {
        return new MenuItemBuilder(itemStack);
    }

    public abstract T build();

    protected ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
    }

    protected TextComponent fromStringToComponent(String string) {
        return Component.text(string);
    }

    protected List<TextComponent> fromStringToComponent(String... strings) {
        return fromStringToComponent(Arrays.asList(strings));
    }

    protected List<TextComponent> fromStringToComponent(List<String> strings) {
        return strings.stream().map(this::fromStringToComponent).collect(Collectors.toList());
    }

    protected String fromComponentToString(Component component) {
        return component.toString();
    }

    protected List<String> fromComponentToString(Component... components) {
        return fromComponentToString(Arrays.asList(components));
    }

    protected List<String> fromComponentToString(List<Component> components) {
        return components.stream().map(this::fromComponentToString).collect(Collectors.toList());
    }
}

package dev.mega.megacore.inventory.builder;

import dev.mega.megacore.inventory.builder.menu.MenuItemBuilder;
import dev.mega.megacore.inventory.builder.object.MegaStack;
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
    protected final MegaStack stack;

    public MegaItemBuilder(MegaStack stack) {
        this.stack = stack;
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
        toItemStack().setType(type);
        return build();
    }

    public T setAmount(int amount) {
        toItemStack().setAmount(amount);
        return build();
    }

    public T addEnchantment(Enchantment enchantment, int level) {
        toItemStack().addEnchantment(enchantment, level);
        return build();
    }

    public T addUnsafeEnchantment(Enchantment enchantment, int level) {
        toItemStack().addUnsafeEnchantment(enchantment, level);
        return build();
    }

    public T removeEnchantment(Enchantment enchantment) {
        toItemStack().removeEnchantment(enchantment);
        return build();
    }

    public T setName(String name) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(Color.getTranslated(name));
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T setLore(List<String> lore) {
        ItemMeta itemMeta = getItemMeta();

        Color.getTranslated(lore);

        itemMeta.setLore(lore);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return build();
    }

    public T removeLoreLine(String line) {
        ItemMeta itemMeta = toItemStack().getItemMeta();

        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.remove(line);

        itemMeta.setLore(lore);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T removeLoreLine(int index) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());

        lore.remove(index);
        itemMeta.setLore(lore);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T addLoreLine(String line) {
        ItemMeta itemMeta = getItemMeta();
        List<String> lore = itemMeta.hasLore()
                ? new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()))
                : new ArrayList<>();

        lore.add(line);
        itemMeta.setLore(lore);
        toItemStack().setItemMeta(itemMeta);
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
        toItemStack().setItemMeta(itemMeta);
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
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T addFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T removeFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.removeItemFlags(itemFlags);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T resetFlags() {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.removeItemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]));
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T setArmorColor(org.bukkit.Color color) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) toItemStack().getItemMeta();
        itemMeta.setColor(color);
        toItemStack().setItemMeta(itemMeta);
        return build();
    }

    public T makeGlow() {
        return addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                .addFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemStack toItemStack() {
        return stack.getItemStack();
    }

    public MenuItemBuilder toMenuItem() {
        return new MenuItemBuilder(getStack());
    }

    public abstract T build();

    protected ItemMeta getItemMeta() {
        return toItemStack().getItemMeta();
    }

    protected MegaStack getStack() {
        return stack;
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

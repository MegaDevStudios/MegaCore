package dev.mega.megacore.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

/**
 * Code was taken from https://github.com/MrMicky-FR/MegaInventory/blob/master/src/main/java/fr/mrmicky/MegaInventory/InventoryScheme.java
 */
public class InventoryScheme {

    private final List<String> masks = new ArrayList<>();
    private final Map<Character, ItemStack> items = new HashMap<>();
    private final Map<Character, Consumer<InventoryClickEvent>> handlers = new HashMap<>();

    /**
     * Add a mask to this scheme including all sort of characters.
     * For example: "110101011"
     *
     * @param mask a 9 characters mask
     * @return this scheme instance
     */
    public InventoryScheme mask(String mask) {
        Objects.requireNonNull(mask);
        this.masks.add(mask.length() > 9 ? mask.substring(0, 10) : mask);

        return this;
    }

    /**
     * Add multiples masks to this scheme including all sort of characters.
     * For example: "111111111", "110101011", "111111111"
     *
     * @param masks multiple 9-characters masks
     * @return this scheme instance
     */
    public InventoryScheme masks(String... masks) {
        for (String mask : Objects.requireNonNull(masks)) {
            mask(mask);
        }
        return this;
    }

    /**
     * Bind character to the corresponding item in the inventory.
     *
     * @param character the associated character in the mask
     * @param item      the item to use for this character
     * @param handler   consumer for the item
     * @return this scheme instance
     */
    public InventoryScheme bindItem(char character, ItemStack item, Consumer<InventoryClickEvent> handler) {
        this.items.put(character, Objects.requireNonNull(item));

        if (handler != null) {
            this.handlers.put(character, handler);
        }
        return this;
    }

    /**
     * Bind character to the corresponding item in the inventory.
     *
     * @param character the associated character in the mask
     * @param item      the item to use for this character
     * @return this scheme instance
     */
    public InventoryScheme bindItem(char character, ItemStack item) {
        return this.bindItem(character, item, null);
    }

    /**
     * Unbind any item from this character.
     *
     * @param character the character to unbind
     * @return this scheme instance
     */
    public InventoryScheme unbindItem(char character) {
        this.items.remove(character);
        this.handlers.remove(character);
        return this;
    }

    /**
     * Apply the current inventory scheme to the MegaInventory instance.
     *
     * @param inv the MegaInventory instance to apply this scheme to
     */
    public void apply(MegaInventory inv) {
        for (int line = 0; line < this.masks.size(); line++) {
            String mask = this.masks.get(line);

            for (int slot = 0; slot < mask.length(); slot++) {
                char c = mask.charAt(slot);
                ItemStack item = this.items.get(c);
                Consumer<InventoryClickEvent> handler = this.handlers.get(c);

                if (item != null) {
                    inv.setItem(9 * line + slot, item, handler);
                }
            }
        }
    }
}

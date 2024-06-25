package dev.mega.megacore.inventory.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mega.megacore.inventory.builder.object.BukkitItemStack;
import dev.mega.megacore.util.MegaCoreUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.util.UUID;

public class HeadBuilder extends MegaItemBuilder<HeadBuilder> {
    public HeadBuilder(String texture) {
        super(new BukkitItemStack(new ItemStack(Material.PLAYER_HEAD)));

        SkullMeta meta = (SkullMeta) getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "null");
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Method mtd = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(meta, profile);
        } catch (ReflectiveOperationException exception) {
            MegaCoreUtil.getLogger().warning("HeadBuilder cannot create a head with this texture. " +
                    "Report the bug to the developers");
        }

        toItemStack().setItemMeta(meta);
    }

    @Override
    public HeadBuilder build() {
        return this;
    }
}

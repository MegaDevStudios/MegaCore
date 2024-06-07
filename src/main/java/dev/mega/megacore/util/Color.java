package dev.mega.megacore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class Color {
    public static List<String> getTranslated(List<String> strings) {
        return strings.stream().map(Color::getTranslated).toList();
    }

    public static String getTranslated(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}

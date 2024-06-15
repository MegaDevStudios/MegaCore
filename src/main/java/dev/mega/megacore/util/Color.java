package dev.mega.megacore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Color {
    public static List<String> getTranslated(List<String> strings) {
        return strings.stream().map(Color::getTranslated).collect(Collectors.toList());
    }

    public static String getTranslated(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}

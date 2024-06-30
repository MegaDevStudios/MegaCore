package dev.mega.megacore.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the util for colorizing operations over string.
 */
public final class Color {
    public static List<String> getTranslated(List<String> strings) {
        return strings.stream().map(Color::getTranslated).collect(Collectors.toList());
    }

    public static String getTranslated(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> getTranslated(List<String> strings, char symbol) {
        return strings.stream().map(s -> getTranslated(s, symbol)).collect(Collectors.toList());
    }

    public static String getTranslated(String string, char symbol) {
        return ChatColor.translateAlternateColorCodes(symbol, string);
    }
}

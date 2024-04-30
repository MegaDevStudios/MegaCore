package dev.mega.megacore.command.matcher;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerArg implements ArgumentMatcher {
    private Player player;

    public PlayerArg(Player player) {
        this.player = player;
    }

    public PlayerArg() {
    }

    @Override
    public boolean matches(String argument) {
        if (player != null)
            return Objects.equals(Bukkit.getPlayer(argument), player);
        return Bukkit.getPlayer(argument) != null;
    }
}

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
    public boolean matches(String playerName) {
        if (player != null)
            return Objects.equals(Bukkit.getPlayer(playerName), player);
        return Bukkit.getPlayer(playerName) != null;
    }

    @Override
    public String getValue() {
        if (player == null) return "";
        return player.getName();
    }
}

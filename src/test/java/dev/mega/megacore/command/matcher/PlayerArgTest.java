package dev.mega.megacore.command.matcher;

import dev.mega.megacore.util.StringPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerArgTest {
    @BeforeAll
    static void setUp() {
        Server server = mock(Server.class);
        Player player = mock(Player.class);

        when(player.getName()).thenReturn("Dreaght");
        when(server.getPlayer("Dreaght")).thenReturn(player);

        Logger logger = Logger.getGlobal();
        when(server.getLogger()).thenReturn(logger);

        if (Bukkit.getServer() == null)
            Bukkit.setServer(server);
    }

    /**
     * Test different players are not equals
     */
    @Test
    void matchesTestPlayerNotEqualsDreaght() {
        Player testPlayer = mock(Player.class);
        when(testPlayer.getName()).thenReturn("TestPlayer");

        PlayerArg playerArg = new PlayerArg(testPlayer);
        assertFalse(playerArg.matches("Dreaght"));
    }

    /**
     * Test player does exist
     */
    @Test
    void matchesPlayerExistTrue() {
        PlayerArg playerArg = new PlayerArg();
        assertTrue(playerArg.matches("Dreaght"));
    }

    /**
     * Test player does NOT exist
     */
    @Test
    void matchesPlayerExistFalse() {
        PlayerArg playerArg = new PlayerArg();
        assertFalse(playerArg.matches("OtherPlayer"));
    }
}

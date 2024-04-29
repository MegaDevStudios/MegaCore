package dev.mega.megacore.util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MegaCoreUtilTest {
    @BeforeEach
    void setUp() {
        Server server = mock(Server.class);

        when(server.getLogger()).thenReturn(Logger.getGlobal());

        Bukkit.setServer(server);


    }

    @Test
    void getLogger() {
        assertDoesNotThrow(() -> {
            MegaCoreUtil.getLogger();
        });
    }
}
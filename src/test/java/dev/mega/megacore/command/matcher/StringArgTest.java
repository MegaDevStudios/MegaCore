package dev.mega.megacore.command.matcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringArgTest {
    @Test
    void matchesAnyTrue() {
        StringArg stringArg = new StringArg();
        assertTrue(stringArg.matches(""));
    }

    @Test
    void matchesDifferentStringFalse() {
        StringArg stringArg = new StringArg("test1");
        assertFalse(stringArg.matches("test2"));
    }
}
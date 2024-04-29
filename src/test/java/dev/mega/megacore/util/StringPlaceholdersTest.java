package dev.mega.megacore.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringPlaceholdersTest {
    StringPlaceholders stringPlaceholders;

    @BeforeEach
    void setUp() {
        stringPlaceholders = StringPlaceholders.of("VALUE", 100);
    }

    @Test
    void getPlaceholdersEquals() {
        Map<String, String> actualMap = stringPlaceholders.getPlaceholders();
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("VALUE", "100");

        assertEquals(expectedMap, actualMap);
    }

    @Test
    void applyEquals() {
        String actual = stringPlaceholders.apply("%VALUE%");
        assertEquals("100", actual);
    }
}
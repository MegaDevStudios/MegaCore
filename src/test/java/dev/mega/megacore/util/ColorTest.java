package dev.mega.megacore.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void getTranslatedMessagesEquals() {
        List<String> messages = new ArrayList<>();

        messages.add("&4Test message");
        messages.add("&lAnother test message");

        List<String> actualList = Color.getTranslated(messages);
        List<String> exceptedList = List.of("§4Test message", "§lAnother test message");

        assertEquals(exceptedList, actualList);
    }

    @Test
    void testGetTranslatedMessageEquals() {
        String excepted = "§4Test message";
        String actual = "§4Test message";

        assertEquals(excepted, actual);
    }
}
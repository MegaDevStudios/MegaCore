package dev.mega.megacore.command;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CommandManager {
    private static CommandManager instance;
    private final Map<String, Argument> commands = new HashMap<>();

    private CommandManager() {
    }

    public static CommandManager init() {
        instance = new CommandManager();
        return instance;
    }

    public void addCommand(String label, Argument argument) {
        commands.put(label, argument);
    }
}

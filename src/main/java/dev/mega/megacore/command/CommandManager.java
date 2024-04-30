package dev.mega.megacore.command;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandManager {
    private static CommandManager instance;
    private final List<Argument> commands = new ArrayList<>();

    private CommandManager() {
    }

    public static CommandManager init() {
        instance = new CommandManager();
        return instance;
    }

    public void addCommand(Argument argument) {
        commands.add(argument);
    }
}

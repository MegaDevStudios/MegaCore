package dev.mega.megacore.command;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class CommandManager {
    private static CommandManager instance;
    @Getter
    private static final List<Argument> commands = new ArrayList<>();

    private CommandManager() {
    }

    public static CommandManager init() {
        instance = new CommandManager();

        return instance;
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public void addCommand(Argument argument) {
        commands.add(argument);
    }

    public void registerCommands(JavaPlugin plugin) {
        commands.forEach(arg -> {
            Objects.requireNonNull(plugin.getCommand(

                    arg.getMatcher().getValue())).setExecutor(arg);
        });
    }
}

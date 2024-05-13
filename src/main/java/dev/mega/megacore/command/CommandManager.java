package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private final Map<String, Method> commands = new HashMap<>();
    private final JavaPlugin plugin;
    private final Map<Method, Object> instances = new HashMap<>();

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands(Object... objects) {
        for (Object object : objects) {
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(CommandHandler.class)) {
                    CommandHandler annotation = method.getAnnotation(CommandHandler.class);
                    String cmdName = annotation.name().toLowerCase();
                    commands.put(cmdName, method);
                    plugin.getCommand(cmdName).setExecutor(this);
                    instances.put(method, object);
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdName = command.getName().toLowerCase();
        Method method = commands.get(cmdName);
        if (method != null) {
            CommandHandler annotation = method.getAnnotation(CommandHandler.class);
            if (!sender.hasPermission(annotation.permission())) {
                sender.sendMessage("You don't have the privilege.");
                return true;
            }
            try {
                method.invoke(instances.get(method), sender, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                sender.sendMessage("Something went wrong.");
            }
            return true;
        }
        return false;
    }
}


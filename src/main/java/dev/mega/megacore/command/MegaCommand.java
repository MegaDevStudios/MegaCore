package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MegaCommand extends Argument {
    public MegaCommand(String label) {
        super(label);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (String arg : args) {
            Argument argument = getArgumentOrDefault(arg);
            argument.onCommand(sender, command, label, args);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Argument argument = getArgumentOrDefault(args[args.length - 1]);
        return argument.onTabComplete(sender, command, label, args);
    }

    private Argument getArgumentOrDefault(String arg) {
        for (Argument argument : getSubcommands().values()) {
            if (arg.equals(argument.getLabel())) {
                return argument;
            }
        }
        return new Argument(getLabel()) {};
    }
}

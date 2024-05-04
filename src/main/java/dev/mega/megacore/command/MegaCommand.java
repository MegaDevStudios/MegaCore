package dev.mega.megacore.command;

import dev.mega.megacore.command.matcher.ArgumentMatcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MegaCommand extends Argument {
    public MegaCommand(ArgumentMatcher matcher) { super(matcher); }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Argument currentParent = this;
        for (String arg : args)
            currentParent = getArgumentOrDefault(currentParent, arg);

        currentParent.onCommand(sender, command, label, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<List<String>> allHistoryArgs = new ArrayList<>();
        Argument currentParent = this;
        for (String arg : args) {
            currentParent = getArgumentOrDefault(currentParent, arg);
            if (currentParent.getMatcher().matches(arg)) {
                allHistoryArgs.add(currentParent.onTabComplete(sender, command, label, args));
            }
        }
        return allHistoryArgs.get(Math.max(0, allHistoryArgs.size() - 2));
    }

    private Argument getArgumentOrDefault(Argument parent, String arg) {
        return parent.getSubcommands().stream()
                .filter(argument -> argument.getMatcher().matches(arg))
                .findFirst()
                .orElse(new Argument(getMatcher()) {});
    }
}

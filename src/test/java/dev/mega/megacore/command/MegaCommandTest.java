package dev.mega.megacore.command;

import dev.mega.megacore.command.matcher.PlayerArg;
import dev.mega.megacore.command.matcher.StringArg;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MegaCommandTest {
    CommandManager commandManager;

    CommandSender sender;
    Command command;
    String label;
    String[] args;

    @BeforeAll
    static void setUpBeforeAll() {
        Server server = mock(Server.class);
        Player player = mock(Player.class);

        when(player.getName()).thenReturn("Dreaght");
        when(server.getPlayer("Dreaght")).thenReturn(player);

        Logger logger = Logger.getGlobal();
        when(server.getLogger()).thenReturn(logger);

        Bukkit.setServer(server);
    }

    @BeforeEach
    void setUp() {
        commandManager = CommandManager.init();

        Argument mega = new MegaCommand(new StringArg("mega"));
        Argument give = new Argument(new StringArg("give")) {};
        Argument player = new Argument(new PlayerArg()) {};
        Argument sum = new Argument(new StringArg("100")) {};

        mega.addArgument(give);
        give.addArgument(player);
        player.addArgument(sum);

        commandManager.addCommand(mega);

        // /mega give Dreaght 100
        sender = mock(CommandSender.class);
        command = mock(Command.class);
        label = "mega";
        args = new String[]{"give", "Dreaght", "100"};
    }

    /**
     * Test if mocked Argument class and called are equals.
     */
    @Test
    void onCommandAssertArgumentsEquals() {
        for (Argument argument : commandManager.getCommands()) {
            argument.onCommand(sender, command, label, args);
        }
    }

    /**
     * Test if tab completions are equals
     */
    @Test
    void testOnTabCompleteEquals100() {
        List<String> expected = new ArrayList<>();
        expected.add("100");

        for (Argument argument : commandManager.getCommands()) {
            Assertions.assertEquals(expected, argument.onTabComplete(sender, command, label, args));
        }
    }
}

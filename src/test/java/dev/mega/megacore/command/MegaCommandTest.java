package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class MegaCommandTest {
    CommandManager commandManager;

    @BeforeEach
    void setUp() {
        commandManager = CommandManager.init();

        Argument mega = new MegaCommand("mega");
        Argument give = new Argument("give") {};
        Argument player = new Argument("Dreaght") {};
        Argument sum = new Argument("100") {};

        commandManager.addCommand(mega);
        mega.addArgument("give", give);
        give.addArgument("Dreaght", player);
        player.addArgument("100", sum);
    }

    /**
     * Test if mocked Argument class and called are equals.
     */
    @Test
    void onCommandAssertArgumentsEquals() {
        // /mega give Dreaght 100

        CommandSender sender = mock(CommandSender.class);
        Command command = mock(Command.class);
        String label = "mega";
        String[] args = {"give", "Dreaght", "100"};

        for (Argument argument : commandManager.getCommands()) {
            argument.onCommand(sender, command, label, args);
        }
    }

    @Test
    void onTabComplete() {
    }
}

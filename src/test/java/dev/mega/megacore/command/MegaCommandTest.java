package dev.mega.megacore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MegaCommandTest {
    CommandManager commandManager;

    @BeforeEach
    void setUp() {
        commandManager = CommandManager.init();

        Argument argument = new Argument("mega") {};
        commandManager.addCommand(argument);
    }

    /**
     * Test if mocked Argument class and called are equals.
     */
    @Test
    void onCommandAssertArgumentsEquals() {
        Argument argument = mock(Argument.class);

        CommandSender sender = mock(CommandSender.class);
        Command command = mock(Command.class);
        String label = "mega";
        String[] args = new String[0];

        when(argument.onCommand(any(), any(), anyString(), any())).thenAnswer(I -> {
            Assertions.assertEquals(argument.getClass(), I.getMock().getClass());
            return null;
        });

        argument.onCommand(sender, command, label, args);
    }

    @Test
    void onTabComplete() {
    }
}

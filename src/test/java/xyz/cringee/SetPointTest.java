package xyz.cringee;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.cringee.commands.setPoint;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class SetPointTest {
    private setPoint setPointCommand;
    private CommandSender sender;
    private Player senderPlayer;
    private Command command;

    @BeforeEach
    public void setUp() {
        setPointCommand = new setPoint();
        sender = mock(CommandSender.class);
        senderPlayer = mock(Player.class);
        command = mock(Command.class);
    }

    @Test
    public void testOnCommand_NoArgs_ReturnsFalse() {
        String[] args = {};
        boolean result = setPointCommand.onCommand(sender, command, "setpoint", args);
        assertFalse(result);
    }

    @Test
    public void testOnCommand_NullSender_ReturnsFalse() {
        String[] args = {"test"};
        boolean result = setPointCommand.onCommand(null, command, "setpoint", args);
        assertFalse(result);
    }

    @Test
    public void testOnCommand_NullCommand_ReturnsFalse() {
        String[] args = {"test"};
        boolean result = setPointCommand.onCommand(sender, null, "setpoint", args);
        assertFalse(result);
    }

    @Test
    public void testOnCommand_ValidXYZArgs_ReturnsTrue() {
        String[] args = {"1", "2", "3", "name"};
        boolean result = setPointCommand.onCommand(sender, command, "setpoint", args);
        assertTrue(result);
    }

    @Test
    public void testOnCommand5() {
        String[] args = {"1", "2", "3", "name"};
        boolean result = setPointCommand.onCommand(senderPlayer, command, "setpoint", args);
        assertTrue(result);
    }

    @Test
    public void testOnCommand6() {
        String[] args = {"1", "2", "3"};
        boolean result = setPointCommand.onCommand(senderPlayer, command, "setpoint", args);
        assertFalse(result);
    }

}

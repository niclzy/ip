package gigglebytes;

import gigglebytes.command.*;
import gigglebytes.exception.GiggleBytesException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Parser} class.
 * <p>
 * Tests various command parsing scenarios including valid commands,
 * edge cases, and error conditions.
 * </p>
 */
public class ParserTest {

    @Test
    public void testParseExitCommand() throws GiggleBytesException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void testParseListCommand() throws GiggleBytesException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testParseTodoCommand() throws GiggleBytesException {
        Command command = Parser.parse("todo Buy milk");
        assertInstanceOf(AddTodoCommand.class, command);
    }

    @Test
    public void testParseTodoCommandEmptyDescription() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("todo");
        });
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    public void testParseTodoCommandOnlySpaces() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("todo     ");
        });
        assertTrue(exception.getMessage().contains("cannot be empty"));
    }

    @Test
    public void testParseDeadlineCommand() throws GiggleBytesException {
        Command command = Parser.parse("deadline Submit report /by 2024-12-31 2359");
        assertInstanceOf(AddDeadlineCommand.class, command);
    }

    @Test
    public void testParseDeadlineCommandMissingBy() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("deadline Submit report");
        });
        assertTrue(exception.getMessage().contains("Missing '/by'"));
    }

    @Test
    public void testParseDeadlineCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("deadline");
        });
        assertTrue(exception.getMessage().contains("Please provide description"));
    }

    @Test
    public void testParseEventCommand() throws GiggleBytesException {
        Command command = Parser.parse("event Meeting /from 2024-10-10 1400 /to 2024-10-10 1500");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    public void testParseEventCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("event");
        });
        assertTrue(exception.getMessage().contains("Please use the format"));
    }

    @Test
    public void testParseMarkCommand() throws GiggleBytesException {
        Command command = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseMarkCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("mark");
        });
        assertTrue(exception.getMessage().contains("Please specify which task"));
    }

    @Test
    public void testParseMarkCommandOnlySpaces() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("mark     ");
        });
        assertTrue(exception.getMessage().contains("Please specify which task"));
    }

    @Test
    public void testParseUnmarkCommand() throws GiggleBytesException {
        Command command = Parser.parse("unmark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseUnmarkCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("unmark");
        });
        assertTrue(exception.getMessage().contains("Please specify which task"));
    }

    @Test
    public void testParseUnmarkCommandOnlySpaces() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("unmark     ");
        });
        assertTrue(exception.getMessage().contains("Please specify which task"));
    }

    @Test
    public void testParseDeleteCommand() throws GiggleBytesException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testParseDeleteCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("delete");
        });
        assertTrue(exception.getMessage().contains("Please specify which task"));
    }

    @Test
    public void testParseInvalidCommand() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("invalid command");
        });
        assertTrue(exception.getMessage().contains("don't know what that means"));
    }

    @Test
    public void testParseEmptyCommand() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("");
        });
        assertTrue(exception.getMessage().contains("type something"));
    }

    @Test
    public void testParseCaseInsensitivity() throws GiggleBytesException {
        Command command1 = Parser.parse("BYE");
        Command command2 = Parser.parse("ByE");
        Command command3 = Parser.parse("LiSt");

        assertInstanceOf(ExitCommand.class, command1);
        assertInstanceOf(ExitCommand.class, command2);
        assertInstanceOf(ListCommand.class, command3);
    }

    @Test
    public void testParseMarkCommandInvalidNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("mark abc");
        });
        assertTrue(exception.getMessage().contains("valid number"));
    }

    @Test
    public void testParseUnmarkCommandInvalidNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("unmark abc");
        });
        assertTrue(exception.getMessage().contains("valid number"));
    }

    @Test
    public void testParseDeleteCommandInvalidNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("delete abc");
        });
        assertTrue(exception.getMessage().contains("valid number"));
    }

    @Test
    public void testParseMarkCommandWithMultipleSpaces() throws GiggleBytesException {
        Command command = Parser.parse("mark   5   ");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseUnmarkCommandWithMultipleSpaces() throws GiggleBytesException {
        Command command = Parser.parse("unmark   3   ");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseDeleteCommandWithMultipleSpaces() throws GiggleBytesException {
        Command command = Parser.parse("delete   2   ");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testParseTodoCommandWithMultipleSpaces() throws GiggleBytesException {
        Command command = Parser.parse("todo    Task with spaces   ");
        assertInstanceOf(AddTodoCommand.class, command);
    }

    @Test
    public void testParseMarkCommandLargeNumber() throws GiggleBytesException {
        Command command = Parser.parse("mark 999");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseUnmarkCommandLargeNumber() throws GiggleBytesException {
        Command command = Parser.parse("unmark 999");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParseDeleteCommandLargeNumber() throws GiggleBytesException {
        Command command = Parser.parse("delete 999");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testParseMarkCommandZero() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("mark 0");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseUnmarkCommandZero() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("unmark 0");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseDeleteCommandZero() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("delete 0");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseMarkCommandNegativeNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("mark -1");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseUnmarkCommandNegativeNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("unmark -1");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseDeleteCommandNegativeNumber() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("delete -1");
        });
        assertTrue(exception.getMessage().contains("must be positive"));
    }

    @Test
    public void testParseFindCommand() throws GiggleBytesException {
        Command command = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void testParseFindCommandEmpty() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("find");
        });
        assertTrue(exception.getMessage().contains("Please specify a keyword"));
    }

    @Test
    public void testParseFindCommandOnlySpaces() {
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            Parser.parse("find     ");
        });
        assertTrue(exception.getMessage().contains("Please specify a keyword"));
    }

    @Test
    public void testParseFindCommandWithMultipleSpaces() throws GiggleBytesException {
        Command command = Parser.parse("find    multiple words   ");
        assertInstanceOf(FindCommand.class, command);
    }
}
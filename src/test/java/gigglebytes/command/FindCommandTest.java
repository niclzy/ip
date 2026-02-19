package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FindCommand} class.
 */
public class FindCommandTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(10);
        ui = new Ui();
        storage = new Storage();
    }

    @Test
    public void testFindCommandWithMatchingTasks() throws GiggleBytesException {
        taskList.addTodo("Read book");
        taskList.addTodo("Buy groceries");
        taskList.addDeadline("Return book", "2024-12-31");
        taskList.addEvent("Book club meeting", "2024-10-10 1400", "2024-10-10 1500");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);
    }

    @Test
    public void testFindCommandWithNoMatches() throws GiggleBytesException {
        taskList.addTodo("Buy groceries");
        taskList.addTodo("Clean room");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);
    }

    @Test
    public void testFindCommandCaseInsensitive() throws GiggleBytesException {
        taskList.addTodo("READ BOOK");
        taskList.addTodo("read book");

        FindCommand findCommand1 = new FindCommand("read");
        findCommand1.execute(taskList, ui, storage);

        FindCommand findCommand2 = new FindCommand("READ");
        findCommand2.execute(taskList, ui, storage);
    }

    @Test
    public void testFindCommandEmptyTaskList() throws GiggleBytesException {
        FindCommand findCommand = new FindCommand("anything");
        findCommand.execute(taskList, ui, storage);
    }

    @Test
    public void testFindCommandPartialMatch() throws GiggleBytesException {
        taskList.addTodo("Reading is fun");
        taskList.addTodo("Cookbook recipes");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);
    }

    @Test
    public void testFindCommandMultipleWords() throws GiggleBytesException {
        taskList.addTodo("Read Harry Potter book");
        taskList.addTodo("Buy Harry Potter merchandise");

        FindCommand findCommand = new FindCommand("Harry Potter");
        findCommand.execute(taskList, ui, storage);
    }
}
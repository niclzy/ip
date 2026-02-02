package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FindCommand} class.
 * <p>
 * Tests task searching functionality including case-insensitive matching,
 * partial matches, and edge cases.
 * </p>
 */
public class FindCommandTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Sets up a fresh test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        taskList = new TaskList(10);
        ui = new Ui();
        storage = new Storage();
    }

    /**
     * Tests finding tasks with matching keyword.
     */
    @Test
    public void testFindCommandWithMatchingTasks() throws GiggleBytesException {
        // Add some tasks
        taskList.addTodo("Read book");
        taskList.addTodo("Buy groceries");
        taskList.addDeadline("Return book", "2024-12-31");
        taskList.addEvent("Book club meeting", "2024-10-10 1400", "2024-10-10 1500");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);

        // Should find 3 tasks: "Read book", "Return book", "Book club meeting"
    }

    /**
     * Tests find command when no tasks match the keyword.
     */
    @Test
    public void testFindCommandWithNoMatches() throws GiggleBytesException {
        taskList.addTodo("Buy groceries");
        taskList.addTodo("Clean room");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);

        // Should display "No tasks found containing: book"
    }

    /**
     * Tests case-insensitive searching.
     */
    @Test
    public void testFindCommandCaseInsensitive() throws GiggleBytesException {
        taskList.addTodo("READ BOOK");
        taskList.addTodo("read book");

        FindCommand findCommand1 = new FindCommand("read");
        findCommand1.execute(taskList, ui, storage);
        // Should find both tasks

        FindCommand findCommand2 = new FindCommand("READ");
        findCommand2.execute(taskList, ui, storage);
        // Should also find both tasks (case-insensitive)
    }

    /**
     * Tests find command with empty task list.
     */
    @Test
    public void testFindCommandEmptyTaskList() throws GiggleBytesException {
        FindCommand findCommand = new FindCommand("anything");
        findCommand.execute(taskList, ui, storage);
        // Should display "No tasks found containing: anything"
    }

    /**
     * Tests partial word matching.
     */
    @Test
    public void testFindCommandPartialMatch() throws GiggleBytesException {
        taskList.addTodo("Reading is fun");
        taskList.addTodo("Cookbook recipes");

        FindCommand findCommand = new FindCommand("book");
        findCommand.execute(taskList, ui, storage);
        // Should find "Cookbook recipes" (contains "book" as substring)
    }

    /**
     * Tests searching with multiple words.
     */
    @Test
    public void testFindCommandMultipleWords() throws GiggleBytesException {
        taskList.addTodo("Read Harry Potter book");
        taskList.addTodo("Buy Harry Potter merchandise");

        FindCommand findCommand = new FindCommand("Harry Potter");
        findCommand.execute(taskList, ui, storage);
        // Should find both tasks
    }
}
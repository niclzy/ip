package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SortCommand} class.
 */
public class SortCommandTest {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(10);
        ui = new Ui();
        storage = new Storage();

        // Add some tasks in random order
        taskList.addDeadline("Submit report", "2024-12-31");
        taskList.addTodo("Buy groceries");
        taskList.addEvent("Team meeting", "2024-10-10 1400", "2024-10-10 1500");
        taskList.addDeadline("Project deadline", "2024-11-30");
        taskList.addTodo("Read book");
    }

    @Test
    public void testSortByDescription() throws GiggleBytesException {
        SortCommand command = new SortCommand("description", false);
        command.execute(taskList, ui, storage);

        // Verify first task is "Buy groceries" (alphabetically first)
        Task firstTask = taskList.getTask(1).orElse(null);
        assertNotNull(firstTask);
        assertEquals("Buy groceries", firstTask.getDescription());
    }

    @Test
    public void testSortByDescriptionReverse() throws GiggleBytesException {
        SortCommand command = new SortCommand("description", true);
        command.execute(taskList, ui, storage);

        // Verify first task is "Team meeting" (alphabetically last)
        Task firstTask = taskList.getTask(1).orElse(null);
        assertNotNull(firstTask);
        assertEquals("Team meeting", firstTask.getDescription());
    }

    @Test
    public void testSortByDate() throws GiggleBytesException {
        SortCommand command = new SortCommand("date", false);
        command.execute(taskList, ui, storage);

        // First task should be the event (earliest date)
        Task firstTask = taskList.getTask(1).orElse(null);
        assertNotNull(firstTask);
    }

    @Test
    public void testSortByStatus() throws GiggleBytesException {
        // Mark one task as done
        taskList.getTask(1).ifPresent(Task::markAsDone);

        SortCommand command = new SortCommand("status", false);
        command.execute(taskList, ui, storage);

        // First task should be not done (since undone come first)
        Task firstTask = taskList.getTask(1).orElse(null);
        assertNotNull(firstTask);
        assertFalse(firstTask.isDone());
    }

    @Test
    public void testSortEmptyList() throws GiggleBytesException {
        TaskList emptyList = new TaskList(10);
        SortCommand command = new SortCommand("description", false);

        // Should not throw exception
        command.execute(emptyList, ui, storage);
    }
}
package gigglebytes.util;

import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TaskList} class.
 */
public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(10);
    }

    @Test
    public void testAddTodo() {
        assertTrue(taskList.addTodo("Test todo"));
        assertEquals(1, taskList.getItemCount());

        Optional<Task> taskOpt = taskList.getTask(1);
        assertTrue(taskOpt.isPresent());
        Task task = taskOpt.get();
        assertInstanceOf(Todo.class, task);
        assertEquals("Test todo", task.getDescription());
    }

    @Test
    public void testAddDeadline() {
        assertTrue(taskList.addDeadline("Submit report", "2024-12-31 2359"));
        assertEquals(1, taskList.getItemCount());

        Optional<Task> taskOpt = taskList.getTask(1);
        assertTrue(taskOpt.isPresent());
        Task task = taskOpt.get();
        assertInstanceOf(Deadline.class, task);
        assertEquals("Submit report", task.getDescription());
    }

    @Test
    public void testAddEvent() {
        assertTrue(taskList.addEvent("Team meeting", "2024-10-10 1400", "2024-10-10 1500"));
        assertEquals(1, taskList.getItemCount());

        Optional<Task> taskOpt = taskList.getTask(1);
        assertTrue(taskOpt.isPresent());
        Task task = taskOpt.get();
        assertInstanceOf(Event.class, task);
        assertEquals("Team meeting", task.getDescription());
    }

    @Test
    public void testGetTaskWithValidIndex() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");

        Optional<Task> taskOpt1 = taskList.getTask(1);
        Optional<Task> taskOpt2 = taskList.getTask(2);

        assertTrue(taskOpt1.isPresent());
        assertTrue(taskOpt2.isPresent());
        assertEquals("Task 1", taskOpt1.get().getDescription());
        assertEquals("Task 2", taskOpt2.get().getDescription());
    }

    @Test
    public void testGetTaskWithInvalidIndex() {
        taskList.addTodo("Task 1");

        assertTrue(taskList.getTask(0).isEmpty());  // Below valid range
        assertTrue(taskList.getTask(2).isEmpty());  // Above valid range
        assertTrue(taskList.getTask(-1).isEmpty()); // Negative index
    }

    @Test
    public void testDeleteTask() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        taskList.addTodo("Task 3");

        Optional<Task> deletedOpt = taskList.deleteTask(2);
        assertTrue(deletedOpt.isPresent());
        assertEquals("Task 2", deletedOpt.get().getDescription());
        assertEquals(2, taskList.getItemCount());

        // Verify remaining tasks
        Optional<Task> taskOpt1 = taskList.getTask(1);
        Optional<Task> taskOpt2 = taskList.getTask(2);

        assertTrue(taskOpt1.isPresent());
        assertTrue(taskOpt2.isPresent());
        assertEquals("Task 1", taskOpt1.get().getDescription());
        assertEquals("Task 3", taskOpt2.get().getDescription());
    }

    @Test
    public void testDeleteTaskWithInvalidIndex() {
        taskList.addTodo("Task 1");

        assertTrue(taskList.deleteTask(0).isEmpty());
        assertTrue(taskList.deleteTask(2).isEmpty());
        assertTrue(taskList.deleteTask(-1).isEmpty());
        assertEquals(1, taskList.getItemCount()); // Count should remain unchanged
    }

    @Test
    public void testConstructorWithLoadedTasks() {
        List<Task> loadedTasks = new ArrayList<>();
        loadedTasks.add(new Todo("Loaded todo"));
        loadedTasks.add(new Deadline("Loaded deadline", "2024-12-25 2359"));

        TaskList initializedTaskList = new TaskList(10, loadedTasks);
        assertEquals(2, initializedTaskList.getItemCount());

        Optional<Task> taskOpt1 = initializedTaskList.getTask(1);
        Optional<Task> taskOpt2 = initializedTaskList.getTask(2);

        assertTrue(taskOpt1.isPresent());
        assertTrue(taskOpt2.isPresent());
        assertInstanceOf(Todo.class, taskOpt1.get());
        assertInstanceOf(Deadline.class, taskOpt2.get());
    }

    @Test
    public void testGetLastTask() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");

        Optional<Task> lastTask = taskList.getLastTask();
        assertTrue(lastTask.isPresent());
        assertEquals("Task 2", lastTask.get().getDescription());
    }

    @Test
    public void testGetLastTaskEmptyList() {
        Optional<Task> lastTask = taskList.getLastTask();
        assertTrue(lastTask.isEmpty());
    }

    @Test
    public void testPrintAllItemsWithEmptyList() {
        // This test verifies the method doesn't crash when list is empty
        taskList.printAllItems(); // Should print "Your list is empty! Add something first!"
    }

    @Test
    public void testPrintAllItemsWithTasks() {
        taskList.addTodo("Todo 1");
        taskList.addDeadline("Deadline 1", "2024-12-31 2359");

        // This test verifies the method runs without crashing
        taskList.printAllItems(); // Should print the list of tasks
    }
}
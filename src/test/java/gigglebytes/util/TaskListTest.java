package gigglebytes.util;

import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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

        Task task = taskList.getTask(1);
        assertNotNull(task);
        assertInstanceOf(Todo.class, task);
        assertEquals("Test todo", task.getDescription());
    }

    @Test
    public void testAddDeadline() {
        assertTrue(taskList.addDeadline("Submit report", "2024-12-31 2359"));
        assertEquals(1, taskList.getItemCount());

        Task task = taskList.getTask(1);
        assertNotNull(task);
        assertInstanceOf(Deadline.class, task);
        assertEquals("Submit report", task.getDescription());
    }

    @Test
    public void testAddEvent() {
        assertTrue(taskList.addEvent("Team meeting", "2024-10-10 1400", "2024-10-10 1500"));
        assertEquals(1, taskList.getItemCount());

        Task task = taskList.getTask(1);
        assertNotNull(task);
        assertInstanceOf(Event.class, task);
        assertEquals("Team meeting", task.getDescription());
    }

    @Test
    public void testGetTaskWithValidIndex() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");

        Task task1 = taskList.getTask(1);
        Task task2 = taskList.getTask(2);

        assertNotNull(task1);
        assertNotNull(task2);
        assertEquals("Task 1", task1.getDescription());
        assertEquals("Task 2", task2.getDescription());
    }

    @Test
    public void testGetTaskWithInvalidIndex() {
        taskList.addTodo("Task 1");

        assertNull(taskList.getTask(0));  // Below valid range
        assertNull(taskList.getTask(2));  // Above valid range
        assertNull(taskList.getTask(-1)); // Negative index
    }

    @Test
    public void testDeleteTask() {
        taskList.addTodo("Task 1");
        taskList.addTodo("Task 2");
        taskList.addTodo("Task 3");

        Task deleted = taskList.deleteTask(2);
        assertNotNull(deleted);
        assertEquals("Task 2", deleted.getDescription());
        assertEquals(2, taskList.getItemCount());

        // Verify remaining tasks
        assertEquals("Task 1", taskList.getTask(1).getDescription());
        assertEquals("Task 3", taskList.getTask(2).getDescription());
    }

    @Test
    public void testDeleteTaskWithInvalidIndex() {
        taskList.addTodo("Task 1");

        assertNull(taskList.deleteTask(0));
        assertNull(taskList.deleteTask(2));
        assertNull(taskList.deleteTask(-1));
        assertEquals(1, taskList.getItemCount()); // Count should remain unchanged
    }

    @Test
    public void testConstructorWithLoadedTasks() {
        List<Task> loadedTasks = new ArrayList<>();
        loadedTasks.add(new Todo("Loaded todo"));
        loadedTasks.add(new Deadline("Loaded deadline", "2024-12-25 2359"));

        TaskList initializedTaskList = new TaskList(10, loadedTasks);
        assertEquals(2, initializedTaskList.getItemCount());

        Task task1 = initializedTaskList.getTask(1);
        Task task2 = initializedTaskList.getTask(2);

        assertInstanceOf(Todo.class, task1);
        assertInstanceOf(Deadline.class, task2);
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
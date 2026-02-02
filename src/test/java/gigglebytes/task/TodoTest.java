package gigglebytes.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Todo} class.
 * <p>
 * Tests todo creation, marking, and display functionality.
 * </p>
 */
public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertTrue(todo.isDone());
        assertEquals("[T][X] Read book", todo.getDisplayString());
    }

    @Test
    public void testMarkAsNotDone() {
        Todo todo = new Todo("Write code");
        todo.markAsDone();
        todo.markAsNotDone();
        assertFalse(todo.isDone());
        assertEquals("[T][ ] Write code", todo.getDisplayString());
    }

    @Test
    public void testGetTypeIcon() {
        Todo todo = new Todo("Test task");
        assertEquals("T", todo.getTypeIcon());
    }

    @Test
    public void testDisplayString() {
        Todo todo = new Todo("Task description");
        assertEquals("[T][ ] Task description", todo.getDisplayString());

        todo.markAsDone();
        assertEquals("[T][X] Task description", todo.getDisplayString());
    }

    @Test
    public void testToString() {
        Todo todo = new Todo("String test");
        assertEquals("[T][ ] String test", todo.toString());
    }
}
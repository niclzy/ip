package gigglebytes.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        Deadline deadline = new Deadline("Submit report", "2024-12-25 2359");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals("2024-12-25 2359", deadline.getBy());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testGetBy() {
        Deadline deadline = new Deadline("Project deadline", "2024-11-30 1800");
        assertEquals("2024-11-30 1800", deadline.getBy());
    }

    @Test
    public void testGetTypeIcon() {
        Deadline deadline = new Deadline("Test", "2024-01-01 0000");
        assertEquals("D", deadline.getTypeIcon());
    }

    @Test
    public void testDisplayString() {
        Deadline deadline = new Deadline("Complete assignment", "2024-10-15 2359");
        String expected = "[D][ ] Complete assignment (by: 2024-10-15 2359)";
        assertEquals(expected, deadline.getDisplayString());

        deadline.markAsDone();
        expected = "[D][X] Complete assignment (by: 2024-10-15 2359)";
        assertEquals(expected, deadline.getDisplayString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-31 2359", "2025-01-01 0000", "2024-06-30 1200"})
    public void testDifferentDeadlineFormats(String dateTime) {
        Deadline deadline = new Deadline("Parameterized test", dateTime);
        assertEquals(dateTime, deadline.getBy());
    }
}
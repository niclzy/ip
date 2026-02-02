package gigglebytes.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Event} class.
 * <p>
 * Tests event creation, time range handling, and display functionality.
 * </p>
 */
public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event("Team meeting", "2024-10-10 1400", "2024-10-10 1500");
        assertEquals("Team meeting", event.getDescription());
        assertEquals("2024-10-10 1400", event.getFrom());
        assertEquals("2024-10-10 1500", event.getTo());
        assertFalse(event.isDone());
    }

    @Test
    public void testGetFromAndTo() {
        Event event = new Event("Conference", "2024-11-01 0900", "2024-11-01 1700");
        assertEquals("2024-11-01 0900", event.getFrom());
        assertEquals("2024-11-01 1700", event.getTo());
    }

    @Test
    public void testGetTypeIcon() {
        Event event = new Event("Test event", "2024-01-01 0000", "2024-01-01 0100");
        assertEquals("E", event.getTypeIcon());
    }

    @Test
    public void testDisplayString() {
        Event event = new Event("Birthday party", "2024-12-25 1900", "2024-12-25 2300");
        String expected = "[E][ ] Birthday party (from: 2024-12-25 1900 to: 2024-12-25 2300)";
        assertEquals(expected, event.getDisplayString());

        event.markAsDone();
        expected = "[E][X] Birthday party (from: 2024-12-25 1900 to: 2024-12-25 2300)";
        assertEquals(expected, event.getDisplayString());
    }
}
package gigglebytes.storage;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void testParseTodoTask() throws GiggleBytesException {
        Storage storage = new Storage();
        String line = "T | 0 | Buy groceries";
        Task task = storage.parseTask(line);

        assertNotNull(task);
        assertInstanceOf(Todo.class, task);
        assertEquals("Buy groceries", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    public void testParseTodoTaskMarkedDone() throws GiggleBytesException {
        Storage storage = new Storage();
        String line = "T | 1 | Read book";
        Task task = storage.parseTask(line);

        assertNotNull(task);
        assertTrue(task.isDone());
        assertEquals("Read book", task.getDescription());
    }

    @Test
    public void testParseDeadlineTask() throws GiggleBytesException {
        Storage storage = new Storage();
        String line = "D | 0 | Submit report | 2024-12-31 2359";
        Task task = storage.parseTask(line);

        assertNotNull(task);
        assertInstanceOf(Deadline.class, task);
        Deadline deadline = (Deadline) task;
        assertEquals("Submit report", deadline.getDescription());
        assertEquals("2024-12-31 2359", deadline.getBy());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testParseEventTask() throws GiggleBytesException {
        Storage storage = new Storage();
        String line = "E | 0 | Team meeting | 2024-10-10 1400 | 2024-10-10 1500";
        Task task = storage.parseTask(line);

        assertNotNull(task);
        assertInstanceOf(Event.class, task);
        Event event = (Event) task;
        assertEquals("Team meeting", event.getDescription());
        assertEquals("2024-10-10 1400", event.getFrom());
        assertEquals("2024-10-10 1500", event.getTo());
        assertFalse(event.isDone());
    }

    @Test
    public void testParseInvalidFormat() {
        Storage storage = new Storage();
        String line = "Invalid line format";
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            storage.parseTask(line);
        });
        assertTrue(exception.getMessage().contains("Invalid format"));
    }

    @Test
    public void testParseUnknownTaskType() {
        Storage storage = new Storage();
        String line = "X | 0 | Unknown task";
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            storage.parseTask(line);
        });
        assertTrue(exception.getMessage().contains("Unknown task type"));
    }

    @Test
    public void testParseDeadlineMissingBy() {
        Storage storage = new Storage();
        String line = "D | 0 | Submit report";
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            storage.parseTask(line);
        });
        assertTrue(exception.getMessage().contains("missing 'by' parameter"));
    }

    @Test
    public void testParseEventMissingFromOrTo() {
        Storage storage = new Storage();
        String line = "E | 0 | Team meeting | 2024-10-10 1400";
        Exception exception = assertThrows(GiggleBytesException.class, () -> {
            storage.parseTask(line);
        });
        assertTrue(exception.getMessage().contains("missing 'from' or 'to' parameter"));
    }

    @Test
    public void testTaskToFileStringTodo() {
        Storage storage = new Storage();
        Todo todo = new Todo("Test todo");
        String result = storage.taskToFileString(todo);
        assertEquals("T | 0 | Test todo", result);
    }

    @Test
    public void testTaskToFileStringTodoDone() {
        Storage storage = new Storage();
        Todo todo = new Todo("Test todo");
        todo.markAsDone();
        String result = storage.taskToFileString(todo);
        assertEquals("T | 1 | Test todo", result);
    }

    @Test
    public void testTaskToFileStringDeadline() {
        Storage storage = new Storage();
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        String result = storage.taskToFileString(deadline);
        assertEquals("D | 0 | Submit report | 2024-12-31 2359", result);
    }

    @Test
    public void testTaskToFileStringDeadlineDone() {
        Storage storage = new Storage();
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        deadline.markAsDone();
        String result = storage.taskToFileString(deadline);
        assertEquals("D | 1 | Submit report | 2024-12-31 2359", result);
    }

    @Test
    public void testTaskToFileStringEvent() {
        Storage storage = new Storage();
        Event event = new Event("Team meeting", "2024-10-10 1400", "2024-10-10 1500");
        String result = storage.taskToFileString(event);
        assertEquals("E | 0 | Team meeting | 2024-10-10 1400 | 2024-10-10 1500", result);
    }

    @Test
    public void testTaskToFileStringEventDone() {
        Storage storage = new Storage();
        Event event = new Event("Team meeting", "2024-10-10 1400", "2024-10-10 1500");
        event.markAsDone();
        String result = storage.taskToFileString(event);
        assertEquals("E | 1 | Team meeting | 2024-10-10 1400 | 2024-10-10 1500", result);
    }

    @Test
    public void testLoadTasksFileDoesNotExist() {
        Storage storage = new Storage();
        File tempFile = tempDir.resolve("nonexistent.txt").toFile();
        assertFalse(tempFile.exists());
    }

    @Test
    public void testSaveAndLoadTasks() throws IOException, GiggleBytesException {
        File testFile = tempDir.resolve("testfile.txt").toFile();
        String content = "T | 1 | Completed todo\n" +
                "D | 0 | Pending deadline | 2024-12-31 2359\n" +
                "E | 1 | Past event | 2024-01-01 1400 | 2024-01-01 1500";

        FileWriter writer = new FileWriter(testFile);
        writer.write(content);
        writer.close();

        Storage storage = new Storage();

        String line1 = "T | 1 | Completed todo";
        Task task1 = storage.parseTask(line1);
        assertNotNull(task1);
        assertTrue(task1.isDone());
        assertEquals("Completed todo", task1.getDescription());

        String line2 = "D | 0 | Pending deadline | 2024-12-31 2359";
        Task task2 = storage.parseTask(line2);
        assertNotNull(task2);
        assertInstanceOf(Deadline.class, task2);
        Deadline deadline = (Deadline) task2;
        assertEquals("Pending deadline", deadline.getDescription());
        assertEquals("2024-12-31 2359", deadline.getBy());

        String line3 = "E | 1 | Past event | 2024-01-01 1400 | 2024-01-01 1500";
        Task task3 = storage.parseTask(line3);
        assertNotNull(task3);
        assertInstanceOf(Event.class, task3);
        Event event = (Event) task3;
        assertEquals("Past event", event.getDescription());
        assertEquals("2024-01-01 1400", event.getFrom());
        assertEquals("2024-01-01 1500", event.getTo());
        assertTrue(event.isDone());
    }

    @Test
    public void testConstructorCreatesDirectory() {
        Storage storage = new Storage();
        File dataDir = new File("./data");
        assertTrue(dataDir.exists() || dataDir.mkdirs());
    }
}
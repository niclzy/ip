package gigglebytes.storage;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.util.TaskList;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.util.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.ResolverStyle;

/**
 * Handles loading and saving of tasks to persistent storage.
 */
public class Storage {
    private static final String DATA_DIR = "./data";
    private static final String DATA_FILE = "./data/gigglebytes.txt";
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
            .withResolverStyle(ResolverStyle.STRICT);

    public Storage() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            File dir = new File(DATA_DIR);
            assert dir.exists() && dir.isDirectory() : "Data directory should exist after construction";
        } catch (IOException e) {
            System.out.println("Error creating data directory! ;-;");
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println(Messages.NO_SAVED_DATA);
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            processFileLines(scanner, tasks);
            displayLoadSummary(tasks);
        } catch (IOException e) {
            System.out.println("Error loading tasks from file! ;-;");
        }

        assert tasks != null : "Tasks list should never be null";
        return tasks;
    }

    private void processFileLines(Scanner scanner, List<Task> tasks) {
        int lineNumber = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                processLine(line, lineNumber, tasks);
            }
            lineNumber++;
        }
    }

    private void processLine(String line, int lineNumber, List<Task> tasks) {
        try {
            Task task = parseTask(line);
            if (task != null) {
                tasks.add(task);
            } else {
                System.out.println("Warning: Could not parse line " + lineNumber + ": " + line);
            }
        } catch (GiggleBytesException e) {
            System.out.println("Warning: Error parsing line " + lineNumber + ": " + e.getMessage());
        }
    }

    private void displayLoadSummary(List<Task> tasks) {
        if (!tasks.isEmpty()) {
            System.out.println(String.format(Messages.LOADED_TASKS, tasks.size()));
        }
    }

    Task parseTask(String line) throws GiggleBytesException {
        assert line != null : "Line to parse cannot be null";

        try {
            String[] parts = line.split(" \\| ");

            if (parts.length < 3) {
                throw new GiggleBytesException("Invalid format: " + line);
            }

            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("1");
            String description = parts[2].trim();

            Task task = createTaskByType(type, parts, description, line);

            if (isDone && task != null) {
                task.markAsDone();
            }

            assert task != null : "Parsed task should not be null";
            assert task.getDescription().equals(description) : "Task description should match parsed description";

            return task;

        } catch (Exception e) {
            throw new GiggleBytesException(e.getMessage());
        }
    }

    private Task createTaskByType(String type, String[] parts, String description, String line)
            throws GiggleBytesException {
        switch (type) {
            case "T":
                return new Todo(description);
            case "D":
                return createDeadline(parts, description, line);
            case "E":
                return createEvent(parts, description, line);
            default:
                throw new GiggleBytesException("Unknown task type: " + type);
        }
    }

    private Deadline createDeadline(String[] parts, String description, String line)
            throws GiggleBytesException {
        if (parts.length < 4) {
            throw new GiggleBytesException("Deadline missing 'by' parameter: " + line);
        }
        String byString = parts[3].trim();
        return new Deadline(description, byString);
    }

    private Event createEvent(String[] parts, String description, String line)
            throws GiggleBytesException {
        if (parts.length < 5) {
            throw new GiggleBytesException("Event missing 'from' or 'to' parameter: " + line);
        }
        String fromString = parts[3].trim();
        String toString = parts[4].trim();
        return new Event(description, fromString, toString);
    }

    public void saveTasks(TaskList taskList) {
        assert taskList != null : "TaskList cannot be null when saving";

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            int savedCount = writeTasksToFile(writer, taskList);
            assert savedCount == taskList.getItemCount() : "Should have saved all tasks";
        } catch (IOException e) {
            System.out.println("Error saving tasks to file! ;-;");
        }
    }

    private int writeTasksToFile(FileWriter writer, TaskList taskList) throws IOException {
        int savedCount = 0;
        for (int i = 1; i <= taskList.getItemCount(); i++) {
            Task task = taskList.getTask(i).orElse(null);
            if (task != null) {
                String line = taskToFileString(task);
                writer.write(line + System.lineSeparator());
                savedCount++;
            }
        }
        return savedCount;
    }

    String taskToFileString(Task task) {
        assert task != null : "Task to convert cannot be null";

        StringBuilder sb = new StringBuilder();

        sb.append(task.getTypeIcon()).append(" | ");
        sb.append(task.isDone() ? "1" : "0").append(" | ");
        sb.append(task.getDescription());

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(deadline.getBy());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(event.getFrom());
            sb.append(" | ").append(event.getTo());
        }

        String result = sb.toString();
        assert result != null && !result.isEmpty() : "File string should not be empty";

        return result;
    }
}
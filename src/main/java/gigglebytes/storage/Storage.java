package gigglebytes.storage;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.util.TaskList;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import gigglebytes.task.Task;
import gigglebytes.task.Todo;

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
 * <p>
 * This class manages the file I/O operations for the GiggleBytes application,
 * including reading tasks from a text file on startup and saving tasks
 * to the file when the application exits.
 * </p>
 */
public class Storage {
    private static final String DATA_DIR = "./data";
    private static final String DATA_FILE = "./data/gigglebytes.txt";
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Constructs a new Storage instance and creates the data directory if it doesn't exist.
     */
    public Storage() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));

            File dir = new File(DATA_DIR);
            assert dir.exists() && dir.isDirectory() : "Data directory should exist after construction";
        } catch (IOException e) {
            System.out.println("Error creating data directory! ;-;");
        }
    }

    /**
     * Loads tasks from the data file.
     * <p>
     * If the data file doesn't exist, returns an empty list.
     * Each line in the file represents one task in a specific format.
     * </p>
     *
     * @return A list of tasks loaded from the file
     */
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("No saved data found. Starting fresh! >.<");
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
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
                lineNumber++;
            }
            scanner.close();

            if (!tasks.isEmpty()) {
                System.out.println("Loaded " + tasks.size() + " tasks from save file! >.<");
            }

            assert tasks != null : "Tasks list should never be null";

        } catch (IOException e) {
            System.out.println("Error loading tasks from file! ;-;");
        }

        return tasks;
    }

    /**
     * Parses a single line from the data file into a Task object.
     * <p>
     * The expected format for each line is:
     * <ul>
     *   <li>Todo: T | 0/1 | description</li>
     *   <li>Deadline: D | 0/1 | description | byDateTime</li>
     *   <li>Event: E | 0/1 | description | fromDateTime | toDateTime</li>
     * </ul>
     * where 0 = not done, 1 = done.
     * </p>
     *
     * @param line The line from the data file to parse
     * @return The parsed Task object
     * @throws GiggleBytesException If the line format is invalid
     */
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

            Task task = null;

            switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        throw new GiggleBytesException("Deadline missing 'by' parameter: " + line);
                    }
                    String byString = parts[3].trim();
                    task = new Deadline(description, byString);
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new GiggleBytesException("Event missing 'from' or 'to' parameter: " + line);
                    }
                    String fromString = parts[3].trim();
                    String toString = parts[4].trim();
                    task = new Event(description, fromString, toString);
                    break;
                default:
                    throw new GiggleBytesException("Unknown task type: " + type);
            }

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

    /**
     * Saves all tasks in the task list to the data file.
     * <p>
     * Overwrites the existing file with the current state of all tasks.
     * </p>
     *
     * @param taskList The TaskList containing tasks to save
     */
    public void saveTasks(TaskList taskList) {
        assert taskList != null : "TaskList cannot be null when saving";

        try {
            FileWriter writer = new FileWriter(DATA_FILE);

            int savedCount = 0;
            for (int i = 1; i <= taskList.getItemCount(); i++) {
                Task task = taskList.getTask(i);
                if (task != null) {
                    String line = taskToFileString(task);
                    writer.write(line + System.lineSeparator());
                    savedCount++;
                }
            }

            writer.close();

            assert savedCount == taskList.getItemCount() : "Should have saved all tasks";

        } catch (IOException e) {
            System.out.println("Error saving tasks to file! ;-;");
        }
    }

    /**
     * Converts a Task object to its string representation for file storage.
     *
     * @param task The Task to convert
     * @return The string representation of the task for file storage
     */
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
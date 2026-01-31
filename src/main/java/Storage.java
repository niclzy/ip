import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static final String DATA_DIR = "./data";
    private static final String DATA_FILE = "./data/gigglebytes.txt";

    public Storage() {
        // Ensure data directory exists
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.out.println("Error creating data directory! ;-;");
        }
    }

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

        } catch (IOException e) {
            System.out.println("Error loading tasks from file! ;-;");
        }

        return tasks;
    }

    private Task parseTask(String line) throws GiggleBytesException {
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
                    String by = parts[3].trim();
                    task = new Deadline(description, by);
                    break;
                case "E":
                    if (parts.length < 5) {
                        throw new GiggleBytesException("Event missing 'from' or 'to' parameter: " + line);
                    }
                    String from = parts[3].trim();
                    String to = parts[4].trim();
                    task = new Event(description, from, to);
                    break;
                default:
                    throw new GiggleBytesException("Unknown task type: " + type);
            }

            if (isDone && task != null) {
                task.markAsDone();
            }

            return task;

        } catch (Exception e) {
            throw new GiggleBytesException(e.getMessage());
        }
    }

    public void saveTasks(TaskList taskList) {
        try {
            FileWriter writer = new FileWriter(DATA_FILE);

            for (int i = 1; i <= taskList.getItemCount(); i++) {
                Task task = taskList.getTask(i);
                if (task != null) {
                    String line = taskToFileString(task);
                    writer.write(line + System.lineSeparator());
                }
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks to file! ;-;");
        }
    }

    private String taskToFileString(Task task) {
        StringBuilder sb = new StringBuilder();

        sb.append(task.getTypeIcon()).append(" | ");

        sb.append(task.isDone() ? "1" : "0").append(" | ");

        sb.append(task.getDescription());

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(getDeadlineBy(task));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(getEventFrom(task));
            sb.append(" | ").append(getEventTo(task));
        }

        return sb.toString();
    }

    private String getDeadlineBy(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getBy();
        }
        return "";
    }

    private String getEventFrom(Task task) {
        if (task instanceof Event) {
            return ((Event) task).getFrom();
        }
        return "";
    }

    private String getEventTo(Task task) {
        if (task instanceof Event) {
            return ((Event) task).getTo();
        }
        return "";
    }
}
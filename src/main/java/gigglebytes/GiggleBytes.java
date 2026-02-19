package gigglebytes;

import gigglebytes.command.Command;
import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.task.Task;
import gigglebytes.util.TaskList;

import java.util.List;

/**
 * The main class for the GiggleBytes task management application.
 * <p>
 * GiggleBytes is a personal digital task manager that helps users
 * track and complete their tasks. It supports todo tasks, deadlines,
 * and events with a command-line interface and GUI.
 * </p>
 */
public class GiggleBytes {
    private Storage storage;
    private TaskList taskList;

    /**
     * Constructs a new GiggleBytes instance.
     * Initializes the UI, storage, and loads existing tasks from file.
     */
    public GiggleBytes() {
        storage = new Storage();
        List<Task> loadedTasks = storage.loadTasks();
        taskList = new TaskList(100, loadedTasks);

        assert storage != null : "Storage should be initialized";
        assert taskList != null : "TaskList should be initialized";
    }

    /**
     * Gets a response from GiggleBytes for the user's input.
     * This method is called by the GUI controller.
     *
     * @param input The user's input string
     * @return The response from GiggleBytes
     */
    public String getResponse(String input) {
        assert input != null : "Input cannot be null";

        try {
            Command command = Parser.parse(input);
            assert command != null : "Parsed command should not be null";

            GuiUi guiUi = new GuiUi();
            command.execute(taskList, guiUi, storage);

            if (command.isExit()) {
                return guiUi.getOutput() + "\nGoodbye!";
            }

            return guiUi.getOutput();
        } catch (GiggleBytesException e) {
            return e.getMessage();
        }
    }

    /**
     * Saves tasks to storage.
     */
    public void saveTasks() {
        assert taskList != null : "TaskList should exist when saving";
        assert storage != null : "Storage should exist when saving";

        storage.saveTasks(taskList);
    }
}
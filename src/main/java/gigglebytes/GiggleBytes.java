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
 * and events with a command-line interface.
 * </p>
 */
public class GiggleBytes {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * Constructs a new GiggleBytes instance.
     * Initializes the UI, storage, and loads existing tasks from file.
     */
    public GiggleBytes() {
        ui = new Ui();
        storage = new Storage();
        List<Task> loadedTasks = storage.loadTasks();
        taskList = new TaskList(100, loadedTasks);
    }

    /**
     * Starts the GiggleBytes application.
     * <p>
     * Shows the welcome message, enters a command loop to process user input,
     * and continues until the user issues an exit command.
     * </p>
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                ui.showLine();

                Command command = Parser.parse(userInput);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();

            } catch (GiggleBytesException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }

        ui.close();
    }

    /**
     * The main entry point for the GiggleBytes application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new GiggleBytes().run();
    }
}
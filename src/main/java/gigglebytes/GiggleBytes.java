package gigglebytes;

import gigglebytes.command.Command;
import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.task.Task;
import gigglebytes.util.TaskList;

import java.util.List;

public class GiggleBytes {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public GiggleBytes() {
        ui = new Ui();
        storage = new Storage();
        List<Task> loadedTasks = storage.loadTasks();
        taskList = new TaskList(100, loadedTasks);
    }

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

    public static void main(String[] args) {
        new GiggleBytes().run();
    }
}
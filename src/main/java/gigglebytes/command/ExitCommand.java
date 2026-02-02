package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;

/**
 * Represents a command to exit the GiggleBytes application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by saving tasks and displaying a farewell message.
     *
     * @param taskList The TaskList containing tasks to save
     * @param ui The Ui to display the farewell message
     * @param storage The Storage to save tasks before exiting
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        // Count completed tasks
        int completed = 0;
        for (int i = 1; i <= taskList.getItemCount(); i++) {
            gigglebytes.task.Task task = taskList.getTask(i);
            if (task != null && task.isDone()) {
                completed++;
            }
        }

        ui.showMessage("Byte you later! Hope to see you again soon! >.<");
        ui.showMessage("Tasks completed today: " + completed + " >.<");
        storage.saveTasks(taskList);
    }

    /**
     * Returns true to indicate this is an exit command.
     *
     * @return true always, since this is an exit command
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
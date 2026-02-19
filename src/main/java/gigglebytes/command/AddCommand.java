package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.util.Messages;

/**
 * Abstract base class for all add commands (Todo, Deadline, Event).
 * Provides common confirmation message functionality.
 */
public abstract class AddCommand extends Command {

    /**
     * Displays confirmation message after successfully adding a task.
     *
     * @param taskList The TaskList containing the newly added task
     * @param ui The Ui to display the confirmation message
     */
    protected void showConfirmation(TaskList taskList, Ui ui) {
        ui.showMessage(Messages.TASK_ADDED);
        taskList.getLastTask().ifPresent(task -> ui.showMessage("  " + task));
        ui.showMessage(String.format(Messages.TASK_COUNT, taskList.getItemCount()));
    }
}
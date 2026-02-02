package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int taskNumber;

    /**
     * Constructs a DeleteCommand for the specified task number.
     *
     * @param taskNumber The 1-based index of the task to delete
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the command by deleting the specified task from the task list.
     *
     * @param taskList The TaskList to delete the task from
     * @param ui The Ui to display messages to the user
     * @param storage The Storage to save tasks (not used in this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        // First check if the list is empty
        if (taskList.getItemCount() == 0) {
            ui.showMessage("Your task list is empty! There's nothing to delete! ;-;");
            return;
        }

        // Then check if the task number is valid
        if (taskNumber < 1 || taskNumber > taskList.getItemCount()) {
            ui.showMessage("Task number " + taskNumber + " doesn't exist! ;-;");
            ui.showMessage("Please choose a number between 1 and " + taskList.getItemCount());
            return;
        }

        Task task = taskList.deleteTask(taskNumber);

        if (task != null) {
            ui.showMessage("Noted! I've removed this task:");
            ui.showMessage("  " + task);
            ui.showMessage("Now you have " + taskList.getItemCount() + " tasks in the list.");
        } else {
            ui.showMessage("Could not delete task " + taskNumber + "! ;-;");
        }
    }
}
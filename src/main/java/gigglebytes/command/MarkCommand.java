package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to mark or unmark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;
    private boolean markAsDone;

    /**
     * Constructs a MarkCommand for the specified task number and action.
     *
     * @param taskNumber The 1-based index of the task to mark/unmark
     * @param markAsDone true to mark as done, false to mark as not done
     */
    public MarkCommand(int taskNumber, boolean markAsDone) {
        this.taskNumber = taskNumber;
        this.markAsDone = markAsDone;
    }

    /**
     * Executes the command by marking or unmarking the specified task.
     *
     * @param taskList The TaskList containing the task to modify
     * @param ui The Ui to display messages to the user
     * @param storage The Storage (not used in this command)
     * @throws GiggleBytesException If an error occurs during execution
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        // First check if the list is empty
        if (taskList.getItemCount() == 0) {
            String actionText = markAsDone ? "mark as done" : "mark as not done";
            ui.showMessage("Your task list is empty! There's nothing to " + actionText + "! ;-;");
            return;
        }

        // Then check if the task number is valid
        if (taskNumber < 1 || taskNumber > taskList.getItemCount()) {
            ui.showMessage("Task number " + taskNumber + " doesn't exist! ;-;");
            ui.showMessage("Please choose a number between 1 and " + taskList.getItemCount());
            return;
        }

        Task task = taskList.getTask(taskNumber);

        if (markAsDone) {
            if (!task.isDone()) {
                task.markAsDone();
                ui.showMessage("Nice! >.< I've marked this task as done:");
            } else {
                ui.showMessage("This task was already marked as done!  0-0");
            }
        } else {
            if (task.isDone()) {
                task.markAsNotDone();
                ui.showMessage("OK ;-; , I've marked this task as not done yet:");
            } else {
                ui.showMessage("This task was already marked as not done! <.<");
            }
        }
        ui.showMessage("   " + task);
    }
}
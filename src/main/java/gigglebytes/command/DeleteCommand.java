package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

public class DeleteCommand extends Command {
    private int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
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
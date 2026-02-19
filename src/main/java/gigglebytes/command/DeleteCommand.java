package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;
import gigglebytes.util.Messages;

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
        assert taskNumber > 0 : "Task number must be positive";
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (isTaskListEmpty(taskList)) {
            ui.showMessage("Your task list is empty! There's nothing to delete! ;-;");
            return;
        }

        if (isTaskNumberInvalid(taskList)) {
            ui.showMessage(String.format(Messages.TASK_NOT_FOUND, taskNumber));
            ui.showMessage(String.format(Messages.CHOOSE_VALID_NUMBER, taskList.getItemCount()));
            return;
        }

        deleteAndConfirm(taskList, ui);
    }

    private boolean isTaskListEmpty(TaskList taskList) {
        return taskList.getItemCount() == 0;
    }

    private boolean isTaskNumberInvalid(TaskList taskList) {
        return taskNumber < 1 || taskNumber > taskList.getItemCount();
    }

    private void deleteAndConfirm(TaskList taskList, Ui ui) {
        taskList.deleteTask(taskNumber).ifPresentOrElse(
                task -> {
                    ui.showMessage(Messages.TASK_REMOVED);
                    ui.showMessage("  " + task);
                    ui.showMessage(String.format(Messages.TASK_COUNT, taskList.getItemCount()));
                },
                () -> ui.showMessage(String.format(Messages.COULD_NOT_DELETE, taskNumber))
        );
    }
}
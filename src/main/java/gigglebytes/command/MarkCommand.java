package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;
import gigglebytes.util.Messages;

/**
 * Represents a command to mark or unmark a task as done.
 */
public class MarkCommand extends Command {
    private int taskNumber;
    private boolean shouldMarkAsDone;

    /**
     * Constructs a MarkCommand for the specified task number and action.
     *
     * @param taskNumber The 1-based index of the task to mark/unmark
     * @param shouldMarkAsDone true to mark as done, false to mark as not done
     */
    public MarkCommand(int taskNumber, boolean shouldMarkAsDone) {
        assert taskNumber > 0 : "Task number must be positive";

        this.taskNumber = taskNumber;
        this.shouldMarkAsDone = shouldMarkAsDone;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (isTaskListEmpty(taskList)) {
            String actionText = shouldMarkAsDone ? "mark as done" : "mark as not done";
            ui.showMessage("Your task list is empty! There's nothing to " + actionText + "! ;-;");
            return;
        }

        if (isTaskNumberInvalid(taskList)) {
            ui.showMessage(String.format(Messages.TASK_NOT_FOUND, taskNumber));
            ui.showMessage(String.format(Messages.CHOOSE_VALID_NUMBER, taskList.getItemCount()));
            return;
        }

        processTask(taskList, ui);
    }

    private boolean isTaskListEmpty(TaskList taskList) {
        return taskList.getItemCount() == 0;
    }

    private boolean isTaskNumberInvalid(TaskList taskList) {
        return taskNumber < 1 || taskNumber > taskList.getItemCount();
    }

    private void processTask(TaskList taskList, Ui ui) {
        taskList.getTask(taskNumber).ifPresentOrElse(
                task -> handleTaskMarking(task, ui),
                () -> ui.showMessage("Could not find task " + taskNumber + "! ;-;")
        );
    }

    private void handleTaskMarking(Task task, Ui ui) {
        String message = shouldMarkAsDone ? task.tryMarkAsDone() : task.tryMarkAsNotDone();
        ui.showMessage(message);
        ui.showMessage("   " + task);
    }
}
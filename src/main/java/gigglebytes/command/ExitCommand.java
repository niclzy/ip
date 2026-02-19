package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.util.Messages;

/**
 * Represents a command to exit the GiggleBytes application.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int completed = 0;
        for (int i = 1; i <= taskList.getItemCount(); i++) {
            gigglebytes.task.Task task = taskList.getTask(i).orElse(null);
            if (task != null && task.isDone()) {
                completed++;
            }
        }

        ui.showMessage(Messages.GOODBYE);
        ui.showMessage(String.format(Messages.TASKS_COMPLETED, completed));
        storage.saveTasks(taskList);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
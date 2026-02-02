package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;

public class ExitCommand extends Command {

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

    @Override
    public boolean isExit() {
        return true;
    }
}
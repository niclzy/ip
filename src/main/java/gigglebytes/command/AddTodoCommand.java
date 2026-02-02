package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

public class AddTodoCommand extends Command {
    private String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        if (!taskList.addTodo(description)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + taskList.getTask(taskList.getItemCount()));
        ui.showMessage("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }
}
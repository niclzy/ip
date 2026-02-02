package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

public class AddDeadlineCommand extends Command {
    private String description;
    private String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        if (!taskList.addDeadline(description, by)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + taskList.getTask(taskList.getItemCount()));
        ui.showMessage("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }
}
package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private String description;
    private String by;

    /**
     * Constructs an AddDeadlineCommand with the specified description and due date/time.
     *
     * @param description The description of the deadline task
     * @param by The due date/time for the deadline
     */
    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the command by adding a deadline task to the task list.
     *
     * @param taskList The TaskList to add the deadline to
     * @param ui The Ui to display messages to the user
     * @param storage The Storage to save tasks (not used in this command)
     * @throws GiggleBytesException If the task list is full and cannot add more tasks
     */
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
package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to add an event task to the task list.
 */
public class AddEventCommand extends Command {
    private String description;
    private String from;
    private String to;

    /**
     * Constructs an AddEventCommand with the specified description, start, and end times.
     *
     * @param description The description of the event task
     * @param from The start date/time for the event
     * @param to The end date/time for the event
     */
    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the command by adding an event task to the task list.
     *
     * @param taskList The TaskList to add the event to
     * @param ui The Ui to display messages to the user
     * @param storage The Storage to save tasks (not used in this command)
     * @throws GiggleBytesException If the task list is full and cannot add more tasks
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        if (!taskList.addEvent(description, from, to)) {
            throw new GiggleBytesException("Task storage is full! Can't add more tasks. ;-;");
        }

        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + taskList.getTask(taskList.getItemCount()));
        ui.showMessage("Now you have " + taskList.getItemCount() + " tasks in the list.");
    }
}
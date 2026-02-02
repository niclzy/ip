package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to add a todo task to the task list.
 */
public class AddTodoCommand extends Command {
    private String description;

    /**
     * Constructs an AddTodoCommand with the specified description.
     *
     * @param description The description of the todo task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command by adding a todo task to the task list.
     *
     * @param taskList The TaskList to add the todo to
     * @param ui The Ui to display messages to the user
     * @param storage The Storage to save tasks (not used in this command)
     * @throws GiggleBytesException If the task list is full and cannot add more tasks
     */
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
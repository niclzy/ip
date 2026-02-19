package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.util.Messages;

/**
 * Represents a command to add a todo task to the task list.
 */
public class AddTodoCommand extends AddCommand {
    private String description;

    /**
     * Constructs an AddTodoCommand with the specified description.
     *
     * @param description The description of the todo task
     */
    public AddTodoCommand(String description) {
        assert description != null : "Description cannot be null";
        assert !description.trim().isEmpty() : "Description cannot be empty";

        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (!taskList.addTodo(description)) {
            throw new GiggleBytesException(Messages.STORAGE_FULL);
        }

        showConfirmation(taskList, ui);
    }
}
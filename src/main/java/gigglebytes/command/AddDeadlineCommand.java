package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.util.Messages;

/**
 * Represents a command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends AddCommand {
    private String description;
    private String by;

    /**
     * Constructs an AddDeadlineCommand with the specified description and due date/time.
     *
     * @param description The description of the deadline task
     * @param by The due date/time for the deadline
     */
    public AddDeadlineCommand(String description, String by) {
        assert description != null : "Description cannot be null";
        assert by != null : "By date cannot be null";
        assert !description.trim().isEmpty() : "Description cannot be empty";
        assert !by.trim().isEmpty() : "By date cannot be empty";

        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (!taskList.addDeadline(description, by)) {
            throw new GiggleBytesException(Messages.STORAGE_FULL);
        }

        showConfirmation(taskList, ui);
    }
}
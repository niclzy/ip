package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.util.Messages;

/**
 * Represents a command to add an event task to the task list.
 */
public class AddEventCommand extends AddCommand {
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
        assert description != null : "Description cannot be null";
        assert from != null : "From date cannot be null";
        assert to != null : "To date cannot be null";
        assert !description.trim().isEmpty() : "Description cannot be empty";
        assert !from.trim().isEmpty() : "From date cannot be empty";
        assert !to.trim().isEmpty() : "To date cannot be empty";

        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (!taskList.addEvent(description, from, to)) {
            throw new GiggleBytesException(Messages.STORAGE_FULL);
        }

        showConfirmation(taskList, ui);
    }
}
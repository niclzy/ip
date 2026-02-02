package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;

/**
 * Represents an executable command in the GiggleBytes application.
 * <p>
 * This is an abstract base class for all command types. Each specific
 * command (e.g., AddTodoCommand, DeleteCommand) extends this class
 * and implements the execute method.
 * </p>
 */
public abstract class Command {
    /**
     * Executes the command with the given context.
     *
     * @param taskList The TaskList containing all tasks
     * @param ui The Ui for user interaction
     * @param storage The Storage for saving/loading tasks
     * @throws GiggleBytesException If an error occurs during command execution
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException;

    /**
     * Returns whether this command should cause the application to exit.
     * <p>
     * By default, commands don't cause the application to exit.
     * Only ExitCommand overrides this to return true.
     * </p>
     *
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
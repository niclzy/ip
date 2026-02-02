package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;

/**
 * Represents a command to find tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase(); // Case-insensitive search
    }

    /**
     * Executes the find command by searching for tasks containing the keyword.
     *
     * @param taskList The TaskList to search through
     * @param ui The Ui to display search results
     * @param storage The Storage (not used in this command)
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        int matchCount = 0;

        ui.showMessage("Here are the matching tasks in your list:");

        for (int i = 1; i <= taskList.getItemCount(); i++) {
            Task task = taskList.getTask(i);
            if (task != null && task.getDescription().toLowerCase().contains(keyword)) {
                matchCount++;
                ui.showMessage(" " + matchCount + "." + task);
            }
        }

        if (matchCount == 0) {
            ui.showMessage("No tasks found containing: " + keyword);
        } else {
            ui.showMessage("Found " + matchCount + " matching task(s).");
        }
    }
}
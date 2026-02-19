package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;
import gigglebytes.util.Messages;

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
        assert keyword != null : "Keyword cannot be null";
        assert !keyword.trim().isEmpty() : "Keyword cannot be empty";

        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert keyword != null : "Keyword should not be null";

        int matchCount = 0;

        ui.showMessage(Messages.MATCHING_TASKS);

        for (int i = 1; i <= taskList.getItemCount(); i++) {
            Task task = taskList.getTask(i).orElse(null);
            if (task != null && task.getDescription().toLowerCase().contains(keyword)) {
                matchCount++;
                ui.showMessage(" " + matchCount + "." + task);
            }
        }

        if (matchCount == 0) {
            ui.showMessage(String.format(Messages.NO_MATCHES, keyword));
        } else {
            ui.showMessage(String.format(Messages.FOUND_MATCHES, matchCount));
        }
    }
}
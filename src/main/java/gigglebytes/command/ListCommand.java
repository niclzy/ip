package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.GuiUi;  // Add this import

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        // Check if we're in GUI mode
        if (ui instanceof GuiUi) {
            // For GUI: capture the output as a string
            StringBuilder sb = new StringBuilder();

            if (taskList.getItemCount() == 0) {
                sb.append("Your list is empty! Add something first!");
            } else {
                sb.append("Here are your tasks:\n");
                for (int i = 1; i <= taskList.getItemCount(); i++) {
                    sb.append("  ").append(i).append(". ")
                            .append(taskList.getTask(i).getDisplayString()).append("\n");
                }
                sb.append("Total tasks: ").append(taskList.getItemCount());
            }

            // Send to GUI through Ui
            ui.showMessage(sb.toString().trim());
        } else {
            // For CLI: print to console
            taskList.printAllItems();
        }
    }
}
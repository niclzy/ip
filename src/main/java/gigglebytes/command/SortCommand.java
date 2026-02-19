package gigglebytes.command;

import gigglebytes.exception.GiggleBytesException;
import gigglebytes.storage.Storage;
import gigglebytes.util.TaskList;
import gigglebytes.Ui;
import gigglebytes.task.Task;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;
import gigglebytes.util.Messages;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a command to sort tasks by various criteria.
 */
public class SortCommand extends Command {

    private enum SortType {
        DATE, DESCRIPTION, STATUS, TYPE
    }

    private SortType sortType;
    private boolean reverse;

    /**
     * Constructs a SortCommand with the specified sort type.
     *
     * @param sortType The type of sort to perform
     * @param reverse Whether to reverse the sort order
     */
    public SortCommand(String sortType, boolean reverse) {
        assert sortType != null : "Sort type cannot be null";

        switch (sortType.toLowerCase()) {
            case "date":
            case "deadline":
            case "time":
                this.sortType = SortType.DATE;
                break;
            case "description":
            case "desc":
            case "name":
                this.sortType = SortType.DESCRIPTION;
                break;
            case "status":
            case "done":
            case "complete":
                this.sortType = SortType.STATUS;
                break;
            case "type":
            case "category":
                this.sortType = SortType.TYPE;
                break;
            default:
                this.sortType = SortType.DESCRIPTION; // Default
        }

        this.reverse = reverse;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws GiggleBytesException {
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";

        if (taskList.getItemCount() == 0) {
            ui.showMessage("Your list is empty! Nothing to sort.");
            return;
        }

        // Get all tasks
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= taskList.getItemCount(); i++) {
            taskList.getTask(i).ifPresent(tasks::add);
        }

        // Sort the tasks
        sortTasks(tasks);

        // Clear and re-add sorted tasks
        for (int i = taskList.getItemCount(); i > 0; i--) {
            taskList.deleteTask(i);
        }

        for (Task task : tasks) {
            addTaskToTaskList(taskList, task);
        }

        // Show confirmation
        String sortDesc = getSortDescription();
        ui.showMessage("Tasks sorted by " + sortDesc + "!");

        // Show the sorted list
        if (ui instanceof gigglebytes.GuiUi) {
            ui.showMessage(taskList.listAllItems());
        } else {
            taskList.printAllItems();
        }
    }

    private void sortTasks(List<Task> tasks) {
        Comparator<Task> comparator = getComparator();
        if (reverse) {
            comparator = comparator.reversed();
        }
        tasks.sort(comparator);
    }

    private Comparator<Task> getComparator() {
        switch (sortType) {
            case DATE:
                return Comparator.comparing(this::getTaskDate,
                        Comparator.nullsLast(Comparator.naturalOrder()));
            case DESCRIPTION:
                return Comparator.comparing(Task::getDescription,
                        String.CASE_INSENSITIVE_ORDER);
            case STATUS:
                return (t1, t2) -> {
                    if (t1.isDone() == t2.isDone()) {
                        return 0;
                    }
                    return t1.isDone() ? 1 : -1;
                };
            case TYPE:
                return Comparator.comparing(Task::getTypeIcon)
                        .thenComparing(Task::getDescription);
            default:
                return Comparator.comparing(Task::getDescription);
        }
    }

    private String getTaskDate(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            return ((Event) task).getFrom();
        }
        return null; // Todo tasks have no date
    }

    private String getSortDescription() {
        String typeDesc;
        switch (sortType) {
            case DATE:
                typeDesc = "date";
                break;
            case DESCRIPTION:
                typeDesc = "description";
                break;
            case STATUS:
                typeDesc = "completion status";
                break;
            case TYPE:
                typeDesc = "task type";
                break;
            default:
                typeDesc = "description";
        }

        return reverse ? typeDesc + " (descending)" : typeDesc;
    }

    private void addTaskToTaskList(TaskList taskList, Task task) {
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            taskList.addDeadline(d.getDescription(), d.getBy());
        } else if (task instanceof Event) {
            Event e = (Event) task;
            taskList.addEvent(e.getDescription(), e.getFrom(), e.getTo());
        } else {
            taskList.addTodo(task.getDescription());
        }

        // Preserve done status
        taskList.getTask(taskList.getItemCount()).ifPresent(t -> {
            if (task.isDone()) {
                t.markAsDone();
            }
        });
    }
}
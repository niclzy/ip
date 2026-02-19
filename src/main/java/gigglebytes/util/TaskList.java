package gigglebytes.util;

import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;

import java.util.List;
import java.util.Optional;

/**
 * Manages a collection of tasks in the GiggleBytes application.
 */
public class TaskList extends DisplayableCollection {

    public TaskList(int maxCapacity) {
        super(maxCapacity);
        assert maxCapacity > 0 : "Max capacity must be positive";
    }

    public TaskList(int maxCapacity, List<Task> loadedTasks) {
        super(maxCapacity);
        assert maxCapacity > 0 : "Max capacity must be positive";
        assert loadedTasks != null : "Loaded tasks list cannot be null";

        for (Task task : loadedTasks) {
            items.add(task);
            itemCount++;
        }

        assert itemCount == loadedTasks.size() : "Item count should match loaded tasks size";
    }

    public boolean addTodo(String description) {
        assert description != null : "Todo description cannot be null";
        assert !description.trim().isEmpty() : "Todo description cannot be empty";

        items.add(new Todo(description));
        itemCount++;

        assert getTask(itemCount).isPresent() : "Task should be added successfully";
        return true;
    }

    public boolean addDeadline(String description, String by) {
        assert description != null : "Deadline description cannot be null";
        assert by != null : "Deadline by date cannot be null";
        assert !description.trim().isEmpty() : "Deadline description cannot be empty";
        assert !by.trim().isEmpty() : "Deadline by date cannot be empty";

        items.add(new Deadline(description, by));
        itemCount++;

        Optional<Task> added = getTask(itemCount);
        assert added.isPresent() && added.get() instanceof Deadline : "Added task should be a Deadline";
        return true;
    }

    public boolean addEvent(String description, String from, String to) {
        assert description != null : "Event description cannot be null";
        assert from != null : "Event from date cannot be null";
        assert to != null : "Event to date cannot be null";
        assert !description.trim().isEmpty() : "Event description cannot be empty";
        assert !from.trim().isEmpty() : "Event from date cannot be empty";
        assert !to.trim().isEmpty() : "Event to date cannot be empty";

        items.add(new Event(description, from, to));
        itemCount++;

        Optional<Task> added = getTask(itemCount);
        assert added.isPresent() && added.get() instanceof Event : "Added task should be an Event";
        return true;
    }

    /**
     * Returns the task at the specified position using Optional.
     *
     * @param index The 1-based index of the task to retrieve
     * @return Optional containing the task, or empty if index is invalid
     */
    public Optional<Task> getTask(int index) {
        if (index >= 1 && index <= itemCount) {
            assert items.get(index - 1) instanceof Task : "Item at valid index should be a Task";
            return Optional.of((Task) items.get(index - 1));
        }
        return Optional.empty();
    }

    /**
     * Returns the last task in the list.
     *
     * @return Optional containing the last task, or empty if list is empty
     */
    public Optional<Task> getLastTask() {
        return getTask(itemCount);
    }

    /**
     * Removes and returns the task at the specified position using Optional.
     *
     * @param index The 1-based index of the task to delete
     * @return Optional containing the deleted task, or empty if index is invalid
     */
    public Optional<Task> deleteTask(int index) {
        if (index >= 1 && index <= itemCount) {
            assert items.get(index - 1) instanceof Task : "Item to delete should be a Task";
            int oldCount = itemCount;

            Displayable removed = removeItem(index);

            assert itemCount == oldCount - 1 : "Item count should decrease by 1 after deletion";
            return Optional.ofNullable((removed instanceof Task) ? (Task) removed : null);
        }
        return Optional.empty();
    }

    /**
     * Returns a string representation of all tasks.
     */
    public String listAllItems() {
        if (itemCount == 0) {
            return Messages.EMPTY_LIST;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Messages.LIST_HEADER).append("\n");
        for (int i = 0; i < itemCount; i++) {
            sb.append("  ").append(i + 1).append(". ")
                    .append(items.get(i).getDisplayString()).append("\n");
        }
        sb.append(String.format(Messages.LIST_TOTAL, itemCount));
        return sb.toString();
    }
}
package gigglebytes.util;

import gigglebytes.task.Task;
import gigglebytes.task.Todo;
import gigglebytes.task.Deadline;
import gigglebytes.task.Event;

import java.util.List;

/**
 * Manages a collection of tasks in the GiggleBytes application.
 * <p>
 * This class extends DisplayableCollection and provides specific
 * methods for adding, retrieving, and deleting tasks.
 * </p>
 */
public class TaskList extends DisplayableCollection {
    /**
     * Constructs a new empty TaskList with the specified maximum capacity.
     *
     * @param maxCapacity The maximum number of tasks the list can hold
     */
    public TaskList(int maxCapacity) {
        super(maxCapacity);
    }

    /**
     * Constructs a new TaskList with pre-loaded tasks.
     *
     * @param maxCapacity The maximum number of tasks the list can hold
     * @param loadedTasks A list of tasks to initialize the TaskList with
     */
    public TaskList(int maxCapacity, List<Task> loadedTasks) {
        super(maxCapacity);
        for (Task task : loadedTasks) {
            items.add(task);
            itemCount++;
        }
    }

    /**
     * Adds a new todo task to the list.
     *
     * @param description The description of the todo
     * @return true if the todo was added successfully
     */
    public boolean addTodo(String description) {
        items.add(new Todo(description));
        itemCount++;
        return true;
    }

    /**
     * Adds a new deadline task to the list.
     *
     * @param description The description of the deadline
     * @param by The due date/time of the deadline
     * @return true if the deadline was added successfully
     */
    public boolean addDeadline(String description, String by) {
        items.add(new Deadline(description, by));
        itemCount++;
        return true;
    }

    /**
     * Adds a new event task to the list.
     *
     * @param description The description of the event
     * @param from The start date/time of the event
     * @param to The end date/time of the event
     * @return true if the event was added successfully
     */
    public boolean addEvent(String description, String from, String to) {
        items.add(new Event(description, from, to));
        itemCount++;
        return true;
    }

    /**
     * Returns the task at the specified position in the list.
     * <p>
     * Positions are 1-indexed (first task is at position 1).
     * </p>
     *
     * @param index The 1-based index of the task to retrieve
     * @return The task at the specified index, or null if the index is invalid
     */
    public Task getTask(int index) {
        Displayable item = getItem(index);
        return (item instanceof Task) ? (Task) item : null;
    }

    /**
     * Removes and returns the task at the specified position in the list.
     * <p>
     * Positions are 1-indexed (first task is at position 1).
     * </p>
     *
     * @param index The 1-based index of the task to delete
     * @return The deleted task, or null if the index is invalid
     */
    public Task deleteTask(int index) {
        Displayable removed = removeItem(index);
        return (removed instanceof Task) ? (Task) removed : null;
    }
}
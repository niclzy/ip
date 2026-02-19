package gigglebytes.task;

import gigglebytes.util.Displayable;

/**
 * Represents a task in the GiggleBytes application.
 * <p>
 * This is an abstract base class for all task types (Todo, Deadline, Event).
 * Each task has a description and a completion status.
 * </p>
 */
public abstract class Task implements Displayable {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * <p>
     * The task is initially marked as not done.
     * </p>
     *
     * @param description The description of the task
     */
    public Task(String description) {
        assert description != null : "Task description cannot be null";
        assert !description.trim().isEmpty() : "Task description cannot be empty";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        assert description != null : "Description should never be null after construction";
        return description;
    }

    /**
     * Returns whether the task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        assert !isDone : "Task should not be already done when marking as done";
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        assert isDone : "Task should be done when marking as not done";
        this.isDone = false;
    }

    /**
     * Returns the status icon for display purposes.
     * <p>
     * [X] for done tasks, [ ] for not done tasks.
     * </p>
     *
     * @return The status icon string
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the type icon for this task.
     * <p>
     * Must be implemented by subclasses:
     * - Todo returns "T"
     * - Deadline returns "D"
     * - Event returns "E"
     * </p>
     *
     * @return The type icon string
     */
    public abstract String getTypeIcon();

    /**
     * Returns the display string representation of the task.
     * <p>
     * Format: [TypeIcon][StatusIcon] Description
     * Example: [T][X] Buy groceries
     * </p>
     *
     * @return The display string for the task
     */
    @Override
    public String getDisplayString() {
        assert getTypeIcon() != null : "Type icon cannot be null";
        return "[" + getTypeIcon() + "]" + getStatusIcon() + " " + getDescription();
    }

    /**
     * Returns the string representation of the task.
     * <p>
     * Same as getDisplayString().
     * </p>
     *
     * @return The string representation of the task
     */
    @Override
    public String toString() {
        return getDisplayString();
    }
}
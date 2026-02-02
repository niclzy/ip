package gigglebytes.task;

/**
 * Represents a todo task with only a description.
 * <p>
 * A todo is the simplest type of task with no date/time associated.
 * </p>
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description The description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for todo tasks.
     *
     * @return "T" for todo
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }
}
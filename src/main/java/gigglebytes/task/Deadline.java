package gigglebytes.task;

/**
 * Represents a deadline task with a due date/time.
 * <p>
 * A deadline has a description and a "by" date/time when it must be completed.
 * </p>
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Constructs a new Deadline task with the given description and due date/time.
     *
     * @param description The description of the deadline
     * @param by The due date/time in string format
     */
    public Deadline(String description, String by) {
        super(description);

        assert by != null : "Deadline 'by' date cannot be null";
        assert !by.trim().isEmpty() : "Deadline 'by' date cannot be empty";

        this.by = by;
    }

    /**
     * Returns the due date/time of this deadline.
     *
     * @return The due date/time string
     */
    public String getBy() {
        assert by != null : "By date should never be null after construction";
        return by;
    }

    /**
     * Returns the type icon for deadline tasks.
     *
     * @return "D" for deadline
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the display string representation of the deadline.
     * <p>
     * Format: [D][StatusIcon] Description (by: DueDateTime)
     * Example: [D][ ] Submit report (by: 2024-12-31 2359)
     * </p>
     *
     * @return The display string for the deadline
     */
    @Override
    public String getDisplayString() {
        assert by != null : "By date should not be null when displaying";
        return super.getDisplayString() + " (by: " + by + ")";
    }
}
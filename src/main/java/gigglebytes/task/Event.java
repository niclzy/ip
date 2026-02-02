package gigglebytes.task;

/**
 * Represents an event task with a start and end date/time.
 * <p>
 * An event has a description, a start time ("from"), and an end time ("to").
 * </p>
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Constructs a new Event task with the given description, start, and end times.
     *
     * @param description The description of the event
     * @param from The start date/time in string format
     * @param to The end date/time in string format
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return The start date/time string
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return The end date/time string
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the type icon for event tasks.
     *
     * @return "E" for event
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Returns the display string representation of the event.
     * <p>
     * Format: [E][StatusIcon] Description (from: StartDateTime to: EndDateTime)
     * Example: [E][ ] Team meeting (from: 2024-10-10 1400 to: 2024-10-10 1500)
     * </p>
     *
     * @return The display string for the event
     */
    @Override
    public String getDisplayString() {
        return super.getDisplayString() + " (from: " + from + " to: " + to + ")";
    }
}
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");

    public Event(String description, String fromString, String toString) throws GiggleBytesException {
        super(description);
        this.from = parseDateTime(fromString);
        this.to = parseDateTime(toString);

        // Validate that 'from' is before 'to'
        if (!from.isBefore(to)) {
            throw new GiggleBytesException("Start time must be before end time!");
        }
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws GiggleBytesException {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new GiggleBytesException("Invalid date/time format! Please use: yyyy-MM-dd HHmm (e.g., 2024-12-31 1800)");
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getFromString() {
        return from.format(DISPLAY_FORMAT);
    }

    public String getToString() {
        return to.format(DISPLAY_FORMAT);
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() + " (from: " + getFromString() + " to: " + getToString() + ")";
    }
}
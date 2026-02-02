import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;  // Add this import

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");

    public Deadline(String description, String byString) throws GiggleBytesException {
        super(description);
        this.by = parseDateTime(byString);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws GiggleBytesException {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new GiggleBytesException("Invalid date/time format! Please use: yyyy-MM-dd HHmm (e.g., 2024-12-31 1800)");
        }
    }

    public LocalDateTime getBy() {
        return by;
    }

    public String getByString() {
        return by.format(DISPLAY_FORMAT);
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String getDisplayString() {
        return super.getDisplayString() + " (by: " + getByString() + ")";
    }
}
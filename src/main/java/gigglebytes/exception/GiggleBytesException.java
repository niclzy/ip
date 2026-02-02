package gigglebytes.exception;

/**
 * Represents an exception specific to the GiggleBytes application.
 * <p>
 * This exception is thrown when errors occur during command parsing
 * or execution in the GiggleBytes application.
 * </p>
 */
public class GiggleBytesException extends Exception {
    /**
     * Constructs a new GiggleBytesException with the specified detail message.
     *
     * @param message The detail message explaining the error
     */
    public GiggleBytesException(String message) {
        super(message);
    }
}
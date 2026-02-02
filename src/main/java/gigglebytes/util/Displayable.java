package gigglebytes.util;

/**
 * Represents an object that can be displayed in the user interface.
 * <p>
 * Classes implementing this interface provide a string representation
 * suitable for display to the user.
 * </p>
 */
public interface Displayable {
    /**
     * Returns a string representation of the object suitable for display.
     *
     * @return The display string representation
     */
    String getDisplayString();
}
package gigglebytes;

/**
 * A UI implementation that captures output for the GUI instead of printing to console.
 */
public class GuiUi extends Ui {
    private StringBuilder output = new StringBuilder();

    @Override
    public void showWelcome() {
        // Not needed for GUI
    }

    @Override
    public void showHelp() {
        // Not needed for GUI
    }

    @Override
    public void showLine() {
        output.append("------------------------------------------------------------------------------\n");
    }

    @Override
    public void showError(String message) {
        output.append(message).append("\n");
    }

    @Override
    public void showMessage(String message) {
        output.append(message).append("\n");
    }

    /**
     * Returns the captured output and clears the buffer.
     *
     * @return The captured output string
     */
    public String getOutput() {
        String result = output.toString();
        output = new StringBuilder(); // Clear buffer
        return result.trim();
    }
}
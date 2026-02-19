package gigglebytes;

/**
 * A UI implementation that captures output for the GUI instead of printing to console.
 */
public class GuiUi extends Ui {
    private StringBuilder output = new StringBuilder();

    @Override
    public void showLine() {
        output.append("------------------------------------------------------------------------------\n");
    }

    @Override
    public void showError(String message) {
        assert message != null : "Error message cannot be null";
        output.append(message).append("\n");
    }

    @Override
    public void showMessage(String message) {
        assert message != null : "Message cannot be null";
        output.append(message).append("\n");
    }

    /**
     * Returns the captured output and clears the buffer.
     *
     * @return The captured output string
     */
    public String getOutput() {
        String result = output.toString();
        output = new StringBuilder();
        return result.trim();
    }
}
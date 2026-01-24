public class Task implements Displayable {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String getDisplayString() {
        return getStatusIcon() + " " + getDescription();
    }

    @Override
    public String toString() {
        return getDisplayString();
    }
}

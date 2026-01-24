public class TaskList extends DisplayableCollection {
    public TaskList(int maxCapacity) {
        super(maxCapacity);
    }

    public boolean addTask(String description) {
        if (itemCount < items.length) {
            items[itemCount] = new Task(description);
            itemCount++;
            return true;
        }
        return false;
    }

    public Task getTask(int index) {
        Displayable item = getItem(index);
        return (item instanceof Task) ? (Task) item : null;
    }
}

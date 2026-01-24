public class TaskList extends DisplayableCollection {
    public TaskList(int maxCapacity) {
        super(maxCapacity);
    }

    public boolean addTodo(String description) {
        if (itemCount < items.length) {
            items[itemCount] = new Todo(description);
            itemCount++;
            return true;
        }
        return false;
    }

    public boolean addDeadline(String description, String by) {
        if (itemCount < items.length) {
            items[itemCount] = new Deadline(description, by);
            itemCount++;
            return true;
        }
        return false;
    }

    public boolean addEvent(String description, String from, String to) {
        if (itemCount < items.length) {
            items[itemCount] = new Event(description, from, to);
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

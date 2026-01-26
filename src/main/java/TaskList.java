public class TaskList extends DisplayableCollection {
    public TaskList(int maxCapacity) {
        super(maxCapacity);
    }

    public boolean addTodo(String description) {
        items.add(new Todo(description));
        itemCount++;
        return true;
    }

    public boolean addDeadline(String description, String by) {
        items.add(new Deadline(description, by));
        itemCount++;
        return true;
    }

    public boolean addEvent(String description, String from, String to) {
        items.add(new Event(description, from, to));
        itemCount++;
        return true;
    }

    public Task getTask(int index) {
        Displayable item = getItem(index);
        return (item instanceof Task) ? (Task) item : null;
    }

    public Task deleteTask(int index) {
        Displayable removed = removeItem(index);
        return (removed instanceof Task) ? (Task) removed : null;
    }
}
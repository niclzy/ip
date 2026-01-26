import java.util.ArrayList;
import java.util.List;

abstract class DisplayableCollection {
    protected List<Displayable> items;
    protected int itemCount;

    public DisplayableCollection(int maxCapacity) {
        this.items = new ArrayList<>(maxCapacity);
        this.itemCount = 0;
    }

    public int getItemCount() {
        return itemCount;
    }

    public Displayable getItem(int index) {
        if (index >= 1 && index <= itemCount) {
            return items.get(index - 1);
        }
        return null;
    }

    public void printAllItems() {
        if (itemCount == 0) {
            System.out.println("Your list is empty! Add something first!");
            return;
        }

        System.out.println("Here are your tasks:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println("  " + (i + 1) + ". " + items.get(i).getDisplayString());
        }
        System.out.println("Total tasks: " + itemCount);
    }

    public Displayable removeItem(int index) {
        if (index >= 1 && index <= itemCount) {
            Displayable removed = items.remove(index - 1);
            itemCount--;
            return removed;
        }
        return null;
    }
}
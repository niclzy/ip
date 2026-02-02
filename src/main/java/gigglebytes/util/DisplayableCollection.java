package gigglebytes.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for collections of Displayable objects.
 * <p>
 * Provides basic operations for managing and displaying a collection
 * of items that implement the Displayable interface.
 * </p>
 */
abstract class DisplayableCollection {
    protected List<Displayable> items;
    protected int itemCount;

    /**
     * Constructs a new DisplayableCollection with the specified maximum capacity.
     *
     * @param maxCapacity The maximum number of items the collection can hold
     */
    public DisplayableCollection(int maxCapacity) {
        this.items = new ArrayList<>(maxCapacity);
        this.itemCount = 0;
    }

    /**
     * Returns the number of items in the collection.
     *
     * @return The current item count
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Returns the item at the specified position in the collection.
     * <p>
     * Positions are 1-indexed (first item is at position 1).
     * </p>
     *
     * @param index The 1-based index of the item to retrieve
     * @return The item at the specified index, or null if the index is invalid
     */
    public Displayable getItem(int index) {
        if (index >= 1 && index <= itemCount) {
            return items.get(index - 1);
        }
        return null;
    }

    /**
     * Prints all items in the collection to standard output.
     * <p>
     * If the collection is empty, displays an appropriate message.
     * Otherwise, displays each item with its position number.
     * </p>
     */
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

    /**
     * Removes and returns the item at the specified position in the collection.
     * <p>
     * Positions are 1-indexed (first item is at position 1).
     * </p>
     *
     * @param index The 1-based index of the item to remove
     * @return The removed item, or null if the index is invalid
     */
    public Displayable removeItem(int index) {
        if (index >= 1 && index <= itemCount) {
            Displayable removed = items.remove(index - 1);
            itemCount--;
            return removed;
        }
        return null;
    }
}
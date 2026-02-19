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
        assert maxCapacity > 0 : "Max capacity must be positive";

        this.items = new ArrayList<>(maxCapacity);
        this.itemCount = 0;

        assert items != null : "Items list should be initialized";
        assert itemCount == 0 : "Item count should start at 0";
    }

    /**
     * Returns the number of items in the collection.
     *
     * @return The current item count
     */
    public int getItemCount() {
        assert itemCount >= 0 : "Item count cannot be negative";
        assert itemCount <= items.size() : "Item count cannot exceed list size";

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
            assert items.get(index - 1) != null : "Items at valid indices should not be null";
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

        assert itemCount > 0 : "Item count should be positive when printing non-empty list";

        System.out.println("Here are your tasks:");
        for (int i = 0; i < itemCount; i++) {
            assert items.get(i) != null : "All items in collection should be non-null";
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
            assert items.get(index - 1) != null : "Item to remove should not be null";

            int oldCount = itemCount;
            Displayable removed = items.remove(index - 1);
            itemCount--;

            assert itemCount == oldCount - 1 : "Item count should decrease by 1 after removal";
            return removed;
        }
        return null;
    }
}
package items;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Constructor for ItemInventory
 */
public class ItemInventory {
    private final Map<Integer, Item> approvedItems = new HashMap<>(); // Items approved by Admin and their ID.
    private final Map<Integer, Item> pendingItems = new HashMap<>(); // Items awaiting approval and their IDs.

    /**
     * The constructor.
     *
     * @param systemItems a list of all items contained in the system
     */
    public ItemInventory(List<Item> systemItems) {
        for (Item systemItem : systemItems) {
            Integer itemID = systemItem.getItemID();
            if (systemItem.isApproved()) {
                approvedItems.put(itemID, systemItem);
            } else {
                pendingItems.put(itemID, systemItem);
            }
        }
    }

    /**
     * Returns the item with the given ID.
     *
     * @param itemID the id of the item to get.
     * @return the Item with itemID.
     */
    public Item getItemWithID(Integer itemID) {
        if (approvedItems.containsKey(itemID)) {
            return approvedItems.get(itemID);
        } else return pendingItems.get(itemID);
    }

    /**
     * Returns a list containing all approved items in the system
     *
     * @return a list of all approved Items.
     */
    public List<Item> getApprovedItems() {
        List<Item> approvedList = new ArrayList<>();
        for (Integer key : approvedItems.keySet()) {
            approvedList.add(approvedItems.get(key));
        }
        return approvedList;
    }

    /**
     * Returns a list containing all items in the system that are waiting to be approved
     *
     * @return a list of items waiting to be approved
     */
    public List<Item> getPendingItems() {
        List<Item> pendingList = new ArrayList<>();
        for (Integer key : pendingItems.keySet()) {
            pendingList.add(pendingItems.get(key));
        }
        return pendingList;
    }

    /**
     * Adds the requestedItem to the list of pending items.
     *
     * @param requestedItem the item to be added.
     */
    public void addToPending(Item requestedItem) {
        pendingItems.put(requestedItem.getItemID(), requestedItem);
    }

    /**
     * Adds the itemToApprove to the list of approved items.
     *
     * @param itemToApprove the item to be added
     */
    public void addToApproved(Item itemToApprove) {
        approvedItems.put(itemToApprove.getItemID(), itemToApprove);
    }

    /**
     * Removes itemToRemove from the list of approved items.
     *
     * @param itemToRemove the item to be removed from the list of approved items.
     */
    public void removeFromApproved(Item itemToRemove) {
        approvedItems.remove(itemToRemove.getItemID());
    }

    /**
     * Removes itemToRemove from the list of pending items.
     *
     * @param itemToRemove the item to be removed from the list of pending items.
     */
    public void removeFromPending(Item itemToRemove) {
        pendingItems.remove(itemToRemove.getItemID());
    }

    /**
     * Returns the userID of the owner of the item with the given ID
     *
     * @param itemID the ID of the item
     * @return the ID of the owner
     */
    public Integer getOwnerID(Integer itemID) {
        Item item = getItemWithID(itemID);
        return item.getOwnerID();
    }

}

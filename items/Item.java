package items;

/**
 * @author Rutwa Engineer
 */

public class Item {
	private final Integer ownerID;
	private final String itemName;
	private final String description;
	private Integer itemID;
	private boolean approved;
	private Double price;

	/**
	 * Item constructor
	 * @param ownerID the ID associated with the UserAccount that owns the item
	 * @param itemName name of the item
	 * @param description describe the item
	 */
	public Item(Integer ownerID, String itemName, String description){
		this.ownerID = ownerID;
		this.itemName = itemName;
		this.description = description;
	}

	/**
	 * Returns the userID of the items owner
	 * @return the userID of the item owner
	 */
	public Integer getOwnerID() {
		return ownerID;
	}

	/**
	 * Returns the item's name
	 * @return the item's name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Returns the item's description
	 * @return the item's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the item's ID
	 * @return the item's ID
	 */
	public Integer getItemID() {
		return itemID;
	}

	/**
	 * Sets the items ID
	 * @param itemID the itemID to set
	 */
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

	/**
	 * Returns true iff the item has been approved by an admin.
	 * @return true iff the item is approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Approves an item.
	 */
	public void approve() {
		approved = true;
	}

	/**
	 * Sets the approved value
	 * @param isApproved whether the item is approved or not
	 */
	public void setApproved(boolean isApproved){
		this.approved = isApproved;
	}

	/**
	 * Returns the item's value
	 * @return the item's value
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the item's value
	 * @param price the item's value
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Returns a String containing the item's name, description, and ID
	 * @return the string of details of the item
	 */
	@Override
	public String toString() {
		return "Item{" +
				"itemName:" + itemName + '\'' +
				", description:" + description + '\'' +
				", itemID:" + itemID +
				'}';
	}
}

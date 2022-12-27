package accounts;

import java.util.ArrayList;
import java.util.List;

import items.Item;

/**
 * @author Rutwa Engineer
 */
public class UserAccount extends Account {
	public boolean onVacation;
    private List<Item> wishlist = new ArrayList<>();
	private List<Item> inventory = new ArrayList<>();
	private int overBorrowed = 0; //borrowed items - lent items > 0
	private int numTrade = 0;  //# of times the User has traded
	private int incompleteTrade = 0;
	private boolean isFrozen = false; //account
	private Double totalFunds;
	private String city;
	protected String mode = "";
	private boolean unfreezeRequested = false;
	//protected boolean isDemoUser;

	/**
	 * User constructor
	 * @param password the user's password
	 */
	public UserAccount(String password) {
		super(password);
		//this.isDemoUser = false;
	}


	/**
	 * sets the mode of the user
	 * @param mode the mode of the user
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Gets the wishlist
	 * @return a list of items inside the wishlist
	 */
	public List<Item> getWishlist() {
		return wishlist;
	}

	/**
	 * Sets the wishlist
	 * @param wishlist wishlist
	 */
	public void setWishlist(List<Item> wishlist) {
		this.wishlist = wishlist;
	}

	/**
	 * Add an item to the wishlist
	 * @param i Item
	 */
	public void addToWishlist (Item i){
		wishlist.add(i);
	}

	/**
	 * Removes item from the wishlist
	 * @param i Item
	 */
	public void removeFromWishlist(Item i){
		wishlist.remove(i);
	}

	/**
	 * Remove an item from the wishlist with the given ID
	 * @param itemID item to remove
	 */
	public void removeFromWishlist(Integer itemID){
		Item toRemove = null;
		for (Item item : wishlist) {
			if (item.getItemID().equals(itemID)) {
				toRemove = item;
			}
		}
		if(!(toRemove == null)) {
			removeFromWishlist(toRemove);
		}
	}

	/**
	 * Add an item to the inventory
	 * @param i Item
	 */
	public void addToInventory(Item i){
		inventory.add(i);
	}

	/**
	 * Remove an item from the inventory
	 * @param i Item
	 */
	public void removeFromInventory(Item i){
		inventory.remove(i);
	}

	/**
	 * Remove an item from the inventory with the given ID
	 * @param itemID item to remove
	 */
	public void removeFromInventory(Integer itemID){
		Item itemToRemove = null;
		boolean itemExists = false;
		for (Item item : inventory) {
			if (item.getItemID().equals(itemID)) {
				itemToRemove = item;
				itemExists = true;
			}
		}
		if (itemExists) {
			removeFromInventory(itemToRemove);
		}
	}

	/**
	 * Gets the inventory
	 * @return the list of items in inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	/**
	 * Sets the inventory
	 * @param inventory inventory
	 */
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

	/**
	 * Returns true iff the user has the item in their inventory.
	 * @param item The item to check
	 * @return true iff the user owns the item
	 */
	public boolean ownsItem(Item item) {
		boolean owns = false;
		try {
			for (Item myItem : inventory) {
				if (item.getItemID().equals(myItem.getItemID())) {
					owns = true;
					break;
				}
			}
		} catch (NullPointerException e) {
			owns = false;
		}
		return owns;
	}

	/**
	 * Returns true iff the user has the item in their wishlist
	 * @param item The item to check
	 * @return true iff the user wants the item
	 */
	public boolean wantsItem(Item item) {
		boolean wants = false;
		try {
			for (Item myItem : wishlist) {
				if (item.getItemID().equals(myItem.getItemID())) {
					wants = true;
					break;
				}
			}
		} catch (NullPointerException e) {
			wants = false;
		}
		return wants;
	}

	/**
	 * Gets the difference between the number of borrowed items and lent items
	 * @return the number of item that were over borrowed
	 */
	public int getOverBorrowed() {
		return overBorrowed;
	}

	/**
	 * Sets the number of over borrowed items
	 * @param overBorrowed number of over borrowed items
	 */
	public void setOverBorrowed(int overBorrowed) {
		this.overBorrowed = overBorrowed;
	}

	/**
	 * Gets the number of trades the user made
	 * @return the number of trades
	 */
	public int getNumTrade() {
		return numTrade;
	}

	/**
	 * Sets the number of trades
	 * @param numTrade number of trades
	 */
	public void setNumTrade(int numTrade) {
		this.numTrade = numTrade;
	}

	/**
	 * Gets the number of incomplete trades
	 * @return the number of incomplete trades
	 */
	public int getIncompleteTrade() {
		return incompleteTrade;
	}

	/**
	 * Sets the number of incomplete trades
	 * @param incompleteTrade number of incomplete trades
	 */
	public void setIncompleteTrade(int incompleteTrade) {
		this.incompleteTrade = incompleteTrade;
	}

	/**
	 * Gets if the account is frozen
	 * @return true if account is frozen, else false
	 */
	public boolean isFrozen() {
		return isFrozen;
	}

	/**
	 * Freeze or unfreeze an account
	 * @param freeze account frozen status
	 */
	public void setFrozen(Boolean freeze)
	{
		this.isFrozen = freeze;
	}

	/**
	 * Gets the amount total funds in the user account
	 * @return the total funds
	 */
	public Double getTotalFunds() {
		return totalFunds;
	}

	/**
	 * Sets the total funds
	 * @param totalFunds the total funds
	 */
	public void setTotalFunds(Double totalFunds) {
		this.totalFunds = totalFunds;
	}

	/**
	 * gets the home city of the user
	 * @return the home city of the user
	 */
	public String getCity() {
		return city;
	}

	/**
	 * sets the home city of the user
	 * @param city the home city of the user
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * User account who is on vacation
	 * @param user the user to on vacation
	 */
	public void userOnVacation (UserAccount user) { user.setOnVacation(true);}

	/**
	 * User account who is not on vacation
	 * @param user the user to not on vacation
	 */
	public void  userNotOnVacation (UserAccount user) { user.setOnVacation(false);}

	/**
	 * returns the status if the user is on vacation
	 * @return boolean if the user is on vacation
	 */
	public boolean isOnVacation() {
		return onVacation;
	}

	/**
	 * sets the status of if the user is on vacation or not
	 * @param onVacation boolean if the user is on vacation
	 */
	public void setOnVacation(boolean onVacation) {
		this.onVacation = onVacation;
	}

	/**
	 * Set the status of whether the user has requested to unfreeze.
	 * @param unfreezeRequested the new value of whether the user has requested to unfreeze.
	 */
	public void setUnfreezeRequested(boolean unfreezeRequested){
		this.unfreezeRequested = unfreezeRequested;
	}

	/**
	 * Return whether the user has requested to unfreeze the account.
	 * @return true iff the user has requested to unfreeze the account.
	 */
	public boolean getUnfreezeRequested(){
		return this.unfreezeRequested;
	}

}



package controllers;

import accounts.UserAccount;
import accounts.UserManager;
import database.DataReader;
import items.Item;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagerBuilder {

    private final DataReader dataReader;
    private String mode;

    /**
     * Constructor
     * @param mode the mode of the user
     */
    public UserManagerBuilder(String mode) {
    	this.mode = mode;
        dataReader = new DataReader(this.mode);
    }

    /**
     * Creates an Item object from a map of each variable to its value
     * @param itemData a Map of item's data
     * @return Item
     */
    private Item createItem(Map<String, Object> itemData) {
        Integer ownerID = (Integer) itemData.get("OWNER_ID");
        String name = (String) itemData.get("NAME");
        String description = (String) itemData.get("DESCRIPTION");
        Item item = new Item(ownerID, name, description);
        item.setItemID((Integer) itemData.get("ITEM_ID"));
        item.setPrice((Double) itemData.get("PRICE"));
        boolean approved = ((Integer) itemData.get("IS_APPROVED") == 1);
        item.setApproved(approved);
        return item;
    }

    /**
     * Returns a list of items in a user's wishlist by reading from the database
     * @param userID the id of the user
     * @return the user user's wish list
     * @throws SQLException
     */
    private List<Item> getWishlist(Integer userID) throws SQLException {
        List<Integer> wishlistIDs = dataReader.getUserWishlist(userID);
        List<Item> wishlist = new ArrayList<>();
        for (Integer itemID : wishlistIDs) {
            Map<String, Object> itemData = dataReader.getItem(itemID);
            Item item = createItem(itemData);
            wishlist.add(item);

        }
        return wishlist;
    }


    /**
     * Returns a list of items in a user's inventory by reading from the database
     * @param userID the id of the user
     * @return a list of Item
     * @throws SQLException
     */
    public List<Item> getInventory(Integer userID) throws SQLException {
        List<Integer> inventoryIDs = dataReader.getUserInventory(userID); //TODO - need gateway method
        List<Item> inventory = new ArrayList<>();
        for (Integer itemID : inventoryIDs) {
            Map<String, Object> itemData = dataReader.getItem(itemID);
            Item item = createItem(itemData);
            inventory.add(item);
        }
        return inventory;
    }

    /**
     * helper method
     * @param userData a Map of the user's data
     * @param userID the id of the user
     * @param user UserAccount
     * @throws SQLException
     */
    private void userObjHelper(Map<String, Object> userData, Integer userID, UserAccount user) throws SQLException {
        user.setUserID(userID);
        user.setCity((String) userData.get("CITY"));
        user.setOverBorrowed((Integer) userData.get("OVERBORROWED"));
        user.setNumTrade((Integer) userData.get("NUM_TRADES"));
        user.setIncompleteTrade((Integer) userData.get("INCOMPLETE_TRADES"));
        user.setTotalFunds((Double) userData.get("FUNDS"));
        user.setOnVacation((Integer) userData.get("IS_ON_VACATION") == 0);
        user.setWishlist(getWishlist(userID));
        user.setInventory(getInventory(userID));
    }

    /**
     * Creates a User object from a map of each variable to its value
     * @param userData a Map of the user's data
     * @return UserAccount
     * @throws SQLException
     */
    public UserAccount createUserObject(Map<String, Object> userData) throws SQLException {
        String password = "";
        Integer userID = (Integer) userData.get("USER_ID");
        UserAccount user = new UserAccount(password);
        userObjHelper(userData, userID, user);
        boolean frozen = ((Integer) userData.get("IS_FROZEN") == 1);
        user.setFrozen(frozen);
        boolean requestedUnfreeze = ((Integer) userData.get("UNFREEZE_REQUEST") == 1);
        user.setUnfreezeRequested(requestedUnfreeze);
        boolean isOnVacation = ((Integer) userData.get("IS_ON_VACATION") == 1);
        user.setOnVacation(isOnVacation);
        return user;
    }

    /**
     * Creates a User object from a map of each variable to its value
     * @param userID the id of the user
     * @return UserAccount
     * @throws SQLException
     */
    public UserAccount getUserAccount(int userID) throws SQLException {
        Map<String, Object> userDataMap = this.dataReader.getUser(userID);
        return createUserObject(userDataMap);
    }

    /**
     * Reads all users from the storage file and returns a new a UserManager to store them.
     * @return a UserManager containing all users in the system
     * @throws SQLException thrown if reading from the database fails
     */
    public UserManager createUserManager() throws SQLException {
        HashMap<Integer, UserAccount> idToUsers = new HashMap<>();
        List<Integer> allUserIDs = dataReader.getAllUserIDs();
        for (Integer userID : allUserIDs) {
            UserAccount user = createUserObject(dataReader.getUser(userID));
            idToUsers.put(userID, user);
        }
        return new UserManager(idToUsers);
    }
}

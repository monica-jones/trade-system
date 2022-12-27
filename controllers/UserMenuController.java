package controllers;

import gui.loginUI.LoginPage;
import accounts.UserEnforcer;

import items.Item;
import items.ItemInventory;
import trades.Trade;
import accounts.UserAccount;
import accounts.UserManager;
import trades.TradeLogManager;
import trades.TradeManager;

import java.sql.SQLException;
import java.util.*;


import database.*;



public class UserMenuController {

    private final DataDeleter dataDeleter;
    private final DataInserter dataInserter;
    private final DataReader dataReader;
    UserAccount currentUser;
    private final DataUpdater dataUpdater;
    private final String mode;

    /**
     * @param currentUser the current user's UserAccount
     * @author yuchong zhang
     * Creates a UserMenuController associated with the current user.
     */
    public UserMenuController(UserAccount currentUser, String mode) {
        this.mode = mode;
        this.currentUser = currentUser;
        this.currentUser.setMode(this.mode);
        this.dataDeleter = new DataDeleter(this.mode);
        this.dataInserter = new DataInserter(this.mode);
        this.dataReader = new DataReader(this.mode);
        this.dataUpdater = new DataUpdater(this.mode);
    }

    /**
     * Helper method used to add a new item with a name and description to the user's inventory,
     * return a map containing the item info
     * @param description the description of the item requesting to be added
     * @param name the name of the item requesting to be added
     */
    private Map<String, Object> returnItemInfoMap(String description, String name, double price) {
        Map<String, Object> itemInfo = new HashMap<>();

        Integer ownerID = LoginPage.getCurrentUserName();
        itemInfo.put("OWNER_ID", ownerID);
        itemInfo.put("NAME", name);
        itemInfo.put("DESCRIPTION", description);
        itemInfo.put("PRICE", price);
        return itemInfo;
    }

    /**
     * Adds a new item with the name and description to the user's inventory.
     * Updates the storage files.
     * @param description the description of the item requesting to be added
     * @param name the name of the item requesting to be added
     * @param price the price of the item requesting to be added
     * @param itemInventory ItemInventory
     */
    public void requestAddToMyInventory(String description, String name, double price,
                                        ItemInventory itemInventory) {

        Map<String, Object> itemInfo = returnItemInfoMap(description, name, price);
        Integer id = null;
        try {
            if(mode.equals("")) {
                id = dataInserter.insertItem(itemInfo, false);
            } else{
                id = dataInserter.insertItem(itemInfo, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Item newItem = new Item(LoginPage.getCurrentUserName(), name, description);
        newItem.setItemID(id);
        newItem.setPrice(price);
        itemInventory.addToPending(newItem);
        currentUser.addToInventory(newItem);


    }


    /**
     * Removes the Item with id
     * @param idToRemove the id of the item to be removed from it's inventory
     * @param toRemove   the item to remove from it's inventory
     * @param userManager UserManager
     */
    private void removeItemHelper(Integer idToRemove, Item toRemove, UserManager userManager) {
        try {
            Integer userID = currentUser.getUserID();
            dataDeleter.removeData(idToRemove, 2, userID); // remove from Items
            List<UserAccount> allUsers = userManager.getAllUsers();
            for (UserAccount userAccount : allUsers) {
                if (userAccount.wantsItem(toRemove)) {
                    userAccount.removeFromWishlist(toRemove);
                    dataDeleter.removeData(idToRemove, 0, userAccount.getUserID());
                }
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the Item with id idToRemove from the current user's inventory.
     * Removes the item from the system's item
     * inventory and any other users' wish lists. Updates the item storage files.
     * @param idToRemove the id of the item to be removed from it's inventory
     * @param itemInventory ItemInventory
     * @param userManager UserManager
     */
    public void removeFromUserInv(Integer idToRemove, ItemInventory itemInventory,
                                  UserManager userManager) {
        try {
            Item toRemove = itemInventory.getItemWithID(idToRemove);
            currentUser.removeFromInventory(toRemove);
            if (toRemove.isApproved()) {
                itemInventory.removeFromApproved(toRemove);
            } else {
                itemInventory.removeFromPending(toRemove);
            }
            removeItemHelper(idToRemove, toRemove, userManager);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the item with id itemID to the current user's wishlist. Updates the storage file.
     * @param itemID the ID of the item being added to the user's wish list
     * @param itemInventory ItemInventory
     */
    public void addToMyWishList(Integer itemID, ItemInventory itemInventory) {
        Item toWish;
        try {
            toWish = itemInventory.getItemWithID(itemID);
            if (toWish == null) {

                return;
            }
            currentUser.addToWishlist(toWish);
            dataInserter.addItemToUserWishlist(toWish.getItemID(), currentUser.getUserID());
        } catch (NullPointerException | SQLException e1) {
            e1.printStackTrace();
        }
    }



    /**
     * Removes the Item with ID idToRemove from the current user's wishlist. Updates the storage file.
     * @param idToRemove the ID of the item to be removed from the user's wish list
     * @param itemInventory ItemInventory
     */
    public void removeFromWishList (Integer idToRemove, ItemInventory itemInventory){
        try {
            Item toRemove = itemInventory.getItemWithID(idToRemove);
            currentUser.removeFromWishlist(toRemove);
            dataDeleter.removeData(idToRemove, 0, currentUser.getUserID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of the current user's three most recent trades,
     * or fewer if they have not participated in three trades.
     * @param tradeManager TradeManager
     * @return list of user's three most recent Trade
     */
    public List<Trade> getRecentTrades (TradeManager tradeManager){
        return tradeManager.getRecentTrades(currentUser);
    }

    /**
     * Returns a list of the IDs of the top three most frequent trading partners of the current user,
     * or fewer if they have not traded with three users.
     * @param tradeLogManager TradeLogManager
     * @return list of ids of the top three most frequent trading partners of current user
     */
    public List<Integer> getFrequentTraders (TradeLogManager tradeLogManager){
        return tradeLogManager.getTopTradingPartners(currentUser);
    }

    /**
     * Allow the user to request to be unfrozen. return true iff request is successfully made.
     * @param userEnforcer the UserEnforcer in the system.
     * @return true iff request is successfully made.
     */
    public boolean requestToUnfreeze(UserEnforcer userEnforcer) {
        boolean requestSuccess = userEnforcer.requestUnfrozen(currentUser.getUserID());
        if(requestSuccess){
            try {
                dataUpdater.changeUnfreezeRequest(currentUser.getUserID(), 1);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return requestSuccess;

    }


    /**
     * User to turn on vacation mode
     * @param userAccount the user's account
     */
    public void goOnVacation(UserAccount userAccount) {
        if (userAccount.isOnVacation()) {
            changeVacationStatus(userAccount.getUserID());
        }
    }

    /**
     * change the vacation status of the given user
     * @param userID the id of the user
     */
    public void changeVacationStatus(Integer userID) {
        try {
            dataUpdater.changeVacationStatus(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the status of whether the user is on vacation
     * @param userID ID of the user
     * @return true if the user is on vacation or false otherwise
     */
    public Integer getVacationStatus(int userID) {
        Integer isOnVacation = 1;
        try {
            isOnVacation = (Integer)dataReader.getUser(userID).get("IS_ON_VACATION");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOnVacation;
    }
    
    public Double getUserFunds(int userID) {
    	double userFunds = 0.0;
        try {
        	userFunds = dataReader.getOwnerFunds(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFunds;
    }
}
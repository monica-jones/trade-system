package controllers;

import accounts.UserAccount;
import accounts.UserManager;
import items.Item;
import items.ItemInventory;
import trades.Trade;
import trades.TradeLogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
@author Janine Newton
 */

public class TradePresenter {


    /**
     * Returns a list of all approved items in the system that do not belong to the current user.
     * @param currentUser the current user
     * @param itemInventory the ItemInventory object containing all items in the system
     * @return a list of items in the system that do not belong to the current user
     */
    public List<Item> getAvailableItems(UserAccount currentUser, ItemInventory itemInventory, UserManager userManager) {
        List<Item> availableItems = new ArrayList<>();
        List<Item> approvedItems = itemInventory.getApprovedItems();
        for (Item item : approvedItems) {
            UserAccount owner = userManager.getUser(item.getOwnerID());
            if (!currentUser.ownsItem(item) && currentUser.getCity().equals(owner.getCity()) && !owner.isOnVacation()) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }

    /**
     * Returns an Item for the current user to request given its ID. If the current user owns the item, returns null.
     * @param currentUserID the current user's ID
     * @param itemID the ID of the item to request
     * @param itemInventory the itemInventory object containing all items in the system
     * @return the Item for the user to request, or null if the user owns the item
     */
    public Item getItemToRequest(Integer currentUserID, Integer itemID, ItemInventory itemInventory) {
        Item item = itemInventory.getItemWithID(itemID);
        if (item.getOwnerID().equals(currentUserID)) {
            return null;
        } else {
            return item;
        }
    }

    /**
     * Returns a list of items that can be offered in exchange for the desired item. These items appear in the desired
     * item's owner's wishlist as well as the current user's inventory.
     * @param currentUser the current user whose inventory is to be checked
     * @param itemID the ID of the item the current user wants
     * @param itemInventory the ItemInventory object that stores all items in the system
     * @param userManager the UserManager object that stores all
     * @return a list of items in both the current user's inventory and the other user's wishlist
     */
    public List<Item> getItemsToOffer(UserAccount currentUser, Integer itemID, ItemInventory itemInventory, UserManager userManager) {
        Item desiredItem = itemInventory.getItemWithID(itemID);
        UserAccount otherUser = userManager.getUser(desiredItem.getOwnerID());
        List<Item> desiredItems = new ArrayList<>();
        for (Item myItem : currentUser.getInventory()) {
            if (otherUser.wantsItem(myItem)) {
                desiredItems.add(myItem);
            }
        }
        return desiredItems;
    }

    /**
     * Returns all users in the system in the same city as the current user
     * @param currentUser the UserAccount of the current user
     * @param userManager UserManager
     * @return a list of users that are in the same city as the current user
     */
    private List<UserAccount> getHomeCityUsers(UserAccount currentUser, UserManager userManager) {
        List<UserAccount> homeCityUsers = new ArrayList<>();
        String myCity = currentUser.getCity();
        for (UserAccount user : userManager.getAllUsers()) {
            if (user.getCity().equals(myCity)) {
                homeCityUsers.add(user);
            }
        }
        return homeCityUsers;
    }

    /**
     * helper method for three way trade
     * @param currentUser UserAccount of current user
     * @param otherUser UserAccount of other user
     * @param requestedItemID the id of the item requested
     * @param receiverMap s list of integer
     * @param thirdUser UserAccount of third user
     * @param desiredItem Item desired
     * @param myItemsToOffer a list of Item to offer
     */
    private void thirdUserHelper(UserAccount currentUser, UserAccount otherUser, Integer requestedItemID,
                                 Map<Integer, List<Integer>> receiverMap, UserAccount thirdUser,
                                 Item desiredItem, List<Item> myItemsToOffer) {
        List<Integer> requestedItems = new ArrayList<>();
        requestedItems.add(requestedItemID);
        List<Integer> desiredItems = new ArrayList<>();
        desiredItems.add(desiredItem.getItemID());
        List<Integer> myItems = new ArrayList<>();
        myItems.add(myItemsToOffer.get(0).getItemID());
        receiverMap.put(currentUser.getUserID(), requestedItems);
        receiverMap.put(otherUser.getUserID(), desiredItems);
        receiverMap.put(thirdUser.getUserID(), myItems);
    }


    /**
     * Returns the receiver map for a possible three way trade for an item the user wants. If no three way trade is
     * possible, returns an empty map. Only includes users in the same city as the current user.
     * @param otherUser the user who owns the item the current user wants
     * @param requestedItemID the ID of the item the current user wants
     * @param userManager the UserManager object containing all users in the system
     * @return a map representing who will receive which item in the potential trade
     */
    public Map<Integer, List<Integer>> findThreeWayTradeMap(UserAccount currentUser, UserAccount otherUser,
                                                            Integer requestedItemID, ItemInventory itemInventory, UserManager userManager) {
        List<UserAccount> userList = getHomeCityUsers(currentUser, userManager);
        Map<Integer, List<Integer>> receiverMap = new HashMap<>();
        // check other users for items on the other user's wishlist
        List<Item> wishlist = otherUser.getWishlist();
        for (UserAccount thirdUser : userList) {
            for (Item desiredItem : wishlist) {
                if (thirdUser.ownsItem(desiredItem)) {
                    // see if we have something the third user wants
                    List<Item> myItemsToOffer = getItemsToOffer(currentUser, desiredItem.getItemID(), itemInventory, userManager);
                    if (myItemsToOffer.size() > 0) {
                        thirdUserHelper(currentUser, otherUser, requestedItemID, receiverMap, thirdUser, desiredItem, myItemsToOffer);
                    }
                }
            }
        }
        return receiverMap;
    }

    /**
     * Returns a String description of the receiverMap for a three way trade
     * @param currentUserID the id of the current user
     * @param receiverMap list of integer
     * @param itemInventory ItemInventory
     * @return a string description of the receiverMap for a three way trade
     */
    public String formatThreeWayTrade(Integer currentUserID, Map<Integer, List<Integer>> receiverMap,
                                      ItemInventory itemInventory) {

        if (receiverMap.size() == 0) {
            return "There are no possible three way trades for the requested item.";
        }

        String description = "";
        for (Integer traderID : receiverMap.keySet()) {
            for (Integer itemID : receiverMap.get(traderID)) {
                Item item = itemInventory.getItemWithID(itemID);
                description += "User " + item.getOwnerID() + " will give item " + item.getItemID() + " ("
                        + item.getItemName() + ") to user " + traderID + ".\n";
            }
        }
        description += "Your funds will increase/decrease by " + getFundsChange(currentUserID, receiverMap,
                itemInventory);
        return description;
    }

    /**
     * Returns a list of all trades with a particular status associated with the current user.
     * @param currentUserID the current user's ID
     * @param tradeLogManager the tradeLogManager object which contains all trades in the system
     * @return a list of trades with the desired status associated with the user
     */
    public List<Trade> getStatusTradesList(Integer currentUserID, String status, TradeLogManager tradeLogManager) {
        List<Trade> userTrades = tradeLogManager.getUserTrades(currentUserID);
        List<Trade> statusTrades = new ArrayList<>();
        for (Trade trade : userTrades) {
            if (trade.getStatus().equals(status)) {
                statusTrades.add(trade);
            }
        }
        return statusTrades;
    }

    /** Returns a Trade wtith a particular status given its ID. If the trade does not have the desired status or is not
     * associated with the current user, returns null.
     * @param tradeID the ID of the pending trade
     * @param currentUserID the current user's ID
     * @param tradeLogManager the TradeLogManager object containing all trades in the system
     * @return the pending trade
     */
    public Trade getStatusTrade(Integer currentUserID, Integer tradeID, String status, TradeLogManager tradeLogManager) {
        Trade trade = tradeLogManager.getTradeWithID(tradeID);
        if (trade != null && trade.getStatus().equals(status) && trade.getReceiverMap().containsKey(currentUserID)) {
            return trade;
        } else {
            return null;
        }
    }

    /**
     * Returns the overall change in funds for the given user in a given trade. This can be positive or negative.
     * @param userID the userID of the user to check
     * @param trade the trade to check
     * @param itemInventory the ItemInventory object containing all items in the system
     * @return the overall change in funds for the given user
     */
    public double getFundsChange(Integer userID, Trade trade, ItemInventory itemInventory) {
        return getFundsChange(userID, trade.getReceiverMap(), itemInventory);
    }


    /**
     * Returns the overall change in funds for the given user in a given trade. This can be positive or negative.
     * @param userID the userID of the user to check
     * @param receiverMap a map containing who will receive which items in a trade
     * @param itemInventory the ItemInventory object containing all items in the system
     * @return the overall change in funds for the given user
     */
    public double getFundsChange(Integer userID, Map<Integer, List<Integer>> receiverMap, ItemInventory itemInventory) {
        double total = 0;
        // add funds for items given away
        List<Integer> items = new ArrayList<>();
        for (List<Integer> item : receiverMap.values()) {
            items.addAll(item);
        }
        for (Integer itemGivenID : items) {
            Item item = itemInventory.getItemWithID(itemGivenID);
            if (item.getOwnerID().equals(userID)) {
                total += item.getPrice();
            }
        }
        // subtract funds for items received
        for (Integer itemReceivedID : receiverMap.get(userID)) {
            Item item = itemInventory.getItemWithID(itemReceivedID);
            total -= item.getPrice();
        }
        return total;
    }
}

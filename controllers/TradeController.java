package controllers;

import database.*;
import accounts.UserAccount;
import items.*;
import trades.Meeting;
import trades.Trade;
import trades.TradeLogManager;
import trades.TradeManager;
import accounts.UserManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janine Newton
 */

public class TradeController {
    private final UserAccount currentUser;
    public final TradeManager tradeManager;
    public final TradeLogManager tradeLogManager;
    private final ItemInventory itemInventory;
    private final UserManager userManager;
    private final DataDeleter dataDeleter;
    private final DataInserter dataInserter;
    private final DataUpdater dataUpdater;
    private final DataReader dataReader;
    private final int meetingEditLimit;
    private final MeetingController meetingController;
    private String mode;
    public TradePresenter tradePresenter;


    /**
     * @param currentUser the current user's UserAccount
     * @param tradeManager the tradeManager object which contains methods related to creating and modifying trades
     * @param tradeLogManager the tradeLogManager object which stores all Trades
     * @param itemInventory the itemInventory object which stores all Items
     * @param userManager the userManager object which stores all UserAccounts
     */
    public TradeController(UserAccount currentUser, TradeManager tradeManager, TradeLogManager tradeLogManager,
                           ItemInventory itemInventory, UserManager userManager, int meetingEditLimit, String mode) {
        this.currentUser = currentUser;
        this.tradeManager = tradeManager;
        this.tradeLogManager = tradeLogManager;
        this.itemInventory = itemInventory;
        this.userManager = userManager;
        this.meetingEditLimit = meetingEditLimit;
        this.mode = mode;
        this.meetingController = new MeetingController(this.mode);
        this.dataDeleter = new DataDeleter(mode);
        this.dataInserter = new DataInserter(mode);
        this.dataUpdater = new DataUpdater(mode);
        this.dataReader = new DataReader(mode);
        this.tradePresenter = new TradePresenter();
    }

    /**
     * Creates a new one way trade with the requester receiving the requested item. Returns true iff the trade is
     * created successfully.
     * @param requesterID the ID of the user requesting the trade
     * @param itemRequestedID the ID of the item the user wants
     * @param isPermanent whether the trade is permanent - 0 for temporary, 1 for permanent
     * @return true iff the trade is successfully created
     */
    public boolean requestOneWay(Integer requesterID, Integer itemRequestedID, boolean isPermanent) {
        Map<Integer, List<Integer>> receiverMap = getOneWayMap(requesterID, itemRequestedID);
        return requestTrade(requesterID, receiverMap, isPermanent);
    }

    /**
     * Creates a new two way trade with the requester receiving the requested item, and the owner of that item receiving
     * the offered item. Returns true iff the trade is created successfully.
     * @param requesterID the ID of the user requesting the trade
     * @param itemRequestedID the ID of the item the user wants
     * @param itemOfferedID the ID of the item the user will give
     * @param isPermanent whether the trade is permanent - 0 for temporary, 1 for permanent
     * @return true iff the trade is successfully created
     */
    public boolean requestTwoWay(Integer requesterID, Integer itemRequestedID, Integer itemOfferedID, boolean isPermanent) {
        Map<Integer, List<Integer>> receiverMap = getTwoWayMap(requesterID, itemRequestedID, itemOfferedID);
        return requestTrade(requesterID, receiverMap, isPermanent);
    }

    /**
     * Marks the trade as accepted by the current user. Returns true iff the trade was successfully marked accepted.
     * Note: Works for one- and two- way trades only - does NOT work for three way trades.
     * @param currentUserID the current user's ID
     * @param trade the Trade to mark accepted
     * @return true iff the trade was successfully marked accepted
     */
    public boolean acceptTradeForCurrentUser(Integer currentUserID, Trade trade) {
        boolean success = false;
        if (!(trade.getRequesterID().equals(currentUserID))){ // trade must not be requested by this person
            try {
                trade.acceptTrade(currentUserID);
                tradeManager.acceptTrade(trade);
                updateTradeStatusFile(trade, "pending");
                success = true;
            } catch (SQLException e) {
             e.printStackTrace();
            }
        }else {success = false;}
        return success;
    }

    /**
     * Changes a trade's status from 'requested' to 'declined'. Updates the new status in the storage file.
     *
     * @param trade The Trade to edit
     */
    public boolean declineTrade(Trade trade) {
        boolean success = true;
        try {
            tradeManager.declineTrade(trade);
            updateTradeStatusFile(trade, "declined");
        } catch (SQLException e) {
            success = false;
        }
        return success;
    }

    /**
     * Changes a trade's status from pending to open and returns true iff the trade was successfully confirmed. Call
     * this when all users have accepted the first meeting.
     * @param trade the Trade to be confirmed
     * @return true iff the trade was successfully confirmed
     */
    public boolean confirmTrade(Trade trade) {
        boolean confirmed = false;
        try {
            if (tradeManager.confirmTrade(trade)) { //checks meeting is confirmed and updates meeting to return meeting
                updateTradeStatusFile(trade, "open"); //updates trade's status to open
                confirmed = true;
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }

        return confirmed;
    }


    /**
     * Completes a trade and returns true iff the trade was successfully complete. Returns false if the trade's final
     * meeting was not marked complete.
     * @param trade the Trade to mark complete
     * @return true iff the trade was successfully marked complete
     */
    public boolean completeTrade(Trade trade)  {
        boolean complete = false;
        try {
            if (meetingController.allMeetingsComplete(trade)) { //first trade (for permanent) or second trade (for temporary) must be complete first
                updateTradeStatusFile(trade, "complete");
                tradeManager.completeTrade(trade);
                incrementUserVariables(trade);
                complete = deleteItems(trade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complete;
    }

    /**
     * Changes the trade's status to 'incomplete' and updates the trade in the storage file.
     * Returns true iff the file was successfully updated.
     * This is used when either user did not show up for the meeting.
     * @param trade the Trade
     * @return boolean if trade's status is changed to "incomplete"
     */
    public boolean incompleteTrade(Trade trade) {
        boolean success = true;
        try {
            tradeManager.incompleteTrade(trade);
            updateTradeStatusFile(trade, "incomplete");
            List<UserAccount> traders = getTraders(trade);
            for (UserAccount trader : traders) {
                trader.setIncompleteTrade(trader.getIncompleteTrade() + 1);
            }
        } catch (SQLException e) {
            success = false;
        }
        return success;
    }

    /**
     * Returns a list of all UserAccounts involved in a trade, including the requester
     * @param trade the Trade
     * @return the list of all UserAccounts involved in a trade
     */
    protected List<UserAccount> getTraders(Trade trade) {
        List<UserAccount> users = new ArrayList<>();
        for (Integer userID : trade.getTraderIDs()) {
            UserAccount user = userManager.getUser(userID);
            users.add(user);
        }
        return users;
    }


    /**
     * Returns the receiver map needed to create a one way trade.
     * @param requesterID the ID of the user requesting the trade
     * @param itemRequestedID the ID of the item the user wants
     * @return a map of who will receive what item in a trade
     */
    private Map<Integer, List<Integer>> getOneWayMap(Integer requesterID, Integer itemRequestedID) {
        Map<Integer, List<Integer>> receiverMap = new HashMap<>();
        List<Integer> myItemsReceived = new ArrayList<>();
        myItemsReceived.add(itemRequestedID);
        receiverMap.put(requesterID, myItemsReceived);
        receiverMap.put(getOwnerID(itemRequestedID), new ArrayList<>());
        return receiverMap;
    }

    /**
     * Returns the receiver map needed to create a two way trade.
     * @param requesterID the ID of the user requesting the trade
     * @param itemRequestedID the ID of the item the user wants
     * @return a map of who will receive what item in a trade
     */
    private Map<Integer, List<Integer>> getTwoWayMap(Integer requesterID, Integer itemRequestedID,
                                                     Integer itemOfferedID) {
        Map<Integer, List<Integer>> receiverMap = getOneWayMap(requesterID, itemRequestedID);
        List<Integer> itemsOffered = new ArrayList<>();
        itemsOffered.add(itemOfferedID);
        receiverMap.put(itemInventory.getOwnerID(itemRequestedID), itemsOffered);
        return receiverMap;
    }

    /**
     * Creates a Trade with status 'requested', adds it to the TradeLogManager, and adds it to the storage file.
     * Returns true iff this is successful.
     * @param requesterID the requester's id
     * @param receiverMap a Map of each userID to a list of the itemIDs they are trading
     * @param isPermanent if the trade is permanent of temporary
     * @return if the request trade is valid or invalid
     */
    public boolean requestTrade(Integer requesterID, Map<Integer, List<Integer>> receiverMap, boolean isPermanent) {
        boolean success = false;
        try {
            // add items to requester wishlist if they are not present TODO factor out as separate method
            reqTradeHelper(requesterID, receiverMap);

            //create and validate trade
            Trade newTrade = tradeManager.createTrade(requesterID, receiverMap, isPermanent);
            if (validateTrade(newTrade)) {
                // add trade to database
                Integer tradeID = dataInserter.insertTrade(receiverMap, mapTradeData(newTrade));
                newTrade.setTradeID(tradeID);
                tradeLogManager.addTrade(tradeID, newTrade);
                success = true;
            }

        } catch (SQLException | NullPointerException e) {
           e.printStackTrace();
        }
        return success;
    }

    private void reqTradeHelper(Integer requesterID, Map<Integer, List<Integer>> receiverMap) throws SQLException {
        UserAccount requester = userManager.getUser(requesterID);
        for (Integer itemID : receiverMap.get(requesterID)) {
            Item item = itemInventory.getItemWithID(itemID);
            if (!requester.wantsItem(item)) {
                requester.addToWishlist(item);
                dataInserter.addItemToUserWishlist(itemID, requesterID);
            }
        }
    }

    /**
     * Checks if everything that is related to the funds of a three-way trade is good to go
     * @param threeWayTrade Map containing the list of users and the list of items they want to get
     * @return true if every owner has enough funds to get the item he/she requested. Otherwise,
     * 		   it returns false
     */
    private boolean checkThreeWayTradeFunds(Map<Integer, List<Integer>> threeWayTrade) {
        boolean tradeEligibility = true;
        Double requestedTotal=0.0;
//        DataReader dataReader = new DataReader();
        try {
            for(Integer x: threeWayTrade.keySet()) {
                requestedTotal = 0.0;
                for(Integer y: threeWayTrade.get(x)) {
                    requestedTotal += (Double) dataReader.getItem(y).get("PRICE");
                }
                if((Double) dataReader.getUser(x).get("FUNDS") >= requestedTotal) {
                    tradeEligibility = false;
                }
            }
        } catch (NullPointerException | SQLException e) {
            //errors.priceNotFound();
        }
        return tradeEligibility;
    }

    /**
     * Writes the trade's new status to the storage file
     * @param trade the Trade
     * @param status the status of the trade
     * @throws SQLException
     */
    private void updateTradeStatusFile(Trade trade, String status) throws SQLException {
        dataUpdater.updateTrade(trade.getTradeID(), 1, status);
    }



    /**
     * puts the data of a trade in a map
     * @param trade the Trade
     * @return a map of the details of a trade
     */
    private Map<String, Object> mapTradeData(Trade trade) {
        Map<String, Object> tradeData = new HashMap<>();
        Meeting firstMeeting = new Meeting(0, LocalDateTime.now(),"","pending");
        int permanentInt = trade.getIsPermanent() ? 1 : 0; // Get integer representation of isPermanent boolean
        tradeData.put("TRADE_ID", trade.getTradeID());
        tradeData.put("TRADE_STATUS", trade.getStatus());
        tradeData.put("IS_PERMANENT", permanentInt);
        tradeData.put("LOCATION", firstMeeting.getLocation());
        tradeData.put("FIRST_MEETING_STATUS", firstMeeting.getStatus());
        tradeData.put("TRADE_DATE", firstMeeting.getTime());
        tradeData.put("FIRST_MEETING_LAST_EDITOR", firstMeeting.getLastEditorID());
        tradeData.put("FIRST_MEETING_NUM_EDITS", firstMeeting.getNumEdits());
        tradeData.put("SECOND_MEETING_STATUS", "");
        tradeData.put("EXPECTED_RETURN_DATE",null);
        tradeData.put("SECOND_MEETING_LAST_EDITOR", 0);
        tradeData.put("SECOND_MEETING_NUM_EDITS", 0);
        tradeData.put("REQUESTER_ID", trade.getRequesterID());
        return tradeData;
    }

    /**
     * Returns the ID of the owner of an item
     * @param itemID the item's ID
     * @return the ID of the owner of the item
     */
    private Integer getOwnerID(Integer itemID) {
        Item item = itemInventory.getItemWithID(itemID);
        return item.getOwnerID();
    }

    /**
     * Returns the items that the user will give in a trade
     * @param trade the Trade
     * @param trader the UserAccount that will give items in a trade
     * @return the items that are given out by the trader
     */
    private List<Integer> getGivenItems(Trade trade, UserAccount trader) {
        List<Integer> givenItems = new ArrayList<>();
        for (Integer itemID : trade.getAllItems()) {
            Item item = itemInventory.getItemWithID(itemID);
            if (trader.ownsItem(item)) {
                givenItems.add(itemID);
            }
        }
        return givenItems;
    }

    /*
    Helper method for completeTrade. Updates the variables in the UserAccounts. Increases the number of trades
    * for each user. If the trade is one way, increases overborrowed for the borrower and decreases it for the lender.
    */

    /**
     * Helper method for completeTrade.
     * Updates the variables in the UserAccounts. Increases the number of trades for each user.
     * If the trade is one way, increases overborrowed for the borrower and decreases it for the lender.
     * @param trade the Trade
     */
    private void incrementUserVariables(Trade trade) {
        List<UserAccount> traders = getTraders(trade);
        for (UserAccount trader : traders) {
            trader.setNumTrade(trader.getNumTrade() + 1);
            Integer itemsGiven = getGivenItems(trade, trader).size();
            Integer itemsReceived = trade.getReceivedItems(trader.getUserID()).size();
            userManager.changeOverborrowed(trader.getUserID(), itemsReceived - itemsGiven);
        }
    }

    /**
     * Returns a list of the Items involved in a trade
     * @param trade the Trade
     * @return a list of items involved in a trade
     */
    private List<Item> getItems(Trade trade) {
        List<Item> items = new ArrayList<>();
        for (Integer itemID : trade.getAllItems()) {
            items.add(itemInventory.getItemWithID(itemID));
        }
        return items;
    }

    /**
     * Helper method for validateTrade
     * Returns true iff each item in the list is approved, appears in the inventory of a user in traders,
     * and appears on the wishlist the item's receiver.
     * @param trade the Trade
     * @return boolean if all items in the list is approved, appears in the inventory of a user in traders,
     * and appears on the wishlist the item's received
     */
    private boolean validateItems(Trade trade) {
        List<UserAccount> traders = getTraders(trade);
        List<Item> items = getItems(trade);
        boolean isValid = true;
        for (Item item : items) {
            boolean owned = false;
            for (UserAccount trader : traders) {
                if (trader.ownsItem(item)) {
                    owned = true;
                }
            }
            if (!owned  || !item.isApproved()) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Helper method for validateTrade.
     * Returns true iff all traders in the trade are located in the same city and none of the traders are frozen.
     * @param trade the Trade
     * @return boolean if traders are in same city and are both unfrozen
     */
    private boolean validateUsers(Trade trade) {
        boolean isValid = true;
        String requesterCity = userManager.getHomeCity(trade.getRequesterID());
        for (Integer traderID : trade.getTraderIDs()) { //check all users are in the same city
            UserAccount trader = userManager.getUser(traderID);
            if (!trader.getCity().equals(requesterCity) || trader.isFrozen() ) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Helper method for requestTrade
     * a trade is valid if both user are not frozen and all items are approved and each item appears in the
     * correct inventory and wishlist
     * @param trade the Trade
     * @return boolean if a Trade is valid
     */
    private boolean validateTrade(Trade trade) {
        return validateItems(trade) && validateUsers(trade);
    }

    /**
     * Helper method for completeTrade.
     * Deletes items from the system inventory and users' inventories and wishlists.
     * @param trade the Trade
     */
    private boolean deleteItems(Trade trade){
        boolean success = true;
        for (Integer itemID : trade.getAllItems()) {
            Item item = itemInventory.getItemWithID(itemID);
            UserAccount owner = userManager.getUser(item.getOwnerID());
            UserAccount recipient = userManager.getUser(trade.getReceiver(itemID));
            owner.removeFromInventory(item);
            recipient.removeFromWishlist(item);
            itemInventory.removeFromApproved(item);
            if (!deleteItemsFromFile(trade)) {
                success = false;
            }
        }
        return success;
    }

    /*
    Updates the file storage of each item in a completed trade. Removes items from the inventory and wishlist of each
    user. Removes items from the system inventory.
     */

    /**
     * Updates the file storage of each item in a completed trade
     * Removes items from the inventory and wishlist of each user.
     * Removes items from the system inventory.
     * @param trade the Trade
     */
    private boolean deleteItemsFromFile(Trade trade) {
        boolean success = false;
        try {
            for (Integer itemID : trade.getAllItems()) {
                Integer ownerID = itemInventory.getItemWithID(itemID).getOwnerID();
                Integer recipientID = trade.getReceiver(itemID);
                dataDeleter.removeData(itemID, 0, recipientID); //remove from wishlist
                dataDeleter.removeData(itemID, 1, ownerID); //remove from system inventory
                success = true;
            }
        } catch (NullPointerException | SQLException e) {
            success = false;
        }
        return success;
    }

    /**
     * gets the MeetingController
     * @return MeetingController
     */
    public MeetingController getMeetingController() {
        return meetingController;
    }

    /** gets the TradePresenter
     * @return the TradePresenter
     */
    public TradePresenter getTradePresenter() {
        return tradePresenter;
    }
}



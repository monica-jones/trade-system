package trades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wen Hua Tung
 */
public class Trade {
    private Integer tradeID;
    private boolean isPermanent;
    private String status;
    private final Map<Integer, List<Integer>> receiverMap; // maps each userID to a List of the itemIDs they are receiving - should contain every user involved even if they're not receiving anything
    private final Map<Integer, Integer> acceptedMap; //records whether each user in the trade has accepted the trade - 0 for no, 1 for yes
    private final Integer requesterID; //requester of trade
    private Meeting meeting;

    /*
    Trade statuses:
    invalid: trade was not valid and could not be requested (not stored in TradeLogManager)
    requested: trade has not been accepted
    declined: trade was declined
    pending: trade is accepted but meeting has not been confirmed
    open: meeting has been confirmed
    complete: all meetings were marked complete by both users
    incomplete: a meeting was marked incomplete
    (potentially add: cancelled)
     */

    /**
     * Constructor for Trade
     * @param requesterID ID of the user requesting the trade
     * @param receiverMap a Map of each userID to a list of the itemIDs they are receiving
     */
    public Trade(Integer requesterID, Map<Integer, List<Integer>> receiverMap) {
        this.requesterID = requesterID;
        this.receiverMap = receiverMap;
        this.acceptedMap = new HashMap<>();
        for (Integer traderID : receiverMap.keySet()) {
            acceptedMap.put(traderID, 0);
        }
    }

    /**
     * Gets the ID of the trade
     * @return the Trade's ID
     */
    public Integer getTradeID() {
        return tradeID;
    }

    /**
     * Sets the ID of the trade
     * @param tradeID the ID of the Trade
     */
    public void setTradeID(Integer tradeID) {
        this.tradeID = tradeID;
    }

    /**
     * Returns true if the trade is permanent, false if the trade is temporary
     * @return true if the Trade is permanent
     */
    public boolean getIsPermanent() {
        return isPermanent;
    }

    /**
     * Sets whether the Trade is permanent - true for permanent, false for temporary
     * @param inputPermanent whether the Trade is permanent
     */
    public void setIsPermanent(boolean inputPermanent) {
        isPermanent = inputPermanent;
    }

    /**
     * Returns the status of the trade
     * @return status of the trade
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the trade
     * @param inputStatus status of the trade
     */
    public void setStatus(String inputStatus) {
        status = inputStatus;
    }

    /**
     * Returns the receiverMap, a map of each user in the trade to a list of the items they are receiving
     * @return the receiverMap for the trade
     */
    public Map<Integer, List<Integer>> getReceiverMap() {
        return receiverMap;
    }

    /**
     * Returns  the ID of user who requested the trade
     * @return the requester's ID
     */
    public Integer getRequesterID() {
        return requesterID;
    }

//    /**
//     * @param acceptedMap
//     */
//    public void setAcceptedMap(Map<Integer, Integer> acceptedMap) {
//        this.acceptedMap = acceptedMap;
//        //TODO - database method to store and read acceptedMap
//    }

    /**
     * Returns the current Meeting associated with the trade
     * @return the current Meeting associated with the trade
     */
    public Meeting getMeeting() {
        return meeting;
    }

    /**
     * Sets the meeting to be associated with the trade
     * @param meeting the meeting to be associated with the trade
     */
    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    /**
     * Returns a List of the IDs of the traders involved in the trade, including the requester
     * @return a List of the IDs of the traders
     */
    public List<Integer> getTraderIDs() {
        return new ArrayList<>(receiverMap.keySet());
    }


    /**
     * Returns a list of all the items being traded in the trade
     * @return a list of all the items being traded in the trade
     */
    public List<Integer> getAllItems() {
        List<Integer> items = new ArrayList<>();
        for (List<Integer> item : receiverMap.values()) {
            items.addAll(item);
        }
        return items;
    }

    /**
     * Returns a List the itemIDs that the given user will receive in the trade
     */
    public List<Integer> getReceivedItems(Integer userID) {
        return receiverMap.get(userID);
    }

    /**
     * Returns the userID of the user who is receiving the item with the given ID in the trade
     * @param itemID the ID of the item
     * @return the ID of the user receiving the item
     */
    public Integer getReceiver(Integer itemID) {
        for (Integer traderID : getTraderIDs()) {
            if (receiverMap.get(traderID).contains(itemID)) {
                return traderID;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        ArrayList<Integer> traderIDs = new ArrayList<>();
        traderIDs.add(getRequesterID());
        traderIDs.addAll(getTraderIDs());

        return isPermanent + "Trade{" +
                "tradeID=" + tradeID +
                ", status:" + status + '\'' +
                "ids of traders:" + traderIDs +
                '}';
    }

    /*
     * TODO: Delete these methods?
     */

    /**
     * Accepts a trade for the given user
     * @param userID the id of the user that accepts the trade
     */
    public void acceptTrade(Integer userID) {
        acceptedMap.put(userID, 1);
    }

    /**
     * Returns true iff all users involved in the trade have accepted it
     * @return true iff all users have accepted the trade
     */
    public boolean acceptedByAllUsers() {
        boolean check = true;
        for (Integer traderID : getTraderIDs()) {
            if (acceptedMap.get(traderID) == 0) {
                check = false;
            }
        }
        return check;
    }

    /**
     * TODO: what is this method for?
     * @return true iff accepted by receivers
     */
    public boolean acceptedByReceivers() {
        boolean check = true;
        for (Integer traderID : getTraderIDs()) {
            if (acceptedMap.get(traderID) == 0 && !traderID.equals(getRequesterID())) {
                check = false;
            }
        }
        return check;
    }










}

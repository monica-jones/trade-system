package trades;

import accounts.UserAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Richard Zhou
 * @author Monica Jones
 * The class TradeLogManager manage TradeLog.
 */
public class TradeLogManager {

    private final TradeLog tradeLog;
    private final Map<String, List<Trade>> statusDictionary;
    private final Map<Integer, List<Trade>> userDictionary;

    /**
     * Constructor
     */
    public TradeLogManager(){
       this(new TradeLog());
    }

    /**
     * Constructor
     * @param tLog a TradeLog
     */
    public TradeLogManager(TradeLog tLog){
        tradeLog = tLog;
        statusDictionary = buildStatusDictionary(tLog);
        userDictionary = buildUserDictionary(tLog);
    }

    /**
     * Build a Status Dictionary from TradeLog tLog
     * @param tLog a TradeLog to build a StatusDictionary
     * @return a StatusDictionary
     */
    private Map<String, List<Trade>> buildStatusDictionary(TradeLog tLog) {
        Map<String, List<Trade>> statusDictionary = getStringListMap();

        if (tLog.getTradeHistory().size() != 0) {
            for (Trade trade : tLog.getTradeHistory().values()) {
                String status = trade.getStatus();
                statusDictionary.get(status).add(trade);
            }
        }
        return statusDictionary;
    }

    /***
     * Gets a list of trades
     * @return a map of trade status to the trade
     */
    private Map<String, List<Trade>> getStringListMap() {
        Map<String, List<Trade>> statusDictionary = new HashMap<>();
        statusDictionary.put("requested", new ArrayList<>());
        statusDictionary.put("pending", new ArrayList<>());
        statusDictionary.put("open", new ArrayList<>());
        statusDictionary.put("complete", new ArrayList<>());
        statusDictionary.put("complete1", new ArrayList<>());
        statusDictionary.put("incomplete", new ArrayList<>());
        statusDictionary.put("declined", new ArrayList<>());
        return statusDictionary;
    }


    /**
     * Build a user dictionary from TradeLog tLog
     * @param tLog a TradeLog to build a UserDictionary
     * @return a User Dictionary
     */
    private Map<Integer, List<Trade>> buildUserDictionary(TradeLog tLog) {
        Map<Integer, List<Trade>> userDictionary = new HashMap<>();

        if (!(tLog.getTradeHistory().size() == 0)) {
            for (Trade trade : tLog.getTradeHistory().values()) {
                List<Integer> traders = trade.getTraderIDs();

                // add users to dictionary if they aren't in it
                for (Integer traderID : traders) {
                    if (!userDictionary.containsKey(traderID)) {
                        userDictionary.put(traderID, new ArrayList<>());
                    }
                    userDictionary.get(traderID).add(trade);
                }
            }
        }
        return userDictionary;
    }


//    /**
//     * Get the Status Dictionary
//     * @return a StatusDictionary
//     */
//    public Map<String, List<Trade>> getStatusDictionary(){
//            return statusDictionary;
//        }
//
//
//    /**
//     *  Get the User Dictionary
//     * @return a UserDictionary
//     */
//    public Map<Integer, List<Trade>> getUserDictionary(){
//        return userDictionary;
//    }


    /**
     * Get a user's list of trades
     * @param userID the user ID to get Trades of the user from UserDictionary.
     * @return a list of Trades
     */
    public List<Trade> getUserTrades(Integer userID) {
        if (!userDictionary.containsKey(userID)) {
            return new ArrayList<>();
        }
        return userDictionary.get(userID);
        }

    /**
     * Get a user's list of trades with a particular status
     * @param userID the user ID to get Trades of the user from UserDictionary.
     * @return a list of trade requested associated with the user
     */
    public List<Trade> getUserTradesByStatus(Integer userID, String status) {
        List<Trade> userTrades = getUserTrades(userID);
        List<Trade> trades = new ArrayList<>();
        for (Trade trade : userTrades) {
            if (trade.getStatus().equals(status)) {
                trades.add(trade);
            }
        }
        return trades;
    }


    /**
     * Add trade to TradeLog, StatusDictionary, and UserDictionary.
     * @param tradeID the unique ID of the trade to be added
     * @param trade a Trade to be added to UserDictionary and StatusDictionary
     */
    public void addTrade(Integer tradeID, Trade trade) {
            //add to tradeLog
            tradeLog.addTrade(tradeID, trade);

            // add to statusDictionary
            statusDictionary.get(trade.getStatus()).add(trade);

            // add to userDictionary
            List<Integer> traders = trade.getTraderIDs();
            traders.add(trade.getRequesterID());
            for (Integer traderID : traders) {
                if (!userDictionary.containsKey(traderID)) {
                    userDictionary.put(traderID, new ArrayList<>());
                }
                userDictionary.get(traderID).add(trade);
            }
        }

    /**
     * Remove the trade from TradeLog, StatusDictionary, and UserDictionary.
     * @param trade a Trade to be removed
     */
    public void removeTrade(Trade trade) {
        // remove from tradeLog
        tradeLog.removeTrade(trade);

        // remove from statusDictionary
        if (statusDictionary.get(trade.getStatus()) != null) {
            statusDictionary.get(trade.getStatus()).remove(trade);
        }

        List<Integer> traders = trade.getTraderIDs();
        traders.add(trade.getRequesterID());
        for (Integer traderID : traders) {
            if (userDictionary.get(traderID) != null) {
                userDictionary.get(traderID).remove(trade);
            }
        }
    }


    /**
     * Remove Trade from current position in statusDictionary and move
     * it to desired position, update trade Requests if necessary
     * @param trade a Trade to be updated
     * @param status the status of a Trade to be updated
     */
        public void updateStatus(Trade trade, String status) {
            String currentStatus = trade.getStatus();
            if (currentStatus != null) {
                statusDictionary.get(currentStatus).remove(trade);
                }
            statusDictionary.get(status).add(trade);
        }

    /**
     * Get Trade with the Trade ID tradeID
     * @param tradeID Trade ID
     * @return Trade with trade ID tradeID
     */
    public Trade getTradeWithID(Integer tradeID) {
        return tradeLog.getTradeHistory().get(tradeID);
    }

    /***
     * Helper method used to find the common 3 partners
     * @param partners other user ids that have traded with the user
     * @return list of top 3 partners that the user has traded with
     */
    private List<Integer> findCommonHelper(Map<Integer, Integer> partners) {
        List<Integer> common = new ArrayList<>();

        for (int j = 0; j < 3; j++) {
            int max = 0;
            int i = -1;
            for (Map.Entry<Integer, Integer> val : partners.entrySet()) {
                if (max < val.getValue()) {
                    i = val.getKey();
                    max = val.getValue();
                }
            }
            common.add(partners.get(i));
            partners.remove(partners.get(i));
        }

        return common;
    }



    /**
     * Return the userIDs of the three most common trading partners of a given user.
     * @param user the user
     * @return the list of userIDs of the three most common trading partners of the user
     */
    public List<Integer> getTopTradingPartners(UserAccount user) {
        Integer userID = user.getUserID();
        List<Trade> trades = getUserTrades(userID);
        Map<Integer, Integer> partners = new HashMap<>();

        for (Trade trade : trades) {
            if (trade.getRequesterID().equals((user.getUserID()))) {
                User1IdHelper(partners, trade.getTraderIDs());
            } else {
                User2IdHelper(partners, trade.getRequesterID());
            }
        }
        return findCommonHelper(partners);
    }

    /***
     * Helper method of the owner (User1) of the item
     * @param partners other users that have traded with this user
     * @param user1IDs the owner of the item id
     */
    private void User1IdHelper(Map<Integer, Integer> partners, List<Integer> user1IDs) {
        for (Integer user1ID : user1IDs) {
            if (partners.containsKey(user1ID)) {
                partners.put(user1ID,
                        partners.get(user1ID) + 1);
            } else {
                partners.put(user1ID, 1);
            }
        }
    }

    /***
     * Helper method for the other user (User2)
     * @param partners other users that have traded with this user
     * @param user2ID The other user that wants to trade
     */
    private void User2IdHelper(Map<Integer, Integer> partners, Integer user2ID) {
        if (partners.containsKey(user2ID)) {
            partners.put(user2ID,
                    partners.get(user2ID) + 1);
        } else {
            partners.put(user2ID, 1);
        }
    }
}


package trades;

import accounts.UserAccount;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Janine Newton
 */

public class TradeManager {
    private final TradeLogManager tradeLogManager;


    /***
     * The constructor
     * @param tradeLogManager TradeLogManager object
     */
    public TradeManager(TradeLogManager tradeLogManager) {
        this.tradeLogManager = tradeLogManager;
       
    }

    /**
     * Changes the trade's status and moves it to the correct place in the TradeLogManager's statusDictionary.
     * @param trade the trade to be updated
     * @param status the new status for the trade
     */
    public void updateStatus(Trade trade, String status) {
        tradeLogManager.updateStatus(trade, status);
        trade.setStatus(status);
    }

    /**
     * Creates a Trade with status "requested". Does NOT add it to the TradeLogManager.
     * @param requesterID the ID of the user requesting the trade
     * @param itemMap a Map of each userID to a list of the itemIDs they are trading
     * @param isPermanent whether the trade is permanent or not
     * @return true iff the trade was successfully created
     */
    public Trade createTrade(int requesterID, Map<Integer, List<Integer>> itemMap, boolean isPermanent) {
        Trade newTrade = new Trade(requesterID, itemMap);

        newTrade.setIsPermanent(isPermanent);
        newTrade.setStatus("requested");
        newTrade.setMeeting(new Meeting(requesterID, LocalDateTime.now(), "location not set", "pending")); //add an empty meeting
        return newTrade;
    }

    /**
     * Accepts a requested Trade by updating its status to "pending".
     * @param trade the trade to be accepted
     */
    public void acceptTrade(Trade trade) {
        updateStatus(trade, "pending");
    }

    /**
     * Declines a requested trade by updating its status to "declined".
     * @param trade the trade to be declined
     */
    public void declineTrade(Trade trade) {
        updateStatus(trade, "declined");
    }



    /** Confirms a trade by updating its status to "open". The Trade's first meeting must be confirmed.
     * @param trade the trade to be confirmed
     */
    public boolean confirmTrade(Trade trade) {
        if (!trade.getMeeting().getStatus().equals("open")) {
            return false; // First meeting was not yet confirmed
        } else {
            updateStatus(trade, "open");
            return true;
        }
    }

    /** Completes a trade by updating its status to "complete".
     * @param trade the trade to be completed
     */
    public void completeTrade(Trade trade) {
        updateStatus(trade, "complete");
    }

//    /* Helper method for completeTrade. Returns true iff all the Meetings associated with the trade are complete. */
//    private boolean allMeetingsComplete(Trade trade) {
//        boolean meetingsComplete = true;
//        for (Meeting meeting : trade.returnMeeting()) {
//            if (!meeting.getStatus().equals("complete")) {
//                meetingsComplete = false;
//                break;
//            }
//        }
//        return meetingsComplete;
//    }

//    /* Helper method for incompleteTrade. Returns true iff the trade is associated with an incomplete meeting. */
//    private boolean hasIncompleteMeeting(Trade trade) {
//        boolean hasIncompleteMeeting = false;
//        for (Meeting meeting : trade.returnMeeting()) {
//            if (meeting.getStatus().equals("incomplete")) {
//                hasIncompleteMeeting = true;
//                break;
//            }
//        }
//        return hasIncompleteMeeting;
//    }

//    /**
//     * Return a list of trades requested of a given user.
//     * @param user the user
//     * @return the list of requested trades
//     */
//    public List<Trade> checkTradeRequests(UserAccount user) {
//        Integer userID = user.getUserID();
//        List<Trade> aTrades = tradeLogManager.getUserTrades(userID);
//        if (aTrades.size() == 0) {
//            return new ArrayList<>();
//        }
//        List<Trade> requests = new ArrayList<>();
//        for (Trade aTrade : aTrades) {
//            if (aTrade.getStatus().equals("requested") && aTrade.getTraderIDs().contains(userID)) {
//                requests.add(aTrade);
//            }
//        }
//        return requests;
//    }

//    /**
//     * Return all trades associated with a given user.
//     * @param user the user
//     * @return the List of the user's trades
//     */
//    public List<Trade> getTrades(UserAccount user) {
//        return tradeLogManager.getUserTrades(user.getUserID());
//    }


    /**
     * Return a list of up to three most recent completed trades associated with a given user. If user has completed less
     * than three trades, returns all of them.
     @param user the user
     @return the list of the user's three most recent trades
     **/
    public List<Trade> getRecentTrades(UserAccount user) {
        Integer userID = user.getUserID();
        List<Trade> uTrades = tradeLogManager.getUserTrades(userID);
        List<Trade> finished = new ArrayList<>();
        List<Trade> recent = new ArrayList<>();

        for (Trade uTrade : uTrades) {
            if (uTrade.getStatus().equals("complete")) {
                finished.add(uTrade);
            }
        }

        int count = 0;
        while (count < 3 && finished.size() > 0)
        {
            recent.add(finished.remove(finished.size() - 1));
            count++;
        }
        return recent;

    }

    /**
     * Marks a Trade as incomplete by updating its status to incomplete.
     * @param trade the trade being marked as incomplete
     */
    public void incompleteTrade(Trade trade) {
        updateStatus(trade, "incomplete");
    }
}
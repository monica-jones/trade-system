package controllers;

import database.DataReader;
import database.DataUpdater;

import trades.Meeting;
import trades.Trade;


import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Janine Newton
 * @author Rutwa Engineer
 */

public class MeetingController {

    DataUpdater dataUpdater;
    DataReader dataReader;
    String mode = "";

    public MeetingController(String mode) {
    	this.mode = mode;
    	this.dataUpdater = new DataUpdater(this.mode);
    	this.dataReader = new DataReader(this.mode);
    }

    /**
     * Returns true iff all meetings in a trade were marked complete by all users.
     * @param trade the trade to check
     * @return true iff all meetings in the trade was marked complete by all users
     * @throws SQLException if failed to read from database
     */
    public boolean allMeetingsComplete(Trade trade) throws SQLException {
        boolean allComplete;
        if (trade.getIsPermanent()) {
            allComplete = firstMeetingComplete(trade.getTradeID());
        } else {
            allComplete = secondMeetingComplete(trade.getTradeID());
        }
        return allComplete;
    }

    /**
     * returns true if the first meeting in a trade was marked complete by all users
     * @param tradeID the id of the trade
     * @return boolean if the status of the first trade meeting is "complete"
     * @throws SQLException
     */
    private boolean firstMeetingComplete(Integer tradeID) throws SQLException {
        String status = dataReader.getMeetingInfo(tradeID).get("FIRST_MEETING_STATUS");
        return status.equals("complete");
    }

    /**
     * returns true if the second meeting in a trade was marked complete by all users
     * @param tradeID the id of the trade
     * @return boolean if the status of the second trade meeting is "complete"
     * @throws SQLException
     */
    private boolean secondMeetingComplete(Integer tradeID) throws SQLException {
        String status = dataReader.getMeetingInfo(tradeID).get("SECOND_MEETING_STATUS");
        return status.equals("complete");
    }



    /**
     * Edits the time and location of the meeting.
     * The current user must not be the most recent editor.
     * The meeting's status must be pending
     * @param currentUserID the current user's ID
     * @param location      the new location to set
     * @param time          the new time to set
     */
    public void editMeeting(int currentUserID, Integer tradeID, Meeting meeting, String location, LocalDateTime time) throws SQLException {
        meeting.increaseNumEdits();
        int numEdits = meeting.getNumEdits();
        dataUpdater.updateTrade(tradeID, 2, location);
        dataUpdater.updateTrade(tradeID, 6, String.valueOf(time));
        dataUpdater.updateTrade(tradeID, 8, String.valueOf(currentUserID));
        dataUpdater.updateTrade(tradeID, 9, String.valueOf(numEdits));
    }

    /**
     * stores the returning meeting
     * @param tradeID the id of the trade
     * @param meeting the meeting
     * @throws SQLException
     */
    public void storeReturnMeeting( Integer tradeID, Meeting meeting,  String status) throws SQLException {
        dataUpdater.updateTrade(tradeID, 5, status);
        dataUpdater.updateTrade(tradeID, 7, String.valueOf(meeting.getTime()));
        dataUpdater.updateTrade(tradeID, 10, String.valueOf(0)); //must set lastEditor to 0 so both users can mark meeting complete
        dataUpdater.updateTrade(tradeID, 9, String.valueOf(meeting.getNumEdits()));
    }


    /**
     * Gets meeting details from the database and creates and returns a meeting using that information
     * @param tradeID the id of the trade
     * @return a meeting
     */
    public Meeting getMeeting(int tradeID) {

        Map<String, String> meetingInfo = null;
        try {
            meetingInfo = dataReader.getMeetingInfo(tradeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String date = meetingInfo.get("TRADE_DATE");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        Meeting meeting = new Meeting(Integer.parseInt(meetingInfo.get("FIRST_MEETING_LAST_EDITOR")),dateTime
                , meetingInfo.get("LOCATION"), meetingInfo.get("FIRST_MEETING_STATUS"));
        meeting.setNumEdits(Integer.valueOf(meetingInfo.get("FIRST_MEETING_NUM_EDITS")));

        return meeting;

    }

    /**
     * Gets return meeting details from the database and creates and returns a meeting using that information
     * @param tradeID the id of the trade
     * @return a meeting
     */

    public Meeting getReturnMeeting(int tradeID) {

        Map<String, String> meetingInfo = null;
        try {
            meetingInfo = dataReader.getMeetingInfo(tradeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String date = meetingInfo.get("TRADE_DATE");
        LocalDateTime dateTime = LocalDateTime.parse(date);
        Meeting meeting = new Meeting(Integer.parseInt(meetingInfo.get("SECOND_MEETING_LAST_EDITOR")),dateTime
                , meetingInfo.get("LOCATION"), meetingInfo.get("SECOND_MEETING_STATUS"));
        meeting.setNumEdits(Integer.valueOf(meetingInfo.get("SECOND_MEETING_NUM_EDITS")));

        return meeting;

    }



    /**
     * Marks the meeting as accepted and updates the first meeting's status to 'open' in the database. Sets the last
     * editor to 0. Does not update the trade's status.
     * @param tradeID the ID of the trade the meeting belongs to
     * @param meeting the meeting to update
     */
    public boolean acceptMeeting(Integer tradeID, Meeting meeting) {
        boolean success = true;
        meeting.setStatus("open");

        try {
            dataUpdater.updateTrade(tradeID, 4, "open");
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        try {
            dataUpdater.updateTrade(tradeID, 8, String.valueOf(0));
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        return success;

    }

    /**
     * Returns true if trade is permanent
     * @param tradeID of the trade
     * @return true iff the trade is permanent
     */

    public Boolean getTradePermanent(Integer tradeID)  {
        Boolean isPermanent;
        Map<String, String> tradeInfo = null;
        try {
            tradeInfo = dataReader.getMeetingInfo(tradeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String permanent = tradeInfo.get("IS_PERMANENT");
        if (permanent.equals("0")){
            isPermanent = false;
        }
        else {
            isPermanent = true;
        }
        return isPermanent;


    }



    /**
     * Returns true if all meetings in the trade are now complete - if this returns true, call
     * TradeController.completeTrade(). This method does NOT check if users marked the meeting complete.
     * @param tradeID of the trade whose meeting is to be marked complete
     * @return true iff the final meeting in the trade was marked complete
     */

    public boolean completeFirstMeeting(Integer currentUserID, Integer tradeID) {
        boolean allComplete = false;
        try {
            if (getMeeting(tradeID).getStatus().equals("open")) { //If first meeting was already complete, we must be completing the second meeting
                dataUpdater.updateTrade(tradeID, 4, "complete1");
                dataUpdater.updateTrade(tradeID, 8, String.valueOf(currentUserID));
                allComplete = false;
            }
            else if(getMeeting(tradeID).getStatus().equals("complete1"))  { // If the first meeting was NOT already complete, we are completing the first meeting
                if (!getTradePermanent(tradeID)) {
                    storeReturnMeeting(tradeID, getMeeting(tradeID), "open");
                    dataUpdater.updateTrade(tradeID, 4, "complete");
                    dataUpdater.updateTrade(tradeID, 1, "open");
                }
                else {
                    dataUpdater.updateTrade(tradeID, 1, "complete");
                    dataUpdater.updateTrade(tradeID, 4, "complete");
                }
                allComplete = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allComplete;

        }

    public boolean completeReturnMeeting(Integer currentUserID, Integer tradeID) throws SQLException {
        boolean allComplete = false;
        try {
            if (getReturnMeeting(tradeID).getStatus().equals("open")) { //If first meeting was already complete, we must be completing the second meeting
                dataUpdater.updateTrade(tradeID, 5, "complete1");
                dataUpdater.updateTrade(tradeID, 10,(String.valueOf(currentUserID)));
                allComplete = false;
            }
            else if(getReturnMeeting(tradeID).getStatus().equals("complete1"))  { // If the first meeting was NOT already complete, we are completing the first meeting
                    dataUpdater.updateTrade(tradeID, 5, "complete");
                    dataUpdater.updateTrade(tradeID, 1, "complete");
                allComplete = true;
                }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allComplete;

    }


    public void incompleteMeeting(Trade trade) {
        trade.getMeeting().setStatus("incomplete");
        try {
            if (firstMeetingComplete(trade.getTradeID())) {
                dataUpdater.updateTrade(trade.getTradeID(), 5, "incomplete"); // sets first meeting to incomplete
            } else if (!trade.getIsPermanent()){
                dataUpdater.updateTrade(trade.getTradeID(), 4, "incomplete"); // sets second meeting to incomplete (only if trade was temporary)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /***
     * Puts the second meeting like the status, return date, number of
     * time the user edited the meeting,etc
     * @param meeting the second meeting
     * @return the second meetings data
     */
    public Map<String, Object> mapSecondMeeting(Meeting meeting) {
        Map<String, Object> meetingData = new HashMap<>();
        meetingData.put("SECOND_MEETING_STATUS", meeting.getStatus());
        try {
            meetingData.put("EXPECTED_RETURN_DATE", meeting.getTime());
        } catch (DateTimeException | NullPointerException e) {
            meetingData.put("TRADE_DATE", null);
        }
        meetingData.put("SECOND_MEETING_LAST_EDITOR", meeting.getLastEditorID());
        meetingData.put("SECOND_MEETING_NUM_EDITS", meeting.getNumEdits());
        return meetingData;
    }

    public String getTime(int tradeId) throws SQLException {
        Map<String, String> meetingInfo;

            meetingInfo = dataReader.getMeetingInfo(tradeId);

        String h = meetingInfo.get("time");
        return h;

    }

//    /***
//     * Updates the database with a return meeting and first meeting is complete
//     * @param currentUserID user id
//     * @param trade the trade
//     * @throws SQLException thrown if there is failure or interruption in the SQL operations or when either
//     * the userID do not exist in the database
//     */
//    private void compMeetingHelper(Integer currentUserID, Trade trade) throws SQLException {
//        addReturnMeeting(trade); //adds return meeting to trade object
//        storeReturnMeeting(currentUserID, trade.getTradeID(), trade.getMeeting()); //updates database with return meeting
//        dataUpdater.updateTrade(trade.getTradeID(), 4, "complete");
//
//    }

}

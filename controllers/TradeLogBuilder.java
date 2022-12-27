package controllers;

import accounts.UserAccount;
import database.DataReader;
import trades.Meeting;
import trades.Trade;
import trades.TradeLog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeLogBuilder {
//    private boolean demoMode;
	private String mode;
    private DataReader dataReader;

    public TradeLogBuilder(String mode){
    	this.mode = mode;
        this.dataReader = new DataReader(this.mode);
//        this.demoMode = demoMode;
//        if(demoMode){
//            this.dataReader = new DataReader("_demo");
//        }else{
//            this.dataReader = new DataReader("");
//        }
    }

    /**
     * creates the first meeting
     * @param tradeData a map containing details of the meeting
     * @return Meeting
     */
    private Meeting createFirstMeeting(Map<String, Object> tradeData) {
        List<String> columnHeaders = new ArrayList<>();
        columnHeaders.add("LOCATION");
        columnHeaders.add("FIRST_MEETING_STATUS");
        columnHeaders.add("TRADE_DATE");
        columnHeaders.add("FIRST_MEETING_LAST_EDITOR");
        columnHeaders.add("FIRST_MEETING_NUM_EDITS");
        return createMeeting(tradeData, columnHeaders);
    }

    /**
     * creates second meeting
     * @param tradeData a map containing details of the meeting
     * @return Meeting
     */
    private Meeting createSecondMeeting(Map<String, Object> tradeData) {
        List<String> columnHeaders = new ArrayList<>();
        columnHeaders.add("LOCATION");
        columnHeaders.add("SECOND_MEETING_STATUS");
        columnHeaders.add("EXPECTED_RETURN_DATE");
        columnHeaders.add("SECOND_MEETING_LAST_EDITOR");
        columnHeaders.add("SECOND_MEETING_NUM_EDITS");
        return createMeeting(tradeData, columnHeaders);
    }

    /**
     * creates meeting
     * @param tradeData a map containing details of the meeting
     * @param columnHeaders the headers of the column in file
     * @return Meeting
     */
    private Meeting createMeeting(Map<String, Object> tradeData, List<String> columnHeaders) {
        String location = (String) tradeData.get(columnHeaders.get(0));
        String firstStatus = (String) tradeData.get(columnHeaders.get(1));
        String tradeDate = (String) tradeData.get(columnHeaders.get(2));
        LocalDateTime time = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            time = LocalDateTime.parse(tradeDate, formatter);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        Integer lastEditorID = (Integer) tradeData.get(columnHeaders.get(3));
        Integer numEdits = (Integer) tradeData.get(columnHeaders.get(4));
        Meeting meeting = new Meeting(lastEditorID, time, location, firstStatus);
        meeting.setNumEdits(numEdits);
        return meeting;
    }

//    private List<Meeting> createMeetings(Map<String, Object> tradeData) {
//        List<Meeting> meetings = new ArrayList<>();
//        meetings.add(createFirstMeeting(tradeData));
//        if (!tradeData.get("SECOND_MEETING_STATUS").equals("")) { //if a second meeting exists
//            meetings.add(createSecondMeeting(tradeData));
//        }
//        return meetings;
//    }

    /**
     * Helper method for getModifiableTrade.
     * Returns false iff the trade is a valid trade to be modified by the current user.
     * @param user the UserAccount
     * @param trade the Trade
     * @return boolean of the trade is a valid trade to be modified by the current user
     */
    private boolean tradeModified(UserAccount user, Trade trade) {
        Integer userId = user.getUserID();
        //user is trading
        if (trade.getTraderIDs().contains(userId) &&
                trade.getRequesterID().equals(userId)) {
            return true;
        }
        //incorrect status
        if (trade.getStatus().equals("pending") && trade.getStatus().equals("open")) {
            return true; //trade has a correct status
        }
        //trade in the system
        if (trade != null) {
            return true;
        }
        return false;
    }

    /**
     * creates trade
     * @param tradeData a map containing trade data
     * @param dataReader DataReader
     * @return the created Trade
     * @throws SQLException
     */
    private Trade createTrade(Map<String, Object> tradeData, DataReader dataReader) throws SQLException {
        Integer tradeID = (Integer) tradeData.get("TRADE_ID");
        Integer requesterID = (Integer) tradeData.get("REQUESTER_ID");
        Map<Integer, List<Integer>> receiverMap = dataReader.getReceivingUsers(tradeID);
        Trade trade = new Trade(requesterID, receiverMap);
        trade.setTradeID(tradeID);
        trade.setStatus((String) tradeData.get("TRADE_STATUS"));
        trade.setIsPermanent(tradeData.get("IS_PERMANENT").equals(1));
        if (tradeData.get("FIRST_MEETING_STATUS").equals("complete") && !(trade.getIsPermanent())) {
            trade.setMeeting(createSecondMeeting(tradeData)); //if trade is not permanent or the first meeting is complete, add second meeting
        } else {
            trade.setMeeting(createFirstMeeting(tradeData)); //if trade is temporary but first meeting is not complete, or trade is permanent, add first meeting
        }
        return trade;
    }

    /**
     * builds trade log
     * @return TradeLog
     * @throws SQLException
     */
    public TradeLog buildTradeLog() throws SQLException {
        Map<Integer, Trade> allTrades = new HashMap<>();
        for (Integer tradeID : dataReader.getAllTradeIDs()) {
            Trade trade = createTrade(dataReader.getTrade(tradeID), dataReader);
            allTrades.put(tradeID, trade);
        }
        return new TradeLog(allTrades);
    }
}

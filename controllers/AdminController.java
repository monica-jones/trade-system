package controllers;

import database.DataReader;
import database.DataDeleter;
import database.DataInserter;
import database.DataUpdater;
import items.Item;
import items.ItemInventory;
import accounts.UserAccount;
import accounts.UserEnforcer;
import accounts.UserManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * @author Rutwa Engineer
 */


/***
 * The AdminController constructor
 */
public class AdminController {
    protected UserManager userManager;
    protected ItemInventory itemInventory;
    protected UserEnforcer userEnforcer;
    protected String mode;
    protected DataUpdater dataUpdater;
    protected DataReader dataReader;
    protected DataDeleter dataDeleter;
    protected DataInserter dataInserter;
    private Component mainFrame;


    /***
     *
     * @param currentAdmin the Account of the logged in admin
     * @param userManager stores all users and maps them to user id
     * @param itemInventory list of all items
     * @param userEnforcer tracks borrowing limit of user, and freezes, flag, unfreezes, or unflag the account
     */
    public AdminController(UserAccount currentAdmin, UserManager userManager, ItemInventory itemInventory, UserEnforcer userEnforcer
            , String mode) {
        this.mode = mode;
        this.dataUpdater = new DataUpdater(this.mode);
        this.dataReader = new DataReader(this.mode);
        this.dataDeleter = new DataDeleter(this.mode);
        this.dataInserter = new DataInserter(this.mode);
        this.userManager = userManager;
        this.itemInventory = itemInventory;
        this.userEnforcer = userEnforcer;
    }

    /***
     * Limit helper method used to edit the limits
     * @param meetingEditLimit the number of times a user can edit meetings
     * @param overBorrowLimit the over borrow limit
     * @param tradeLimit the limit on the number of trades a user can make
     * @param incompleteLimit incomplete trades limits
     *
     */
    private void limitsHelper(String meetingEditLimit, String overBorrowLimit, String tradeLimit, String incompleteLimit, Map<String, Integer> limits) {
        if (!meetingEditLimit.equals("")) {
            limits.put("meetingEditLimit", Integer.parseInt(meetingEditLimit));
        }
        if (!overBorrowLimit.equals("")) {
            limits.put("overBorrowLimit", Integer.parseInt(overBorrowLimit));
        }
        if (!tradeLimit.equals("")) {
            limits.put("tradeLimit", Integer.parseInt(tradeLimit));
        }
        if (!incompleteLimit.equals("")) {
            limits.put("incompleteLimit", Integer.parseInt(incompleteLimit));
        }
    }

    /**
     * Updates the limtis in the config.txt file with the given limits.
     * @param meetingEditLimit the number of times a meeting may be edited before the trade is cancelled
     * @param overBorrowLimit  the number of items a user may borrow more than they lend
     * @param tradeLimit       the maximum number of trades a user may participate in per week
     * @param incompleteLimit  the maximum number of incomplete trades a user may participate it
     */
    public boolean changeAllLimits(String meetingEditLimit, String overBorrowLimit, String tradeLimit, String incompleteLimit) {
        try {
            Map<String, Integer> limits = dataReader.getTradeThresholds();
            limitsHelper(meetingEditLimit, overBorrowLimit, tradeLimit, incompleteLimit, limits);
            dataInserter.writeTradeThresholds(limits);
            userEnforcer.updateLimits(limits);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * returns a string of limits containing meeting edit limit, overborrow limit, trades per week limit,
     * and incomplete trades limit,
     *
     * @return s string of limits
     */
    public String displayLimits() {
        String limitString;
        try {
            Map<String, Integer> limits = dataReader.getTradeThresholds();
            limitString = "Meeting edit limit: " + limits.get("meetingEditLimit") + "\nOverborrow limit: " +
                    limits.get("overBorrowLimit") + "\nTrades per week limit: " + limits.get("tradeLimit") +
                    "\nIncomplete trades limit: " + limits.get("incompleteLimit");
        } catch (IOException e) {
            limitString = "";
        }
        return limitString;
    }

    /**
     * gets the ItemInventory
     *
     * @return ItemInventory
     */
    public ItemInventory getItemInventory() {
        return this.itemInventory;
    }

    /**
     * Admin can approves the item
     *
     * @param item the item waiting to be approved
     * @return boolean if the item is approved
     */
    public boolean approveItem(Item item) {
        boolean success;
        if (this.itemInventory.getPendingItems().contains(item)) {
            item.approve();
            itemInventory.addToApproved(item);
            itemInventory.removeFromPending(item);
            success = true;
            try {
                dataUpdater.approveItem(item.getItemID());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            success = false;
        }

        return success;
    }

    /**
     * Admin can deny the item
     *
     * @param item the item waiting to be approved
     * @return boolean if the admin denies the item
     */
    public Boolean denyItem(Item item) {
        Boolean success;
        if (this.itemInventory.getPendingItems().contains(item)) {
            itemInventory.removeFromPending(item);
            UserAccount owner = userManager.getUser(item.getOwnerID());
            owner.removeFromInventory(item);
            success = true;
            try {
                dataDeleter.removeData(item.getItemID(), 2, item.getOwnerID());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            success = false;
        }

        return success;

    }

    /**
     * Gets a list of user items pending approval with their IDs. Returns true iff there is at least one item
     * pending approval.
     *
     * @return true iff there is at least one item pending approval
     */
    public boolean getPendingItems() {
        List<Item> getPendingItems = itemInventory.getPendingItems();
        if (getPendingItems.size() == 0) {
            JOptionPane.showMessageDialog(this.mainFrame, "There are no items waiting for approval.");
            return false;
        }
        JOptionPane.showMessageDialog(this.mainFrame, "The following methods are waiting for approval");
        for (Item item : getPendingItems) {
            System.out.println(item.getItemName() + ": " + item.getDescription() +
                    " (ID: " + item.getItemID() + ")");
        }
        JOptionPane.showMessageDialog(this.mainFrame, "Enter the the id to approve or deny.");
        return true;
    }


    /**
     * changes the freeze status from freeze to unfreeze or unfreeze to freeze
     *
     * @param userAccount the UserAccount of user
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    public void changeFreezeStatus(UserAccount userAccount) throws SQLException {
        this.userEnforcer.unfreezeUser(userAccount);
        dataUpdater.changeFreezeStatus(userAccount.getUserID());
        if (userAccount.getUnfreezeRequested()) {
            dataUpdater.changeUnfreezeRequest(userAccount.getUserID(), 0);
        }

    }

    /**
     * Checks if the user frozen
     *
     * @param userAccount the UserAccount of user
     * @return boolean if the user is frozen
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    public boolean checkFreezeStatus(UserAccount userAccount) throws SQLException {
        return dataReader.getIsFrozen(userAccount.getUserID());
    }

    /***
     * Get a list of user that are frozen
     * @return list of user accounts that are frozen
     */
    public List<UserAccount> getFrozenUsers() {
        List<Integer> frozenUserIDs = userEnforcer.getFrozenUsers();
        List<UserAccount> frozenUsers = new ArrayList<>();
        for (Integer frozenID : frozenUserIDs) {
            frozenUsers.add(userManager.getUser(frozenID));
        }
        return frozenUsers;
    }

    /**
     * Checks if the user is flagged
     *
     * @param userAccount the UserAccount of user
     * @return boolean if the user is flagged
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    public boolean checkFlaggedStatus(UserAccount userAccount) throws SQLException {
        return dataReader.getIsFlagged(userAccount.getUserID());
    }

    /**
     * Gets the UserId of the user
     *
     * @param userAccount the UserAccount of user
     * @return s String of the user's username
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    public String getUserName(UserAccount userAccount) throws SQLException {
        Map<String, Object> userInfo = dataReader.getUser(userAccount.getUserID());
        return (String) userInfo.get("NAME");
    }

    /**
     * Gets the overborrowed number of the user
     *
     * @param userAccount the UserAccount of user
     * @return the overborrowed number of the user
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    public Integer getOverBorrowedNum(UserAccount userAccount) throws SQLException {
        Map<String, Object> userInfo = dataReader.getUser(userAccount.getUserID());
        return (Integer) userInfo.get("OVERBORROWED");
    }

    /**
     * Admin goes through all flagged user and can freeze user
     *
     * @param userAccount the user id
     */
    public void freezeUser(UserAccount userAccount) {
        if (userAccount.isFrozen() || !userEnforcer.isFlagged(userAccount)) {

            if (userAccount.isFrozen()) {

            }
            return;
        }
        try {
            userEnforcer.freezeUser(userAccount);
            userEnforcer.unFlagUser(userAccount);
            dataUpdater.changeFreezeStatus(userAccount.getUserID());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Admin can unfreeze a user account
     *
     * @param userAccount the user's account
     */
    public void unfreezeUser(UserAccount userAccount) {
        if (!userAccount.isFrozen()) {

            return;
        }
        try {
            userEnforcer.unfreezeUser(userAccount);
            dataUpdater.changeFreezeStatus(userAccount.getUserID());
            if (userAccount.getUnfreezeRequested()) {
                dataUpdater.changeUnfreezeRequest(userAccount.getUserID(), 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /**
     * Admin resets user's number of trade back to zero each week
     */
    public void resetWeeklyTrades() {
        userEnforcer.resetNumTrades(userManager);
        try {
            for (UserAccount user : userManager.getAllUsers()) {
                dataUpdater.updateNumTrades(user.getUserID(), 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Gets the unfreeze User request
     * @return the user id who requested the unfreeze
     */
    public List<Integer> getUnfreezeRequests() {
        return this.userEnforcer.getUnfreezeRequestIDs();
    }


}

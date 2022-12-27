package accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserEnforcer {
    private final UserManager userManager;
    private int tradeLimit;
    private int incompleteLimit;
    private int overBorrowLimit;
    private final List<Integer> flaggedUsers;
    private final List<Integer> frozenUsers;
    private final List<Integer> unfrozenRequestIDs; // A list of IDs of the userAccount that requires to be unfrozen.

    /**
     * The UserEnforcer constructor.
     * @param userManager UserManager object
     * @param tradeLimit maximum number of trades allowed
     * @param incompleteLimit maximum number of incomplete trades allowed
     * @param overBorrowLimit number of items borrowed more than lent.
     */
    public UserEnforcer(UserManager userManager, int tradeLimit, int incompleteLimit, int overBorrowLimit){
        this.userManager = userManager;
        this.tradeLimit = tradeLimit;
        this.incompleteLimit = incompleteLimit;
        this.overBorrowLimit = overBorrowLimit;
        this.flaggedUsers = new ArrayList<>();
        this.frozenUsers = new ArrayList<>();
        this.unfrozenRequestIDs = new ArrayList<>();
        buildLists(userManager);
    }

    /** Updates the value of the tradeLimit, IncompleteLimit, and overBorrowLimit given a map of the limit's name to
     * its desired value.
     * @param limits a Map of each limit's name to its desired value
     */
    public void updateLimits(Map<String, Integer> limits) {
        tradeLimit = limits.get("tradeLimit");
        incompleteLimit = limits.get("incompleteLimit");
        overBorrowLimit = limits.get("overBorrowLimit");
    }

    /**
     * Add a user to the list of flagged users.
     * @param user the user to add
     */
    public void flagUser(UserAccount user){
        flaggedUsers.add(user.getUserID());
    }

    /**
     * Remove a user from the list of flagged users.
     * @param user the user to remove
     */
    public void unFlagUser(UserAccount user){
        flaggedUsers.remove(user.getUserID());
    }

    /**
     * Return the list of flagged users.
     * @return the list of flagged users
     */
    public List<Integer> getFlaggedUsers(){
        return flaggedUsers;

    }

    /**
     * Return the list of frozen users.
     * @return the list of frozen users
     */
    public List<Integer> getFrozenUsers(){
        return frozenUsers;
    }

    /**
     * Freeze a user's account.
     * @param user the user to freeze
     */
    public void freezeUser (UserAccount user){
       user.setFrozen(true);
       frozenUsers.add(user.getUserID());
    }

    /**
     * Unfreeze a user's account.
     * @param user the user to unfreeze
     */
    public void unfreezeUser (UserAccount user){
        user.setFrozen(false);
        frozenUsers.remove(user.getUserID());
    }

    /**
     * Reset all users number of trades to zero.
     * @param userManager the userManager object
     */
    public void resetNumTrades(UserManager userManager){
        for (UserAccount user : userManager.getAllUsers()) {
            user.setNumTrade(0);
        }
    }

    /**
     * Returns true iff a user has been flagged by the system
     * @param user The UserAccount to check
     * @return true iff the user is flagged
     */
    public boolean isFlagged(UserAccount user) {
        return flaggedUsers.contains(user.getUserID());
    }

    /**
     * Return a list of the ids of frozen UserAccounts that requested to be unfrozen.
     * @return a list of the ids of frozen UserAccounts that requested to be unfrozen.
     */
    public List<Integer> getUnfreezeRequestIDs(){
        return this.unfrozenRequestIDs;
    }

    /**
     * Allow a frozen UserAccount to request unfreeze.
     * @param userID the ID of the UserAccount that request to be unfrozen.
     */
    public boolean requestUnfrozen(Integer userID){
        boolean requestSuccess = false;
        UserAccount requester = userManager.getUser(userID);
        if(requester.isFrozen() && !(requester.getUnfreezeRequested())){
            requester.setUnfreezeRequested(true);
            requestSuccess = true;
            addToUnfreezeRequests(userID);
        }
        return requestSuccess;
    }

    /**
     * Add userID to the list of users who requested to unfreeze
     * @param userID the userID to be added.
     */
    public void addToUnfreezeRequests(Integer userID){
        unfrozenRequestIDs.add(userID);
    }

    /**
     * Flags all users who have borrowed more items than the limit, participated in the maximum number of incomplete
     * trades, or participated in the maximum number of weekly trades.
     * @param userManager the userManager object
     */
    private void buildLists(UserManager userManager) {
        for (UserAccount user : userManager.getAllUsers()) {
            if (!user.isFrozen() && (user.getOverBorrowed() > overBorrowLimit || user.getNumTrade() >= tradeLimit ||
                    user.getIncompleteTrade() >= incompleteLimit)) {
                flagUser(user);
            }
            if (user.isFrozen()) {
                frozenUsers.add(user.getUserID());
                unfrozenRequestIDs.add(user.getUserID());
            }
        }
    }
}



package controllers;
import accounts.UserAccount;
import accounts.UserEnforcer;
import accounts.UserManager;
import items.Item;
import items.ItemInventory;
import trades.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author Richard Zhou
 *
 */


/**
 * class AdminUndoController encapsulates all activities related to undo.
 */
public class AdminUndoController extends AdminController implements AdminUndoable{
    private final static int NUMRECENTTRADES = 3;

    /**
     *  Constructor
     * @param currentAdmin  current admin account
     * @param userManager user manager (managing uer to id map)
     * @param itemInventory list of items
     * @param userEnforcer enforce rules for users
     * @param mode database mode
     */
    public AdminUndoController(UserAccount currentAdmin, UserManager userManager, ItemInventory itemInventory, UserEnforcer userEnforcer, String mode) {
        super(currentAdmin, userManager, itemInventory, userEnforcer, mode);
    }

    /**
     * Undoes the most recent trade by removing it from the TradeLogManager and the Database
     * @param tradeLogManager TradeLogManager
     * @return boolean if the most recent trade is undone
     */
    public boolean undoLastTrade(TradeLogManager tradeLogManager){
        return undoRecentTrade(1, tradeLogManager);
    }

    /**
     * Undo the most recent trades(3)
     * @return boolean if the recent trade is undone
     */
    public boolean undoRecentTrades(TradeLogManager tradeLogManager){
        undoRecentTrade(NUMRECENTTRADES, tradeLogManager);
        return true;
    }


    /***
     * undo helper method
     * @param numTrades number of trades to be undone
     * @param ids list of trade IDs
     * @tradeLogManager   TradeLogManager
     * @throws SQLException
     */
    private boolean undoRecentTradeHelper(int numTrades, List<Integer> ids, TradeLogManager tradeLogManager) throws SQLException {
            //Remove trade from database table
            try {
                List<Integer> listOfTradeIds = dataReader.getAllTradeIDs();
                Collections.sort(listOfTradeIds);
                int size = listOfTradeIds.size();
                for (int i = 0; i < numTrades; i++) {
                    int tradeID = listOfTradeIds.get(size - 1 - i);
                    dataDeleter.removeData(tradeID, 3, 0);
                    //Remove Trade from TradeLog
                    Trade tr = tradeLogManager.getTradeWithID(tradeID);
                    tradeLogManager.removeTrade(tr);
                }
            } catch (SQLException e) {
               return false;
            }
        return true;
    }


    /**
     * undo trade with given tradeID
     * @param tradeID given trade ID
     * @tradeLogManager   TradeLogManager
     */
    public boolean undoGivenTrade(int tradeID, TradeLogManager tradeLogManager){
        try{
            //Remove trade from database table
            dataDeleter.removeData(tradeID, 3, 0);

            //Remove Trade from TradeLog
            Trade tr = tradeLogManager.getTradeWithID(tradeID);
            tradeLogManager.removeTrade(tr);
        } catch (SQLException | NullPointerException e){
            return false;
        }
        return true;
    }


    /**
     * Undo recent numTrades trades
     * @param numTrades number of recent Trades to be undone
     * @tradeLogManager   TradeLogManager
     */
    private boolean undoRecentTrade(int numTrades, TradeLogManager tradeLogManager){
        List<Integer> ids = null;
        try {
            ids = dataReader.getAllTradeIDs();
            if (! ids.isEmpty()) {
                Collections.sort(ids);
            }

            //no index out of bounds
            if (numTrades > ids.size())
                numTrades = ids.size();

            //Remove numTrades Trades
            undoRecentTradeHelper(numTrades, ids, tradeLogManager);
        }catch (SQLException e){
            return false;
        }
        return true;
    }


    /**
     * Removes item itemID belonging to userID from inventory
     * @param itemID item id
     */
    public boolean removeItemFromInventory(int itemID){
        //Remove item from ITEMS database table
        try {
            Item item = itemInventory.getItemWithID(itemID);
            UserAccount owner = userManager.getUser(item.getOwnerID());

            owner.removeFromInventory(itemID);
            if (item.isApproved()) {
                itemInventory.removeFromApproved(item);
            } else {
                itemInventory.removeFromPending(item);
            }
            dataDeleter.removeData(itemID, 2, owner.getUserID());

        }catch (SQLException | NullPointerException e){
            return false;
        }
        return true;
    }


    /**
     * Removes the item with the given ID from the user with the given ID's wishlist. Returns true iff the removal is
     * successful.
     * @param itemID the item to remove
     * @param userID the user to remove from wishlist
     * @return true iff the item was successfully removed from the user's wishlist
     */
    public boolean removeItemFromWishlist(Integer itemID, Integer userID){
        //Remove item from ITEMS database table
        try {
            dataDeleter.removeData(itemID, 0, userID);
            userManager.getUser(userID).removeFromWishlist(itemID);
        }catch (NullPointerException | SQLException e){
            return false;
        }
        return true;
    }
}


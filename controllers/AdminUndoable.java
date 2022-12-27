package controllers;

import trades.TradeLogManager;



/**
 * @author Richard Zhou
 * Interface AdminUndoable provides method definition for admin
 * undo actions.
 * 
 */
public interface AdminUndoable {
    public boolean undoLastTrade(TradeLogManager tradeLogManager);
    public boolean undoRecentTrades(TradeLogManager tradeLogManager);
    public boolean undoGivenTrade(int tradeID, TradeLogManager tradeLogManager);
    public boolean removeItemFromWishlist(Integer itemID, Integer userID);
    public boolean removeItemFromInventory(int itemID);
}

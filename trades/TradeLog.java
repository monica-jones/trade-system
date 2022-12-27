package trades;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wen Hua Tung
 */

public class TradeLog {

    private final Map<Integer, Trade> tradeHistory;

    /**
     * constructor of TradeLog.
     */
    public TradeLog() {
        this.tradeHistory = new HashMap<>();
    }

    /**
     * constructor of TradeLog.
     *
     * @param allTrades the list of all trades in the system.
     */
    public TradeLog(Map<Integer, Trade> allTrades) {
        this.tradeHistory = allTrades;
    }

    /**
     * Gets the history of trade
     *
     * @return the history of trade
     */
    public Map<Integer, Trade> getTradeHistory() {
        return tradeHistory;
    }

    /**
     * add trade to the list of trade history
     *
     * @param tradeID the unique ID of the trade to be added
     * @param trade   the Trade to be added
     */
    public void addTrade(Integer tradeID, Trade trade) {
        tradeHistory.put(tradeID, trade);
    }

    /**
     * remove trade to the list of trade history
     *
     * @param trade the Trade to be removed
     */
    public void removeTrade(Trade trade) {
        tradeHistory.remove(trade.getTradeID());
    }
}

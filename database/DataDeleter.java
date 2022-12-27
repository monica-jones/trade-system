package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Aly Abdelwahed
 */

public class DataDeleter {

    private String database = "";

    /**
     * Constructor
     * @param name the name of the database
     */
    public DataDeleter(String name){
        this.database += name;
    }

    /**
     * Removes items from the WISHLIST table with the given itemID
     * @param connection connection variable to the database you'd like to write the tables to.
     * @param itemID ID of the item
     * @param userID ID of the user (This is only used in case the method is removing only the row where the
     *               itemID from the table for a specific userID)
     * @param removeAll if this is true, it removes all instances of the itemID in the table
     *                  if this is false, it will remove the instance where the userID and the itemID match
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    protected void removeItemFromWishlist(Connection connection, int itemID, int userID, boolean removeAll)
            throws SQLException {
        String sql; PreparedStatement preparedStatement;
        if (removeAll){
            sql = "DELETE FROM WISHLIST WHERE ITEM_ID = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemID);
            preparedStatement.executeUpdate();
        }else{
            sql = "DELETE FROM WISHLIST WHERE ITEM_ID = ? AND WISHER_ID = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemID);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate(); }
        preparedStatement.close();
    }

    /**
     * Removes all the traded items in the ITEMS_TRADED table
     * @param connection connection variable to the database you'd like to write the tables to.
     * @param tradeID ID of the trade
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     */
    protected void removeTradedItems(Connection connection, int tradeID) throws SQLException {
        String sql = "DELETE FROM ITEMS_TRADED WHERE TRADE_ID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, tradeID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Removes an item with all of its' information in either the WISHLIST, ITEMS, TRADES tables
     * @param id ID of the trade/item
     * @param typeOfRemoval if this value is 0, then it updates the WISHLIST table
     *                      if this value is 2, then the method updates the ITEMS CSV table
     *                      if this value is 3, then the method updates the TRADES CSV table
     * @param userID ID of the user for which the item will be removed (Only used if the removal is from
     *               the WISHLIST table)
     * @throws SQLException thrown if there is failure or interruption in the SQL operations
     * @throws NullPointerException thrown when null is passed as an argument
     */
    public void removeData(int id, int typeOfRemoval, int userID) throws SQLException, NullPointerException {
        Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
        if(typeOfRemoval == 0){
            removeItemFromWishlist(connection, id, userID, false);
        }else if (typeOfRemoval == 2){
            removeItemFromInventory(connection, id, userID);
        }else if(typeOfRemoval == 3){
            removeTrade(connection, id);
        }
        connection.close();
    }


    /**
     * remove items from the given trade id
     * @param connection connection variable to the database you'd like to write the tables to.
     * @param tradeID ID of the trade
     * @throws SQLException thrown if there was an error in the SQL Operation
     */
    private void removeItemsTraded (Connection connection, int tradeID) throws SQLException {
        String sql = "DELETE FROM ITEMS_TRADED WHERE TRADE_ID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, tradeID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * remove the trade
     * @param connection connection variable to the database you'd like to write the tables to.
     * @param tradeID id of the trade
     * @throws SQLException thrown if there was an error in the SQL Operation
     */
    private void removeTrade(Connection connection, int tradeID) throws SQLException{
        removeItemsTraded(connection, tradeID);
        String sql = "DELETE FROM TRADES WHERE TRADE_ID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, tradeID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * remove the given item id from the given user id's inventory
     * @param connection connection variable to the database you'd like to write the tables to.
     * @param itemID id of the item
     * @param userID id of the user
     * @throws SQLException thrown if there was an error in the SQL Operation
     */
    private void removeItemFromInventory(Connection connection, int itemID, int userID) throws SQLException {
        removeItemFromWishlist(connection, itemID, userID, true);
        String sql = "DELETE FROM ITEMS WHERE ITEM_ID = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, itemID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


/**
 * @author Aly Abdelwahed
 */

public class DataUpdater {

	private String database = "";

	/**
	 * Constructor
	 * @param name name of the database
	 */
	public DataUpdater(String name){
		this.database += name;
	}

	/**
	 * Updates the value that determines whether the user has accepted to do the meeting or not
	 * @param tradeID ID of the trade
	 * @param tradingUserID ID of the trading user
	 * @param columnIndex Index of the column that has the data that needs to be updated
	 * @param newData if it is 0, then the user refused the meeting/trade
	 *                        If it is 1, then the user accepted the meeting/trade
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	public void updateTradeMap(int tradeID, int columnIndex, int tradingUserID, int newData) throws SQLException {
		checkColumnUpdated(columnIndex);
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADE_MAP WHERE TRADE_ID = " + tradeID+";");
		Map<Integer, String> updateValue = getColumns(resultSet);
		String sql = "UPDATE TRADE_MAP SET " + updateValue.get(columnIndex)+ " = ? " +" WHERE AND TRADING_TRADE_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newData);
		preparedStatement.setInt(2, tradeID);
		preparedStatement.executeUpdate();
		closeResources(connection, preparedStatement, resultSet);
	}

	/**
	 * Updates any column in the TRADES table given the new data
	 * @param tradeID ID of the Trade
	 * @param columnIndex Index of the column that has the data that needs to be updated
	 * @param newData The new data that will be inserted in the specified column index
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or if columnIndex is 0
	 *                      which the method is being asked to update the value of the tradeID
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void updateTrade(int tradeID, int columnIndex, String newData) throws SQLException,
	NullPointerException {
		checkColumnUpdated(columnIndex);
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADES WHERE TRADE_ID = " + tradeID+";");
		Map<Integer, String> updateValue = getColumns(resultSet);
		String sql = "UPDATE TRADES SET " + updateValue.get(columnIndex)+ " = ? " +" WHERE TRADE_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		if(columnIndex!=1 && columnIndex!=2 && columnIndex!=4 && columnIndex!=5 && columnIndex!=6 && columnIndex!=7){
			preparedStatement.setInt(1, Integer.parseInt(newData)); }
		else{ preparedStatement.setString(1, newData); }
		checkTradeCompleted(connection, tradeID, columnIndex, newData);
		preparedStatement.setInt(2, tradeID);
		preparedStatement.executeUpdate();
		closeResources(connection, preparedStatement, resultSet);
	}

	/**
	 * checks if the trade is complete
	 * @param connection the connection of the database
	 * @param tradeID the id of the trade
	 * @param columnIndex the index of the column
	 * @param newData a string of the new data
	 * @throws SQLException
	 */
	private void checkTradeCompleted(Connection connection, int tradeID, int columnIndex, String newData) throws SQLException {
		if ((columnIndex == 1 || columnIndex == 4 || columnIndex == 5) && "complete".equals(newData.toLowerCase())){
			completeTrade(connection, tradeID, columnIndex);
			setExpectedReturnData(connection, tradeID);
		}else if (columnIndex == 1 && "incomplete".equals(newData.toLowerCase())) {
			incompleteTrade(connection, tradeID);
			new DataDeleter(this.database).removeTradedItems(connection, tradeID);
		}
	}
	

	/**
	 * This method changes the status of an item from unapproved to approved in the ITEMS table with the given
	 * the itemID
	 * @param itemID ID of the item
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void approveItem(int itemID) throws SQLException, NullPointerException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		String sql = "UPDATE ITEMS SET IS_APPROVED = 1 WHERE ITEM_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, itemID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}

	/**
	 * This method allows an adminUser to modify the trade count of a specified UserAccount
	 * @param userID ID of the user whose trade count will be modified
	 * @param newNumTrade new trade count for the user
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void updateNumTrades(int userID, int newNumTrade) throws SQLException, NullPointerException {
		String sql = "UPDATE USERS SET NUM_TRADES = ? WHERE USER_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newNumTrade);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}


	/**
	 * Changes the freeze status of a user in the 'Users' CSV file
	 * @param userID ID of the user
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or if the user does
	 *                      not exist in the database
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
    public void changeFreezeStatus(int userID) throws SQLException, NullPointerException {
		String sql = "UPDATE USERS SET IS_FROZEN = ? WHERE USER_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int freezeValue = 1;
		if (new DataReader(this.database).getIsFrozen(userID)){
			freezeValue = 0;
		}
		preparedStatement.setInt(1, freezeValue);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}

	/**
	 * Updates the amount of funding a user has for the given userID
	 * @param userID ID of the user
	 * @param newFund amount of the new fund
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	private void updateUserFund(Connection connection, int userID, double newFund) throws SQLException {
		String sql = "UPDATE USERS SET FUNDS = ? WHERE USER_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setDouble(1, newFund);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
	}

	/**
	 * Updates the price of an item for the given itemID
	 * @param itemID ID of the item
	 * @param newPrice new price of the item
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	protected void updateItemPrice(int itemID, double newPrice) throws SQLException {
		String sql = "UPDATE ITEMS SET PRICE = ? WHERE ITEM_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setDouble(1, newPrice);
		preparedStatement.setInt(2, itemID);
		preparedStatement.executeUpdate();
		closeResources(connection, preparedStatement);
	}
	
	/**
	 * Updates the USERS table in the database by adding the price of newly added item to the users' funds
	 * @param userID ID of the user/owner
	 * @param price price of the new item
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	protected void updateUserFunds(int userID, Double price) throws SQLException{
		String sql = "UPDATE USERS SET FUNDS = ? WHERE USER_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setDouble(1, new DataReader(this.database).getOwnerFunds(userID) + price);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closeResources(connection, preparedStatement);
	}

	/**
	 * checks if the column is updated
	 * @param columnIndex the index of the column
	 * @throws SQLException
	 */
	private void checkColumnUpdated(int columnIndex) throws SQLException{
		if (columnIndex == 0){
			throw new SQLException("Updating The Value Of The Trade ID Is Forbidden. Please Try Again With A Different" +
					" Value To Update!");
		}
	}


	/**
	 * Helper method that returns a new date which is exactly 30 days from the date in the given row
	 * @param connection connection variable to the database we'd like to write the tables to.
	 * @param tradeID ID of the trade
	 */
	private void setExpectedReturnData(Connection connection, int tradeID) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADES WHERE TRADE_ID = " + tradeID + ";");
		LocalDateTime expectedData;
		String newDate= "";
		while(resultSet.next()){
			if(resultSet.getInt("IS_PERMANENT") == 0){
				expectedData = LocalDateTime.parse(resultSet.getString("TRADE_DATE")).plusDays(30);
				newDate = String.valueOf(expectedData);}}
		String sql = "UPDATE TRADES SET EXPECTED_RETURN_DATE = ? WHERE TRADE_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, newDate);
		preparedStatement.setInt(2, tradeID);
		preparedStatement.executeUpdate();
		closeResources(preparedStatement, resultSet, statement);
	}

	/**
	 * gets the column of the ResultSet
	 * @param resultSet the ResultSet
	 * @return a map of the table data
	 * @throws SQLException
	 */
	private Map<Integer, String> getColumns (ResultSet resultSet) throws SQLException {
		ResultSetMetaData metadata = resultSet.getMetaData();
		int columnCount = metadata.getColumnCount();
		Map<Integer, String> tableData = new HashMap<>();
		for (int i=1; i<=columnCount; i++) {
			tableData.put(i-1, metadata.getColumnName(i));
		}
		return tableData;
	}

	/**
	 * updates information of the user trade
	 * @param connection connection of the database
	 * @param borrowerID the user id of the borrower
	 * @param lenderID the user id of the lender
	 * @throws SQLException
	 */
	private void updateUserTradeInfo(Connection connection, int borrowerID, int lenderID) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS;");
		String sql = "UPDATE USERS SET OVERBORROWED = ? WHERE USER_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		while (resultSet.next()){
			if(resultSet.getInt("USER_ID") == borrowerID) {
				preparedStatement.setInt(1, resultSet.getInt("OVERBORROWED") - 1);
				preparedStatement.setInt(2, borrowerID);
				preparedStatement.executeUpdate();
			}else if(resultSet.getInt("USER_ID") == lenderID) {
				preparedStatement.setInt(1, resultSet.getInt("OVERBORROWED") + 1);
				preparedStatement.setInt(2, lenderID);
				preparedStatement.executeUpdate();
			}
		}
		closeResources(preparedStatement, resultSet, statement);
	}

	/**
	 * updates the item inventories
	 * @param connection the connection of the database
	 * @param itemID the id of the item
	 * @param newOwnerID the user id of the new owner of the item
	 * @throws SQLException
	 */
	private void updateItemInventories(Connection connection, int itemID, int newOwnerID) throws SQLException {
		String sql = "UPDATE ITEMS SET OWNER_ID = ? WHERE ITEM_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newOwnerID);
		preparedStatement.setInt(2, itemID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
	}

	/**
	 * updates the owner id of the the item
	 * @param connection the connection of the database
	 * @param tradeID the id of the trade
	 * @throws SQLException
	 */
	private void updateOwnerID(Connection connection, int tradeID, int columnIndex) throws SQLException{
		Map<Integer, List<Integer>> originalMap = new DataReader(this.database).getOriginalOwnership(connection, tradeID);
		Map<Integer, List<Integer>> recievers = new DataReader(this.database).getRecieversMap(connection, tradeID);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID+";");
		String columnName = "ITEM_TRADED_ID";
		DataDeleter deleteData = new DataDeleter(this.database);
		Map<String, Object> data = new HashMap<>();
		while(resultSet.next()) {
			if(resultSet.getInt("ITEM_TRADED_ID") != 0){
				data.put("borrower", resultSet.getInt("RECIEVER_ID"));
				data.put("lender", new DataReader(this.database).getOwnerIDOfItem(resultSet.getInt("ITEM_TRADED_ID")));
				deleteData.removeItemFromWishlist(connection, resultSet.getInt(columnName),(int) data.get("borrower"), false);
				updateUserTradeInfo(connection, (int)data.get("borrower"), (int)data.get("lender"));
				updateItemInventories(connection, resultSet.getInt(columnName), (int)data.get("borrower"));
			}
		}
		if(columnIndex == 1 || columnIndex == 5) {
			deleteData.removeTradedItems(connection, tradeID);
		}else if(columnIndex == 4 && (Integer)new DataReader(this.database).getTrade(tradeID).get("IS_PERMANENT") == 0) {
			updateItemsTradedSecondMeeting(connection, tradeID, originalMap, recievers);
		}
		closeResources(resultSet, statement);
	}
	
	
	private Map<Integer, List<Integer>> getSecondMeetingMap (Map<Integer, List<Integer>> originalMap, 
			Map<Integer, List<Integer>> newMap){
		Map<Integer, List<Integer>> returnMeetingMap = new HashMap<Integer, List<Integer>>();
		returnMeetingMap.putAll(originalMap);
		List<Integer> oneWayIntegers;
		for(Integer x: newMap.keySet()) {
			oneWayIntegers = new ArrayList<Integer>();
			if(!returnMeetingMap.containsKey(x)) {
				oneWayIntegers.add(0);
				returnMeetingMap.put(x, oneWayIntegers);
			}
		}
		return returnMeetingMap;
	}
	
	private void updateItemsTradedSecondMeeting(Connection connection, int tradeID, Map<Integer, List<Integer>> originalMap, 
			Map<Integer, List<Integer>> newMap) throws SQLException {
		Map<Integer, List<Integer>> secondMeetingMap = new DataUpdater(this.database).getSecondMeetingMap(originalMap, newMap);
		
		String sql = "UPDATE ITEMS_TRADED SET RECIEVER_ID = ? WHERE ITEM_TRADED_ID = ? AND TRADE_ID = ?;";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		for(Integer recieverID: secondMeetingMap.keySet()) {
			for(Integer item: secondMeetingMap.get(recieverID)) {
				preparedStatement.setInt(1, recieverID);
				preparedStatement.setInt(2, item);
				preparedStatement.setInt(3, tradeID);
				preparedStatement.executeUpdate();
			}
		}
		closePreparedStatement(preparedStatement);
	}
	
//	public static void printMap(Map<Integer, List<Integer>> map) {
//	for(Integer x: map.keySet()) {
//		System.out.print("User ID " +x+": " );
//		for(Integer y: map.get(x)) {
//			System.out.print(y + " ");
//		}
//		System.out.println();
//	}
//}
	
//	public static void main(String[] args) throws SQLException{
//		DataUpdater dataUpdater = new DataUpdater("");
//		dataUpdater.updateTrade(1, 4, "complete");
//		Connection connection = new DatabaseDriver("").connectOrCreateDataBase();
//		System.out.println("Original: ");
//		printMap(new DataReader("").getCurrentxOwnership(connection, 1));
//		System.out.println("\nRecievers: ");
//		printMap(new DataReader("").getRecieversMap(connection, 1));
//		
//		System.out.println("\nReturn Meeting: ");
//		printMap(new DataUpdater("").secondMeetingMap(new DataReader("").getCurrentxOwnership(connection, 1), new DataReader("").getRecieversMap(connection, 1)));
//		connection.close();
//	}
	

	/**
	 * updates the user's funds after the trade
	 * @param connection the connection of the database
	 * @param tradeID the id of the trade
	 * @param itemID the id of the item
	 * @param recieverID the user id of the receiver
	 * @throws SQLException
	 */
	private void updateUserFundsAfterTrade(Connection connection, int tradeID, int itemID, int recieverID) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID+";");
		DataReader dataReader = new DataReader(this.database);
		int databaseItemID = 0, databaseRecieverID=0, ownerID=0;
		while (resultSet.next()){
			databaseRecieverID = resultSet.getInt("RECIEVER_ID");
			databaseItemID = resultSet.getInt("ITEM_TRADED_ID");
			if(databaseItemID == itemID && databaseRecieverID == recieverID) {
				ownerID = dataReader.getOwnerIDOfItem(itemID);
				updateUserFund(connection, recieverID, dataReader.getOwnerFunds(recieverID) - dataReader.getItemPrice(itemID));
				updateUserFund(connection, ownerID, dataReader.getOwnerFunds(ownerID) + dataReader.getItemPrice(itemID));
			}
		}
		closeResources(resultSet, statement);
	}


	/**
	 * Changes the vacation status of a user in the 'Users' CSV file
	 * @param userID ID of the user
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void changeVacationStatus(int userID) throws SQLException, NullPointerException {
		String sql = "UPDATE USERS SET IS_ON_VACATION = ? WHERE USER_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int vacationValue = 1;
		if (new DataReader(this.database).getIsOnVacation(userID)){
			vacationValue = 0;
		}
		preparedStatement.setInt(1, vacationValue);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}

	/**
	 * Changes the UNFREEZE_REQUEST column in the USERS table in the database
	 * @param userID ID of the user
	 * @param unfreezeRequestValue new Unfreeze value (1 - means the user requested to be unfrozen, 0-means the user did
	 * not request to be unfrozen)
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void changeUnfreezeRequest(int userID, int unfreezeRequestValue) throws SQLException, NullPointerException {
		String sql = "UPDATE USERS SET UNFREEZE_REQUEST = ? WHERE USER_ID = ?;";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, unfreezeRequestValue);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}


	/**
	 * completes the trade
	 * @param connection the connection of the database
	 * @param tradeID the id of the trade
	 * @throws SQLException
	 */
	private void completeTrade(Connection connection, int tradeID, int columnIndex) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID+";");
		while (resultSet.next()){
			if(resultSet.getInt("ITEM_TRADED_ID") != 0) {
				updateUserFundsAfterTrade(connection, tradeID, resultSet.getInt("ITEM_TRADED_ID"), resultSet.getInt("RECIEVER_ID"));
			}
		}
		updateOwnerID(connection, tradeID, columnIndex);
		closeResources(resultSet, statement);
	}
	
	/**
	 * Increments the value of the INCOMPLETE_TRADES COLUMN in the USERS table by 1
	 * @param connection connection variable to the database we'd like to write the tables to.
	 * @param userID ID of the user
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	private void incrementUserIncomplete(Connection connection, int userID) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		if(resultSet.next()) {
			int originalIncompleteValue = resultSet.getInt("INCOMPLETE_TRADES");
			String sql = "UPDATE USERS SET INCOMPLETE_TRADES = ? WHERE USER_ID = ?;";		
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, originalIncompleteValue+1);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
			closeResources(preparedStatement, resultSet, statement);
		}
	}

	/**
	 * incomplete trade
	 * @param connection the connection of the database
	 * @param tradeID the id of the trade
	 * @throws SQLException
	 */
	private void incompleteTrade(Connection connection, int tradeID) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID+";");
		List<Integer> tradingIDs = new ArrayList<>();
		int traderID;
		while (resultSet.next()){
			traderID = resultSet.getInt("RECIEVER_ID");
			if(!tradingIDs.contains(traderID)) {
				tradingIDs.add(traderID);
				incrementUserIncomplete(connection, traderID);
			}
		}
		closeResources(resultSet, statement);
	}

	/**
	 * closes resources
	 * @param resultSet the ResultSet
	 * @param statement the Statement
	 * @throws SQLException
	 */
	private void closeResources(ResultSet resultSet, Statement  statement) throws SQLException{
		closeResultSet(resultSet);
		closeStatement(statement);
	}

	/**
	 * closes resources
	 * @param connection the database connection
	 * @param preparedStatement the PreparedStatement
	 * @param resultSet the ResultSet
	 * @throws SQLException
	 */
	private void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException{
		closeConnection(connection);
		closeResultSet(resultSet);
		closePreparedStatement(preparedStatement);
	}

	/**
	 * closes resources
	 * @param preparedStatement the PreparedStatement
	   @param resultSet the ResultSet
	 * @param statement the Statement
	 * @throws SQLException
	 */
	private void closeResources( PreparedStatement preparedStatement, ResultSet resultSet, Statement  statement) throws SQLException{
		closeResultSet(resultSet);
		closeStatement(statement);
		closePreparedStatement(preparedStatement);
	}

	/**
	 * closes resources
	 * @param connection the database connection
	 * @param preparedStatement the PreparedStatement
	 * @throws SQLException
	 */
	private void closeResources(Connection connection, PreparedStatement preparedStatement) throws SQLException{
		closeConnection(connection);
		closePreparedStatement(preparedStatement);
	}

	/**
	 * closes resources
	 * @param resultSet the ResultSet
	 * @throws SQLException
	 */
	private void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	}

	/**
	 * closes resources
	 * @param connection the connection to database
	 * @throws SQLException
	 */
	private void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

	/**
	 * closes resources
	 * @param statement the Statement
	 * @throws SQLException
	 */
	private void closeStatement(Statement  statement) throws SQLException {
		statement.close();
	}

	/**
	 * closes resources
	 * @param preparedStatement the PreparedStatement
	 * @throws SQLException
	 */
	private void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.close();
	}
}
package database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



/**
 * @author Aly Abdelwahed
 */

public class DataReader {

	private String database = "";

	/**
	 * Constructor
	 * @param name name of the database
	 */
	public DataReader(String name){
		this.database += name;
	}

	/**
	 * This method looks through the TRADES table and returns the meeting information about the trade
	 * @param tradeID ID of the trade
	 * @return Map containing information about the meeting related to a trade
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public Map<String, String> getMeetingInfo(int tradeID) throws SQLException, NullPointerException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADES WHERE TRADE_ID = " + tradeID+";");
		Map<String, String> meetingInformationMap = new HashMap<String, String>();
		if(resultSet.next()){
			meetingInformationMap.put("LOCATION", resultSet.getString("LOCATION"));
			meetingInformationMap.put("TRADE_DATE", resultSet.getString("TRADE_DATE"));
			meetingInformationMap.put("FIRST_MEETING_NUM_EDITS", resultSet.getString("FIRST_MEETING_NUM_EDITS"));
			meetingInformationMap.put("FIRST_MEETING_STATUS", resultSet.getString("FIRST_MEETING_STATUS"));
			meetingInformationMap.put("SECOND_MEETING_STATUS", resultSet.getString("SECOND_MEETING_STATUS"));
			meetingInformationMap.put("FIRST_MEETING_LAST_EDITOR", resultSet.getString("FIRST_MEETING_LAST_EDITOR"));
			meetingInformationMap.put("SECOND_MEETING_LAST_EDITOR", resultSet.getString("SECOND_MEETING_LAST_EDITOR"));
			meetingInformationMap.put("SECOND_MEETING_NUM_EDITS", resultSet.getString("SECOND_MEETING_NUM_EDITS"));
			meetingInformationMap.put("IS_PERMANENT", resultSet.getString("IS_PERMANENT"));
		}else{
			throw new SQLException("Trade " + tradeID +" Does Not Exist. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return meetingInformationMap;
	}


	/**
	 * Looks through the ACCOUNTS table to find the user and determine whether it is an AdminAccount or not
	 * @param accountID The ID of the User
	 * @return A boolean value, if it is true, then the user is an AdminAccount. Otherwise, if it is false,
	 *         then the user is a UserAccount
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public boolean getIsAdmin(int accountID) throws SQLException, NullPointerException{
		boolean isAdmin = true;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE ACCOUNT_ID = " + accountID+";");
		if(resultSet.next()){
			if(resultSet.getInt("IS_ADMINISTRATOR") == 0){ isAdmin = false; }
		}else{
			throw new SQLException("User " + accountID+" Does Not Exist. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return isAdmin;
	}


	/**
	 * This methods looks through the ITEMS_TRADED table and returns a map that maps the users who will receive items to
	 * the items they will receive using the given tradeID
	 * @param tradeID ID of the trade
	 * @return map that maps the users who will receive items to the items they will receive
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public Map<Integer, List<Integer>> getReceivingUsers(int tradeID) throws SQLException, NullPointerException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID +" ORDER BY "
				+ "RECIEVER_ID ASC;");
		Map<Integer, List<Integer>> data = new HashMap<>();
		int recieverID = 0;
		while(resultSet.next()) {
			recieverID = resultSet.getInt("RECIEVER_ID");
			int itemID = resultSet.getInt("ITEM_TRADED_ID");
			if(!data.containsKey(recieverID)) { //add user to receiver map if they are not already in it
				data.put(recieverID, new ArrayList<>());
			}
			if (itemID != 0) { //add item to receiverMap if it does not have ID 0
				data.get(recieverID).add(itemID);
			}
		}
		closeResources(connection, resultSet, statement);
		return data;
	}

	/**
	 * This method finds the ID of the owner of the given itemID from the ITEMS table
	 * @param itemID ID of the item
	 * @return ID of the owner
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or if the item ID
	 * 		   does not exist in the database
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	protected int getOwnerIDOfItem(int itemID) throws SQLException, NullPointerException{
		int ownerID = 0;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID = " + itemID+";");
		if(resultSet.next()){
			ownerID = resultSet.getInt("OWNER_ID");
		}else{
			throw new SQLException("Item ID " + itemID +" Does Not Exist. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return ownerID;
	}

	/**
	 * finds the amount of funding the userID has from the USERS table
	 * @param userID ID of the user
	 * @return the amount of funding the user has
	 * @throws SQLException if there is failure or interruption in the SQL operations or if the item ID
	 * 	 					does not exist in the database
	 * @throws NullPointerException
	 */
	public double getOwnerFunds(int userID) throws SQLException, NullPointerException{
		double fund = 0;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		if(resultSet.next()){
			fund = resultSet.getDouble("FUNDS");
		}else{
			throw new SQLException("User ID " + userID +" Does Not Exist. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return fund;
	}

	/**
	 * finds the price of a given itemID from the ITEMS table
	 * @param itemID itemID ID of the item
	 * @return price of the item
	 * @throws SQLException if there is failure or interruption in the SQL operations or if the item ID
	 * 	 		   			does not exist in the database
	 */
	public double getItemPrice(int itemID) throws SQLException{
		double price = 0;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID = " + itemID+";");
		if(resultSet.next()){
			price = resultSet.getDouble("PRICE");
		}else{
			throw new SQLException("Item ID " + itemID +" Does Not Exist. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return price;
	}

	/**
	 * This method queries the 'USERS' table in the database to check if the user is frozen or not
	 * @param userID ID of the user
	 * @return true if the user is frozen, false if the user is not frozen
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or if the user does
	 *                      not exist in the database
	 */
	public boolean getIsFrozen(int userID) throws SQLException{
		boolean isFrozen = true;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		if(resultSet.next()){
			if(resultSet.getInt("IS_FROZEN") == 0){ isFrozen = false; }
		}else{
			throw new SQLException("User " + userID + " Does Not Exist in The System. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return isFrozen;
	}

	/**
	 * return if the user is flagged
	 * @param userID ID of the user
	 * @return boolean if the given user is flagged
	 * @throws SQLException
	 */
	public boolean getIsFlagged(int userID) throws SQLException{
		boolean isFlagged = true;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		if(resultSet.next()){
			if(resultSet.getInt("OVERBORROWED") == 0){ isFlagged = false; }
		}else{
			throw new SQLException("User " + userID + " Does Not Exist in The System. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return isFlagged;
	}

	/**
	 * This method reads the data of all tradeThresholds from a txt file called 'config.txt'
	 * @return Map containing the type of Threshold as key and the data as values under the key
	 * @throws IOException thrown if there is failure or interruption in the I/O operations
	 */
	public Map<String, Integer> getTradeThresholds() throws IOException {
		File configFile = new File("./config.txt");
		if (!configFile.exists()){
			Map<String, Integer> defaultFile = new HashMap<>();
			defaultFile.put("meetingEditLimit", 7);
			defaultFile.put("tradeLimit", 4);
			defaultFile.put("incompleteLimit", 1);
			defaultFile.put("overBorrowLimit", 11);
			new DataInserter("").writeTradeThresholds(defaultFile);
		}
		FileReader configReader = new FileReader("./config.txt");
		Properties properties = new Properties();
		properties.load(configReader);
		Map<String, Integer> tradeThresholds = new HashMap<>();
		for (String x : properties.stringPropertyNames()) {
			tradeThresholds.put(x, Integer.parseInt(properties.getProperty(x)));
		}
		configReader.close();
		return tradeThresholds;
	}


	/**
	 * Queries through the 'PASSWORDS' table in the database and returns a string that contains the password of the
	 * given account ID
	 * @param accountID The ID of the Account
	 * @return The password of the user
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument or when the given UserID
	 *                              does not exist in the CSV file
	 */
	public String findPassword(int accountID)throws SQLException, NullPointerException {
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM PASSWORDS WHERE ACCOUNT_ID = " + accountID+";");
		String password = resultSet.getString("PASSWORD");
		closeResources(connection, resultSet, statement);
		return password;
	}

	/**
	 * Queries through the 'ACCOUNTS' table in the database and returns a map with the information about
	 * the given adminID
	 * @param adminID The ID of the administrator
	 * @return Map containing the appropriate information of the given adminID
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument or when the user is not found or
	 *                              does not exist in the CSV file
	 */
	protected Map<String, Object> getAdminAccount(int adminID) throws SQLException, NullPointerException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE ACCOUNT_ID = " + adminID +
				" AND IS_ADMINISTRATOR = 1;");
		Map <String, Object> data = new HashMap<>();
		while(resultSet.next()){
			data = addData(resultSet);
			data.put("PASSWORD", findPassword(adminID));
		}
		closeResources(connection, resultSet, statement);
		return data;
	}

	/**
	 * Queries through the 'USERS' table in the database and returns a map with the information about the given userID
	 * @param userID The ID of the User
	 * @return Map containing the appropriate information of the given userID
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument or when the user is not found or
	 *                              does not exist in the CSV file
	 */
	public Map<String, Object> getUser(int userID)throws SQLException, NullPointerException {
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		Map <String, Object> data = new HashMap<>();
		while(resultSet.next()){
			data = addData(resultSet);
			data.put("PASSWORD", findPassword(userID));
			data = addData(resultSet);
		}
		closeResources(connection, resultSet, statement);
		return data;
	}


	/**
	 * This method queries the 'USERS' table in the database to check if the user is on vacation or not
	 * @param userID ID of the user
	 * @return true if the user is on vacation, false if the user is not on vacation
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or if the user does
	 *                      not exist in the database
	 */
	protected boolean getIsOnVacation(int userID) throws SQLException{
		boolean isOnVacation = true;
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE USER_ID = " + userID+";");
		if(resultSet.next()){
			if(resultSet.getInt("IS_ON_VACATION") == 0){ isOnVacation = false; }
		}else{
			throw new SQLException("User " + userID + " Does Not Exist in The System. Please Try Again!");
		}
		closeResources(connection, resultSet, statement);
		return isOnVacation;
	}


	/**
	 * Queries through the 'ITEMS' table in the database and returns a map with the information about the given itemID
	 * @param itemID The ID of the item
	 * @return Map containing the appropriate information of the given itemID
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public Map<String, Object> getItem(int itemID) throws SQLException, NullPointerException {
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID = " + itemID+";");
		Map <String, Object> data = new HashMap<>();
		while(resultSet.next()){
			data = addData(resultSet);
		}
		closeResources(connection, resultSet, statement);
		return data;
	}

	/**
	 * Queries through the 'WISHLIST' table in the database and returns a map with the information about the given
	 * ownerID's wishlist of items
	 * @param wisherID ID of the user who wishes to have this item
	 * @return list of integers consisting of itemIDs the the given wisherID wishes to have
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public List<Integer> getUserWishlist(int wisherID)throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM WISHLIST WHERE WISHER_ID = " + wisherID+";");
		List<Integer> wishList = new ArrayList<>();
		while (resultSet.next()){
			wishList.add(resultSet.getInt("ITEM_ID"));
		}
		closeResources(connection, resultSet, statement);
		return wishList;
	}

	/**
	 * Queries through the 'ITEMS' table in the database and returns a list of integers consisting
	 * of itemIDs the the given ownerID owns
	 * @param ownerID ID of the user who owns the item
	 * @return list of integers consisting of itemIDs the the given ownerID owns
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public List<Integer> getUserInventory(int ownerID) throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE OWNER_ID = " + ownerID+";");
		List<Integer> inventory = new ArrayList<>();
		while (resultSet.next()){
			inventory.add(resultSet.getInt("ITEM_ID"));
		}
		closeResources(connection, resultSet, statement);
		return inventory;
	}


	/**
	 * Queries through the 'TRADES' table in the database and returns a map with the information about the given tradeID
	 * @param tradeID ID of the trade
	 * @return Map containing the appropriate information of the given tradeID
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public Map<String, Object> getTrade(int tradeID) throws SQLException, NullPointerException {
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADES WHERE TRADE_ID = " + tradeID+";");
		Map <String, Object> data = new HashMap<>();
		while(resultSet.next()){
			data = addData(resultSet);
			data.putAll(getTradedItems(tradeID));
		}
		closeResources(connection, resultSet, statement);
		return data;
	}

	/**
	 * Returns a list of all user IDs that exist in the system using the ACCOUNTS table
	 * @return The list of all the user IDs
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	public List<Integer> getAllUserIDs() throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE IS_ADMINISTRATOR = 0;");
		List<Integer> userIDs= new ArrayList<>();
		while(resultSet.next()){
			userIDs.add(resultSet.getInt("ACCOUNT_ID"));
		}
		closeResources(connection, resultSet, statement);
		return userIDs;
	}

	/**
	 * Returns a list of all trade IDs that are stored in the TRADES table
	 * @return The list of all the trade IDs
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	public List<Integer> getAllTradeIDs() throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM TRADES;");
		List<Integer> tradeIDs= new ArrayList<>();
		while(resultSet.next()){
			tradeIDs.add(resultSet.getInt("TRADE_ID"));
		}
		closeResources(connection, resultSet, statement);
		return tradeIDs;
	}

	/**
	 * Returns a list of all administrator accountIDs that are stored in the ACCOUNTS table in the database
	 * @return The list of all the administrator IDs
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	public List<Integer> getAllAdminIDs() throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNTS WHERE IS_ADMINISTRATOR = 1;");
		List<Integer> accountIDs= new ArrayList<>();
		while(resultSet.next()){
			accountIDs.add(resultSet.getInt("ACCOUNT_ID"));
		}
		closeResources(connection, resultSet, statement);
		return accountIDs;
	}
	
	/**
	 * Goes through the ITEMS table to get the map of original item owners for a given trade
	 * @param connection connection variable to the database you'd like to write the tables to.
	 * @param tradeID ID of the trade
	 * @return Map containing the original owners of the items and what items they own
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	protected Map<Integer, List<Integer>> getOriginalOwnership(Connection connection, int tradeID) throws SQLException{
		Map<Integer, List<Integer>> originalOwners = new HashMap<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID in (SELECT ITEM_TRADED_ID FROM ITEMS_TRADED"
				+ " WHERE TRADE_ID = "+ tradeID +");");
		int ownerID, itemID;
	    List<Integer> itemsOwnedIntegers;
		while(resultSet.next()){
			itemsOwnedIntegers = new ArrayList<Integer>();
		    ownerID = resultSet.getInt("OWNER_ID");
		    itemID = resultSet.getInt("ITEM_ID");
			if(originalOwners.containsKey(ownerID)) {
				originalOwners.get(ownerID).add(itemID);
			}else {
				itemsOwnedIntegers.add(itemID);
				originalOwners.put(ownerID, itemsOwnedIntegers);
			}
		}
		closeResources(resultSet, statement);
		return originalOwners;
	}
	
	/**
	 * Goes through the ITEMS_TRADED table to get the map of receivers item owners for a given trade
	 * @param connection connection variable to the database you'd like to write the tables to.
	 * @param tradeID ID of the trade
	 * @return Map containing the receivers of the items after a trade and what items they are receiving
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 */
	protected Map<Integer, List<Integer>> getRecieversMap(Connection connection, int tradeID) throws SQLException{
		Map<Integer, List<Integer>> recieversMap = new HashMap<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID +";");
		int recieverID, itemID;
	    List<Integer> itemsRecieved;
		while(resultSet.next()){
			itemsRecieved = new ArrayList<Integer>();
			recieverID = resultSet.getInt("RECIEVER_ID");
		    itemID = resultSet.getInt("ITEM_TRADED_ID");
			if(recieversMap.containsKey(recieverID)) {
				recieversMap.get(recieverID).add(itemID);
			}else {
				itemsRecieved.add(itemID);
				recieversMap.put(recieverID, itemsRecieved);
			}
		}
		closeResources(resultSet, statement);
		return recieversMap;
	}

	/**
	 * gets a map of the traded items
	 * @param tradeID ID of the trade
	 * @return a mpa of traded items
	 * @throws SQLException
	 */
	private Map<String, Object> getTradedItems(int tradeID)throws SQLException{
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEMS_TRADED WHERE TRADE_ID = " + tradeID +";");
		Map<String, Object> data = new HashMap<>();
		int counter = 1;
		while (resultSet.next()){
			data.put("ITEM_TRADED_ID #" + counter, resultSet.getObject("ITEM_TRADED_ID"));
			counter++;
		}
		closeResources(connection, resultSet, statement);
		return data;
	}


	/**
	 * adds data to the table
	 * @param resultSet the ResultSet
	 * @return a map of the data
	 * @throws SQLException
	 */
	private Map<String, Object> addData(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metadata = resultSet.getMetaData();
		int columnCount = metadata.getColumnCount();
		Map<String, Object> tableData = new HashMap<>();
		for (int i=1; i<=columnCount; i++) {
			tableData.put(metadata.getColumnName(i), resultSet.getObject(metadata.getColumnName(i)));
		}
		return tableData;
	}

	/**
	 * close all the resources
	 * @param connection the connection of the database
	 * @param resultSet the ResultSet
	 * @param statement the Statement
	 * @throws SQLException
	 */
	private void closeResources(Connection connection, ResultSet resultSet, Statement  statement) throws SQLException{
		connection.close();
		statement.close();
		resultSet.close();
	}
	
	private void closeResources(ResultSet resultSet, Statement  statement) throws SQLException{
		statement.close();
		resultSet.close();
	}
}
package database;

import accounts.Authenticator;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author Aly Abdelwahed
 */

public class DataInserter {

	private String database = "";

	/**
	 * Constructor
	 * @param name name of the database
	 */
	public DataInserter(String name){
		this.database += name;
	}

	/**
	 * This method adds an itemID in either the WISHLIST table in the database
	 * @param itemID  ID of the new item
	 * @param userID ID of the user for which the item will belong
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations or when either
	 *                      the itemID or the userID do not exist in the database
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void addItemToUserWishlist(int itemID, int userID) throws SQLException, NullPointerException{
		String sql = "INSERT INTO WISHLIST(ITEM_ID, WISHER_ID) VALUES(?, ?);";
		Connection connection = new DatabaseDriver(this.database).connectOrCreateDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		if (new DataReader(this.database).getItem(itemID).size() > 0 && new DataReader(this.database).getUser(userID).size() >0){
			preparedStatement.setInt(1, itemID);
			preparedStatement.setInt(2, userID);
			preparedStatement.executeUpdate();
		}else{
			throw new SQLException("Either Item ID " + itemID +" Does Not Exist or User ID " + userID +
					" Does Not Exist or Both Do Not Exist in the system. Please Try Again!");
		}
		preparedStatement.close();
		connection.close();
	}

	/**
	 * Stores a given Trade Entity with its information in the 'Trades.csv' CSV file
	 * Note: Every trade this method inserts is inserted with a trade_date which represents the time the
	 * method is called, it is being done this way to match the way the code works
	 * @param tradeData map containing the user Information with keys having the same name as the column names in the
	 *                  TRADES table in the database
	 * @return the ID of the trade with the given trade specifications that are passed as parameters
	 * @throws SQLException thrown if there is failure or interruption in the SQL operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public int insertTrade(Map<Integer, List<Integer>> receivers, Map<String, Object> tradeData) throws SQLException{
		DatabaseDriver driver = new DatabaseDriver(this.database);
		Connection connection = driver.connectOrCreateDataBase();
		int tradeID = insertTradeInDB(connection, tradeData);
		//		List<Integer> tradingUser = new ArrayList<>(receivers.keySet());
		//		insertTradeMap(connection, tradingUser, tradeID);
		insertTradedItemsInDB(connection, receivers, tradeID);
		connection.close();
		return tradeID;
	}

	/**
	 * Stores a given Item Entity with its information in the 'Items.csv' CSV file
	 * @param itemInformation map containing the item Information with keys having the same name
	 *                        as the column names in the ITEMS table in the database
	 * @param approved represents if the item is approved or not
	 * @return the itemID for the Item entity with the given specification
	 * @throws SQLException thrown if there is failure or interruption in the SQL operation
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public int insertItem(Map<String, Object> itemInformation, boolean approved) throws SQLException,
	NullPointerException{
		int approvedInteger = 0;
		if (approved){ approvedInteger = 1; }
		DatabaseDriver driver = new DatabaseDriver(this.database);
		Connection connection = driver.connectOrCreateDataBase();
		int itemID = insertItemInDB(connection, itemInformation, approvedInteger);
		connection.close();
		return itemID;
	}

	/**
	 * Stores a given UserAccount Entity with its information in the 'Users.csv' CSV
	 * @param password Represents the password of the User
	 * @param userInformation map containing the user Information with keys having the same name
	 *                        as the column names in the USERS table in the database
	 * @return The appropriate user ID of the given information
	 * @throws SQLException thrown if there is failure or interruption in the SQL operation
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public int insertUser(String password, Map<String, Object> userInformation) throws NullPointerException, SQLException {
		DatabaseDriver driver = new DatabaseDriver(this.database);
		Connection connection = driver.connectOrCreateDataBase();
		int id = insertUserInDB(connection, insertAccountInDB(connection, 0), userInformation);
		storePassword(connection, id, password);
		connection.close();
		return id;
	}

	/**
	 * Stores a given AdminAccount Entity with its information in the 'Administrators.csv' CSV
	 * @param password Represents the password of the administrator
	 * @return the ID of the admin with the given password
	 * @throws SQLException thrown if there is failure or interruption in the SQL operation
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public int insertAdmin(String password) throws SQLException, NullPointerException{
		DatabaseDriver driver = new DatabaseDriver(this.database);
		Connection connection = driver.connectOrCreateDataBase();
		int adminID = insertAccountInDB(connection, 1);
		storePassword(connection, adminID, password);
		connection.close();
		return adminID;
	}

	/**
	 * This method writes the data of all tradeThresholds in a txt file called 'Config.txt'
	 * @param tradeThresholds Map containing the type of Threshold as key and the data as values under the key
	 * @throws IOException thrown if there is failure or interruption in the I/O operations
	 * @throws NullPointerException thrown when null is passed as an argument
	 */
	public void writeTradeThresholds(Map<String, Integer> tradeThresholds) throws IOException,
	NullPointerException {
		FileWriter writer = new FileWriter("./config.txt");
		for(String x : tradeThresholds.keySet()){
			writer.write(x+"="+tradeThresholds.get(x) + "\n");
		}
		writer.close();
	}

	/**
	 * Helper method that stores a password in the 'Passwords.csv' CSV file for a given userID
	 * @param userID ID of the user
	 * @param password the password associated with the userID
	 * @param connection the database connection.
	 * @throws SQLException if something goes wrong.
	 */
	private void storePassword(Connection connection, int userID, String password) throws SQLException {
		Authenticator authenticator = new Authenticator();
		String sql = "INSERT INTO PASSWORDS(ACCOUNT_ID, PASSWORD) VALUES(?,?);";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, userID);
		preparedStatement.setString(2, authenticator.passwordHash(password));
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	/**
	 * insert the given admin account in the database
	 * @param connection the database connection.
	 * @param isAdmin 1 if the user is an admin
	 * @return the id of the account
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private int insertAccountInDB(Connection connection, int isAdmin) throws SQLException{
		String sql = "INSERT INTO ACCOUNTS(IS_ADMINISTRATOR) VALUES(?);";
		int accountID = -1;
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, isAdmin);
		int id = preparedStatement.executeUpdate();
		if (id > 0) {
			ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
			if (uniqueKey.next()) {
				accountID = uniqueKey.getInt(1);
			}
			uniqueKey.close();
		}
		preparedStatement.close();
		return accountID;
	}

	/**
	 * insert the given admin account in the database
	 * @param connection the database connection.
	 * @param userID the id of the user
	 * @param userInformation a map containing the user's information
	 * @return the id of the user
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private int insertUserInDB(Connection connection, int userID, Map<String, Object> userInformation)
			throws SQLException{
		String sql = "INSERT INTO USERS(USER_ID, NAME, CITY, OVERBORROWED, NUM_TRADES, INCOMPLETE_TRADES," +
				"FUNDS, IS_ON_VACATION, IS_FROZEN, UNFREEZE_REQUEST) VALUES(?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, userID);
		preparedStatement.setString(2, (String) userInformation.get("NAME"));
		preparedStatement.setString(3, (String) userInformation.get("CITY"));
		preparedStatement.setInt(4, (Integer) userInformation.get("OVERBORROWED"));
		preparedStatement.setInt(5, (Integer) userInformation.get("NUM_TRADES"));
		preparedStatement.setInt(6, (Integer) userInformation.get("INCOMPLETE_TRADES"));
		preparedStatement.setDouble(7, 5000.0);
		preparedStatement.setInt(8, (Integer) userInformation.get("IS_ON_VACATION"));
		preparedStatement.setInt(9, (Integer) userInformation.get("IS_FROZEN"));
		preparedStatement.setInt(10, 0);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		return userID;
	}

	/**
	 * insert the given item in the database
	 * @param connection the database connection.
	 * @param itemInformation a map containing the information of the item
	 * @param isApproved 1 if the item is approved
	 * @return the id of the item
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private int insertItemInDB(Connection connection, Map<String, Object> itemInformation, int isApproved)
			throws SQLException{
		String sql = "INSERT INTO ITEMS(OWNER_ID, NAME, DESCRIPTION, PRICE, IS_APPROVED) VALUES(?,?,?,?,?);";
		int itemID = -1;
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, (Integer) itemInformation.get("OWNER_ID"));
		preparedStatement.setString(2, (String) itemInformation.get("NAME"));
		preparedStatement.setString(3, (String) itemInformation.get("DESCRIPTION"));
		preparedStatement.setDouble(4, (Double) itemInformation.get("PRICE"));
		preparedStatement.setInt(5, isApproved);
		int id = preparedStatement.executeUpdate();
		if (id > 0) { ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
		if (uniqueKey.next()) { itemID = uniqueKey.getInt(1); }
		uniqueKey.close();}
		//		new DataUpdater().addItemPriceToFunds((Integer) itemInformation.get("OWNER_ID"),
		//				(Double) itemInformation.get("PRICE"));
		preparedStatement.close();
		return itemID;
	}

	/**
	 * insert the traded items in the database
	 * @param connection the database connection.
	 * @param receivers a list of receivers id
	 * @param tradeID the id of the trade
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private void insertTradedItemsInDB(Connection connection, Map<Integer, List<Integer>> receivers, int tradeID) throws SQLException {
		String sql = "INSERT INTO ITEMS_TRADED(ITEM_TRADED_ID, RECIEVER_ID, TRADE_ID) VALUES(?,?,?);";
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		for(Integer key: receivers.keySet()){
			if (receivers.get(key).size() == 0) {
				preparedStatement.setInt(1, 0);
				preparedStatement.setInt(2, key);
				preparedStatement.setInt(3, tradeID);
				preparedStatement.executeUpdate();
			} else {
				for (Integer x : receivers.get(key)) {
					preparedStatement.setInt(1, x);
					preparedStatement.setInt(2, key);
					preparedStatement.setInt(3, tradeID);
					preparedStatement.executeUpdate();
				}
			}
		}
		preparedStatement.close();
	}

	/**
	 * creates the trade statement
	 * @param connection the database connection.
	 * @param sql a string
	 * @param tradeData a map of the trade data
	 * @return a PreparedStatement
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private PreparedStatement createTradeStatement(Connection connection, String sql, Map<String, Object> tradeData)
			throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, (String) tradeData.get("TRADE_STATUS"));
		preparedStatement.setString(2, (String) tradeData.get("LOCATION"));
		preparedStatement.setInt(3, (Integer) tradeData.get("IS_PERMANENT"));
		preparedStatement.setString(4, (String) tradeData.get("FIRST_MEETING_STATUS"));
		preparedStatement.setString(5, (String) tradeData.get("SECOND_MEETING_STATUS"));
		preparedStatement.setObject(6, tradeData.get("TRADE_DATE"));
		preparedStatement.setObject(7, tradeData.get("EXPECTED_RETURN_DATE"));
		preparedStatement.setInt(8, (Integer)tradeData.get("FIRST_MEETING_LAST_EDITOR"));
		preparedStatement.setInt(9, (Integer) tradeData.get("FIRST_MEETING_NUM_EDITS"));
		preparedStatement.setInt(10, (Integer)tradeData.get("SECOND_MEETING_LAST_EDITOR"));
		preparedStatement.setInt(11, (Integer) tradeData.get("SECOND_MEETING_NUM_EDITS"));
		preparedStatement.setInt(12, (Integer) tradeData.get("REQUESTER_ID"));
		return preparedStatement;
	}


	/**
	 * inserts trade in the database
	 * @param connection the database connection.
	 * @param tradeData a map of the trade data
	 * @return the id of the trade
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private int insertTradeInDB (Connection connection, Map<String, Object> tradeData) throws SQLException{
		String sql = "INSERT INTO TRADES(TRADE_STATUS, LOCATION,IS_PERMANENT,FIRST_MEETING_STATUS,"
				+ "SECOND_MEETING_STATUS, TRADE_DATE, EXPECTED_RETURN_DATE, FIRST_MEETING_LAST_EDITOR,"
				+ "FIRST_MEETING_NUM_EDITS, SECOND_MEETING_LAST_EDITOR, SECOND_MEETING_NUM_EDITS, REQUESTER_ID) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		int tradeID = -1;
		PreparedStatement preparedStatement = createTradeStatement(connection, sql, tradeData);
		int id = preparedStatement.executeUpdate();
		if (id > 0) {
			ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
			if (uniqueKey.next()) { tradeID = uniqueKey.getInt(1); }
			uniqueKey.close();
		}
		preparedStatement.close();
		return tradeID;
	}
}

package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Aly Abdelwahed
 */

public class TablesBuilder implements DatabaseColumnsInterface {

    Map<String, List<String>> tablesList = new HashMap<String, List<String>>();

	/**
	 * sets the user's columns
	 */
	private void setUserColumns() {
	    List<String> userColumns = new ArrayList<>();
		userColumns.add("USER_ID INTEGER NOT NULL,"); 
		userColumns.add("NAME CHAR(64) NOT NULL,"); 
		userColumns.add("CITY STRING NOT NULL,"); 
		userColumns.add("OVERBORROWED INTEGER NOT NULL,"); 
		userColumns.add("NUM_TRADES INTEGER NOT NULL,"); 
		userColumns.add("INCOMPLETE_TRADES INTEGER NOT NULL,"); 
		userColumns.add("FUNDS DOUBLE NOT NULL,");
		userColumns.add("IS_ON_VACATION INTEGER NOT NULL,");
		userColumns.add("IS_FROZEN INTEGER NOT NULL,"); 
		userColumns.add("UNFREEZE_REQUEST INTEGER NOT NULL,"); 
		userColumns.add("FOREIGN KEY(USER_ID) REFERENCES ACCOUNTS(ACCOUNT_ID),"); 
		userColumns.add("PRIMARY KEY(USER_ID)"); 
		this.tablesList.put("USERS", userColumns);
	}

	/**
	 * sets the account's columns
	 */
	private void setAccountColumns() {
		List<String> accountColumns = new ArrayList<>();
		accountColumns.add("ACCOUNT_ID INTEGER PRIMARY KEY NOT NULL,"); 
		accountColumns.add("IS_ADMINISTRATOR INTEGER NOT NULL"); 
		this.tablesList.put("ACCOUNTS", accountColumns);
	}


	/**
	 * sets the trade's columns
	 */
	private void setTradeColumns() {
		List<String> tradeColumns = new ArrayList<>();
		tradeColumns.add("TRADE_ID INTEGER PRIMARY KEY NOT NULL,");
		tradeColumns.add("TRADE_STATUS CHAR(64) NOT NULL, LOCATION CHAR(64) NOT NULL,"); 
		tradeColumns.add("IS_PERMANENT INTEGER NOT NULL, FIRST_MEETING_STATUS CHAR(64) NOT NULL,");
		tradeColumns.add("SECOND_MEETING_STATUS CHAR(64) NOT NULL, TRADE_DATE CHAR(64),");
		tradeColumns.add("EXPECTED_RETURN_DATE CHAR(64), FIRST_MEETING_LAST_EDITOR INTEGER NOT NULL,");
		tradeColumns.add("FIRST_MEETING_NUM_EDITS INTEGER NOT NULL, SECOND_MEETING_LAST_EDITOR INTEGER NOT NULL,"); 
		tradeColumns.add("SECOND_MEETING_NUM_EDITS INTEGER NOT NULL,"); 
		tradeColumns.add("REQUESTER_ID INTEGER NOT NULL,"); 
		tradeColumns.add("FOREIGN KEY(FIRST_MEETING_LAST_EDITOR) REFERENCES USERS(USER_ID),"); 
		tradeColumns.add("FOREIGN KEY(SECOND_MEETING_LAST_EDITOR) REFERENCES USERS(USER_ID)"); 
		this.tablesList.put("TRADES", tradeColumns);
	}

	/**
	 * sets the password's columns
	 */
	private void setPasswordColumns() {
		List<String> passwordColumns = new ArrayList<>();
		passwordColumns.add("ACCOUNT_ID INTEGER NOT NULL,");
		passwordColumns.add("PASSWORD CHAR(64) NOT NULL,");
		passwordColumns.add("FOREIGN KEY(ACCOUNT_ID) REFERENCES ACCOUNTS(ACCOUNT_ID),");
		passwordColumns.add("PRIMARY KEY(ACCOUNT_ID)");
		this.tablesList.put("PASSWORDS", passwordColumns);
	}

	/**
	 * sets the items's columns
	 */
	private void setItemsColumns() {
		 List<String> itemsColumns = new ArrayList<>();
		itemsColumns.add("ITEM_ID INTEGER PRIMARY KEY NOT NULL,"); 
		itemsColumns.add("OWNER_ID INTEGER NOT NULL,");
		itemsColumns.add("NAME CHAR(64) NOT NULL,");
		itemsColumns.add("DESCRIPTION STRING NOT NULL,");
		itemsColumns.add("PRICE DOUBLE NOT NULL,");
		itemsColumns.add("IS_APPROVED INTEGER NOT NULL,");
		itemsColumns.add("FOREIGN KEY(OWNER_ID) REFERENCES USERS(USER_ID)");
		this.tablesList.put("ITEMS", itemsColumns);
	}

	/**
	 * sets the item traded columns
	 */
	private void setItemTradedColumns() {
		List<String> itemTradedColumns = new ArrayList<>();
		itemTradedColumns.add("ITEM_TRADED_ID INTEGER NOT NULL,"); 
		itemTradedColumns.add("RECIEVER_ID INTEGER NOT NULL,"); 
		itemTradedColumns.add("TRADE_ID INTEGER NOT NULL,"); 
		itemTradedColumns.add("FOREIGN KEY(ITEM_TRADED_ID) REFERENCES ITEMS(ITEM_ID),"); 
		itemTradedColumns.add("FOREIGN KEY(RECIEVER_ID) REFERENCES USERS(USER_ID),"); 
		itemTradedColumns.add("FOREIGN KEY(TRADE_ID) REFERENCES TRADES(TRADE_ID),"); 
		itemTradedColumns.add("PRIMARY KEY (ITEM_TRADED_ID, RECIEVER_ID, TRADE_ID)"); 
		this.tablesList.put("ITEMS_TRADED", itemTradedColumns);
	}

	/**
	 * sets the wish lists columns
	 */
	private void setWishlistColumns() {
		List<String> wishlistColumns = new ArrayList<>();
		wishlistColumns.add("ITEM_ID INTEGER NOT NULL,");
		wishlistColumns.add("WISHER_ID INTEGER NOT NULL,");
		wishlistColumns.add("FOREIGN KEY(ITEM_ID) REFERENCES ITEMS(ITEM_ID),");
		wishlistColumns.add("FOREIGN KEY(WISHER_ID) REFERENCES USERS(USER_ID),");
		wishlistColumns.add("PRIMARY KEY(ITEM_ID, WISHER_ID)");
		this.tablesList.put("WISHLIST", wishlistColumns);
	}

	/**
	 *  creates a map of tables
	 */
	@Override
	public void createTablesMap(){
		setAccountColumns();
		setUserColumns();
		setPasswordColumns();
		setItemsColumns();
		setTradeColumns();
		setItemTradedColumns();
		setWishlistColumns();
	}

	/**
	 * adds map to database
	 * @return a Map with string and list of string
	 */
	@Override
	public Map<String, List<String>> addTableMapToDatabase() {
		createTablesMap();
		return this.tablesList;
	}

}

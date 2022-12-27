package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author Aly Abdelwahed
 */

public class DatabaseDriver {

	private String database = "database";

	/**
	 * Constructor
	 * @param name name of the database
	 */
	public DatabaseDriver(String name){
		this.database += name;
	}

	/**
	 * This will connect to existing database, or create it if it's not there.
	 * @return the database connection.
	 */
	public Connection connectOrCreateDataBase() {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+database+".db");

		} catch (Exception e) {
			System.out.println("Something went wrong with your connection! see below details: ");
			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * This will initialize the database/add the given table to the database, or throw a SQLException.
	 * @param database Map containing the names of the tables that will be created and the names and values of
	 *        the columns of all the tables that will be created for a specified table
	 * @param connection connection variable to the database you'd like to write the tables to.
	 * @return the connection passed in the parameter to allow the code to continue.
	 * @throws SQLException thrown if there was an error in the SQL Operation like the tables couldn't be initialized
	 */
	public Connection initialize(DatabaseColumnsInterface database, Connection connection) throws SQLException {
		if (!addTables(database.addTableMapToDatabase(), connection)) {
			throw new SQLException("Something went wrong. Please Try again.");
		}
		return connection;
	}

	/**
	 * add the tables to the database
	 * @param tables a list of stings
	 * @param connection connection variable to the database you'd like to write the tables to
	 * @return a true iff the tables were added properly
	 */
	private boolean addTables(Map<String, List<String>> tables, Connection connection) {
		boolean checkExecution = false;
		try {
			Statement statement = connection.createStatement();
			tableParseHelper(tables, statement);
			statement.close();
			checkExecution = true;
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkExecution;
	}

	/***
	 * Goes through the database and create tables and columns
	 * @param tables the tables
	 * @param statement SQL statement
	 * @throws SQLException thrown if there was an error in the SQL Operation
	 */
	private void tableParseHelper(Map<String, List<String>> tables, Statement statement) throws SQLException {
		String sqlStatementString;
		for (String tableName : tables.keySet()) {
			sqlStatementString = "CREATE TABLE ";
			sqlStatementString += tableName + "(";
			for(String columns: tables.get(tableName)) {
				sqlStatementString += columns;
			}
			sqlStatementString += ");";
			statement.executeUpdate(sqlStatementString);
		}
	}
}

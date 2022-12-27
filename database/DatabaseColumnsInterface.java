package database;

import java.util.List;
import java.util.Map;

/**
 * @author Aly Abdelwahed
 */

public interface DatabaseColumnsInterface {
	
    Map<String, List<String>> tablesList = null;

	/***
	 * Creates table maps
	 */
	public void createTablesMap();

	/***
	 * Adds the table map to the database
	 * @return a map
	 */
	public Map<String, List<String>> addTableMapToDatabase();
}

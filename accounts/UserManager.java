package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Monica Jones
 */

public class UserManager {
    public Map<Integer, UserAccount> allUsers;
    public Map<Integer, UserAccount> allUserOnVacation;

    /***
     * The constructor.
     */
    public UserManager() {
        allUsers = new HashMap<>();
    }

    /***
     * The constructor.
     * @param map Map of userid to useraccount
     */
    public UserManager(Map<Integer, UserAccount> map) {
        allUsers = map;
    }

    /**
     * Returns a list of all the users in the system.
     * @return a list of all the users in the system
     */
    public List<UserAccount> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

    /**
     * Adds a user to the allUsers list.
     * @param user the user to be added
     */
    public void addUser(UserAccount user) {
        allUsers.put(user.getUserID(), user);
    }

    /**
     * Removes a user from the allUsers list.
     * @param user the user to be removed
     */
    public void removeUser(UserAccount user) {
        allUsers.remove(user.getUserID());
    }

    /**
     * Returns the user associated with a given userID
     * @param userID the ID of the user
     * @return the user
     */
    public UserAccount getUser(Integer userID) {

        return allUsers.get(userID);
    }

    /**
     * gets all the user that are on vacation
     * @return a map of all users that are on vacation
     */
    public Map<Integer, UserAccount> getAllUserOnVacation() {
        return allUserOnVacation;
    }

    /**
     * removes a user from a list of users that are on vacation
     * @param user removes the user that is on vacation
     */
    public void removeUserOnVacation(UserAccount user) {
        allUserOnVacation.remove(user.getUserID());
    }

    /**
     * Increases the user's number of trades by one.
     * @param userID the ID of the user
     */
    public void incrementNumTrade(Integer userID) {
        UserAccount user = getUser(userID);
        user.setNumTrade(user.getNumTrade() + 1);
    }

    /**
     * Increases or decreases the user's number of items overborrowed by the amount given. Can be negative if the
     * overborrowed variable is to decrease.
     * @param userID the ID of the user
     * @param amount the amount to increase or decrease the overborrowed variable by
     */
    public void changeOverborrowed(Integer userID, int amount) {
        UserAccount user = getUser(userID);
        user.setOverBorrowed(user.getOverBorrowed() + amount);
    }

    /**
     * gets the user's home city
     * @param user an userid
     * @return the home city of a user
     */
    public String getHomeCity(Integer user){
        UserAccount user_city = getUser(user);
        return user_city.getCity();
    }

    /**
     * Generate an id for a demo user without interacting with the database. Demo-ids are negative
     * integers or 0.
     * @return the demo-id for the demo user.
     */
    public Integer generateDemoUserID(){
        int id = 0;
        for(Integer key: allUsers.keySet()){
            id -= 1;
        }
        return id;
    }
}


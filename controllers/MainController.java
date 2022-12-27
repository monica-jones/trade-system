package controllers;

import gui.loginUI.LoginPage;
import accounts.UserEnforcer;
import accounts.UserManager;
import database.DataInserter;
import database.DataReader;
import database.DatabaseDriver;
import database.TablesBuilder;
import items.Item;
import items.ItemInventory;
import trades.*;
import accounts.UserAccount;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class MainController {
    private UserAccount currentUser;
    private TradeManager tradeManager;
    private UserManager userManager;
    private TradeLogManager tradeLogManager;
    private UserEnforcer userEnforcer;
    private ItemInventory itemInventory;
    private DataReader dataReader;
    private AdminController adminController;
    private AdminUndoable adminUndoController;
    private TradeController tradeController;
    private UserMenuController userMenuController;
    private String mode = "";


    /**
     * Creates an instance MainController, which allows logging in, signing up, and accessing menus for users
     * and administrators.
     * @param firstRun a string representing whether the program is running for the first time or not. 0 indicates
     *                 it is running for the first time and 1 indicates it is not.
     */
    public MainController(String firstRun, String mode) {
        if (firstRun.equals("0")) {
            firstTimeRun();
        }
        this.mode = mode;
        this.dataReader = new DataReader(mode);
        this.tradeLogManager = createTradeLogManager();
        this.userManager = createUserManager();
        this.userEnforcer = createUserEnforcer(userManager);
        this.itemInventory = createItemInventory(userManager);
        this.tradeManager = createTradeManager(tradeLogManager);
    }

    /***
     * Initialize after loggin
     * @param mode the mode of the system
     * @param isAdmin the user is an admin
     */
    public void initAfterLogin(String mode, boolean isAdmin) {
        creationHelper(mode);
        if (isAdmin) {
            this.adminController = new AdminController(currentUser, this.userManager, this.itemInventory, this.userEnforcer, this.mode);
            this.adminUndoController = new AdminUndoController(currentUser, this.userManager, this.itemInventory, this.userEnforcer, this.mode);
        } else {
            this.currentUser = this.userManager.getUser(LoginPage.getCurrentUserName());
            this.userMenuController = new UserMenuController(this.currentUser, this.mode);
            this.tradeController = new TradeController(currentUser, this.tradeManager, this.tradeLogManager, this.itemInventory,
                    this.userManager, getMeetingEditLimit(), this.mode);
        }
    }

    /***
     * Helps with initializing the classes
     * @param mode the mode of the system
     */
    private void creationHelper(String mode) {
        this.mode = mode;
        this.tradeLogManager = createTradeLogManager();
        this.tradeManager = createTradeManager(tradeLogManager);
        this.userManager = createUserManager();
        this.itemInventory = createItemInventory(userManager);
        this.userEnforcer = createUserEnforcer(userManager);
    }


    /**
     * Reads all trades from the storage file and returns a new TradeLogManager to store them.
     * Returns an empty TradeLogManager if there is no file or the file read fails.
     * @return a new TradeLogManager to store all trades from the storage file
     */
    private TradeLogManager createTradeLogManager() {
        TradeLogManager tradeLogManager = new TradeLogManager(new TradeLog(new HashMap<>())); //creates empty trade log if file read fails or no file exists
        try {
            TradeLogBuilder builder = new TradeLogBuilder(this.mode);
            tradeLogManager = new TradeLogManager(builder.buildTradeLog());
        } catch (SQLException e) {
//            mainMP.readDataError(); // error message if file expected
            e.printStackTrace();
        }
        return tradeLogManager;
    }

    /**
     * Reads all users from the storage file and returns a new UserManager to store them.
     * Returns an empty UserManager if there is no file or the file read fails.
     * @return a new UserManager to store all users from the storage file
     */
    public UserManager createUserManager() {
        UserManager userManager = new UserManager();
        try {
            UserManagerBuilder builder = new UserManagerBuilder(this.mode);
            userManager = builder.createUserManager();
        } catch (SQLException | NullPointerException e) {
//            mainMP.readDataError(); // error message if file expected
            e.printStackTrace();
        }
        return userManager;
    }

    /**
     * Collects all items from all users' inventories in the UserManager and returns a new ItemInventory to store them.
     * Returns an empty ItemInventory if there are no items.
     * @param userManager stores all items from all users' inventories
     * @return a new ItemInventory to store all items from all users' inventories
     */
    public ItemInventory createItemInventory(UserManager userManager) {
        ArrayList<Item> allItems = new ArrayList<>();
        for (UserAccount user : userManager.getAllUsers()) {
            allItems.addAll(user.getInventory());
        }
        return new ItemInventory(allItems);
    }

    /**
     * Returns the meeting edit limit from the configuration file
     * @return the meeting edit limit from the configuration file
     */
    public int getMeetingEditLimit() {
        int meetingEditLimit = 0;
        try {
            meetingEditLimit = dataReader.getTradeThresholds().get("meetingEditLimit");
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Failed to read from config.txt"); //TODO remove this later
        }
        return meetingEditLimit;
    }

    /**
     * Returns a new TradeManager to manage trades
     * @param tlm TradeLogManager
     * @return a new TradeManager to manage trades
     */
    private TradeManager createTradeManager(TradeLogManager tlm) {
        return new TradeManager(tlm);
    }

    /*
    Reads the overborrow limit, trade limit, and incomplete limit from the configuration file and returns a new
    UserEnforcer to enforce these limits.
     */

    /**
     * Reads the overborrow limit, trade limit, and incomplete limit from the configuration file and returns a new
     * UserEnforcer to enforce these limits.
     * @param um UserManager
     * @return a new UserEnforcer to enforce limits
     */
    private UserEnforcer createUserEnforcer(UserManager um){
        Integer overBorrowLimit = 0;
        Integer tradeLimit = 0;
        Integer incompleteLimit = 0;
        try{
            Map<String, Integer> tradeThreshold = dataReader.getTradeThresholds();
            overBorrowLimit = tradeThreshold.get("overBorrowLimit");
            tradeLimit = tradeThreshold.get("tradeLimit");
            incompleteLimit = tradeThreshold.get("incompleteLimit");
        } catch(IOException e){
            e.printStackTrace();
        }
        UserEnforcer userEnforcer = new UserEnforcer(um, tradeLimit, incompleteLimit, overBorrowLimit);
        List<UserAccount> allUsers = um.getAllUsers();
        for(UserAccount userAccount: allUsers){
            if(userAccount.getUnfreezeRequested()){
                userEnforcer.addToUnfreezeRequests(userAccount.getUserID());
            }
        }
        return userEnforcer;
    }


    private void firstTimeRunHelper(DatabaseDriver dbDriver, Connection connection) throws SQLException {
        TablesBuilder tablesBuilder = new TablesBuilder();
        dbDriver.initialize(tablesBuilder, connection);
        DataInserter dataInserter = new DataInserter("");
        dataInserter.insertAdmin("blerg"); // create initial admin account

        dbDriver = new DatabaseDriver("_demo");
        connection = dbDriver.connectOrCreateDataBase();
        dbDriver.initialize(tablesBuilder, connection);
    }

    /**
     * Runs when the program is run for the first time.
     * Initializes all storage files and creates the initial administrator account.
     */
    private void firstTimeRun() {
        DatabaseDriver dbDriver = new DatabaseDriver("");
        try {
            Connection connection = dbDriver.connectOrCreateDataBase();
            firstTimeRunHelper(dbDriver, connection);
        } catch (SQLException e) {
            System.out.println("Something went wrong while initializing the files.");
        }
    }

    

    /**
     * gets the item inventory
     * @return the item inventory
     */
    public ItemInventory getItemInventory() {
        return this.itemInventory;
    }

    /**
     * gets the user manager
     * @return the user manager
     */
    public UserManager getUserManager(){
        return this.userManager;
    }

    /**
     * gets the trade manager
     * @return the trade manger
     */
    public TradeManager getTradeManager() {
        return this.tradeManager;
    }

    /**
     * gets the admin controller
     * @return the admin controller
     */
    public AdminController getAdminController(){
        return this.adminController;
    }

    /**
     * gets the admin undo controller
     * @return the admin undo controller
     */
    public AdminUndoable getAdminUndoController(){
        return this.adminUndoController;
    }

    /**
     * gets the user enforcer
     * @return the user enforcer
     */
    public UserEnforcer getUserEnforcer(){
        return  this.userEnforcer;
    }

    /**
     * gets the trade log manager
     * @return the trade log manager
     */
    public TradeLogManager getTradeLogManager(){return  this.tradeLogManager;}

    /**
     * gets the account of the current user
     * @return the current user
     */
    public UserAccount getCurrentUserAccount(){ return this.currentUser;}

    /**
     * gets the trade controller
     * @return the trade controller
     */
    public TradeController getTradeController() {return this.tradeController;
    }

    /**
     * Return whether the main controller is in demo mode
     * @return true iff the main controller is in demo mode.
     */
    public String getMode() {
        return this.mode;
    }

    /**
     * gets the user menu controller
     * @return the user menu controller
     */
    public UserMenuController getUserMenuController() {
        return this.userMenuController;
    }

}



package gui.loginUI;



import controllers.MainController;

import javax.swing.*;


import gui.adminUI.*;
import gui.userUI.*;
import gui.tradeUI.*;

import java.awt.*;

import java.io.File;

/**
 * @author Namratha Anil
 */
public class MainPage extends JFrame {


    private MainController mainController;
//    private boolean demoMode = false;
    private String mode = "";
    private Component mainFrame;

    /**
     * constructor
     */
    public MainPage() {
        initComponents();
        initMainController();

        setView("StartUpPage");
    }

    /**
     * sets the mode
     * @param mode String
     */
    public void setMode(String mode){
        this.mode = mode;
    }


    private void initMainController() {
        File file = new File("./database.db");
        boolean exists = file.exists();
        int argument = 0;
        if(exists) {
            argument = 1;
        }
        this.mainController = new MainController(String.valueOf(argument), this.mode);
    }

    /**
     * gets the MainController
     * @return the MainController
     */
    public MainController getMainController(){
        return this.mainController;
    }

    /**
     *  sets the view
     * @param viewName String of the view
     */
    public void setView(String viewName)  {
        switch(viewName){
            case "StartUpPage":
                StartUpPage startUpPage = new StartUpPage();
                startUpPage.setMainFrame(this);
                setContentPane(startUpPage);
                break;
            case "UserSignUp":
                UserSignUp userSignUp = new UserSignUp(this.mode);
                userSignUp.setMainFrame(this);
                setContentPane(userSignUp);
                break;
            case "LoginPage":
                LoginPage loginPage = new LoginPage(this.mode);
                loginPage.setMainFrame(this);
                setContentPane(loginPage);
                break;
            case "UserMainMenu":
                UserMainMenu userMainMenu = new UserMainMenu(this.mode);
                userMainMenu.setMainFrame(this);
                setContentPane(userMainMenu);
                break;
            case "AdminMainMenu":
                AdminMainMenu adminMainMenu = new AdminMainMenu();
                adminMainMenu.setMainFrame(this);
                setContentPane(adminMainMenu);
                break;
            case "TradeMainMenu":
                TradeMainMenu tradeMainMenu = new TradeMainMenu(this.mode, this);
                tradeMainMenu.setMainFrame(this);
                setContentPane(tradeMainMenu);
                break;



            case "UserAccountMenu":
                UserAccountMenu userAccountMenu = new UserAccountMenu(this.mode);
                userAccountMenu.setMainFrame(this);
                setContentPane(userAccountMenu);
                break;

            case "WishlistMenu":
                WishlistMenu wishlistMenu = new WishlistMenu(this.mode);
                wishlistMenu.setMainFrame(this);
                setContentPane(wishlistMenu);
                break;
            case "UserInventoryMenu":
                UserInventoryMenu userInventoryMenu = new UserInventoryMenu(this.mode);
                userInventoryMenu.setMainFrame(this);
                setContentPane(userInventoryMenu);
                break;
            case "AdminApprovals":
                AdminApprovals adminApprovals = new AdminApprovals(this.mode);
                adminApprovals.setMainFrame(this);
                setContentPane(adminApprovals);
                break;

            case "AdminUndo":
                AdminUndo adminUndo = new AdminUndo();
                adminUndo.setMainFrame(this);
                setContentPane(adminUndo);
                break;

            case "TwoWayTrade":
                TwoWayTrade twoWayTrade = new TwoWayTrade(this.mode);
                twoWayTrade.setMainFrame(this);
                setContentPane(twoWayTrade);
                break;

            case "OneWayTrade":
                OneWayTrade oneWayTrade = new OneWayTrade(this.mode);
                oneWayTrade.setMainFrame(this);
                setContentPane(oneWayTrade);
                break;
            case "ThreeWayTrade":
                ThreeWayTrade threeWayTrade = new ThreeWayTrade(this.mode);
                threeWayTrade.setMainFrame(this);
                setContentPane(threeWayTrade);
                break;
            case "ManageOpenTrades":
                ManageOpenTrades manageOpenTrades = new ManageOpenTrades(this.mode);
                manageOpenTrades.setMainFrame(this);
                setContentPane(manageOpenTrades);

                break;

            case "ManagePendingTrades":
                ManagePendingTrades managePendingTrades = new ManagePendingTrades(this.mode);
                managePendingTrades.setMainFrame(this);
                setContentPane(managePendingTrades);
                break;

            case "ManageRequestedTrades":
                ManageRequestedTrades manageRequestedTrades = new ManageRequestedTrades(this.mode);
                manageRequestedTrades.setMainFrame(this);
                setContentPane(manageRequestedTrades);
                break;


            case "ManageReturnMeeting":
                ManageReturnMeeting manageReturnMeeting = new ManageReturnMeeting();
                manageReturnMeeting.setMainFrame(this);
                setContentPane(manageReturnMeeting);
                break;

            default:

                break;
                //error
                //PENDING

        }//switch
        pack();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        panel1 = new JPanel();

        //======== this ========
        setVisible(true);
        Container contentPane = getContentPane();

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
            . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing
            . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
            Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
            ) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
            public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName (
            ) )) throw new RuntimeException( ); }} );

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGap(0, 652, Short.MAX_VALUE)
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGap(0, 485, Short.MAX_VALUE)
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(15, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public static void main(String... args)
    {


        new MainPage();



    }


    }


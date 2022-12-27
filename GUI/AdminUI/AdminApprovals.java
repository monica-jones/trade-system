
package gui.adminUI;

import java.awt.*;

import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import accounts.UserEnforcer;
import accounts.UserManager;
import controllers.AdminController;
import controllers.MainController;
import items.Item;
import items.ItemInventory;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class AdminApprovals extends JPanel {
    private MainPage mainFrame;
    private MainController mainController;
    private DefaultListModel<String> stringListModel = new DefaultListModel<String> ();
    private String mode = "";


    /**
     * Constructor
     * @param mode String
     */
    public AdminApprovals(String mode) {
    	this.mode = mode;
        initComponents();
        this.list1.setModel(stringListModel);
    }

    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        this.mainController = mainFrame.getMainController();
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("AdminMainMenu");
    }

    private void displayItemsBActionPerformed(ActionEvent e) {
        stringListModel.clear();

        ItemInventory itemInventory = this.mainController.getItemInventory();
        List<Item> pendingItems = itemInventory.getPendingItems();
        String itemInfo;
        for (Item item : pendingItems) {
            itemInfo = DisplayUtil.getItemDisplay(item);
            stringListModel.addElement(itemInfo);
        }
    }

    private void approveBActionPerformed(ActionEvent e) {
        Integer itemId;
        try {
            itemId = Integer.valueOf(itemIdTF.getText());
        }catch (NumberFormatException  ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Item Id must be a valid integer number.");
            return;
        }
        ItemInventory itemInventory = this.mainController.getAdminController().getItemInventory();
        Item item = itemInventory.getItemWithID(itemId);
        Boolean success = this.mainController.getAdminController().approveItem(item);
        if (success == true) {
            JOptionPane.showMessageDialog(this.mainFrame, "Pending Item was approved");
            itemIdTF.setText("");
            displayItemsBActionPerformed(e);
        }

       else {
            JOptionPane.showMessageDialog(this.mainFrame, "Item Does Not Exist In Pending List. " +
                    "\nPlease Click Display Pending Items To View Pending Items.");
        }
    }

    private void denyBActionPerformed(ActionEvent e) {
        Integer itemId;
        try {
            itemId = Integer.valueOf(itemIdTF.getText());
        }catch (NumberFormatException  ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Item Id must be a valid integer number.");
            return;
        }

        ItemInventory itemInventory = this.mainController.getAdminController().getItemInventory();
        Item item = itemInventory.getItemWithID(itemId);
        Boolean success = this.mainController.getAdminController().denyItem(item);
        if (success == true) {
            JOptionPane.showMessageDialog(this.mainFrame, "Pending Item was removed");
            itemIdTF.setText("");
            displayItemsBActionPerformed(e);
    }

       else {
        JOptionPane.showMessageDialog(this.mainFrame, "Item Does Not Exist In Pending List. " +
                "\nPlease Click Display Pending Items To View Pending Items.");
        }
    }

    private void displayFlaggedUsersActionPerformed(ActionEvent e) {
        stringListModel.clear();

        UserManager userManager = this.mainController.getUserManager();
        String userInfo;
        try {
            List<UserAccount> allUsers = userManager.getAllUsers();
            AdminController adminController = this.mainController.getAdminController();
            if (allUsers != null) {
                for (UserAccount username : allUsers) {
                    if (adminController.checkFlaggedStatus(username)) {
                        userInfo = DisplayUtil.getUserDisplay(username);
                        stringListModel.addElement("User id: " + userInfo + " | Name: " + adminController.getUserName(username) +
                                " | Overborrowed: " + adminController.getOverBorrowedNum(username));
                    }
                }
            }
        } catch (SQLException ignored) { }
    }



    private void displayFrozenUsersBActionPerformed(ActionEvent e) {
        stringListModel.clear();

        UserManager userManager = this.mainController.getUserManager();
        String userInfo;
        try {
            List<UserAccount> allUsers = userManager.getAllUsers();
            AdminController adminController = this.mainController.getAdminController();
            if (allUsers != null) {
                for (UserAccount username : allUsers) {
                    if (adminController.checkFreezeStatus(username)) {
                        userInfo = DisplayUtil.getUserDisplay(username);
                        stringListModel.addElement("User id: " + userInfo +" | Name: "+ adminController.getUserName(username));
                    }
                }
            }
        } catch (SQLException ignored) { }
    }

    private void freezeBActionPerformed(ActionEvent e) {
        int usernameValue;
        try {
            usernameValue = Integer.parseInt(userIdTF.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid username. Must be a number.");
            return;
        }

        AdminController adminController = this.mainController.getAdminController();
        UserManager userManager = this.mainController.getUserManager();
        UserAccount user = userManager.getUser(usernameValue);
        try {
            // check if the user exists
            if (!adminController.checkFreezeStatus(user)){
                adminController.changeFreezeStatus(user);
            }
            else {
                JOptionPane.showMessageDialog(mainFrame, "The entered user is already frozen!");
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(mainFrame, "The entered userId doesn't exist!");
        }

    }

    private void unfreezeBActionPerformed(ActionEvent e) {
        int usernameValue;
        try {
            usernameValue = Integer.parseInt(userIdTF.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid username. Must be a number.");
            return;
        }

        AdminController adminController = this.mainController.getAdminController();
        UserManager userManager = this.mainController.getUserManager();
        UserAccount user = userManager.getUser(usernameValue);
        try {
            if (adminController.checkFreezeStatus(user)){ // true  = frozen
                adminController.changeFreezeStatus(user);
            }
            else {
                JOptionPane.showMessageDialog(mainFrame, "The entered user is not frozen!");
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(mainFrame, "The entered userId doesn't exist!");
        }
    }

    private void button3ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void unfreezeListBActionPerformed(ActionEvent e) {
        stringListModel.clear();
        UserEnforcer userEnforcer = this.mainController.getUserEnforcer();
        UserManager userManager = this.mainController.getUserManager();
        List<Integer> requestUnfreezeIDs = userEnforcer.getUnfreezeRequestIDs();
        AdminController adminController = this.mainController.getAdminController();
        String userInfo;
        try {
            for (Integer requesterID : requestUnfreezeIDs) {
                UserAccount requester = userManager.getUser(requesterID);
                userInfo = DisplayUtil.getUserDisplay(requester);
                stringListModel.addElement("User id: " + userInfo + " | Name: " + adminController.getUserName(requester));
            }
        } catch(SQLException ignored){
        }
    }

    private void initComponents() {
        //Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.AdminApprovals");
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        displayItemsB = new JButton();
        displayFlaggedUsers = new JButton();
        displayFrozenUsersB = new JButton();
        label2 = new JLabel();
        freezeB = new JButton();
        userIdTF = new JTextField();
        unfreezeB = new JButton();
        label3 = new JLabel();
        itemIdTF = new JTextField();
        approveB = new JButton();
        denyB = new JButton();
        back = new JButton();
        unfreezeListB = new JButton();

        //======== this ========
        setBackground(new Color(0, 102, 102));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder ( 0
        , 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
        , new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) ,
         getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

        //---- label1 ----
        label1.setText(bundle.getString("AdminApprovals.label1.text"));
        label1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 26));
        label1.setForeground(Color.white);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setBackground(Color.darkGray);
            list1.setForeground(Color.white);
            list1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 17));
            scrollPane1.setViewportView(list1);
        }

        //---- displayItemsB ----
        displayItemsB.setText(bundle.getString("AdminApprovals.displayItemsB.text"));
        displayItemsB.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 15));
        displayItemsB.setBackground(Color.darkGray);
        displayItemsB.addActionListener(e -> displayItemsBActionPerformed(e));

        //---- displayFlaggedUsers ----
        displayFlaggedUsers.setText(bundle.getString("AdminApprovals.displayFlaggedUsers.text"));
        displayFlaggedUsers.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 15));
        displayFlaggedUsers.setBackground(Color.darkGray);
        displayFlaggedUsers.addActionListener(e -> displayFlaggedUsersActionPerformed(e));

        //---- displayFrozenUsersB ----
        displayFrozenUsersB.setText(bundle.getString("AdminApprovals.displayFrozenUsersB.text"));
        displayFrozenUsersB.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 15));
        displayFrozenUsersB.setBackground(Color.darkGray);
        displayFrozenUsersB.addActionListener(e -> {
			button3ActionPerformed(e);
			displayFrozenUsersBActionPerformed(e);
		});

        //---- label2 ----
        label2.setText(bundle.getString("AdminApprovals.label2.text"));
        label2.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
        label2.setForeground(Color.white);

        //---- freezeB ----
        freezeB.setText(bundle.getString("AdminApprovals.freezeB.text"));
        freezeB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
        freezeB.setBackground(Color.darkGray);
        freezeB.addActionListener(e -> freezeBActionPerformed(e));

        //---- userIdTF ----
        userIdTF.setBackground(Color.darkGray);
        userIdTF.setForeground(Color.white);
        userIdTF.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));

        //---- unfreezeB ----
        unfreezeB.setText(bundle.getString("AdminApprovals.unfreezeB.text"));
        unfreezeB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
        unfreezeB.setBackground(Color.darkGray);
        unfreezeB.addActionListener(e -> unfreezeBActionPerformed(e));

        //---- label3 ----
        label3.setText(bundle.getString("AdminApprovals.label3.text"));
        label3.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
        label3.setForeground(Color.white);

        //---- itemIdTF ----
        itemIdTF.setBackground(Color.darkGray);
        itemIdTF.setForeground(Color.white);
        itemIdTF.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));

        //---- approveB ----
        approveB.setText(bundle.getString("AdminApprovals.approveB.text"));
        approveB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
        approveB.setBackground(Color.darkGray);
        approveB.addActionListener(e -> approveBActionPerformed(e));

        //---- denyB ----
        denyB.setText(bundle.getString("AdminApprovals.denyB.text"));
        denyB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
        denyB.setBackground(Color.darkGray);
        denyB.addActionListener(e -> denyBActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("AdminApprovals.back.text"));
        back.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
        back.setBackground(Color.darkGray);
        back.addActionListener(e -> backActionPerformed(e));

        //---- unfreezeListB ----
        unfreezeListB.setText(bundle.getString("AdminApprovals.unfreezeListB.text"));
        unfreezeListB.setBackground(Color.darkGray);
        unfreezeListB.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 15));
        unfreezeListB.addActionListener(e -> unfreezeListBActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(161, Short.MAX_VALUE)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
                    .addGap(153, 153, 153))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(49, 49, 49)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(denyB, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(unfreezeB, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(approveB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(itemIdTF, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                .addComponent(freezeB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(userIdTF, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
                            .addGap(29, 29, 29)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(unfreezeListB, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                    .addComponent(displayFrozenUsersB, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                    .addComponent(displayFlaggedUsers, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                    .addComponent(displayItemsB, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(label1)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(userIdTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(freezeB)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(unfreezeB)
                            .addGap(52, 52, 52)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label3, GroupLayout.Alignment.TRAILING)
                                .addComponent(itemIdTF, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(approveB)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(denyB))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addComponent(displayItemsB, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(displayFlaggedUsers, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(displayFrozenUsersB, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(unfreezeListB, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addGap(13, 13, 13)
                    .addComponent(back)
                    .addGap(21, 21, 21))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton displayItemsB;
    private JButton displayFlaggedUsers;
    private JButton displayFrozenUsersB;
    private JLabel label2;
    private JButton freezeB;
    private JTextField userIdTF;
    private JButton unfreezeB;
    private JLabel label3;
    private JTextField itemIdTF;
    private JButton approveB;
    private JButton denyB;
    private JButton back;
    private JButton unfreezeListB;
    // End of variables declaration  //GEN-END:variables
}

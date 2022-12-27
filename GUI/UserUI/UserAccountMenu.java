
package gui.userUI;

import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import accounts.UserEnforcer;
import accounts.UserManager;
import controllers.UserMenuController;
import controllers.MainController;
import trades.Trade;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;

//import com.sun.jmx.snmp.SnmpStringFixed;

/**
 * @author Namratha Anil
 */
public class UserAccountMenu extends JPanel {
    private MainPage mainFrame;
    private MainController mainController;
    private String mode = "";
    private DefaultListModel<String> recentTradesList = new DefaultListModel<>();
    private DefaultListModel<String> frequentTradersList = new DefaultListModel<>();
    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.UserAccountMenu");


    public UserAccountMenu(String mode) {
        this.mode = mode;
        initComponents();

    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        this.mainController = mainFrame.getMainController();
    }

    private void wishlistActionPerformed(ActionEvent e) {
        mainFrame.setView("WishlistMenu");
    }

    private void userInventoryActionPerformed(ActionEvent e) {
        mainFrame.setView("UserInventoryMenu");
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("UserMainMenu");
    }

    private void frequentTradersActionPerformed(ActionEvent e) {
        frequentTradersList.clear();
        list1.setModel(frequentTradersList);
        UserManager userManager = this.mainController.getUserManager();
        UserAccount currentUser = userManager.getUser(LoginPage.getCurrentUserName());
        UserMenuController userMenuController = new UserMenuController(currentUser, this.mode);
        List<Integer> frequentTrades = userMenuController.getFrequentTraders(this.mainController.getTradeLogManager());
        if (!(frequentTrades.size() == 0)) {
        for (Integer userID : frequentTrades) {
            frequentTradersList.addElement("User ID " + userID);
        }
        }
        else {JOptionPane.showMessageDialog(mainFrame, popups.getString("noTop.text"));}

    }

    private void topTradesBActionPerformed(ActionEvent e) {
        recentTradesList.clear();
        list1.setModel(recentTradesList);
        UserManager userManager = this.mainController.getUserManager();
        UserAccount currentUser = userManager.getUser(LoginPage.getCurrentUserName());
        UserMenuController userMenuController = new UserMenuController(currentUser, this.mode);
        List<Trade> recentTrades = userMenuController.getRecentTrades(this.mainController.getTradeManager());
        if (!(recentTrades.size() == 0)) {
            for (Trade trade : recentTrades) {
                recentTradesList.addElement(DisplayUtil.getTradeDisplay(trade));
            }
        }
        else {JOptionPane.showMessageDialog(mainFrame, popups.getString("noTop.text"));}
    }

    private void requestUnfreezeActionPerformed(ActionEvent e) {
        UserEnforcer userEnforcer = this.mainController.getUserEnforcer();
        UserManager userManager = this.mainController.getUserManager();
        UserAccount currentUser = userManager.getUser(LoginPage.getCurrentUserName());
        UserMenuController userMenuController = new UserMenuController(currentUser, this.mode);
        boolean requestSuccess = userMenuController.requestToUnfreeze(userEnforcer);
        if(requestSuccess) {
            JOptionPane.showMessageDialog(this.mainFrame, popups.getString("unfreezeRequest.text"));
        } else{
            JOptionPane.showMessageDialog(this.mainFrame, popups.getString("unfreezeFail.text"));
        }
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        ResourceBundle bundle = ResourceBundle.getBundle("GUI.locales.UserAccountMenu");
        label1 = new JLabel();
        wishlist = new JButton();
        userInventory = new JButton();
        frequentTraders = new JButton();
        topTradesB = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        back = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        button1 = new JButton();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
        . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing
        . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
        Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
        ) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
        public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .getPropertyName (
        ) )) throw new RuntimeException( ); }} );

        //---- label1 ----
        label1.setText(bundle.getString("UserAccountMenu.label1.text"));
        label1.setForeground(new Color(153, 255, 255));
        label1.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- wishlist ----
        wishlist.setText(bundle.getString("UserAccountMenu.wishlist.text"));
        wishlist.setFont(new Font("Verdana", Font.PLAIN, 14));
        wishlist.setForeground(Color.white);
        wishlist.setBackground(new Color(0, 51, 51));
        wishlist.setOpaque(true);
        wishlist.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        wishlist.addActionListener(e -> wishlistActionPerformed(e));

        //---- userInventory ----
        userInventory.setText(bundle.getString("UserAccountMenu.userInventory.text"));
        userInventory.setFont(new Font("Verdana", Font.PLAIN, 14));
        userInventory.setForeground(Color.white);
        userInventory.setBackground(new Color(0, 51, 51));
        userInventory.setOpaque(true);
        userInventory.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        userInventory.addActionListener(e -> userInventoryActionPerformed(e));

        //---- frequentTraders ----
        frequentTraders.setText(bundle.getString("UserAccountMenu.frequentTraders.text"));
        frequentTraders.setFont(new Font("Verdana", Font.PLAIN, 14));
        frequentTraders.setForeground(Color.white);
        frequentTraders.setBackground(new Color(0, 51, 51));
        frequentTraders.setOpaque(true);
        frequentTraders.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        frequentTraders.addActionListener(e -> frequentTradersActionPerformed(e));

        //---- topTradesB ----
        topTradesB.setText(bundle.getString("UserAccountMenu.topTradesB.text"));
        topTradesB.setFont(new Font("Verdana", Font.PLAIN, 14));
        topTradesB.setForeground(Color.white);
        topTradesB.setBackground(new Color(0, 51, 51));
        topTradesB.setOpaque(true);
        topTradesB.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        topTradesB.addActionListener(e -> topTradesBActionPerformed(e));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setFont(new Font("Verdana", Font.PLAIN, 14));
            list1.setForeground(Color.white);
            list1.setBackground(Color.darkGray);
            scrollPane1.setViewportView(list1);
        }

        //---- back ----
        back.setText(bundle.getString("UserAccountMenu.back.text"));
        back.setForeground(Color.white);
        back.setBackground(new Color(12, 115, 115));
        back.setOpaque(true);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.addActionListener(e -> backActionPerformed(e));

        //---- label2 ----
        label2.setText(bundle.getString("UserAccountMenu.label2.text"));
        label2.setFont(new Font("Verdana", Font.PLAIN, 14));
        label2.setForeground(Color.white);

        //---- label3 ----
        label3.setText(bundle.getString("UserAccountMenu.label3.text"));
        label3.setForeground(Color.white);
        label3.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- button1 ----
        button1.setText(bundle.getString("UserAccountMenu.button1.text"));
        button1.setForeground(Color.white);
        button1.setFont(new Font("Verdana", Font.PLAIN, 14));
        button1.setBackground(new Color(0, 51, 51));
        button1.setOpaque(true);
        button1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button1.addActionListener(e -> requestUnfreezeActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(label1)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(topTradesB, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                                        .addGap(77, 77, 77)
                                        .addComponent(frequentTraders, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))))
                            .addGap(46, 46, 46))))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 161, Short.MAX_VALUE))
                        .addComponent(button1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                        .addComponent(userInventory, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                        .addComponent(wishlist, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(253, 253, 253))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addGap(8, 8, 8)
                    .addComponent(label3)
                    .addGap(18, 18, 18)
                    .addComponent(wishlist, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(userInventory, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                    .addComponent(label2)
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(topTradesB, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                        .addComponent(frequentTraders, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                    .addGap(33, 33, 33)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15))
        );
        // End of component initialization  //GEN-END:initComponents
    }

    // JVariables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JLabel label1;
    private JButton wishlist;
    private JButton userInventory;
    private JButton frequentTraders;
    private JButton topTradesB;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton back;
    private JLabel label2;
    private JLabel label3;
    private JButton button1;
    // End of variables declaration  //GEN-END:variables
}

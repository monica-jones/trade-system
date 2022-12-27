/*
 * Created by JFormDesigner on Sat Aug 08 00:09:05 EDT 2020
 */

package gui.tradeUI;


import java.awt.*;
import javax.swing.border.*;
import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;

import accounts.UserManager;
import controllers.MainController;
import controllers.TradeController;

import items.Item;
import items.ItemInventory;

import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Namratha Anil
 */
public class OneWayTrade extends JPanel {
    private MainPage mainFrame;
    private String mode = "";


    public MainController mainController;
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();


    public OneWayTrade(String mode) {
    	this.mode = mode;
        initComponents();
    }
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        list1.setModel(sysInventoryList);
        this.mainController = mainFrame.getMainController();


    }

    private void reqOneWayBActionPerformed(ActionEvent e) {

        TradeController tradeController = mainFrame.getMainController().getTradeController();
        int itemId;
        try {
            itemId = Integer.parseInt(itemIdTF.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid Item ID. Please Enter a number. \nID must be a number.");
            return;
        }


        boolean isPermanent = isPermanentCheckBox.isSelected();
        if (tradeController.requestOneWay(LoginPage.getCurrentUserName(), itemId, isPermanent)) {
            JOptionPane.showMessageDialog(mainFrame, "Trade requested successfully");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Trade request unsuccessful");
        }

    }

   private void availItemsActionPerformed(ActionEvent e) {
       sysInventoryList.clear();
       ItemInventory itemInventory = this.mainController.getItemInventory();
       UserManager userManager = this.mainController.getUserManager();
       UserAccount currentUser = this.mainController.getCurrentUserAccount();
       List<Item> approvedItems = this.mainController.getTradeController().getTradePresenter().getAvailableItems(currentUser, itemInventory, userManager);
       if (approvedItems.size() == 0){
           JOptionPane.showMessageDialog(mainFrame, "There are no items to display");
       }
       else {
       String itemInfo;
       for (Item item : approvedItems) {
           itemInfo = DisplayUtil.getItemDisplay(item);
           sysInventoryList.addElement(itemInfo);
       }
       }
   }

   private void backActionPerformed(ActionEvent e) {
       mainFrame.setView("TradeMainMenu");
   }

   private void button2ActionPerformed(ActionEvent e) {
       // TODO add your code here
   }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.OneWayTrade");
        itemIdTF = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        reqOneWayB = new JButton();
        back = new JButton();
        isPermanentCheckBox = new JCheckBox();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        availItems = new JButton();
        label2 = new JLabel();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
        . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax
        . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,
        12 ), java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans
        . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .
        getPropertyName () )) throw new RuntimeException( ); }} );

        //---- itemIdTF ----
        itemIdTF.setBackground(new Color(12, 115, 115));
        itemIdTF.setForeground(Color.white);
        itemIdTF.setFont(new Font("Verdana", Font.PLAIN, 14));
        itemIdTF.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- label3 ----
        label3.setText(bundle.getString("OneWayTrade.label3.text"));
        label3.setForeground(Color.white);
        label3.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- label4 ----
        label4.setText(bundle.getString("OneWayTrade.label4.text"));
        label4.setForeground(new Color(153, 255, 255));
        label4.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- reqOneWayB ----
        reqOneWayB.setText(bundle.getString("OneWayTrade.reqOneWayB.text"));
        reqOneWayB.setForeground(Color.white);
        reqOneWayB.setFont(new Font("Verdana", Font.PLAIN, 14));
        reqOneWayB.setBackground(new Color(0, 51, 51));
        reqOneWayB.setOpaque(true);
        reqOneWayB.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        reqOneWayB.addActionListener(e -> reqOneWayBActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("OneWayTrade.back.text"));
        back.setForeground(Color.white);
        back.setBackground(new Color(12, 115, 115));
        back.setOpaque(true);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> {
			button2ActionPerformed(e);
			backActionPerformed(e);
		});

        //---- isPermanentCheckBox ----
        isPermanentCheckBox.setText(bundle.getString("OneWayTrade.isPermanentCheckBox.text"));
        isPermanentCheckBox.setForeground(Color.white);
        isPermanentCheckBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        isPermanentCheckBox.setBackground(new Color(12, 115, 115));
        isPermanentCheckBox.setOpaque(true);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setForeground(Color.white);
            list1.setBackground(Color.darkGray);
            list1.setFont(list1.getFont().deriveFont(list1.getFont().getSize() + 3f));
            list1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
            scrollPane1.setViewportView(list1);
        }

        //---- availItems ----
        availItems.setText(bundle.getString("OneWayTrade.availItems.text"));
        availItems.setForeground(Color.white);
        availItems.setBackground(new Color(0, 51, 51));
        availItems.setOpaque(true);
        availItems.setFont(new Font("Verdana", Font.PLAIN, 14));
        availItems.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        availItems.addActionListener(e -> availItemsActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(scrollPane1, GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(isPermanentCheckBox, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(availItems, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(reqOneWayB, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE))))
                            .addContainerGap(32, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(itemIdTF, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 410, Short.MAX_VALUE))))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(636, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(label4)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label4)
                    .addGap(46, 46, 46)
                    .addComponent(label3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(reqOneWayB, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(itemIdTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(15, 15, 15)
                    .addComponent(isPermanentCheckBox)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(availItems, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                    .addGap(19, 19, 19))
        );

        //---- label2 ----
        label2.setText(bundle.getString("OneWayTrade.label2.text"));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JTextField itemIdTF;
    private JLabel label3;
    private JLabel label4;
    private JButton reqOneWayB;
    private JButton back;
    private JCheckBox isPermanentCheckBox;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton availItems;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

/*
 * Created by JFormDesigner on Sat Aug 08 00:15:26 EDT 2020
 */

package gui.tradeUI;



import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import accounts.UserManager;
import controllers.TradeController;
import controllers.TradePresenter;
import items.Item;
import items.ItemInventory;

import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Namratha Anil
 */
public class TwoWayTrade extends JPanel {
    private MainPage mainFrame;
    private String mode = "";
    private DefaultListModel <String> viewOfferList = new DefaultListModel<String>();
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();

    public TwoWayTrade(String mode) {
    	this.mode = mode;
        initComponents();
    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
    }



    private void requestTradesActionPerformed(ActionEvent e) {
        //TODO requires check and number format

        TradeController tradeController = mainFrame.getMainController().getTradeController();
        int reqitemId = Integer.parseInt(requestedItemTF.getText());
        int oddereditemId = Integer.parseInt(offerItemTF.getText());
        boolean isPermanent = checkBox1.isSelected();
        boolean success = tradeController.requestTwoWay(LoginPage.getCurrentUserName(), reqitemId, oddereditemId, isPermanent);
        JOptionPane.showMessageDialog(mainFrame, "Trade has been successfully requested"); //TODO check for invalid trade ID
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeMainMenu");
    }

    private void viewOfferActionPerformed(ActionEvent e) {
        viewOfferList.clear();
        list2.setModel(viewOfferList);
        Integer desiredItemID;
        try {
            desiredItemID = Integer.valueOf(requestedItemTF.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid Item ID. Please Enter a number.\nID must be a number.");
            return;
        }
        //TODO number format exception and check for invalid ID
        ItemInventory itemInventory =  mainFrame.getMainController().getItemInventory();
        UserManager userManager = mainFrame.getMainController().getUserManager();
        UserAccount current = mainFrame.getMainController().getCurrentUserAccount();
        List<Item> itemsToOffer = mainFrame.getMainController().getTradeController().getTradePresenter()
                .getItemsToOffer(current, desiredItemID, itemInventory ,userManager);
        String itemInfo;
        for (Item item : itemsToOffer) {
            itemInfo = item.getItemID() + " | " + item.getItemName() + " | " + item.getDescription()+ " | " + item.getPrice();
            viewOfferList.addElement(itemInfo);
        }
    }

    private void allInvBActionPerformed(ActionEvent e) {
        list2.setModel(sysInventoryList);
        sysInventoryList.clear();
        TradePresenter tradePresenter = mainFrame.getMainController().getTradeController().getTradePresenter();
        UserAccount current = mainFrame.getMainController().getCurrentUserAccount();
        ItemInventory itemInventory =  mainFrame.getMainController().getItemInventory();
        UserManager userManager = mainFrame.getMainController().getUserManager();
        List<Item> approvedItems = tradePresenter.getAvailableItems(current, itemInventory , userManager);
        String itemInfo;
        for (Item item : approvedItems) {
                itemInfo = DisplayUtil.getItemDisplay(item);
                sysInventoryList.addElement(itemInfo);
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.TwoWayTrade");
        label1 = new JLabel();
        requestedItemTF = new JTextField();
        viewOffer = new JButton();
        label3 = new JLabel();
        offerItemTF = new JTextField();
        requestTrades = new JButton();
        back = new JButton();
        checkBox1 = new JCheckBox();
        scrollPane2 = new JScrollPane();
        list2 = new JList();
        allInvB = new JButton();
        label4 = new JLabel();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
        . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing. border. TitledBorder. CENTER, javax
        . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,
        12 ), java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans
        . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .
        getPropertyName () )) throw new RuntimeException( ); }} );

        //---- label1 ----
        label1.setText(bundle.getString("TwoWayTrade.label1.text_2"));
        label1.setForeground(new Color(153, 255, 255));
        label1.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- requestedItemTF ----
        requestedItemTF.setForeground(Color.white);
        requestedItemTF.setFont(new Font("Verdana", Font.PLAIN, 14));
        requestedItemTF.setBackground(new Color(12, 115, 115));
        requestedItemTF.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- viewOffer ----
        viewOffer.setText(bundle.getString("TwoWayTrade.viewOffer.text"));
        viewOffer.setForeground(Color.white);
        viewOffer.setFont(new Font("Verdana", Font.BOLD, 14));
        viewOffer.setBackground(new Color(0, 51, 51));
        viewOffer.setOpaque(true);
        viewOffer.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        viewOffer.addActionListener(e -> viewOfferActionPerformed(e));

        //---- label3 ----
        label3.setText(bundle.getString("TwoWayTrade.label3.text_2"));
        label3.setForeground(Color.white);
        label3.setFont(new Font("Verdana", Font.BOLD, 14));

        //---- offerItemTF ----
        offerItemTF.setForeground(Color.white);
        offerItemTF.setFont(new Font("Verdana", Font.PLAIN, 14));
        offerItemTF.setBackground(new Color(12, 115, 115));
        offerItemTF.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- requestTrades ----
        requestTrades.setText(bundle.getString("TwoWayTrade.requestTrades.text"));
        requestTrades.setForeground(Color.white);
        requestTrades.setFont(new Font("Verdana", Font.BOLD, 14));
        requestTrades.setOpaque(true);
        requestTrades.setBackground(new Color(0, 51, 51));
        requestTrades.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        requestTrades.addActionListener(e -> requestTradesActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("TwoWayTrade.back.text"));
        back.setForeground(Color.white);
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.setOpaque(true);
        back.setBackground(new Color(12, 115, 115));
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- checkBox1 ----
        checkBox1.setText(bundle.getString("TwoWayTrade.checkBox1.text_2"));
        checkBox1.setForeground(Color.white);
        checkBox1.setFont(new Font("Verdana", Font.PLAIN, 14));
        checkBox1.setBackground(new Color(12, 115, 115));
        checkBox1.setOpaque(true);

        //======== scrollPane2 ========
        {

            //---- list2 ----
            list2.setForeground(Color.white);
            list2.setFont(new Font("Verdana", Font.PLAIN, 14));
            list2.setBackground(Color.darkGray);
            scrollPane2.setViewportView(list2);
        }

        //---- allInvB ----
        allInvB.setText(bundle.getString("TwoWayTrade.allInvB.text"));
        allInvB.setForeground(Color.white);
        allInvB.setFont(new Font("Verdana", Font.BOLD, 14));
        allInvB.setOpaque(true);
        allInvB.setBackground(new Color(0, 51, 51));
        allInvB.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        allInvB.addActionListener(e -> allInvBActionPerformed(e));

        //---- label4 ----
        label4.setText(bundle.getString("TwoWayTrade.label4.text_2"));
        label4.setForeground(Color.white);
        label4.setFont(new Font("Verdana", Font.BOLD, 14));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 610, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(51, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 646, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(requestedItemTF, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(viewOffer, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(allInvB, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
                                    .addGap(37, 37, 37)
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(offerItemTF, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkBox1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(requestTrades, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))))
                            .addGap(50, 50, 50))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label3))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(requestedItemTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(offerItemTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(viewOffer, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkBox1))
                    .addGap(34, 34, 34)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(requestTrades, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(allInvB, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addGap(18, 18, 18)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JTextField requestedItemTF;
    private JButton viewOffer;
    private JLabel label3;
    private JTextField offerItemTF;
    private JButton requestTrades;
    private JButton back;
    private JCheckBox checkBox1;
    private JScrollPane scrollPane2;
    private JList list2;
    private JButton allInvB;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

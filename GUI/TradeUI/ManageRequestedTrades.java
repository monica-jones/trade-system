/*
 * Created by JFormDesigner on Sat Aug 08 00:50:13 EDT 2020
 */

package gui.tradeUI;

import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import controllers.TradeController;
import trades.Trade;
import trades.TradeLogManager;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Namratha Anil
 */
public class ManageRequestedTrades extends JPanel {
    private MainPage mainFrame;
    private DefaultListModel<String> sysInventoryList = new DefaultListModel<String>();
    private String mode = "";
    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.ManageRequestedTrades");


    public ManageRequestedTrades(String mode) {
        this.mode = mode;
        initComponents();
        list1.setModel(sysInventoryList);
    }

    public void setMainFrame(MainPage mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeMainMenu");
    }

    private void acceptActionPerformed(ActionEvent e) {

        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeIdTF.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        Integer userID = LoginPage.getCurrentUserName();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "requested", tlm); // confirms this is a valid requested trade
        if (!(mainFrame.getMainController().getTradeLogManager().getUserTrades(userID).contains(trade))) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
        }
            else{
                boolean success = mainFrame.getMainController().getTradeController().acceptTradeForCurrentUser(LoginPage.getCurrentUserName(),trade); // confirms this is a valid requested trade
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, popups.getString("tradeAccepted.text"));
                } else {
                    JOptionPane.showMessageDialog(mainFrame, popups.getString("waitUser.text"));
                }
            }
    }

    private void declineActionPerformed(ActionEvent e) {
        TradeController tradeController = mainFrame.getMainController().getTradeController();
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeIdTF.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        Integer userID = LoginPage.getCurrentUserName();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "requested", tlm); // confirms this is a valid requested trade
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
            return;
        }
        if (tradeController.declineTrade(trade)) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("tradeDeclined"));
        } else {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("tradeDeclineError"));
        }
    }

    private void displayActionPerformed(ActionEvent e) {
        sysInventoryList.clear();
        Integer userID = LoginPage.getCurrentUserName();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        List<Trade> tradeRequests = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTradesList(userID, "requested",tlm);
        String itemInfo;
        for (Trade trade : tradeRequests) {
            itemInfo = DisplayUtil.getTradeDisplay(trade);
            sysInventoryList.addElement(itemInfo);
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.ManageRequestedTrades");
        tradeIdTF = new JTextField();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        label1 = new JLabel();
        accept = new JButton();
        decline = new JButton();
        display = new JButton();
        back = new JButton();
        label2 = new JLabel();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );

        //---- tradeIdTF ----
        tradeIdTF.setFont(new Font("Verdana", Font.PLAIN, 14));
        tradeIdTF.setBackground(new Color(12, 115, 115));
        tradeIdTF.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        tradeIdTF.setForeground(Color.white);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setBackground(Color.darkGray);
            list1.setFont(new Font("Verdana", Font.PLAIN, 14));
            list1.setForeground(Color.white);
            scrollPane1.setViewportView(list1);
        }

        //---- label1 ----
        label1.setText(bundle.getString("ManageRequestedTrades.label1.text"));
        label1.setForeground(Color.white);
        label1.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- accept ----
        accept.setText(bundle.getString("ManageRequestedTrades.accept.text"));
        accept.setFont(new Font("Verdana", Font.PLAIN, 14));
        accept.setForeground(Color.white);
        accept.setBackground(new Color(0, 51, 51));
        accept.setOpaque(true);
        accept.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        accept.addActionListener(e -> acceptActionPerformed(e));

        //---- decline ----
        decline.setText(bundle.getString("ManageRequestedTrades.decline.text"));
        decline.setFont(new Font("Verdana", Font.PLAIN, 14));
        decline.setForeground(Color.white);
        decline.setBackground(new Color(0, 51, 51));
        decline.setOpaque(true);
        decline.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        decline.addActionListener(e -> declineActionPerformed(e));

        //---- display ----
        display.setText(bundle.getString("ManageRequestedTrades.display.text"));
        display.setFont(new Font("Verdana", Font.PLAIN, 14));
        display.setForeground(Color.white);
        display.setBackground(new Color(0, 51, 51));
        display.setOpaque(true);
        display.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        display.addActionListener(e -> displayActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("ManageRequestedTrades.back.text"));
        back.setBackground(new Color(12, 115, 115));
        back.setForeground(Color.white);
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- label2 ----
        label2.setText(bundle.getString("ManageRequestedTrades.label2.text"));
        label2.setForeground(new Color(0, 204, 204));
        label2.setFont(new Font("Verdana", Font.PLAIN, 12));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label2)
                        .addComponent(back, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
                    .addGap(15, 15, 15))
                .addGroup(layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(tradeIdTF, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(accept, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(decline, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                        .addComponent(display, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(label2)
                    .addGap(29, 29, 29)
                    .addComponent(label1)
                    .addGap(32, 32, 32)
                    .addComponent(tradeIdTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(36, 36, 36)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(accept, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                        .addComponent(decline, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(display, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JTextField tradeIdTF;
    private JScrollPane scrollPane1;
    private JList list1;
    private JLabel label1;
    private JButton accept;
    private JButton decline;
    private JButton display;
    private JButton back;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

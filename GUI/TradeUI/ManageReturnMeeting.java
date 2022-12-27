/*
 * Created by JFormDesigner on Sat Aug 15 15:44:31 EDT 2020
 */

package gui.tradeUI;

import java.util.*;
import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import trades.Meeting;
import trades.Trade;
import trades.TradeLogManager;

import java.awt.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Namratha Anil
 */
public class ManageReturnMeeting extends JPanel {
    private MainPage mainFrame;
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();
    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.ManageReturnMeeting");
    public ManageReturnMeeting() {
        initComponents();
        list1.setModel(sysInventoryList);
    }

    private void displayTradeActionPerformed(ActionEvent e) {
        UserAccount currentUser = mainFrame.getMainController().getCurrentUserAccount();
        List<Trade> approvedItems = mainFrame.getMainController().getTradeLogManager().getUserTradesByStatus(currentUser.getUserID(), "open");

        String itemInfo;
        for (Trade trade : approvedItems) {
            itemInfo = DisplayUtil.getTradeDisplay(trade);
            sysInventoryList.addElement(itemInfo);
            sysInventoryList.addElement("");

        }
    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;



    }

    private void meetingActionPerformed(ActionEvent e) {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
            return;
        }
        Meeting meeting =mainFrame.getMainController().getTradeController().getMeetingController().getMeeting(tradeID); //TODO: update this properly according to demo mode.
        String meetingInfo;
        meetingInfo = meeting.getLocation() + " | " + meeting.getTime() + " | " + meeting.getStatus() + " | " ;
        meetingDetails.setText(meetingInfo);
    }

    private void button3ActionPerformed(ActionEvent e) {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);


        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
        } else {

            Meeting meeting = mainFrame.getMainController().getTradeController().getMeetingController().getReturnMeeting(tradeID);
            if (!meeting.getStatus().equals("complete")) {
                if (!(meeting.getLastEditorID() == LoginPage.getCurrentUserName())) {

                    Boolean success = null;
                    try {
                        success = mainFrame.getMainController().getTradeController().getMeetingController().completeReturnMeeting(LoginPage.getCurrentUserName(), tradeID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(mainFrame
                                , popups.getString("returnComplete.text"));
                        mainFrame.getMainController().getTradeController().completeTrade(trade);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, popups.getString("markedComplete.text"));

                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, popups.getString("wait.text"));
                }
            }
            else {
                JOptionPane.showMessageDialog(mainFrame, popups.getString("alreadyComplete.text"));
            }
        }

    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("ManageOpenTrades");
    }

    private void incompleteActionPerformed(ActionEvent e) {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
            return;
        }
        mainFrame.getMainController().getTradeController().getMeetingController().incompleteMeeting(trade);
        JOptionPane.showMessageDialog(mainFrame, popups.getString("meetingIncomplete.text"));
        mainFrame.getMainController().getTradeController().incompleteTrade(trade);
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.ManageReturnMeeting");
        label1 = new JLabel();
        tradId = new JLabel();
        tradeId = new JTextField();
        label4 = new JLabel();
        meetingDetails = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        displayTrade = new JButton();
        back = new JButton();
        button3 = new JButton();
        incomplete = new JButton();
        meetingDisplay = new JButton();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
        javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax
        . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
        .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans.
        PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .
        equals (e .getPropertyName () )) throw new RuntimeException( ); }} );

        //---- label1 ----
        label1.setText(bundle.getString("ManageReturnMeeting.label1.text"));
        label1.setFont(new Font("Verdana", Font.PLAIN, 12));
        label1.setForeground(new Color(153, 255, 255));

        //---- tradId ----
        tradId.setText(bundle.getString("ManageReturnMeeting.tradId.text_2"));
        tradId.setFont(new Font("Verdana", Font.PLAIN, 16));
        tradId.setForeground(Color.white);

        //---- tradeId ----
        tradeId.setFont(new Font("Verdana", Font.PLAIN, 14));
        tradeId.setForeground(Color.white);
        tradeId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        tradeId.setBackground(new Color(12, 115, 115));

        //---- label4 ----
        label4.setText(bundle.getString("ManageReturnMeeting.label4.text_2"));
        label4.setForeground(Color.white);
        label4.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- meetingDetails ----
        meetingDetails.setText(bundle.getString("ManageReturnMeeting.meetingDetails.text"));
        meetingDetails.setFont(new Font("Verdana", Font.PLAIN, 14));
        meetingDetails.setForeground(Color.white);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setFont(new Font("Verdana", Font.PLAIN, 14));
            list1.setForeground(Color.white);
            list1.setBackground(Color.darkGray);
            list1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
            scrollPane1.setViewportView(list1);
        }

        //---- displayTrade ----
        displayTrade.setText(bundle.getString("ManageReturnMeeting.displayTrade.text"));
        displayTrade.setBackground(new Color(0, 51, 51));
        displayTrade.setForeground(Color.white);
        displayTrade.setOpaque(true);
        displayTrade.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        displayTrade.addActionListener(e -> displayTradeActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("ManageReturnMeeting.back.text"));
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.setForeground(Color.white);
        back.setOpaque(true);
        back.setBackground(new Color(12, 115, 115));
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- button3 ----
        button3.setText(bundle.getString("ManageReturnMeeting.button3.text_2"));
        button3.setBackground(new Color(0, 51, 51));
        button3.setFont(new Font("Verdana", Font.PLAIN, 14));
        button3.setForeground(Color.white);
        button3.setOpaque(true);
        button3.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button3.setMultiClickThreshhold(3L);
        button3.addActionListener(e -> button3ActionPerformed(e));

        //---- incomplete ----
        incomplete.setText(bundle.getString("ManageReturnMeeting.incomplete.text_2"));
        incomplete.setBackground(new Color(0, 51, 51));
        incomplete.setFont(new Font("Verdana", Font.PLAIN, 14));
        incomplete.setForeground(Color.white);
        incomplete.setOpaque(true);
        incomplete.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        incomplete.addActionListener(e -> incompleteActionPerformed(e));

        //---- meetingDisplay ----
        meetingDisplay.setText(bundle.getString("ManageReturnMeeting.meetingDisplay.text"));
        meetingDisplay.setBackground(new Color(0, 51, 51));
        meetingDisplay.setFont(new Font("Verdana", Font.PLAIN, 14));
        meetingDisplay.setOpaque(true);
        meetingDisplay.setForeground(Color.white);
        meetingDisplay.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        meetingDisplay.addActionListener(e -> meetingActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap(576, Short.MAX_VALUE)
                            .addComponent(label1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(tradId, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(meetingDisplay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(79, 79, 79)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(incomplete, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                        .addComponent(button3, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)))
                                .addComponent(displayTrade, GroupLayout.PREFERRED_SIZE, 642, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(meetingDetails, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                                    .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)))
                            .addGap(0, 53, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 629, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label1)
                            .addGap(68, 68, 68)
                            .addComponent(tradId)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(66, 66, 66))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(button3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(incomplete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addComponent(meetingDisplay, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
                    .addGap(3, 3, 3)
                    .addComponent(label4)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(meetingDetails, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(displayTrade, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(back, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(13, 13, 13))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JLabel tradId;
    private JTextField tradeId;
    private JLabel label4;
    private JLabel meetingDetails;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton displayTrade;
    private JButton back;
    private JButton button3;
    private JButton incomplete;
    private JButton meetingDisplay;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

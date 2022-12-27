/*
 * Created by JFormDesigner on Sat Aug 08 00:49:17 EDT 2020
 */

package gui.tradeUI;


import java.awt.*;
import java.sql.SQLException;

import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import controllers.MeetingController;

import trades.Meeting;
import trades.Trade;
import trades.TradeLogManager;

import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.text.MaskFormatter;

/**
 * @author Namratha Anil
 */
public class ManagePendingTrades extends JPanel {
    private MainPage mainFrame;
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();
    private MaskFormatter mask;
    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.ManagePendingTrades"); // DO NOT REMOVE

    {
        try {
            mask = new MaskFormatter("####-##-## ##:##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ;
    private String mode = "";



    public ManagePendingTrades(String mode) {
        this.mode = mode;
    	initComponents();
        list1.setModel(sysInventoryList);
        mask.setPlaceholder("#");

    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;



    }

    private void backActionPerformed(ActionEvent e)  {
        mainFrame.setView("TradeMainMenu");
    }

    private void displayActionPerformed(ActionEvent e) {
        sysInventoryList.clear();
        Integer userID = LoginPage.getCurrentUserName();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        List<Trade> tradeRequests = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTradesList(userID, "pending",tlm);
        String itemInfo;
        for (Trade trade : tradeRequests) {
            itemInfo = DisplayUtil.getTradeDisplay(trade);
            sysInventoryList.addElement(itemInfo);
        }
    }

    private void displayInfoActionPerformed(ActionEvent e)  {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }

        Meeting meeting = mainFrame.getMainController().getTradeController().getMeetingController().getMeeting(tradeID);
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "pending", tlm); // confirms this is a valid requested trade
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
        }
        else {
            String meetingInfo;
            if (meeting.getNumEdits() == 0) {
                meetingLabel.setText(popups.getString("noMeetings.text"));
            } else {
                meetingInfo = DisplayUtil.getMeetingDisplay(meeting);
                meetingLabel.setText(meetingInfo);
            }
        }



    }

    private void suggestActionPerformed(ActionEvent e)  {

        MeetingController meetingController = mainFrame.getMainController().getTradeController().getMeetingController();
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, popups.getString("numberTradeID.text"));
            return;
        }
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(LoginPage.getCurrentUserName(), tradeID, "pending", tlm); // confirms this is a valid requested trade
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
        }
        else {

            Meeting meeting =trade.getMeeting();

            if (meeting.getNumEdits() > mainFrame.getMainController().getMeetingEditLimit()) {
                JOptionPane.showMessageDialog(mainFrame, popups.getString("editLimit.text"));
            }
            if (meeting.getLastEditorID() == LoginPage.getCurrentUserName()) {
                JOptionPane.showMessageDialog(mainFrame, popups.getString("partnerMeeting.text"));
                time.setValue("");
                location.setText("");
            } else {
                String date = time.getText();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, format);
                try {
                    meetingController.editMeeting(LoginPage.getCurrentUserName(), tradeID, meeting, location.getText(), dateTime);
                    JOptionPane.showMessageDialog(mainFrame, popups.getString("meetingSuggested.text"));
                    time.setValue("");
                    location.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        }


    }

    private void acceptActionPerformed(ActionEvent e)  {
        Integer tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a number. Trade ID must be a number");
            return;
        }

        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "pending", tlm); // confirms this is a valid requested trade
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("invalidTradeID.text"));
            return;
        }
        MeetingController meetingController = mainFrame.getMainController().getTradeController().getMeetingController();
        Meeting meeting = trade.getMeeting();
        if (meeting.getLastEditorID() == LoginPage.getCurrentUserName()) {
           JOptionPane.showMessageDialog(mainFrame, popups.getString("partnerMeeting.text"));
       }
         else if (meeting.getLastEditorID() == 0) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("suggestMeeting.text"));
        } else {
            mainFrame.getMainController().getTradeController().getMeetingController().acceptMeeting(trade.getTradeID(), meeting);
                if (mainFrame.getMainController().getTradeController().confirmTrade(trade)) {
                    JOptionPane.showMessageDialog(mainFrame, popups.getString("meetingConfirmed.text"));
                }

        }
    }






    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.ManagePendingTrades");
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        display = new JButton();
        tradeId = new JTextField();
        label2 = new JLabel();
        meetingLabel = new JLabel();
        accept = new JButton();
        label4 = new JLabel();
        displayInfo = new JButton();
        location = new JTextField();
        label5 = new JLabel();
        suggest = new JButton();
        back = new JButton();
        label6 = new JLabel();
        time = new JFormattedTextField();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setPreferredSize(new Dimension(750, 600));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder
        (0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing.border
        .TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt
        .Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void
        propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});

        //---- label1 ----
        label1.setText(bundle.getString("ManagePendingTrades.label1.text"));
        label1.setForeground(new Color(153, 255, 255));
        label1.setFont(new Font("Verdana", Font.PLAIN, 12));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setFont(new Font("Verdana", Font.PLAIN, 14));
            list1.setForeground(Color.white);
            list1.setBackground(Color.darkGray);
            list1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
            scrollPane1.setViewportView(list1);
        }

        //---- display ----
        display.setText(bundle.getString("ManagePendingTrades.display.text"));
        display.setFont(new Font("Verdana", Font.PLAIN, 14));
        display.setForeground(Color.white);
        display.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        display.setBackground(new Color(0, 51, 51));
        display.setOpaque(true);
        display.addActionListener(e -> displayActionPerformed(e));

        //---- tradeId ----
        tradeId.setFont(new Font("Verdana", Font.PLAIN, 14));
        tradeId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        tradeId.setBackground(new Color(12, 115, 115));
        tradeId.setForeground(Color.white);

        //---- label2 ----
        label2.setText(bundle.getString("ManagePendingTrades.label2.text"));
        label2.setForeground(Color.white);
        label2.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- meetingLabel ----
        meetingLabel.setText(bundle.getString("ManagePendingTrades.meetingLabel.text"));
        meetingLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        meetingLabel.setForeground(Color.white);

        //---- accept ----
        accept.setText(bundle.getString("ManagePendingTrades.accept.text"));
        accept.setFont(new Font("Verdana", Font.PLAIN, 14));
        accept.setBackground(new Color(0, 51, 51));
        accept.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        accept.setForeground(Color.white);
        accept.setOpaque(true);
        accept.setMultiClickThreshhold(1L);
        accept.addActionListener(e -> {
			acceptActionPerformed(e);
			acceptActionPerformed(e);
		});

        //---- label4 ----
        label4.setText(bundle.getString("ManagePendingTrades.label4.text"));
        label4.setForeground(Color.white);
        label4.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- displayInfo ----
        displayInfo.setText(bundle.getString("ManagePendingTrades.displayInfo.text"));
        displayInfo.setFont(new Font("Verdana", Font.PLAIN, 14));
        displayInfo.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        displayInfo.setBackground(new Color(0, 51, 51));
        displayInfo.setForeground(Color.white);
        displayInfo.setOpaque(true);
        displayInfo.addActionListener(e -> displayInfoActionPerformed(e));

        //---- location ----
        location.setFont(new Font("Verdana", Font.PLAIN, 14));
        location.setBackground(new Color(12, 115, 115));
        location.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        location.setForeground(Color.white);

        //---- label5 ----
        label5.setText(bundle.getString("ManagePendingTrades.label5.text"));
        label5.setFont(new Font("Verdana", Font.PLAIN, 14));
        label5.setForeground(Color.white);

        //---- suggest ----
        suggest.setText(bundle.getString("ManagePendingTrades.suggest.text"));
        suggest.setFont(new Font("Verdana", Font.PLAIN, 14));
        suggest.setBackground(new Color(0, 51, 51));
        suggest.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        suggest.setForeground(Color.white);
        suggest.setOpaque(true);
        suggest.addActionListener(e -> suggestActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("ManagePendingTrades.back.text"));
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.setBackground(new Color(12, 115, 115));
        back.setForeground(Color.white);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- label6 ----
        label6.setText(bundle.getString("ManagePendingTrades.label6.text"));
        label6.setForeground(Color.white);
        label6.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- time ----
        time.setFont(new Font("Verdana", Font.PLAIN, 14));
        time.setBackground(new Color(12, 115, 115));
        time.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        time.setForeground(Color.white);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup()
                                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(label6, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(time, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                                .addComponent(location, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                                        .addComponent(meetingLabel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(displayInfo, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                            .addComponent(accept, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGap(28, 28, 28))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                .addComponent(suggest, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)))
                    .addGroup(layout.createParallelGroup()
                        .addComponent(display, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))
                    .addGap(31, 31, 31))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 564, Short.MAX_VALUE)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(617, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addGap(9, 9, 9)
                    .addComponent(label2)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                            .addComponent(display, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(displayInfo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(meetingLabel)
                            .addGap(64, 64, 64)
                            .addComponent(accept, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(label6)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(37, 37, 37)
                            .addComponent(suggest, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(113, Short.MAX_VALUE))))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton display;
    private JTextField tradeId;
    private JLabel label2;
    private JLabel meetingLabel;
    private JButton accept;
    private JLabel label4;
    private JButton displayInfo;
    private JTextField location;
    private JLabel label5;
    private JButton suggest;
    private JButton back;
    private JLabel label6;
    private JFormattedTextField time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

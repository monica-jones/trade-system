/*
 * Created by JFormDesigner on Sun Aug 09 18:00:10 EDT 2020
 */

package gui.tradeUI;

import java.awt.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import trades.Meeting;
import trades.Trade;
import trades.TradeLogManager;

import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Namratha Anil
 */
public class ManageOpenTrades extends JPanel {
    private MainPage mainFrame;
    private String mode = "";
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();

    public ManageOpenTrades(String mode) {
    	this.mode = mode;
        initComponents();
        list1.setModel(sysInventoryList);
    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeMainMenu");
    }

    private void displayActionPerformed(ActionEvent e) {
        sysInventoryList.clear();
        UserAccount currentUser = mainFrame.getMainController().getCurrentUserAccount();
        List<Trade> approvedItems = mainFrame.getMainController().getTradeLogManager().getUserTradesByStatus(currentUser.getUserID(), "open");

        String itemInfo;
        for (Trade trade : approvedItems) {
            itemInfo = DisplayUtil.getTradeDisplay(trade);
            sysInventoryList.addElement(itemInfo);
            sysInventoryList.addElement("");

        }



    }

    private void completeActionPerformed(ActionEvent e) {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid Trade ID. Please Enter a number for trade ID.");
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);


        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid trade ID");
        } else {

            Meeting meeting = mainFrame.getMainController().getTradeController().getMeetingController().getMeeting(tradeID);
            if (!meeting.getStatus().equals("complete")) {
                if (!(meeting.getLastEditorID() == LoginPage.getCurrentUserName())) {

                    Boolean success = mainFrame.getMainController().getTradeController().getMeetingController().completeFirstMeeting(LoginPage.getCurrentUserName(), tradeID);

                    if (success == true) {
                        if (trade.getIsPermanent() == true){
                        JOptionPane.showMessageDialog(mainFrame
                                , "Meeting is set to complete");
                            mainFrame.getMainController().getTradeController().completeTrade(trade);}
                        else {
                            JOptionPane.showMessageDialog(mainFrame
                                    , "First Meeting is set to complete.\nYour return meeting is set in the same Location, 1 month from your meeting date ");

                        }

                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "meeting is marked as complete by you. wait for the other");

                    }
                }else {
                    JOptionPane.showMessageDialog(mainFrame, "Please wait for the other user to mark meeting as complete");
                }
            }
            else {
                JOptionPane.showMessageDialog(mainFrame, "Meeting is already complete");
            }
        }
    }




    private void displayMeetingActionPerformed(ActionEvent e) {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid Trade ID. Please Enter a number for trade ID.");
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid trade ID");
            return;
        }
        Meeting meeting =mainFrame.getMainController().getTradeController().getMeetingController().getMeeting(tradeID); //TODO: update this properly according to demo mode.
        String meetingInfo;
        meetingInfo = meeting.getLocation() + " | " + meeting.getTime() + " | " + meeting.getStatus() + " | " ;
       meetingDet.setText(meetingInfo);
    }

    private void incompleteActionPerformed(ActionEvent e)  {
        int tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(mainFrame, "Invalid Trade ID. Please Enter a number for trade ID.");
            return;
        }
        Integer userID = mainFrame.getMainController().getCurrentUserAccount().getUserID();
        TradeLogManager tlm = mainFrame.getMainController().getTradeLogManager();
        Trade trade = mainFrame.getMainController().getTradeController().getTradePresenter().getStatusTrade(userID, tradeID, "open", tlm);
        if (trade == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid trade ID");
            return;
        }
        mainFrame.getMainController().getTradeController() .getMeetingController().incompleteMeeting(trade);
        JOptionPane.showMessageDialog(mainFrame, "Meeting was set as Incomplete. Trade status is now set as incomplete");
        mainFrame.getMainController().getTradeController().incompleteTrade(trade);
    }

    private void returnStatusActionPerformed(ActionEvent e) {
        mainFrame.setView("ManageReturnMeeting");
    }

    private void button4ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }






    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.ManageOpenTrades");
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        button1 = new JButton();
        tradeId = new JTextField();
        label1 = new JLabel();
        meetingDet = new JLabel();
        displayMeeting = new JButton();
        complete = new JButton();
        incomplete = new JButton();
        button5 = new JButton();
        label3 = new JLabel();
        back = new JButton();
        display = new JButton();
        label2 = new JLabel();
        returnStatus = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(750, 600));
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new
        javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax
        . swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java
        . awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt
        . Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .
        PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .
        equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setBackground(Color.darkGray);
            list1.setForeground(Color.white);
            list1.setFont(new Font("Verdana", Font.PLAIN, 14));
            scrollPane1.setViewportView(list1);
        }

        //---- button1 ----
        button1.setText(bundle.getString("ManageOpenTrades.button1.text"));

        //---- tradeId ----
        tradeId.setForeground(Color.white);
        tradeId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        tradeId.setBackground(new Color(12, 115, 115));
        tradeId.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- label1 ----
        label1.setText(bundle.getString("ManageOpenTrades.label1.text"));
        label1.setFont(new Font("Verdana", Font.PLAIN, 16));
        label1.setForeground(Color.white);

        //---- meetingDet ----
        meetingDet.setText(bundle.getString("ManageOpenTrades.meetingDet.text"));
        meetingDet.setFont(new Font("Verdana", Font.PLAIN, 14));
        meetingDet.setForeground(Color.white);

        //---- displayMeeting ----
        displayMeeting.setText(bundle.getString("ManageOpenTrades.displayMeeting.text"));
        displayMeeting.setFont(new Font("Verdana", Font.PLAIN, 14));
        displayMeeting.setBackground(new Color(0, 51, 51));
        displayMeeting.setForeground(Color.white);
        displayMeeting.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        displayMeeting.setOpaque(true);
        displayMeeting.addActionListener(e -> displayMeetingActionPerformed(e));

        //---- complete ----
        complete.setText(bundle.getString("ManageOpenTrades.complete.text"));
        complete.setFont(new Font("Verdana", Font.PLAIN, 14));
        complete.setForeground(Color.white);
        complete.setBackground(new Color(0, 51, 51));
        complete.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        complete.setOpaque(true);
        complete.addActionListener(e -> completeActionPerformed(e));

        //---- incomplete ----
        incomplete.setText(bundle.getString("ManageOpenTrades.incomplete.text"));
        incomplete.setFont(new Font("Verdana", Font.PLAIN, 14));
        incomplete.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        incomplete.setBackground(new Color(0, 51, 51));
        incomplete.setForeground(Color.white);
        incomplete.setOpaque(true);
        incomplete.addActionListener(e -> button4ActionPerformed(e));

        //---- button5 ----
        button5.setText(bundle.getString("ManageOpenTrades.button5.text"));

        //---- label3 ----
        label3.setText(bundle.getString("ManageOpenTrades.label3.text"));
        label3.setFont(new Font("Verdana", Font.PLAIN, 12));
        label3.setForeground(new Color(153, 255, 255));

        //---- back ----
        back.setText(bundle.getString("ManageOpenTrades.back.text"));
        back.setFont(new Font("Verdana", Font.PLAIN, 14));
        back.setBackground(new Color(12, 115, 115));
        back.setForeground(Color.white);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- display ----
        display.setText(bundle.getString("ManageOpenTrades.display.text"));
        display.setFont(new Font("Verdana", Font.PLAIN, 14));
        display.setBackground(new Color(0, 51, 51));
        display.setForeground(Color.white);
        display.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        display.setOpaque(true);
        display.addActionListener(e -> displayActionPerformed(e));

        //---- label2 ----
        label2.setText("Agreed Meeting:");
        label2.setFont(new Font("Verdana", Font.PLAIN, 14));
        label2.setForeground(Color.white);

        //---- returnStatus ----
        returnStatus.setText("Manage return meeting status");
        returnStatus.setBackground(new Color(0, 51, 51));
        returnStatus.setFont(new Font("Verdana", Font.PLAIN, 12));
        returnStatus.setForeground(Color.white);
        returnStatus.setOpaque(true);
        returnStatus.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        returnStatus.addActionListener(e -> returnStatusActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label3)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(displayMeeting, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(incomplete, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup()
                                .addComponent(returnStatus, GroupLayout.PREFERRED_SIZE, 513, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(label1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(complete, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(label2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(meetingDet, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                                    .addComponent(display, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)))))
                    .addContainerGap(61, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(486, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                            .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label3)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addComponent(complete, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(incomplete, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addComponent(displayMeeting, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(label1)
                            .addGap(18, 18, 18)
                            .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addComponent(label2)
                    .addGap(18, 18, 18)
                    .addComponent(meetingDet)
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(display, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(returnStatus, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addGap(1027, 1027, 1027)
                    .addComponent(button1)
                    .addGap(27, 27, 27)
                    .addComponent(button5)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton button1;
    private JTextField tradeId;
    private JLabel label1;
    private JLabel meetingDet;
    private JButton displayMeeting;
    private JButton complete;
    private JButton incomplete;
    private JButton button5;
    private JLabel label3;
    private JButton back;
    private JButton display;
    private JLabel label2;
    private JButton returnStatus;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

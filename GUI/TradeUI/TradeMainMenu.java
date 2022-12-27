
package GUI.TradeUI;


import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import GUI.LoginUI.LoginPage;
import GUI.LoginUI.MainPage;
import GUI.util.DisplayUtil;
import controllers.MainController;
import items.Item;
import items.ItemInventory;

import java.awt.event.*;

import java.util.List;

import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class TradeMainMenu extends JPanel {
    private MainPage mainFrame;
    private Object Item;
    public MainController mainController;
    private DefaultListModel<String> sysInventoryListModel = new DefaultListModel<String>();
    private String mode="";

    public TradeMainMenu(String mode, MainPage mainFrame) {
    	this.mode = mode;
    	setMainFrame(mainFrame);
        initComponents();
        label8.setText("Total Funds: $" + this.mainFrame.getMainController().getUserMenuController().getUserFunds(LoginPage.getCurrentUserName()));
    }

    /**
     *
     * @param mainFrame
     */
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        this.mainController = mainFrame.getMainController();

    }



    private void button2ActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeManageMenu");
    }

    private void requestTradesActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeRequest");
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("UserMainMenu");
    }

    private void reqOneWayBActionPerformed(ActionEvent e) {
        mainFrame.setView("OneWayTrade");
    }

    private void reqTwoWayBActionPerformed(ActionEvent e) {
        mainFrame.setView("TwoWayTrade");
    }

    private void reqThreeWayBActionPerformed(ActionEvent e) {
        mainFrame.setView("ThreeWayTrade");
    }

    private void requestedActionPerformed(ActionEvent e) {
        mainFrame.setView("ManageRequestedTrades");
    }



    private void pendingActionPerformed(ActionEvent e) {
        mainFrame.setView("ManagePendingTrades");
    }


    private void openActionPerformed(ActionEvent e) {
       mainFrame.setView("ManageOpenTrades");
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        //Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        ResourceBundle bundle = ResourceBundle.getBundle("GUI.locales.TradeMainMenu");
        label1 = new JLabel();
        requested = new JButton();
        pending = new JButton();
        back = new JButton();
        reqTwoWayB = new JButton();
        label2 = new JLabel();
        reqOneWayB = new JButton();
        reqThreeWayB = new JButton();
        open = new JButton();
        label4 = new JLabel();
        separator1 = new JSeparator();
        label3 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
        EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing
        .border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))
        throw new RuntimeException();}});

        //---- label1 ----
        label1.setText(bundle.getString("TradeMainMenu.label1.text"));
        label1.setForeground(Color.white);
        label1.setFont(new Font("Verdana", Font.PLAIN, 20));

        //---- requested ----
        requested.setText(bundle.getString("TradeMainMenu.requested.text"));
        requested.setFont(new Font("Verdana", Font.BOLD, 14));
        requested.setBackground(new Color(0, 51, 51));
        requested.setOpaque(true);
        requested.setForeground(Color.white);
        requested.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), new Color(0, 204, 204), new Color(0, 204, 204), null));
        requested.addActionListener(e -> {
			button2ActionPerformed(e);
			button2ActionPerformed(e);
			requestedActionPerformed(e);
		});

        //---- pending ----
        pending.setText(bundle.getString("TradeMainMenu.pending.text"));
        pending.setFont(new Font("Verdana", Font.BOLD, 14));
        pending.setBackground(new Color(0, 51, 51));
        pending.setOpaque(true);
        pending.setForeground(Color.white);
        pending.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), new Color(0, 204, 204), new Color(0, 204, 204), null));
        pending.addActionListener(e -> pendingActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("TradeMainMenu.back.text"));
        back.setBackground(new Color(12, 115, 115));
        back.setForeground(Color.white);
        back.setOpaque(true);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- reqTwoWayB ----
        reqTwoWayB.setText(bundle.getString("TradeMainMenu.reqTwoWayB.text"));
        reqTwoWayB.setFont(new Font("Verdana", Font.BOLD, 14));
        reqTwoWayB.setBackground(new Color(0, 51, 51));
        reqTwoWayB.setOpaque(true);
        reqTwoWayB.setForeground(Color.white);
        reqTwoWayB.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), new Color(0, 204, 204), new Color(0, 204, 204), null));
        reqTwoWayB.addActionListener(e -> {
			requestTradesActionPerformed(e);
			reqTwoWayBActionPerformed(e);
		});

        //---- label2 ----
        label2.setText(bundle.getString("TradeMainMenu.label2.text"));
        label2.setFont(new Font("Verdana", Font.PLAIN, 12));
        label2.setForeground(Color.white);

        //---- reqOneWayB ----
        reqOneWayB.setText(bundle.getString("TradeMainMenu.reqOneWayB.text"));
        reqOneWayB.setFont(new Font("Verdana", Font.BOLD, 14));
        reqOneWayB.setDisplayedMnemonicIndex(Integer.parseInt(bundle.getString("TradeMainMenu.reqOneWayB.displayedMnemonicIndex")));
        reqOneWayB.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), Color.cyan, new Color(0, 204, 204), null));
        reqOneWayB.setBackground(new Color(0, 51, 51));
        reqOneWayB.setOpaque(true);
        reqOneWayB.setForeground(Color.white);
        reqOneWayB.addActionListener(e -> reqOneWayBActionPerformed(e));

        //---- reqThreeWayB ----
        reqThreeWayB.setText(bundle.getString("TradeMainMenu.reqThreeWayB.text"));
        reqThreeWayB.setFont(new Font("Verdana", Font.BOLD, 14));
        reqThreeWayB.setBackground(new Color(0, 51, 51));
        reqThreeWayB.setOpaque(true);
        reqThreeWayB.setForeground(Color.white);
        reqThreeWayB.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), new Color(0, 204, 204), new Color(0, 204, 204), null));
        reqThreeWayB.addActionListener(e -> reqThreeWayBActionPerformed(e));

        //---- open ----
        open.setText(bundle.getString("TradeMainMenu.open.text"));
        open.setFont(new Font("Verdana", Font.BOLD, 14));
        open.setBackground(new Color(0, 51, 51));
        open.setOpaque(true);
        open.setForeground(Color.white);
        open.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 204, 204), new Color(0, 204, 204), new Color(0, 204, 204), null));
        open.addActionListener(e -> openActionPerformed(e));

        //---- label4 ----
        label4.setText(bundle.getString("TradeMainMenu.label4.text"));
        label4.setForeground(Color.white);
        label4.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- label3 ----
        label3.setText(bundle.getString("TradeMainMenu.label3.text"));
        label3.setForeground(Color.white);
        label3.setFont(new Font("Verdana", Font.PLAIN, 16));

        //---- label5 ----
        label5.setText(bundle.getString("TradeMainMenu.label5.text"));
        label5.setForeground(Color.white);
        label5.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- label6 ----
        label6.setText(bundle.getString("TradeMainMenu.label6.text"));
        label6.setForeground(Color.white);
        label6.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- label7 ----
        label7.setText(bundle.getString("TradeMainMenu.label7.text"));
        label7.setForeground(Color.white);
        label7.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- label8 ----
        label8.setText(bundle.getString("TradeMainMenu.label8.text"));
        label8.setForeground(Color.white);
        label8.setFont(new Font("Verdana", Font.PLAIN, 14));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                            .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(label6))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)))
                            .addGap(133, 133, 133))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(reqThreeWayB, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reqTwoWayB, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reqOneWayB, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(requested, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(pending, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label7)))
                                .addComponent(open, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
                            .addGap(49, 49, 49))))
                .addGroup(layout.createSequentialGroup()
                    .addGap(276, 276, 276)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label8, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1))
                    .addContainerGap(181, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(612, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                    .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label8)
                    .addGap(57, 57, 57)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(label3))
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(label2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(requested, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addComponent(reqOneWayB, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                            .addGap(24, 24, 24)
                            .addComponent(label6)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(reqTwoWayB, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addComponent(pending, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(label7)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(reqThreeWayB, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addComponent(open, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                    .addGap(76, 76, 76)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addGap(19, 19, 19))
        );
        //End of component initialization  //GEN-END:initComponents
    }

    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JLabel label1;
    private JButton requested;
    private JButton pending;
    private JButton back;
    private JButton reqTwoWayB;
    private JLabel label2;
    private JButton reqOneWayB;
    private JButton reqThreeWayB;
    private JButton open;
    private JLabel label4;
    private JSeparator separator1;
    private JLabel label3;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    // End of variables declaration  //GEN-END:variables
}

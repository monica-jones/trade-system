
package gui.adminUI;

//import controllers.AdminUndoController;
import javax.swing.border.*;
import gui.loginUI.MainPage;
import controllers.AdminUndoable;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class AdminUndo extends JPanel {
    private MainPage mainFrame;

    /**
     * constructor
     */
    public AdminUndo() {
        initComponents();
    }

    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("AdminMainMenu");
    }

    private void undoLastTradeActionPerformed(ActionEvent e) {

        AdminUndoable adminUndoController = mainFrame.getMainController().getAdminUndoController();
        boolean deleted = adminUndoController.undoLastTrade(mainFrame.getMainController().getTradeLogManager());
        if (deleted)
            JOptionPane.showMessageDialog(mainFrame, "The the last trade has been deleted.");
        else
            JOptionPane.showMessageDialog(mainFrame, "Undo last trade unsuccessful.");
    }


    private void undoRecentTradesActionPerformed(ActionEvent e) {

        AdminUndoable adminUndoController = mainFrame.getMainController().getAdminUndoController();

        boolean deleted = adminUndoController.undoRecentTrades(mainFrame.getMainController().getTradeLogManager());
        if (deleted)
            JOptionPane.showMessageDialog(mainFrame, "The three most recent trades have been deleted.");
        else
            JOptionPane.showMessageDialog(mainFrame, "Undo recent trades unsuccessful.");
    }

    private void undoSpecifiedTradeActionPerformed(ActionEvent e) {
        AdminUndoable adminUndoController = mainFrame.getMainController().getAdminUndoController();
        Integer tradeID;
        try {
            tradeID = Integer.parseInt(tradeId.getText());
        }
        catch(NumberFormatException numEx) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid tradeID. Please enter a number.");
            return;
        }

        boolean deleted = adminUndoController.undoGivenTrade(tradeID, mainFrame.getMainController().getTradeLogManager());
        if (deleted)
            JOptionPane.showMessageDialog(mainFrame, "The specified trade has been deleted.");
        else
            JOptionPane.showMessageDialog(mainFrame, "Undo trade unsuccessful.");
    }

    private void deleteItemFromInventoryActionPerformed(ActionEvent e) {
        AdminUndoable adminUndoController = mainFrame.getMainController().getAdminUndoController();
        Integer itemID;
        try{
            itemID = Integer.parseInt(itemId.getText());
        }catch(NumberFormatException numEx) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid itemID. Please enter a number.");
            return;
        }

        boolean deleted = adminUndoController.removeItemFromInventory(itemID);
        if (deleted)
            JOptionPane.showMessageDialog(mainFrame, "The item has been deleted.");
        else
            JOptionPane.showMessageDialog(mainFrame, "Delete unsuccessful.");
    }

    private void deleteItemFromWishlistActionPerformed(ActionEvent e) {
        AdminUndoable adminUndoController = mainFrame.getMainController().getAdminUndoController();
        Integer itemID;
        Integer userID;

        try{
            itemID = Integer.parseInt(itemId.getText());
            userID = Integer.parseInt(userId.getText());
        }catch(NumberFormatException numEx) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid itemID/userID. Please enter a number.");
            return;
        }

         boolean deleted = adminUndoController.removeItemFromWishlist(itemID, userID);
        if (deleted)
            JOptionPane.showMessageDialog(mainFrame, "The item has been removed from the user's wishlist.");
        else
            JOptionPane.showMessageDialog(mainFrame, "Remove from wishlist unsuccessful.");
    }


    private void initComponents() {
        //Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.AdminUndo");
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        back = new JButton();
        itemId = new JTextField();
        label3 = new JLabel();
        tradeId = new JTextField();
        label4 = new JLabel();
        userId = new JTextField();
        label2 = new JLabel();

        //======== this ========
        setBackground(new Color(0, 102, 102));
        setFont(new Font("Verdana", Font.BOLD, 18));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
        ( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border
        . TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
        propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );

        //---- label1 ----
        label1.setText(bundle.getString("AdminUndo.label1.text"));
        label1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 35));
        label1.setForeground(Color.white);

        //---- button1 ----
        button1.setText(bundle.getString("AdminUndo.button1.text"));
        button1.setForeground(Color.white);
        button1.setFont(new Font("Verdana", Font.BOLD, 14));
        button1.setBackground(new Color(0, 51, 51));
        button1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button1.addActionListener(e -> undoLastTradeActionPerformed(e));

        //---- button2 ----
        button2.setText(bundle.getString("AdminUndo.button2.text"));
        button2.setForeground(Color.white);
        button2.setFont(new Font("Verdana", Font.BOLD, 14));
        button2.setBackground(new Color(0, 51, 51));
        button2.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button2.addActionListener(e -> undoRecentTradesActionPerformed(e));

        //---- button3 ----
        button3.setText(bundle.getString("AdminUndo.button3.text"));
        button3.setForeground(Color.white);
        button3.setFont(new Font("Verdana", Font.BOLD, 14));
        button3.setBackground(new Color(0, 51, 51));
        button3.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button3.addActionListener(e -> undoSpecifiedTradeActionPerformed(e));

        //---- button4 ----
        button4.setText(bundle.getString("AdminUndo.button4.text"));
        button4.setForeground(Color.white);
        button4.setFont(new Font("Verdana", Font.BOLD, 14));
        button4.setBackground(new Color(0, 51, 51));
        button4.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button4.addActionListener(e -> deleteItemFromInventoryActionPerformed(e));

        //---- button5 ----
        button5.setText(bundle.getString("AdminUndo.button5.text"));
        button5.setForeground(Color.white);
        button5.setFont(new Font("Verdana", Font.BOLD, 14));
        button5.setBackground(new Color(0, 51, 51));
        button5.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        button5.addActionListener(e -> deleteItemFromWishlistActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("AdminUndo.back.text"));
        back.setForeground(Color.white);
        back.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
        back.setBackground(Color.darkGray);
        back.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- itemId ----
        itemId.setFont(new Font("Verdana", Font.PLAIN, 14));
        itemId.setForeground(Color.white);
        itemId.setBackground(new Color(12, 115, 115));
        itemId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- label3 ----
        label3.setText(bundle.getString("AdminUndo.label3.text"));
        label3.setForeground(Color.white);
        label3.setBackground(new Color(12, 115, 115));
        label3.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- tradeId ----
        tradeId.setForeground(Color.white);
        tradeId.setBackground(new Color(12, 115, 115));
        tradeId.setFont(new Font("Verdana", Font.PLAIN, 14));
        tradeId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- label4 ----
        label4.setText(bundle.getString("AdminUndo.label4.text"));
        label4.setBackground(new Color(12, 115, 115));
        label4.setFont(new Font("Verdana", Font.PLAIN, 14));
        label4.setForeground(Color.white);

        //---- userId ----
        userId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        userId.setForeground(Color.white);
        userId.setFont(new Font("Verdana", Font.PLAIN, 14));
        userId.setBackground(new Color(12, 115, 115));

        //---- label2 ----
        label2.setText(bundle.getString("AdminUndo.label2.text"));
        label2.setForeground(Color.white);
        label2.setFont(new Font("Verdana", Font.PLAIN, 14));
        label2.setBackground(new Color(12, 115, 115));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(39, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(itemId, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                                .addComponent(tradeId, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(userId, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)))
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(button5, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(59, 59, 59))
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(button2, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                            .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGap(179, 179, 179))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(67, 67, 67)
                    .addComponent(label1)
                    .addGap(48, 48, 48)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tradeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(itemId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3)
                                .addComponent(button4, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(button5, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(userId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                    .addComponent(back)
                    .addGap(29, 29, 29))
        );
        // End of component initialization  //GEN-END:initComponents
    }

    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Josh Fox
	private JLabel label1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton back;
	private JTextField itemId;
	private JLabel label3;
	private JTextField tradeId;
	private JLabel label4;
	private JTextField userId;
	private JLabel label2;
    // End of variables declaration  //GEN-END:variables
}

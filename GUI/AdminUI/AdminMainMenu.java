
package GUI.AdminUI;

import java.awt.*;

import GUI.LoginUI.MainPage;
import controllers.AdminController;
import controllers.MainController;
import controllers.LoginSignUpController;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class AdminMainMenu extends JPanel {
    private MainPage mainFrame;
    private MainController mainController;

    /**
     * constructor
     */
    public AdminMainMenu() {
        initComponents();
    }

    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame) {
        this.mainFrame = mainFrame;
        this.mainController = mainFrame.getMainController();
    }


    private void manageUserActionPerformed(ActionEvent e) {
        mainFrame.setView("AdminApprovals");
    }

    private void undoActionPerformed(ActionEvent e) {
        mainFrame.setView("AdminUndo");
    }

    private void displayLimitsActionPerformed(ActionEvent e) {
        String limits = mainFrame.getMainController().getAdminController().displayLimits();
        viewLimits.setText(limits);
    }

    private void resetLimitBActionPerformed(ActionEvent e) {

        AdminController adminController = this.mainController.getAdminController();
        if (adminController.changeAllLimits(meetingB.getText(), overBorrowB.getText(), tradeB.getText(),
                incompleteB.getText())) {
            JOptionPane.showMessageDialog(mainFrame, "Limits Changed Successfully"
                    , "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Limit change unsuccessful");
        }
    }

    private void logoutActionPerformed(ActionEvent e) {
        mainFrame.setView("StartUpPage");
    }

    private void createNewAdminBActionPerformed(ActionEvent e) {
        LoginSignUpController loginSignUpController = new LoginSignUpController();
        String password = textField1.getText();
        int id = loginSignUpController.signUpAdmin(password);
        JOptionPane.showMessageDialog(mainFrame, "New Admin has been added successfully with id: " + id);
    }

    private void resetWeeklyTradeCountActionPerformed(ActionEvent e) {
        this.mainController.getAdminController().resetWeeklyTrades();
    }

    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        ResourceBundle bundle = ResourceBundle.getBundle("GUI.locales.AdminMainMenu");
        label1 = new JLabel();
        manageUser = new JButton();
        resetLimitB = new JButton();
        button6 = new JButton();
        undo = new JButton();
        logout = new JButton();
        label2 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        meetingB = new JTextField();
        overBorrowB = new JTextField();
        tradeB = new JTextField();
        incompleteB = new JTextField();
        scrollPane1 = new JScrollPane();
        viewLimits = new JTextPane();
        viewLimitsButton = new JButton();
        button1 = new JButton();

        //======== this ========
        setBackground(new Color(0, 102, 102));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
        border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER
        ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font
        . BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r"
        .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

        //---- label1 ----
        label1.setText(bundle.getString("AdminMainMenu.label1.text"));
        label1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 30));
        label1.setForeground(Color.white);

        //---- manageUser ----
        manageUser.setText(bundle.getString("AdminMainMenu.manageUser.text"));
        manageUser.setFont(new Font("Verdana", Font.PLAIN, 16));
        manageUser.setBackground(Color.darkGray);
        manageUser.setForeground(Color.white);
        manageUser.addActionListener(e -> manageUserActionPerformed(e));

        //---- resetLimitB ----
        resetLimitB.setText(bundle.getString("AdminMainMenu.resetLimitB.text"));
        resetLimitB.setFont(new Font("Verdana", Font.PLAIN, 16));
        resetLimitB.setBackground(Color.darkGray);
        resetLimitB.setForeground(Color.white);
        resetLimitB.addActionListener(e -> resetLimitBActionPerformed(e));

        //---- button6 ----
        button6.setText(bundle.getString("AdminMainMenu.button6.text"));
        button6.setFont(new Font("Verdana", Font.PLAIN, 16));
        button6.setBackground(Color.darkGray);
        button6.setForeground(Color.white);
        button6.addActionListener(e -> createNewAdminBActionPerformed(e));

        //---- undo ----
        undo.setText(bundle.getString("AdminMainMenu.undo.text"));
        undo.setFont(new Font("Verdana", Font.PLAIN, 16));
        undo.setBackground(Color.darkGray);
        undo.setForeground(Color.white);
        undo.addActionListener(e -> undoActionPerformed(e));

        //---- logout ----
        logout.setText(bundle.getString("AdminMainMenu.logout.text"));
        logout.setFont(new Font("Verdana", Font.PLAIN, 16));
        logout.setBackground(Color.darkGray);
        logout.setForeground(Color.white);
        logout.addActionListener(e -> logoutActionPerformed(e));

        //---- textField1 ----
        textField1.setBackground(Color.darkGray);
        textField1.setForeground(Color.white);
        textField1.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));

        //---- label3 ----
        label3.setText(bundle.getString("AdminMainMenu.label3.text"));
        label3.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
        label3.setForeground(Color.white);

        //---- label4 ----
        label4.setText(bundle.getString("AdminMainMenu.label4.text"));
        label4.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 12));
        label4.setForeground(Color.white);

        //---- label5 ----
        label5.setText(bundle.getString("AdminMainMenu.label5.text"));
        label5.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 12));
        label5.setForeground(Color.white);

        //---- label6 ----
        label6.setText(bundle.getString("AdminMainMenu.label6.text"));
        label6.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 12));
        label6.setForeground(Color.white);

        //---- label7 ----
        label7.setText(bundle.getString("AdminMainMenu.label7.text"));
        label7.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 12));
        label7.setForeground(Color.white);

        //---- meetingB ----
        meetingB.setBackground(Color.darkGray);
        meetingB.setForeground(Color.white);
        meetingB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 12));

        //---- overBorrowB ----
        overBorrowB.setBackground(Color.darkGray);
        overBorrowB.setForeground(Color.white);
        overBorrowB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 12));

        //---- tradeB ----
        tradeB.setBackground(Color.darkGray);
        tradeB.setForeground(Color.white);
        tradeB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 12));

        //---- incompleteB ----
        incompleteB.setBackground(Color.darkGray);
        incompleteB.setForeground(Color.white);
        incompleteB.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 12));

        //======== scrollPane1 ========
        {

            //---- viewLimits ----
            viewLimits.setBackground(Color.darkGray);
            viewLimits.setForeground(Color.white);
            viewLimits.setFont(new Font("Verdana", Font.PLAIN, 14));
            scrollPane1.setViewportView(viewLimits);
        }

        //---- viewLimitsButton ----
        viewLimitsButton.setText(bundle.getString("AdminMainMenu.viewLimitsButton.text"));
        viewLimitsButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        viewLimitsButton.setBackground(Color.darkGray);
        viewLimitsButton.setForeground(Color.white);
        viewLimitsButton.addActionListener(e -> displayLimitsActionPerformed(e));

        //---- button1 ----
        button1.setText(bundle.getString("AdminMainMenu.button1.text"));
        button1.setFont(new Font("Verdana", Font.PLAIN, 16));
        button1.setBackground(Color.darkGray);
        button1.setForeground(Color.white);
        button1.addActionListener(e -> resetWeeklyTradeCountActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(46, 46, 46)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label5, GroupLayout.Alignment.TRAILING)
                                        .addComponent(label6, GroupLayout.Alignment.TRAILING)))
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(85, 477, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(label7, GroupLayout.Alignment.TRAILING)
                                        .addComponent(label4, GroupLayout.Alignment.TRAILING))))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(meetingB, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addComponent(overBorrowB, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addComponent(tradeB, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addComponent(incompleteB, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(46, Short.MAX_VALUE)
                            .addComponent(viewLimitsButton, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(resetLimitB, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
                                .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGap(65, 65, 65))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 218, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label1)
                                .addComponent(manageUser, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undo, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
                            .addGap(234, 234, 234))))
                .addGroup(layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label3)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 429, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(button6, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                    .addComponent(logout, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                    .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label2)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label1)
                            .addGap(18, 18, 18)
                            .addComponent(undo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(manageUser, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
                    .addGap(50, 50, 50)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(meetingB, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(overBorrowB, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                            .addGap(8, 8, 8)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label6)
                                .addComponent(tradeB, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                            .addGap(8, 8, 8)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label7)
                                .addComponent(incompleteB, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(viewLimitsButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(resetLimitB, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(label3)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button6, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(44, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                            .addComponent(logout, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addGap(14, 14, 14))))
        );
        // End of component initialization  //GEN-END:initComponents
    }


    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JLabel label1;
    private JButton manageUser;
    private JButton resetLimitB;
    private JButton button6;
    private JButton undo;
    private JButton logout;
    private JLabel label2;
    private JTextField textField1;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JTextField meetingB;
    private JTextField overBorrowB;
    private JTextField tradeB;
    private JTextField incompleteB;
    private JScrollPane scrollPane1;
    private JTextPane viewLimits;
    private JButton viewLimitsButton;
    private JButton button1;
    // End of variables declaration  //GEN-END:variables
}

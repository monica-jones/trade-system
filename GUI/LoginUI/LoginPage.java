

package gui.loginUI;

import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import controllers.LoginSignUpController;

import java.awt.event.*;
import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class LoginPage extends JPanel {
    private MainPage mainFrame;

    private static Integer currentUserName;
    //    private boolean demoMode = false;
    private String mode = "";


    /**
     * Constructor
     * @param mode String
     */
    public LoginPage(String mode) {
        this.mode = mode;
        initComponents();
    }

    /**
     * gets the current user's user id
     * @return the current user's user id
     */
    public static Integer getCurrentUserName() {
        return currentUserName;
    }

    /**
     * resets the current user to 0
     */
    public static void resetCurrentUserName() {
        currentUserName = 0;
    }


    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void userLoginActionPerformed(ActionEvent e) {


        LoginSignUpController loginSignUpController = new LoginSignUpController();
        int usernameValue;
        try {
            usernameValue = Integer.parseInt(username.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("usernameInvalid.text"));
            return;
        }

        boolean validUser = loginSignUpController.logIn(usernameValue, String.valueOf(password.getPassword()));
        if (validUser) {
            currentUserName = usernameValue;
            mainFrame.getMainController().initAfterLogin("", false);
            mainFrame.setView("UserMainMenu");
        } else {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("adminInvalid.text"));

        }
    }


    private void guestActionPerformed(ActionEvent e) {
        LoginSignUpController loginSignUpController = new LoginSignUpController();
        int usernameValue;
        try {
            usernameValue = Integer.parseInt(username.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("usernameInvalid.text"));
            return;
        }

        boolean validUser = loginSignUpController.demoAccountLogIn(usernameValue, String.valueOf(password.getPassword()));
        if (validUser) {
            currentUserName = usernameValue;
            mainFrame.getMainController().initAfterLogin("_demo", false);
            mainFrame.setMode("_demo");
            mainFrame.setView("UserMainMenu");
        } else {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("loginInvalid.text"));

        }

    }

    private void adminLoginBActionPerformed(ActionEvent e) {
        LoginSignUpController loginSignUpController = new LoginSignUpController();
        int usernameValue;
        try {
            usernameValue = Integer.parseInt(username.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("usernameInvalid.text"));
            return;
        }

        boolean validUser = loginSignUpController.isAdmin(usernameValue, String.valueOf(password.getPassword()));
        if (validUser) {
            mainFrame.setView("AdminMainMenu");
            mainFrame.getMainController().initAfterLogin(mode, true);
        } else {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("userInvalid.text"));


        }

    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("StartUpPage");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.LoginPage");
        username = new JTextField();
        label2 = new JLabel();
        userLogin = new JButton();
        label3 = new JLabel();
        label1 = new JLabel();
        label4 = new JLabel();
        password = new JPasswordField();
        adminLoginB = new JButton();
        guest = new JButton();
        back = new JButton();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setRequestFocusEnabled(false);
        setPreferredSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (
        new javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion"
        , javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
        , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 )
        , java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (
        new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );

        //---- username ----
        username.setBorder(new MatteBorder(0, 0, 3, 0, Color.white));
        username.setOpaque(true);
        username.setBackground(new Color(12, 115, 115));
        username.setFont(new Font("Menlo", Font.PLAIN, 18));
        username.setForeground(Color.white);

        //---- label2 ----
        label2.setText(bundle.getString("LoginPage.label2.text"));
        label2.setFont(new Font("Verdana", Font.PLAIN, 18));
        label2.setForeground(Color.white);
        label2.setBorder(null);

        //---- userLogin ----
        userLogin.setText(bundle.getString("LoginPage.userLogin.text"));
        userLogin.setFont(new Font("Verdana", Font.BOLD, 16));
        userLogin.setForeground(Color.white);
        userLogin.setIconTextGap(18);
        userLogin.setBackground(new Color(0, 51, 51));
        userLogin.setOpaque(true);
        userLogin.setBorder(new MatteBorder(2, 1, 2, 1, new Color(0, 204, 204)));
        userLogin.addActionListener(e -> userLoginActionPerformed(e));

        //---- label3 ----
        label3.setText(bundle.getString("LoginPage.label3.text"));
        label3.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 28));
        label3.setBackground(Color.white);
        label3.setForeground(Color.white);
        label3.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 204, 204)), bundle.getString("LoginPage.label3.border"), TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
            new Font("Tw Cen MT Condensed", Font.PLAIN, 12), new Color(204, 204, 204)));

        //---- label1 ----
        label1.setText(bundle.getString("LoginPage.label1.text"));
        label1.setFont(new Font("Verdana", Font.PLAIN, 18));
        label1.setForeground(Color.white);
        label1.setBorder(null);
        label1.setBackground(new Color(102, 102, 102));

        //---- label4 ----
        label4.setText(bundle.getString("LoginPage.label4.text"));
        label4.setFont(new Font("Roboto Light", Font.PLAIN, 16));
        label4.setForeground(Color.white);

        //---- password ----
        password.setBorder(new MatteBorder(0, 0, 3, 0, Color.white));
        password.setOpaque(true);
        password.setBackground(new Color(12, 115, 115));
        password.setForeground(Color.white);
        password.setFont(new Font("Lucida Grande", Font.PLAIN, 18));

        //---- adminLoginB ----
        adminLoginB.setText(bundle.getString("LoginPage.adminLoginB.text"));
        adminLoginB.setBorder(new MatteBorder(2, 2, 2, 2, new Color(0, 204, 204)));
        adminLoginB.setBackground(new Color(0, 51, 51));
        adminLoginB.setOpaque(true);
        adminLoginB.setFont(new Font("Verdana", Font.BOLD, 16));
        adminLoginB.setForeground(Color.white);
        adminLoginB.addActionListener(e -> adminLoginBActionPerformed(e));

        //---- guest ----
        guest.setText(bundle.getString("LoginPage.guest.text"));
        guest.setForeground(Color.white);
        guest.setBackground(new Color(0, 51, 51));
        guest.setOpaque(true);
        guest.setBorder(new MatteBorder(2, 2, 2, 2, new Color(0, 204, 204)));
        guest.setFont(new Font("Verdana", Font.BOLD, 14));
        guest.addActionListener(e -> guestActionPerformed(e));

        //---- back ----
        back.setText(bundle.getString("LoginPage.back.text"));
        back.setForeground(Color.white);
        back.setFont(new Font("Verdana", Font.PLAIN, 14));
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.setBackground(new Color(0, 102, 102));
        back.setOpaque(true);
        back.addActionListener(e -> backActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(146, 146, 146)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label3, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                        .addComponent(password)
                        .addComponent(username, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label2)
                                .addComponent(label1))
                            .addGap(320, 328, Short.MAX_VALUE)))
                    .addContainerGap(191, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 611, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                    .addGap(19, 19, 19))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(221, 221, 221)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(adminLoginB, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                                .addComponent(userLogin, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(211, 211, 211)
                            .addComponent(label4)))
                    .addContainerGap(222, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(267, Short.MAX_VALUE)
                    .addComponent(guest, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                    .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(24, 24, 24)
                    .addComponent(label2)
                    .addGap(18, 18, 18)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(userLogin, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(adminLoginB, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label4)
                    .addGap(18, 18, 18)
                    .addComponent(guest, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(back)
                    .addGap(16, 16, 16))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JTextField username;
    private JLabel label2;
    private JButton userLogin;
    private JLabel label3;
    private JLabel label1;
    private JLabel label4;
    private JPasswordField password;
    private JButton adminLoginB;
    private JButton guest;
    private JButton back;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //VARIABLES DECLARATION FOR POPUPS
    ResourceBundle popups = ResourceBundle.getBundle("gui.locales.LoginPage");

}

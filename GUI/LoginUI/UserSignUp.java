

package gui.loginUI;

import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import accounts.UserManager;
import controllers.LoginSignUpController;
import controllers.UserManagerBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * @author Namratha Anil
 */
public class UserSignUp extends JPanel {
    private MainPage mainFrame;
//    private boolean demoMode = false;
    private String mode = "";


    /**
     * constructor
     * @param mode string
     */
    public UserSignUp(String mode) {
    	this.mode = mode;
//    	this.demoMode = demoMode;
        initComponents();
        setupCities();
    }

    private void setupCities() {
        selectCity.addItem("Toronto");
        selectCity.addItem("Montreal");
        selectCity.addItem("Chicago");
        selectCity.addItem("Vancouver");
        selectCity.addItem("New York");
    }



    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
    }



    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("StartUpPage");

    }


    private void guestActionPerformed(ActionEvent e) {
    	String city = (String) selectCity.getSelectedItem();
        LoginSignUpController loginSignUpController = new LoginSignUpController();
        UserManagerBuilder userManagerBuilder = new UserManagerBuilder("_demo");
        UserManager  userManager = null;
        try {
            userManager = userManagerBuilder.createUserManager();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Integer userId = loginSignUpController.signUpDemoUser(password.getText(),name.getText(),city,userManager);
        JOptionPane.showMessageDialog(mainFrame,popups.getString("demoInserted.text") + userId);

    }


    /**
     * Creates a new UserAccount on action
     */

    private void signUpActionPerformed(ActionEvent e) {
        String city = (String) selectCity.getSelectedItem();
        LoginSignUpController loginSignUpController = new LoginSignUpController();
        UserManagerBuilder userManagerBuilder = new UserManagerBuilder(this.mode);
        UserManager userManager = null;
        try {
            userManager = userManagerBuilder.createUserManager();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Integer userId = loginSignUpController.signUpUser(password.getText(),name.getText(),city,userManager);

        userInserted = popups.getString("userInserted.text");

        JOptionPane.showMessageDialog(mainFrame,userInserted + userId);
    }

    private void selectCityActionPerformed(ActionEvent e) {

    }

    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.UserSignUp");
        label1 = new JLabel();
        selectCity = new JComboBox();
        label3 = new JLabel();
        name = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        password = new JTextField();
        back = new JButton();
        label2 = new JLabel();
        guest = new JButton();
        signUp = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(750, 600));
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing
        . border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder
        . CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .
        awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder( )) )
        ;  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} )
        ;

        //---- label1 ----
        label1.setText(bundle.getString("UserSignUp.label1.text"));
        label1.setFont(new Font("Verdana", Font.PLAIN, 16));
        label1.setForeground(Color.white);

        //---- selectCity ----
        selectCity.setBackground(new Color(12, 115, 115));
        selectCity.setOpaque(true);
        selectCity.setFont(new Font("Verdana", Font.PLAIN, 14));
        selectCity.setForeground(new Color(0, 51, 51));
        selectCity.addActionListener(e -> selectCityActionPerformed(e));

        //---- label3 ----
        label3.setText(bundle.getString("UserSignUp.label3.text"));
        label3.setFont(new Font("Verdana", Font.PLAIN, 16));
        label3.setForeground(Color.white);

        //---- name ----
        name.setOpaque(true);
        name.setBackground(new Color(12, 115, 115));
        name.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        name.setForeground(Color.white);
        name.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- label4 ----
        label4.setText(bundle.getString("UserSignUp.label4.text"));
        label4.setFont(new Font("Verdana", Font.PLAIN, 14));
        label4.setForeground(new Color(153, 255, 255));

        //---- label5 ----
        label5.setText(bundle.getString("UserSignUp.label5.text"));
        label5.setFont(new Font("Verdana", Font.PLAIN, 16));
        label5.setForeground(Color.white);

        //---- password ----
        password.setBackground(new Color(12, 115, 115));
        password.setOpaque(true);
        password.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        password.setForeground(Color.white);
        password.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- back ----
        back.setText(bundle.getString("UserSignUp.back.text"));
        back.setFont(new Font("Verdana", Font.PLAIN, 12));
        back.setForeground(Color.white);
        back.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        back.setBackground(new Color(0, 102, 102));
        back.setOpaque(true);
        back.addActionListener(e -> backActionPerformed(e));

        //---- label2 ----
        label2.setText(bundle.getString("UserSignUp.label2.text"));
        label2.setForeground(new Color(204, 255, 255));
        label2.setFont(new Font("Verdana", Font.BOLD, 12));

        //---- guest ----
        guest.setText(bundle.getString("UserSignUp.guest.text"));
        guest.setBackground(new Color(0, 51, 51));
        guest.setFont(new Font("Verdana", Font.PLAIN, 16));
        guest.setForeground(Color.white);
        guest.setOpaque(true);
        guest.setBorder(new MatteBorder(1, 2, 2, 1, Color.white));
        guest.addActionListener(e -> button2ActionPerformed(e));

        //---- signUp ----
        signUp.setText(bundle.getString("UserSignUp.signUp.text"));
        signUp.setBackground(new Color(0, 51, 51));
        signUp.setFont(new Font("Verdana", Font.PLAIN, 18));
        signUp.setForeground(Color.white);
        signUp.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        signUp.setOpaque(true);
        signUp.addActionListener(e -> signUpActionPerformed(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(613, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(label4)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(back, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15))))
                .addGroup(layout.createSequentialGroup()
                    .addGap(84, 84, 84)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 611, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(label3)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                            .addComponent(password, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                            .addComponent(selectCity, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                            .addComponent(name, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(guest, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                            .addComponent(signUp, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)))
                    .addGap(0, 53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label4)
                    .addGap(28, 28, 28)
                    .addComponent(label1)
                    .addGap(18, 18, 18)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(53, 53, 53)
                    .addComponent(label3)
                    .addGap(27, 27, 27)
                    .addComponent(selectCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(44, 44, 44)
                    .addComponent(label5)
                    .addGap(32, 32, 32)
                    .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(42, 42, 42)
                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(signUp, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(guest, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JComboBox selectCity;
    private JLabel label3;
    private JTextField name;
    private JLabel label4;
    private JLabel label5;
    private JTextField password;
    private JButton back;
    private JLabel label2;
    private JButton guest;
    private JButton signUp;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    // VARIABLES DECLARATION FOR POPUPS DO NOT MODIFY
    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.UserSignUp");
    private String userInserted;

}

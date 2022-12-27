

package gui.loginUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.border.*;




/**
 * @author Namratha Anil
 */
public class StartUpPage extends JPanel {
    private MainPage mainFrame;

    public StartUpPage() {
        initComponents();

    }

    /**
     * sets the main frame
     * @param mainFrame the MainPage
     */
    public void setMainFrame(MainPage mainFrame) {
        this.mainFrame = mainFrame;

    }

    /**
     * button action displays JPanel in class UserSignUp
     */

    private void signUpActionPerformed(ActionEvent e) {
        mainFrame.setView("UserSignUp");
    }

    /**
     * button action displays JPanel in class LoginPage to JFrame Container
     */

    private void loginActionPerformed(ActionEvent e) {
        mainFrame.setView("LoginPage");
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void exitActionPerformed(ActionEvent e) {
    //
        System.exit(0);
    }



    /**
     * initializing StartUp Frame and Panel components to JFrame Container
     */


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.StartUpPage");
        label1 = new JLabel();
        label2 = new JLabel();
        login = new JButton();
        signUp = new JButton();
        exit = new JButton();
        label5 = new JLabel();
        label3 = new JLabel();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setPreferredSize(new Dimension(750, 600));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
        new javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn"
        ,javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM
        ,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12)
        ,java.awt.Color.red), getBorder())); addPropertyChangeListener(
        new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});

        //---- label1 ----
        label1.setText(bundle.getString("StartUpPage.label1.text"));
        label1.setFont(new Font("Arial", Font.BOLD, 30));
        label1.setForeground(Color.white);
        label1.setBorder(new TitledBorder(new MatteBorder(3, 4, 3, 4, new Color(0, 153, 153)), bundle.getString("StartUpPage.label1.border"), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, null, new Color(204, 255, 255)));

        //---- login ----
        login.setText(bundle.getString("StartUpPage.login.text"));
        login.setFont(new Font("Verdana", Font.BOLD, 18));
        login.setForeground(Color.white);
        login.setBackground(new Color(0, 51, 51));
        login.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        login.setOpaque(true);
        login.addActionListener(e -> {
			button1ActionPerformed(e);
			loginActionPerformed(e);
		});

        //---- signUp ----
        signUp.setText(bundle.getString("StartUpPage.signUp.text"));
        signUp.setFont(new Font("Verdana", Font.BOLD, 17));
        signUp.setForeground(Color.white);
        signUp.setBackground(new Color(0, 51, 51));
        signUp.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        signUp.setOpaque(true);
        signUp.addActionListener(e -> signUpActionPerformed(e));

        //---- exit ----
        exit.setText(bundle.getString("StartUpPage.exit.text"));
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setForeground(Color.white);
        exit.setBackground(new Color(0, 102, 102));
        exit.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        exit.setOpaque(true);
        exit.addActionListener(e -> exitActionPerformed(e));

        //---- label5 ----
        label5.setText(bundle.getString("StartUpPage.label5.text"));
        label5.setFont(new Font("Verdana", Font.PLAIN, 14));
        label5.setForeground(new Color(204, 255, 255));
        label5.setBackground(new Color(204, 255, 255));

        //---- label3 ----
        label3.setText(bundle.getString("StartUpPage.label3.text"));
        label3.setForeground(new Color(204, 255, 255));
        label3.setFont(new Font("Verdana", Font.PLAIN, 14));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(614, 614, 614)
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(244, 244, 244)
                            .addComponent(exit, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(173, 173, 173)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(label5)
                                .addComponent(signUp, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                                .addComponent(login, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                                .addComponent(label3)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                    .addGap(72, 72, 72)
                    .addComponent(label3)
                    .addGap(18, 18, 18)
                    .addComponent(label2)
                    .addGap(4, 4, 4)
                    .addComponent(login, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addGap(77, 77, 77)
                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(signUp, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                    .addComponent(exit)
                    .addGap(30, 30, 30))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JLabel label1;
    private JLabel label2;
    private JButton login;
    private JButton signUp;
    private JButton exit;
    private JLabel label5;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    }


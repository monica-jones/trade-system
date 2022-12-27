
package gui.userUI;


import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import gui.loginUI.LoginPage;
import gui.loginUI.MainPage;
import gui.util.DisplayUtil;
import accounts.UserAccount;
import accounts.UserManager;
import controllers.MainController;

import controllers.UserManagerBuilder;
import controllers.UserMenuController;
import items.Item;
import items.ItemInventory;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

/**
 * @author Namratha Anil
 */
public class UserInventoryMenu extends JPanel {
    private DefaultListModel<String> listModel;
    private MainPage mainFrame;
    public UserManagerBuilder userManagerBuilder;
    public MainController mainController;
    private String mode = "";
	




    public UserInventoryMenu(String mode) {
    	this.mode = mode;
        initComponents();
        listModel= new DefaultListModel<String>();
        this.list1.setModel(listModel);

    }

    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        this.mainController = mainFrame.getMainController();
        this.userManagerBuilder = new UserManagerBuilder(mainController.getMode());
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("UserAccountMenu");
    }



    private void userInventoryActionPerformed(ActionEvent e) {
        listModel.clear();
        List<Item> inventoryList = null;
        try {
            inventoryList = userManagerBuilder.getInventory(LoginPage.getCurrentUserName());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Error getting user inventory list.");
            return;
        }
        String itemInfo;
       for(Item item: inventoryList){
           itemInfo = DisplayUtil.getItemDisplay(item);
           this.listModel.addElement(itemInfo);
       }

    }

    private void addItemActionPerformed(ActionEvent e)  {
        UserManager userManager = this.mainController.getUserManager();
        UserAccount currentUser = userManager.getUser(LoginPage.getCurrentUserName());
        currentUser.setMode(this.mode);
        UserMenuController userMenuController = new UserMenuController(currentUser, this.mode);
        Double price1;
        try {
             price1 = Double.valueOf(price.getText());
        }catch (NumberFormatException  ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Price must be a valid integer number.");
            return;
        }

        userMenuController.requestAddToMyInventory(description.getText(),name.getText(),price1
                ,this.mainController.getItemInventory());
        String message = "Item was added to your inventory";
        if(this.mode.equals("_demo")){
            message += ". Item is automatically approved because you are using the demo mode.";
        }
        JOptionPane.showMessageDialog(this.mainFrame, message);

    }

    private void deleteActionPerformed(ActionEvent e) {
        UserManager userManager = this.mainController.getUserManager();
      UserMenuController userMenuController = mainFrame.getMainController().getUserMenuController();
        ItemInventory itemInventory = mainFrame.getMainController().getItemInventory();
        Integer itemID = Integer.valueOf(itemId.getText());
        if (!(itemInventory.getItemWithID(itemID) == null)){
        userMenuController.removeFromUserInv(itemID,this.mainController.getItemInventory()
                ,userManager);
        JOptionPane.showMessageDialog(this.mainFrame, "Item was deleted from your inventory");}
        else {
            JOptionPane.showMessageDialog(this.mainFrame, "Item doesn't exist. Please enter correct itemID");
        }

    }


    private void initComponents() {
        // Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Namratha Anil
        ResourceBundle bundle = ResourceBundle.getBundle("gui.locales.UserInventoryMenu");
        label1 = new JLabel();
        back = new JButton();
        addItem = new JButton();
        label2 = new JLabel();
        delete = new JButton();
        name = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        description = new JTextField();
        label5 = new JLabel();
        itemId = new JTextField();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        userInventory = new JButton();
        price = new JTextField();
        label6 = new JLabel();
        label7 = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(750, 600));
        setBackground(new Color(12, 115, 115));
        setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 204, 204)));
        setMaximumSize(new Dimension(750, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
        0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
        . BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
        red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
        beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );

        //---- label1 ----
        label1.setText(bundle.getString("UserInventoryMenu.label1.text"));
        label1.setForeground(new Color(153, 255, 255));
        label1.setFont(new Font("Verdana", Font.PLAIN, 12));

        //---- back ----
        back.setText(bundle.getString("UserInventoryMenu.back.text"));
        back.setForeground(Color.white);
        back.setFont(new Font("Verdana", Font.PLAIN, 13));
        back.setBackground(new Color(12, 115, 115));
        back.setOpaque(true);
        back.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        back.addActionListener(e -> backActionPerformed(e));

        //---- addItem ----
        addItem.setText(bundle.getString("UserInventoryMenu.addItem.text"));
        addItem.setForeground(Color.white);
        addItem.setFont(new Font("Verdana", Font.PLAIN, 14));
        addItem.setBackground(new Color(0, 51, 51));
        addItem.setOpaque(true);
        addItem.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        addItem.addActionListener(e -> addItemActionPerformed(e));


        //---- label2 ----
        label2.setText(bundle.getString("UserInventoryMenu.label2.text"));
        label2.setForeground(Color.white);
        label2.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- delete ----
        delete.setText(bundle.getString("UserInventoryMenu.delete.text"));
        delete.setForeground(Color.white);
        delete.setFont(new Font("Verdana", Font.PLAIN, 14));
        delete.setBackground(new Color(0, 51, 51));
        delete.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        delete.setOpaque(true);
        delete.addActionListener(e -> deleteActionPerformed(e));

        //---- name ----
        name.setBackground(new Color(12, 115, 115));
        name.setForeground(Color.white);
        name.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- label3 ----
        label3.setText(bundle.getString("UserInventoryMenu.label3.text"));
        label3.setForeground(Color.white);
        label3.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- label4 ----
        label4.setText(bundle.getString("UserInventoryMenu.label4.text"));
        label4.setForeground(Color.white);
        label4.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- description ----
        description.setBackground(new Color(12, 115, 115));
        description.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //---- label5 ----
        label5.setText(bundle.getString("UserInventoryMenu.label5.text"));
        label5.setForeground(Color.white);
        label5.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- itemId ----
        itemId.setBackground(new Color(12, 115, 115));
        itemId.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setForeground(Color.white);
            list1.setBackground(Color.darkGray);
            list1.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
            scrollPane1.setViewportView(list1);
        }

        //---- userInventory ----
        userInventory.setText(bundle.getString("UserInventoryMenu.userInventory.text"));
        userInventory.setForeground(Color.white);
        userInventory.setFont(new Font("Verdana", Font.PLAIN, 14));
        userInventory.setBackground(new Color(0, 51, 51));
        userInventory.setBorder(new MatteBorder(1, 2, 2, 1, new Color(0, 204, 204)));
        userInventory.setOpaque(true);
        userInventory.addActionListener(e -> userInventoryActionPerformed(e));

        //---- price ----
        price.setBackground(new Color(12, 115, 115));
        price.setBorder(new MatteBorder(0, 0, 2, 0, Color.white));
        price.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        price.setForeground(Color.white);

        //---- label6 ----
        label6.setText(bundle.getString("UserInventoryMenu.label6.text"));
        label6.setForeground(Color.white);
        label6.setFont(new Font("Verdana", Font.PLAIN, 14));

        //---- label7 ----
        label7.setText("$");
        label7.setForeground(Color.white);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label6)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label7, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)
                            .addComponent(price, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label4)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(name, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                .addComponent(description, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                    .addComponent(label5)
                    .addGap(205, 205, 205))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(itemId, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))
                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(userInventory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(addItem, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
                                    .addGap(91, 91, 91)
                                    .addComponent(delete, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE))
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(62, 62, 62)
                                    .addComponent(label2)))))
                    .addContainerGap(44, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(585, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label1)
                        .addComponent(back, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                    .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addGap(14, 14, 14)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label6)
                        .addComponent(label7)
                        .addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(itemId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addGap(21, 21, 21)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(description, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                    .addComponent(label2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addItem, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(delete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(userInventory, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(back, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14))
        );
        //  End of component initialization  //GEN-END:initComponents
    }

    // Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Namratha Anil
    private JLabel label1;
    private JButton back;
    private JButton addItem;
    private JLabel label2;
    private JButton delete;
    private JTextField name;
    private JLabel label3;
    private JLabel label4;
    private JTextField description;
    private JLabel label5;
    private JTextField itemId;
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton userInventory;
    private JTextField price;
    private JLabel label6;
    private JLabel label7;
    // End of variables declaration  //GEN-END:variables
}

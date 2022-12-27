/*
 * Created by JFormDesigner on Sat Aug 08 00:15:53 EDT 2020
 */

package GUI.TradeUI;

import java.awt.*;
import GUI.LoginUI.LoginPage;
import GUI.LoginUI.MainPage;
import GUI.util.DisplayUtil;
import accounts.UserAccount;
import accounts.UserManager;
import controllers.TradeController;
import controllers.TradePresenter;
import items.Item;
import items.ItemInventory;

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Namratha Anil
 */
public class ThreeWayTrade extends JPanel {
    private MainPage mainFrame;
    private String mode = "";
    private Map<Integer, List<Integer>> suggestedMap = new HashMap<>();
    private DefaultListModel <String> sysInventoryList = new DefaultListModel<String>();

    ResourceBundle popups = ResourceBundle.getBundle("GUI.locales.ThreeWayTrade");

    public ThreeWayTrade(String mode) {
        this.mode = mode;
        initComponents();
    }

    /**
     *
     * @param mainFrame
     */
    public void setMainFrame(MainPage mainFrame){
        this.mainFrame = mainFrame;
        availableItems.setModel(sysInventoryList);
    }

    private void searchActionPerformed(ActionEvent e) {
        TradePresenter tradePresenter = mainFrame.getMainController().getTradeController().getTradePresenter();
        ItemInventory itemInventory = mainFrame.getMainController().getItemInventory();
        UserManager userManager = mainFrame.getMainController().getUserManager();
        UserAccount currentUser = mainFrame.getMainController().getCurrentUserAccount();
        UserAccount otherUser = userManager.getUser(itemInventory.getItemWithID(Integer.parseInt(itemID.getText())).getOwnerID());
        Map<Integer, List<Integer>> receiverMap = tradePresenter.findThreeWayTradeMap(currentUser, otherUser, Integer.parseInt(itemID.getText()), itemInventory, userManager);
        this.suggestedMap = receiverMap;
        displaySuggestion.setText(tradePresenter.formatThreeWayTrade(currentUser.getUserID(), receiverMap, itemInventory));
    }

    private void requestTradeActionPerformed(ActionEvent e) {
        if (suggestedMap.size() == 0) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("noItems.text"));
        }
        TradeController tradeController = mainFrame.getMainController().getTradeController();
        boolean isPermanent = permanentBox.isSelected();
        boolean success = tradeController.requestTrade(LoginPage.getCurrentUserName(), suggestedMap, isPermanent);
        if (success) {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("tradeRequested.text"));
        } else {
            JOptionPane.showMessageDialog(mainFrame, popups.getString("requestError.text"));
        }
    }

    private void backActionPerformed(ActionEvent e) {
        mainFrame.setView("TradeMainMenu");
    }

    private void viewItemsActionPerformed(ActionEvent e) {
        sysInventoryList.clear();
        TradePresenter tradePresenter = mainFrame.getMainController().getTradeController().getTradePresenter();
        UserAccount current = mainFrame.getMainController().getCurrentUserAccount();
        ItemInventory itemInventory =  mainFrame.getMainController().getItemInventory();
        UserManager userManager = mainFrame.getMainController().getUserManager();
        List<Item> approvedItems = tradePresenter.getAvailableItems(current, itemInventory , userManager);
        String itemInfo;
        for (Item item : approvedItems) {
            itemInfo = DisplayUtil.getItemDisplay(item);
            sysInventoryList.addElement(itemInfo);
        }
    }

	private void searchButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void permanentBoxActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void viewItemsButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Monica Jones
        ResourceBundle bundle = ResourceBundle.getBundle("GUI.locales.ThreeWayTrade");
        label1 = new JLabel();
        itemID = new JTextField();
        label2 = new JLabel();
        requestButton = new JButton();
        searchButton = new JButton();
        backButton = new JButton();
        permanentBox = new JCheckBox();
        label4 = new JLabel();
        label5 = new JLabel();
        scrollPane1 = new JScrollPane();
        displaySuggestion = new JTextArea();
        scrollPane2 = new JScrollPane();
        availableItems = new JList();
        viewItemsButton = new JButton();

        //======== this ========
        setBackground(new Color(12, 115, 115));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder
        ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border
        .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt
        . Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void
        propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );

        //---- label1 ----
        label1.setText(bundle.getString("ThreeWayTrade.label1.text"));

        //---- label2 ----
        label2.setText(bundle.getString("ThreeWayTrade.label2.text"));

        //---- requestButton ----
        requestButton.setText(bundle.getString("ThreeWayTrade.requestButton.text"));
        requestButton.addActionListener(e -> {
			requestTradeActionPerformed(e);
			requestTradeActionPerformed(e);
			requestTradeActionPerformed(e);
		});

        //---- searchButton ----
        searchButton.setText(bundle.getString("ThreeWayTrade.searchButton.text"));
        searchButton.addActionListener(e -> {
			searchActionPerformed(e);
			searchActionPerformed(e);
			searchButtonActionPerformed(e);
			searchActionPerformed(e);
		});

        //---- backButton ----
        backButton.setText(bundle.getString("ThreeWayTrade.backButton.text"));
        backButton.addActionListener(e -> {
			backActionPerformed(e);
			backActionPerformed(e);
			backActionPerformed(e);
		});

        //---- permanentBox ----
        permanentBox.setText(bundle.getString("ThreeWayTrade.permanentBox.text"));
        permanentBox.addActionListener(e -> {
			permanentBoxActionPerformed(e);
			requestTradeActionPerformed(e);
		});

        //---- label4 ----
        label4.setText(bundle.getString("ThreeWayTrade.label4.text"));

        //---- label5 ----
        label5.setText(bundle.getString("ThreeWayTrade.label5.text"));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(displaySuggestion);
        }

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(availableItems);
        }

        //---- viewItemsButton ----
        viewItemsButton.setText(bundle.getString("ThreeWayTrade.viewItemsButton.text"));
        viewItemsButton.addActionListener(e -> {
			viewItemsActionPerformed(e);
			viewItemsButtonActionPerformed(e);
			viewItemsActionPerformed(e);
			viewItemsActionPerformed(e);
		});

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(251, 251, 251)
                            .addComponent(itemID, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(83, 83, 83)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(requestButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                            .addGap(141, 141, 141))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                                .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
                                .addComponent(permanentBox, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewItemsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(69, 69, 69))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addGap(41, 41, 41)
                    .addComponent(label5)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(itemID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(35, 35, 35)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(searchButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label4)
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(permanentBox))
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(viewItemsButton)
                        .addComponent(requestButton))
                    .addGap(108, 108, 108)
                    .addComponent(backButton)
                    .addGap(15, 15, 15))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Monica Jones
    private JLabel label1;
    private JTextField itemID;
    private JLabel label2;
    private JButton requestButton;
    private JButton searchButton;
    private JButton backButton;
    private JCheckBox permanentBox;
    private JLabel label4;
    private JLabel label5;
    private JScrollPane scrollPane1;
    private JTextArea displaySuggestion;
    private JScrollPane scrollPane2;
    private JList availableItems;
    private JButton viewItemsButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * StoreView
 * <p>
 * Viewing stores in customer dashboard
 *
 * @author park1504
 * @version 12/11/2022
 */

public class StoreView {

    int action = 0;

    //high low = 1
    //low high = 2
    //revert = 0;

    JFrame frame = new JFrame();

    JButton highLow;
    JButton lowHigh;
    JButton revert;
    JButton exit;
    JPanel storePanel;

    private String customerUsername;
    String storeList;

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == highLow) {
                action = 1;
                try {
                    storePanel.removeAll();
                    storeList = isolateHighLowStores();
                    JTextArea highLowStore = new JTextArea(storeList);
                    highLowStore.setOpaque(false);
                    highLowStore.setEditable(false);
                    highLowStore.setFont(new Font("Arial", Font.PLAIN, 16));
                    storePanel.add(highLowStore, BorderLayout.WEST);
                    storePanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == lowHigh) {
                action = 2;
                try {
                    storePanel.removeAll();
                    storeList = isolateLowHighStores();
                    JTextArea lowHighStores = new JTextArea(storeList);
                    lowHighStores.setOpaque(false);
                    lowHighStores.setEditable(false);
                    lowHighStores.setFont(new Font("Arial", Font.PLAIN, 16));
                    storePanel.add(lowHighStores, BorderLayout.WEST);
                    storePanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == revert) {
                action = 0;
                try {
                    storePanel.removeAll();
                    storeList = isolateOriginalStores();
                    JTextArea originalStores = new JTextArea(storeList);
                    originalStores.setOpaque(false);
                    originalStores.setEditable(false);
                    originalStores.setFont(new Font("Arial", Font.PLAIN, 16));
                    storePanel.add(originalStores, BorderLayout.WEST);
                    storePanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            if (e.getSource() == exit) {
                frame.dispose();
            }
        }
    };


    public String isolateStore() throws IOException {
        if (action == 0) {
            return isolateOriginalStores();
        }
        if (action == 1) {
            return isolateHighLowStores();
        }
        if (action == 2) {
            return isolateLowHighStores();
        }

        return isolateOriginalStores();
    }

    public String isolateOriginalStores() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String originalStores = cd.sendOriginalStores();
        return originalStores;
    }

    public String isolateHighLowStores() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String highLowStores = cd.sendHighLowStores();
        return highLowStores;
    }

    public String isolateLowHighStores() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String lowHighStores = cd.sendLowHighStores();
        return lowHighStores;
    }


    StoreView(String customerUsername) {

        CustomerDashboardGUI cdg = new CustomerDashboardGUI(customerUsername);
        this.customerUsername = cdg.getCustomerUsername();

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel nPanel = new JPanel();
        JPanel cPanel = new JPanel();
        storePanel = new JPanel();

        try {
            storeList = isolateStore();
            JTextArea originalStores = new JTextArea(storeList);
            originalStores.setOpaque(false);
            originalStores.setEditable(false);
            originalStores.setFont(new Font("Arial", Font.PLAIN, 16));
            storePanel.add(originalStores, BorderLayout.WEST);
            JScrollPane pane = new JScrollPane(storePanel);
            frame.add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setSize(400, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel viewStore = new JLabel("All Stores");
        highLow = new JButton("High to Low");
        highLow.addActionListener(actionListener);
        lowHigh = new JButton("Low to High");
        lowHigh.addActionListener(actionListener);
        revert = new JButton("Revert List");
        revert.addActionListener(actionListener);
        exit = new JButton("EXIT");
        exit.addActionListener(actionListener);



        nPanel.add(viewStore);
        content.add(nPanel, BorderLayout.NORTH);
        cPanel.add(highLow);
        content.add(cPanel, BorderLayout.EAST);
        cPanel.add(lowHigh);
        content.add(cPanel, BorderLayout.EAST);
        cPanel.add(revert);
        content.add(cPanel, BorderLayout.EAST);
        cPanel.add(exit);
        content.add(cPanel, BorderLayout.SOUTH);

    }

}
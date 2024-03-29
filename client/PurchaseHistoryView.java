import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * PurchaseHistoryView
 * <p>
 * viewing purchase history via customer dashboard
 *
 * @author park1504
 * @version 12/11/2022
 */
public class PurchaseHistoryView {

    int action = 0;
    //high low = 1
    //low high = 2
    //revert = 0;

    JFrame frame = new JFrame();

    JButton highLow;
    JButton lowHigh;
    JButton revert;
    JButton exit;
    JPanel historyPanel;
    String storeList;
    private String customerUsername;
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == highLow) {
                action = 1;
                try {
                    historyPanel.removeAll();
                    storeList = isolateHighLowHistory();
                    JTextArea history = new JTextArea(storeList);
                    history.setOpaque(false);
                    history.setEditable(false);
                    historyPanel.add(history, BorderLayout.WEST);
                    historyPanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == lowHigh) {
                action = 2;
                try {
                    historyPanel.removeAll();
                    storeList = isolateLowHighHistory();
                    JTextArea history = new JTextArea(storeList);
                    history.setOpaque(false);
                    history.setEditable(false);
                    historyPanel.add(history, BorderLayout.WEST);
                    historyPanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == revert) {
                action = 0;
                try {
                    historyPanel.removeAll();
                    storeList = isolateOriginalHistory();
                    JTextArea history = new JTextArea(storeList);
                    history.setOpaque(false);
                    history.setEditable(false);
                    historyPanel.add(history, BorderLayout.WEST);
                    historyPanel.revalidate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (e.getSource() == exit) {
                frame.dispose();
            }
        }
    };

    PurchaseHistoryView(String customerUsername) {

        CustomerDashboardGUI cdg = new CustomerDashboardGUI(customerUsername);
        this.customerUsername = cdg.getCustomerUsername();


        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel nPanel = new JPanel();
        JPanel cPanel = new JPanel();
        historyPanel = new JPanel();

        try {
            storeList = isolateHistory();
            JTextArea originalStores = new JTextArea(storeList);
            originalStores.setOpaque(false);
            originalStores.setEditable(false);
            historyPanel.add(originalStores, BorderLayout.WEST);
            JScrollPane pane = new JScrollPane(historyPanel);
            frame.add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setSize(400, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel viewStore = new JLabel("All Purchases");
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

    public String isolateHistory() throws IOException {
        if (action == 0) {
            return isolateOriginalHistory();
        }
        if (action == 1) {
            return isolateHighLowHistory();
        }
        if (action == 2) {
            return isolateLowHighHistory();
        }

        return isolateOriginalHistory();
    }

    public String isolateOriginalHistory() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String originalHistory = cd.sendOriginalHistory();
        return originalHistory;
    }

    public String isolateHighLowHistory() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String highLowStores = cd.sendHighLowHistory();
        return highLowStores;
    }

    public String isolateLowHighHistory() throws IOException {
        CustomerDashboard cd = new CustomerDashboard(this.customerUsername);
        String lowHighStores = cd.sendLowHighHistory();
        return lowHighStores;
    }

}
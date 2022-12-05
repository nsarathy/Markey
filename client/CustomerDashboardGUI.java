import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomerDashboardGUI extends JComponent implements Runnable {

    private String customerUsername;

    JButton storeView;
    JButton purchaseHistory;
    JButton exit;

    //when store view is clicked action will equal 1 and 2 for history

    JFrame frame = new JFrame("CustomerDashboardGUI");

    public CustomerDashboardGUI(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }


    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == storeView) {
                StoreView storeView = new StoreView(customerUsername);
            }
            if (e.getSource() == purchaseHistory) {
                PurchaseHistoryView purchaseHistoryView = new PurchaseHistoryView(customerUsername);
            }
            if (e.getSource() == exit) {
                frame.dispose();
            }
        }
    };



    public void main() {
        SwingUtilities.invokeLater(new CustomerDashboardGUI(customerUsername));
    }

    @Override
    public void run() {
        boolean ignored = false;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            ignored = true;
        }

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        JLabel customerDB = new JLabel("Welcome to Customer Dashboard!");
        customerDB.setFont(new Font("Arial", Font.PLAIN, 20));
        storeView = new JButton("View All Stores");
        storeView.setFont(new Font("Arial", Font.PLAIN, 18));
        storeView.addActionListener(actionListener);

        purchaseHistory = new JButton("View Purchase History");
        purchaseHistory.setFont(new Font("Arial", Font.PLAIN, 18));
        purchaseHistory.addActionListener(actionListener);

        exit = new JButton("EXIT");
        exit.setFont(new Font("Arial", Font.PLAIN, 18));
        exit.addActionListener(actionListener);

        JPanel nPanel = new JPanel();
        JPanel cPanel = new JPanel();

        nPanel.add(customerDB);
        content.add(nPanel, BorderLayout.NORTH);
        cPanel.add(storeView);
        content.add(cPanel, BorderLayout.CENTER);
        cPanel.add(purchaseHistory);
        content.add(cPanel, BorderLayout.CENTER);
        cPanel.add(exit);
        content.add(cPanel, BorderLayout.CENTER);
    }


}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CustomerDashboardGUI extends JComponent implements Runnable {

	private String customerUsername;
	
	JButton storeView;
	JButton purchaseHistory;
	JButton exit;
	
	//when store view is clicked action will equal 1 and 2 for history

	public static void main(String[] args) {
		CustomerDashboardGUI cdg = new CustomerDashboardGUI("testUser");
		cdg.main();
	}
	
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
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());

		frame.setSize(500, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);


		JLabel customerDB = new JLabel("Welcome to Customer Dashboard!");
		storeView = new JButton("View All Stores");
		storeView.addActionListener(actionListener);

		purchaseHistory = new JButton("View Purchase History");
		purchaseHistory.addActionListener(actionListener);

		exit = new JButton("EXIT");
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

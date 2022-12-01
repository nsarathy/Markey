import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;


public class CustomerDashboardGUI extends JComponent implements Runnable {

	JButton storeView;
	JButton purchaseHistory;
	JButton exit;
	
	JButton highLowStore;
	JButton lowHighStore;
	JButton revertStore;
	
	JButton highLowHistory;
	JButton lowHighHistory;
	JButton revertHistory;
	
	
	
	
	public static void main(String[] args) {
		CustomerDashboardGUI cdg = new CustomerDashboardGUI();
		cdg.main();
	}
	
	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
		}
	};

	
	
	public void main() {
		SwingUtilities.invokeLater(new CustomerDashboardGUI());
	}
	
	@Override
	public void run() {
		 JFrame frame = new JFrame("CustomerDashboardGUI");
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

	        
	        JPanel nPanel = new JPanel();
	        JPanel cPanel = new JPanel();
	        
	        nPanel.add(customerDB);
	        content.add(nPanel, BorderLayout.NORTH);
	        cPanel.add(storeView);
	        content.add(cPanel, BorderLayout.CENTER);
	        cPanel.add(purchaseHistory);
	        content.add(cPanel, BorderLayout.CENTER);


	        
	        
	}
	
	
}

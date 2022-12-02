import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class StoreView {


	JFrame frame = new JFrame();

	JButton highLow;
	JButton lowHigh;
	JButton revert;
	JButton exit;

	private String customerUsername;



	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == highLow) {
			}
			if (e.getSource() == lowHigh) {
			}
			if (e.getSource() == revert) {
				
			}
			if (e.getSource() == exit) {
				frame.dispose();
			}
		}
	};


	public String isolateOriginalStores() throws IOException {
		CustomerDashboardDuplicate cdd = new CustomerDashboardDuplicate(this.customerUsername);
		String originalStores = cdd.sendOriginalStores();
		System.out.println(originalStores);
		return originalStores;
	}


	StoreView(String customerUsername) {

		CustomerDashboardGUI cdg = new CustomerDashboardGUI(customerUsername);
		this.customerUsername = cdg.getCustomerUsername();		

		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());

		JPanel nPanel = new JPanel();
		JPanel cPanel = new JPanel();

		try {
			String originalStoresLabel = isolateOriginalStores();
			JTextArea originalStores = new JTextArea(originalStoresLabel);
			frame.add(originalStores);
			content.add(cPanel, BorderLayout.CENTER);
			originalStores.setLineWrap(true);
			originalStores.setWrapStyleWord(true);
			originalStores.setOpaque(false);
			originalStores.setEditable(false);

			cPanel.add(originalStores);
			frame.add(cPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.setSize(400, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		
		nPanel.add(viewStore);
		content.add(nPanel, BorderLayout.NORTH);
		cPanel.add(highLow);
		content.add(cPanel, BorderLayout.CENTER);
		cPanel.add(lowHigh);
		content.add(cPanel, BorderLayout.CENTER);
		cPanel.add(revert);
		content.add(cPanel, BorderLayout.CENTER);
		cPanel.add(exit);
		content.add(cPanel, BorderLayout.CENTER);

	}

}

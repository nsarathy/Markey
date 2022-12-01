import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PurchaseHistoryView {

JFrame frame = new JFrame();
	
	JButton highLow;
	JButton lowHigh;
	JButton revert;
	JButton exit;
	
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
	
	
	PurchaseHistoryView() {
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		frame.setSize(300, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        
        JLabel viewStore = new JLabel("Purchase History");
        highLow = new JButton("High to Low");
        highLow.addActionListener(actionListener);
        lowHigh = new JButton("Low to High");
        lowHigh.addActionListener(actionListener);
        revert = new JButton("Revert List");
        revert.addActionListener(actionListener);
        exit = new JButton("EXIT");
        exit.addActionListener(actionListener);
        
        JPanel nPanel = new JPanel();
		JPanel cPanel = new JPanel();

		nPanel.add(viewStore);
		content.add(nPanel, BorderLayout.NORTH);
		cPanel.add(highLow);
		content.add(cPanel, BorderLayout.CENTER);
		cPanel.add(lowHigh);
		content.add(cPanel, BorderLayout.CENTER);
		cPanel.add(exit);
		content.add(cPanel, BorderLayout.CENTER);

	}
	
}

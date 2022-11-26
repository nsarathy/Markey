import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
/*
Methods in server and client for server to transfer data to client - invoked when connection established and refresh
and whenever an action is performed
When button is clicked
symbol is passed to client
then client passes symbol to server
then server calls method to perform action
transfers data to client
 */

public class MarketPlaceGUI implements Runnable, Shared {

    //     public static final String[] BUTTONS = {"#", "<", ">", "<>", "?", "@", "++", "--", "*", "csv", "db", "&",
    //     "q"};


    ActionListener customerListener;
    ActionListener sellerListener;

    Icon refreshIcon;
    JButton refreshButton;

    // seller buttons
    Icon cartIcon;
    JButton viewCartButton;
    JButton sortLowHighButton;
    JButton sortHighLowButton;
    JButton deSortButton;
    JButton sortAvailableButton;
    Icon dbIcon;
    JButton dbButton;
    Icon historyIcon;
    JButton purchaseHistoryButton;
    JButton csvButton;
    Icon searchIcon;
    JButton searchButton;
    JTextField searchField;
    //customer buttons
    Icon storeIcon;
    JButton storeButton;
    JButton addProductButton;
    JButton removeProductButton;
    Icon editIcon;
    JButton editProductButton;
    Icon listIcon;
    JButton seeCartsButton;
    JButton copyDbButton;
    JButton copyCsvButton;
    //listings
    HashMap<JButton, Product> listings;

    public static boolean gettingInput = true;
    public static String input;


    private ArrayList<Product> itemsForSale;
    private ArrayList<String> sellerUsernames;
    private boolean customer;

    public MarketPlaceGUI(boolean customer, ArrayList<Product> itemsForSale, ArrayList<String> sellerUsernames) {
        this.customer = customer;
        this.itemsForSale = itemsForSale;
        this.sellerUsernames = sellerUsernames;
    }

    public static void main(String[] args) {

    }

    public void main() {
        SwingUtilities.invokeLater(this);
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
        listings = new HashMap<>();
        JPanel listingPanel = new JPanel(new GridLayout(0, 3));
        for (Product product : itemsForSale) {
            String buttonString =
                String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>", product.getName().length() <= 30 ?
                        product.getName() : product.getName().substring(0, 27) + "...",
                    product.getStore().getName(),
                    product.getPrice());
            JButton button = new JButton(buttonString);
            button.setMargin(new Insets(10, 10, 10, 10));
            button.setFont(new Font("Arial", Font.PLAIN, 22));
            button.setBorder(new EmptyBorder(10, 10, 10, 10));
            listings.put(button, product);
            listingPanel.add(button);
        }
        JScrollPane scrollPane = new JScrollPane(listingPanel);

        refreshIcon = new ImageIcon("images/markey-logo.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setToolTipText("<html>refresh</html>");

        searchIcon = new ImageIcon("images/search.png");
        searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(36, 36));
        searchField = new JTextField(16);
        searchField.setFont(new Font("Arial", Font.PLAIN, 22));

        cartIcon = new ImageIcon("images/cart.png");
        viewCartButton = new JButton(cartIcon);
        viewCartButton.setPreferredSize(new Dimension(36, 36));
        viewCartButton.setToolTipText("<html>View Cart</html>");

        sortLowHighButton = new JButton("<");
        sortLowHighButton.setFont(new Font("Arial", Font.PLAIN, 22));
        sortLowHighButton.setToolTipText("<html>Sort from low to high cost</html>");

        sortHighLowButton = new JButton(">");
        sortHighLowButton.setFont(new Font("Arial", Font.PLAIN, 22));
        sortHighLowButton.setToolTipText("<html>Sort from high to low cost</html>");

        deSortButton = new JButton("<>");
        deSortButton.setFont(new Font("Arial", Font.PLAIN, 22));
        deSortButton.setToolTipText("<html>de-sort</html>");

        sortAvailableButton = new JButton(">q");
        sortAvailableButton.setFont(new Font("Arial", Font.PLAIN, 22));
        sortAvailableButton.setToolTipText("<html>Sort by availability</html>");

        dbIcon = new ImageIcon("images/db.png");
        dbButton = new JButton(dbIcon);
        dbButton.setPreferredSize(new Dimension(36, 36));
        dbButton.setToolTipText("<html> Dashboard </html>");

        historyIcon = new ImageIcon("images/history.png");
        purchaseHistoryButton = new JButton(historyIcon);
        purchaseHistoryButton.setPreferredSize(new Dimension(36, 36));
        purchaseHistoryButton.setToolTipText("<html>View purchase history</html>");

        csvButton = new JButton(".csv");
        csvButton.setFont(new Font("Arial", Font.PLAIN, 22));
        csvButton.setToolTipText("<html> import or export csv file with your data</html>");

        JFrame actionFrame = new JFrame("Markey");
        actionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel customerPanel = new JPanel();
        customerPanel.add(refreshButton);
        customerPanel.add(searchField);
        customerPanel.add(searchButton);
        customerPanel.add(viewCartButton);
        customerPanel.add(sortLowHighButton);
        customerPanel.add(sortHighLowButton);
        customerPanel.add(deSortButton);
        customerPanel.add(sortAvailableButton);
        customerPanel.add(dbButton);
        customerPanel.add(purchaseHistoryButton);
        customerPanel.add(csvButton);

        storeIcon = new ImageIcon("images/store.png");
        storeButton = new JButton(storeIcon);
        storeButton.setPreferredSize(new Dimension(36, 36));
        storeButton.setToolTipText("<html>Open new store</html>");

        addProductButton = new JButton("+");
        addProductButton.setFont(new Font("Arial", Font.BOLD, 22));
        addProductButton.setToolTipText("<html>Create new listing</html>");

        removeProductButton = new JButton("-");
        removeProductButton.setFont(new Font("Arial", Font.BOLD, 22));
        removeProductButton.setToolTipText("<html>Delete listing</html>");

        editIcon = new ImageIcon("images/edit.png");
        editProductButton = new JButton(editIcon);
        editProductButton.setPreferredSize(new Dimension(36, 36));
        editProductButton.setToolTipText("<html>Edit details of a product on sale</html>");

        listIcon = new ImageIcon("images/list.png");
        seeCartsButton = new JButton(listIcon);
        seeCartsButton.setPreferredSize(new Dimension(36, 36));
        seeCartsButton.setToolTipText("<html>See what's on everyone's carts</html>");

        copyDbButton = new JButton(dbIcon);
        copyDbButton.setPreferredSize(new Dimension(36, 36));
        copyDbButton.setToolTipText("<html> Dashboard </html>");

        copyCsvButton = new JButton(".csv");
        copyCsvButton.setFont(new Font("Arial", Font.PLAIN, 22));
        copyCsvButton.setToolTipText("<html> import or export csv file with your data</html>");

        JPanel sellerPanel = new JPanel();
        if (!customer) {
            sellerPanel.add(refreshButton);
        }
        sellerPanel.add(storeButton);
        sellerPanel.add(addProductButton);
        sellerPanel.add(removeProductButton);
        sellerPanel.add(editProductButton);
        sellerPanel.add(seeCartsButton);
        sellerPanel.add(copyDbButton);
        sellerPanel.add(copyCsvButton);

        if (customer) {
            actionFrame.add(customerPanel, BorderLayout.NORTH);
        } else {
            actionFrame.add(sellerPanel, BorderLayout.NORTH);
        }
        actionFrame.add(scrollPane, BorderLayout.CENTER);

        actionFrame.setSize(1920, 1800);
        actionFrame.setVisible(true);
    }

    public ArrayList<Product> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(ArrayList<Product> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    public ArrayList<String> getSellerUsernames() {
        return sellerUsernames;
    }

    public void setSellerUsernames(ArrayList<String> sellerUsernames) {
        this.sellerUsernames = sellerUsernames;
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public void displayPlainMessage(String message) {
        Icon icon = new ImageIcon("images/markey.png");
        JOptionPane.showMessageDialog(null, message, "Markey", JOptionPane.PLAIN_MESSAGE, icon);
    }
    public void displayInformationMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Markey", JOptionPane.INFORMATION_MESSAGE);
    }
}

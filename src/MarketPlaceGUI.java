import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/*
todo :
Methods in server and client for server to transfer data to client - invoked when connection established and refresh
and whenever an action is performed
When button is clicked
symbol is passed to client
then client passes symbol to server
then server calls method to perform action
transfers data to client
 */

public class MarketPlaceGUI implements Runnable, Shared {
    /**
     * todo: seller
     * todo: server-client
     */


    Icon refreshIcon;
    JButton refreshButton;
    int cartLength = 0;

    // customer buttons
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
    JLabel cartLabel;
    // seller buttons
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
    Icon seeListingsIcon;
    JButton seeListingsButton;
    //listings
    HashMap<JButton, Product> listings;
    // customer stuff
    private ArrayList<Product> itemsForSale;
    private ArrayList<String> sellerUsernames;
    private ArrayList<Product> cartItems;
    private ArrayList<String> cartSellerUsernames;
    private boolean customer;
    private String username;
    ActionListener productListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jButton = (JButton) e.getSource();
            Product p = listings.get(jButton);
            if (p != null) {
                productPage(p);
            }
        }
    };
    ActionListener customerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewCartButton) {
                displayCart();
            } else if (e.getSource() == purchaseHistoryButton) {
                displayPurchaseHistory();
            } else if (e.getSource() == csvButton) {
                customerCsv();
            }
        }
    };
    // seller stuff
    private ArrayList<Product> sellerItems;
    private ArrayList<Store> sellerStores;
    ActionListener sellerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (sellerItems.isEmpty()) {
                displayPlainMessage("<html>Welcome to Markey<br>It is mandatory for you to have at least one " +
                    "store open<br>with at least one item for sale to perform any actions</html>");
                openStore();
            } else if (e.getSource() == seeListingsButton) {
                seeListings();
            } else if (e.getSource() == storeButton) {
                openStore();
            } else if (e.getSource() == addProductButton) {
                addProduct();
            } else if (e.getSource() == removeProductButton) {
                removeProduct();
            } else if (e.getSource() == editProductButton) {
                editProduct();
            } else if (e.getSource() == seeCartsButton) {
                seeCarts();
            }
        }
    };

    // test purposes
    public static void main(String[] args) {
        MarketPlaceGUI gui = new MarketPlaceGUI();
        gui.main(false, "testSeller");
    }


    public void main(boolean customer, String username) {
        // todo: all methods. to be done by client
        MarketPlaceGUI gui = new MarketPlaceGUI();
        gui.setCustomer(customer);
        gui.setUsername(username);

        // reading products.txt
        MarketPlaceMethods methods = new MarketPlaceMethods(username);
        Listing products = methods.readProductsTxt(); // todo
        gui.setItemsForSale(products.getProducts());
        gui.setSellerUsernames(products.getSellers());

        if (customer) {
            // reading and updating carts
            gui.setCartItems(new ArrayList<>());
            gui.setCartSellerUsernames(new ArrayList<>());
            try {
                Listing readCart = methods.readCart(); // todo
                gui.setCartItems(readCart.getProducts());
                gui.setCartSellerUsernames(readCart.getSellers());
                boolean removed = gui.checkCartStatus();
                if (removed) {
                    methods.storeCart(gui.getCartItems(), gui.getCartSellerUsernames()); // todo
                }
            } catch (Exception e) {
                if (!e.getMessage().equals("Your cart is empty")) {
                    displayErrorMessage(e.getMessage());
                }
            }
        } else {
            // seller
            gui.setSellerItems(new ArrayList<>());
            gui.setSellerStores(new ArrayList<>());
            ProductsAndStores productsAndStores = methods.readSeller(); // todo
            if (productsAndStores != null) {
                gui.setSellerStores(productsAndStores.getStores());
                gui.setSellerItems(productsAndStores.getProducts());
            }
        }

        SwingUtilities.invokeLater(gui);
    }

    @Override
    public void run() {
        final ArrayList<Product> deSortList = new ArrayList<>(itemsForSale);
        final ArrayList<String> deSortSellers = new ArrayList<>(sellerUsernames);
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
            if (customer) {
                button.addActionListener(productListener);
            }
        }
        JScrollPane scrollPane = new JScrollPane(listingPanel);

        refreshIcon = new ImageIcon("images/markey-logo.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setToolTipText("<html>refresh</html>");
        refreshButton.addActionListener(customerListener); // todo refresh customer

        searchIcon = new ImageIcon("images/search.png");
        searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(36, 36));
        searchField = new JTextField(16);
        searchField.setFont(new Font("Arial", Font.PLAIN, 22));

        cartIcon = new ImageIcon("images/cart.png");
        viewCartButton = new JButton(cartIcon);
        viewCartButton.setPreferredSize(new Dimension(36, 36));
        viewCartButton.setToolTipText("<html>View Cart</html>");
        viewCartButton.addActionListener(customerListener);

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
        dbButton.addActionListener(customerListener); // todo dashboard

        historyIcon = new ImageIcon("images/history.png");
        purchaseHistoryButton = new JButton(historyIcon);
        purchaseHistoryButton.setPreferredSize(new Dimension(36, 36));
        purchaseHistoryButton.setToolTipText("<html>View purchase history</html>");
        purchaseHistoryButton.addActionListener(customerListener);

        csvButton = new JButton(".csv");
        csvButton.setFont(new Font("Arial", Font.PLAIN, 22));
        csvButton.setToolTipText("<html> import or export csv file with your data</html>");
        csvButton.addActionListener(customerListener);

        cartLabel = new JLabel(String.valueOf(cartLength));
        cartLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JFrame actionFrame = new JFrame("Markey");
        actionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel customerPanel = new JPanel();
        JPanel cartPanel = new JPanel();
        JPanel elsePanel = new JPanel();

        elsePanel.add(refreshButton);
        elsePanel.add(searchField);
        elsePanel.add(searchButton);
        elsePanel.add(sortLowHighButton);
        elsePanel.add(sortHighLowButton);
        elsePanel.add(deSortButton);
        elsePanel.add(sortAvailableButton);
        elsePanel.add(dbButton);
        elsePanel.add(purchaseHistoryButton);
        elsePanel.add(csvButton);

        cartPanel.add(viewCartButton);
        cartPanel.add(cartLabel);

        customerPanel.add(elsePanel, BorderLayout.WEST);
        customerPanel.add(cartPanel, BorderLayout.EAST);

        storeIcon = new ImageIcon("images/store.png");
        storeButton = new JButton(storeIcon);
        storeButton.setPreferredSize(new Dimension(36, 36));
        storeButton.setToolTipText("<html>Open new store</html>");
        storeButton.addActionListener(sellerListener);

        addProductButton = new JButton("+");
        addProductButton.setFont(new Font("Arial", Font.BOLD, 22));
        addProductButton.setToolTipText("<html>Create new listing</html>");
        addProductButton.addActionListener(sellerListener);

        removeProductButton = new JButton("-");
        removeProductButton.setFont(new Font("Arial", Font.BOLD, 22));
        removeProductButton.setToolTipText("<html>Delete listing</html>");
        removeProductButton.addActionListener(sellerListener);

        editIcon = new ImageIcon("images/edit.png");
        editProductButton = new JButton(editIcon);
        editProductButton.setPreferredSize(new Dimension(36, 36));
        editProductButton.setToolTipText("<html>Edit details of a product on sale</html>");
        editProductButton.addActionListener(sellerListener);

        listIcon = new ImageIcon("images/list.png");
        seeCartsButton = new JButton(listIcon);
        seeCartsButton.setPreferredSize(new Dimension(36, 36));
        seeCartsButton.setToolTipText("<html>See what's on everyone's carts</html>");
        seeCartsButton.addActionListener(sellerListener);

        copyDbButton = new JButton(dbIcon);
        copyDbButton.setPreferredSize(new Dimension(36, 36));
        copyDbButton.setToolTipText("<html> Dashboard </html>");
        copyDbButton.addActionListener(sellerListener); // todo seller db

        copyCsvButton = new JButton(".csv");
        copyCsvButton.setFont(new Font("Arial", Font.PLAIN, 22));
        copyCsvButton.setToolTipText("<html> import or export csv file with your data</html>");
        copyCsvButton.addActionListener(sellerListener); // todo seller csv

        seeListingsIcon = new ImageIcon("images/priceTag.png");
        seeListingsButton = new JButton(seeListingsIcon);
        seeListingsButton.setPreferredSize(new Dimension(36, 36));
        seeListingsButton.setToolTipText("<html>See what you have on sale</html>");
        seeListingsButton.addActionListener(sellerListener);

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
        sellerPanel.add(seeListingsButton);

        if (customer) {
            actionFrame.add(customerPanel, BorderLayout.NORTH);
        } else {
            actionFrame.add(sellerPanel, BorderLayout.NORTH);
        }
        actionFrame.add(scrollPane, BorderLayout.CENTER);

        actionFrame.setSize(1920, 1800);
        actionFrame.setVisible(true);


        sortHighLowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listingPanel.removeAll();

                for (int i = 0; i < itemsForSale.size(); i++) {
                    for (int j = 0; j < itemsForSale.size() - i - 1; j++) {
                        if (itemsForSale.get(j).getPrice() < itemsForSale.get(j + 1).getPrice()) {
                            Product temp = itemsForSale.get(j);
                            itemsForSale.set(j, itemsForSale.get(j + 1));
                            itemsForSale.set(j + 1, temp);

                            String tempString = sellerUsernames.get(j);
                            sellerUsernames.set(j, sellerUsernames.get(j + 1));
                            sellerUsernames.set(j + 1, tempString);

                        }
                    }
                }

                for (Product product : itemsForSale) {
                    String buttonString =
                        String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>",
                            product.getName().length() <= 30 ?
                                product.getName() : product.getName().substring(0, 27) + "...",
                            product.getStore().getName(),
                            product.getPrice());
                    JButton button = new JButton(buttonString);
                    button.setMargin(new Insets(10, 10, 10, 10));
                    button.setFont(new Font("Arial", Font.PLAIN, 22));
                    button.setBorder(new EmptyBorder(10, 10, 10, 10));
                    listings.put(button, product);
                    listingPanel.add(button);
                    button.addActionListener(productListener);
                }

                listingPanel.revalidate();

            }
        });

        sortLowHighButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listingPanel.removeAll();

                for (int i = 0; i < itemsForSale.size(); i++) {
                    for (int j = 0; j < itemsForSale.size() - i - 1; j++) {
                        if (itemsForSale.get(j).getPrice() > itemsForSale.get(j + 1).getPrice()) {
                            Product temp = itemsForSale.get(j);
                            itemsForSale.set(j, itemsForSale.get(j + 1));
                            itemsForSale.set(j + 1, temp);

                            String tempString = sellerUsernames.get(j);
                            sellerUsernames.set(j, sellerUsernames.get(j + 1));
                            sellerUsernames.set(j + 1, tempString);
                        }
                    }
                }

                for (Product product : itemsForSale) {
                    String buttonString =
                        String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>",
                            product.getName().length() <= 30 ?
                                product.getName() : product.getName().substring(0, 27) + "...",
                            product.getStore().getName(),
                            product.getPrice());
                    JButton button = new JButton(buttonString);
                    button.setMargin(new Insets(10, 10, 10, 10));
                    button.setFont(new Font("Arial", Font.PLAIN, 22));
                    button.setBorder(new EmptyBorder(10, 10, 10, 10));
                    listings.put(button, product);
                    listingPanel.add(button);
                    button.addActionListener(productListener);
                }

                listingPanel.revalidate();

            }
        });

        deSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listingPanel.removeAll();

                itemsForSale = new ArrayList<>();
                itemsForSale.addAll(deSortList);
                sellerUsernames = new ArrayList<>();
                sellerUsernames.addAll(deSortSellers);

                for (Product product : itemsForSale) {
                    String buttonString =
                        String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>",
                            product.getName().length() <= 30 ?
                                product.getName() : product.getName().substring(0, 27) + "...",
                            product.getStore().getName(),
                            product.getPrice());
                    JButton button = new JButton(buttonString);
                    button.setMargin(new Insets(10, 10, 10, 10));
                    button.setFont(new Font("Arial", Font.PLAIN, 22));
                    button.setBorder(new EmptyBorder(10, 10, 10, 10));
                    listings.put(button, product);
                    listingPanel.add(button);
                    button.addActionListener(productListener);
                }

                listingPanel.revalidate();
            }
        });

        sortAvailableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listingPanel.removeAll();

                for (int i = 0; i < itemsForSale.size(); i++) {
                    for (int j = 0; j < itemsForSale.size() - i - 1; j++) {
                        if (itemsForSale.get(j).getQuantity() < itemsForSale.get(j + 1).getQuantity()) {
                            Product temp = itemsForSale.get(j);
                            itemsForSale.set(j, itemsForSale.get(j + 1));
                            itemsForSale.set(j + 1, temp);

                            String tempString = sellerUsernames.get(j);
                            sellerUsernames.set(j, sellerUsernames.get(j + 1));
                            sellerUsernames.set(j + 1, tempString);
                        }
                    }
                }

                for (Product product : itemsForSale) {
                    String buttonString =
                        String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>",
                            product.getName().length() <= 30 ?
                                product.getName() : product.getName().substring(0, 27) + "...",
                            product.getStore().getName(),
                            product.getPrice());
                    JButton button = new JButton(buttonString);
                    button.setMargin(new Insets(10, 10, 10, 10));
                    button.setFont(new Font("Arial", Font.PLAIN, 22));
                    button.setBorder(new EmptyBorder(10, 10, 10, 10));
                    listings.put(button, product);
                    listingPanel.add(button);
                    button.addActionListener(productListener);
                }

                listingPanel.revalidate();

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listingPanel.removeAll();
                String searchKey = searchField.getText().toString();
                searchKey = searchKey.toUpperCase();
                int count = 0;
                for (int i = 0; i < itemsForSale.size(); i++) {
                    String compare = itemsForSale.get(i).toString().toUpperCase();
                    if (compare.contains(searchKey)) {
                        Product temp = itemsForSale.get(i);
                        itemsForSale.set(i, itemsForSale.get(count));
                        itemsForSale.set(count, temp);

                        String tempString = sellerUsernames.get(i);
                        sellerUsernames.set(i, sellerUsernames.get(count));
                        sellerUsernames.set(count, tempString);

                        count++;
                    }
                }

                for (Product product : itemsForSale) {
                    String buttonString =
                        String.format("<html>%s<br>Sold by: %s<br>Price: %.2f</html>",
                            product.getName().length() <= 30 ?
                                product.getName() : product.getName().substring(0, 27) + "...",
                            product.getStore().getName(),
                            product.getPrice());
                    JButton button = new JButton(buttonString);
                    button.setMargin(new Insets(10, 10, 10, 10));
                    button.setFont(new Font("Arial", Font.PLAIN, 22));
                    button.setBorder(new EmptyBorder(10, 10, 10, 10));
                    listings.put(button, product);
                    listingPanel.add(button);
                    button.addActionListener(productListener);
                }

                listingPanel.revalidate();
            }
        });
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

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Markey", JOptionPane.ERROR_MESSAGE);
    }

    public String displayInputMessage(String message) {
        return JOptionPane.showInputDialog(null, message, "Markey", JOptionPane.PLAIN_MESSAGE);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }

    public ArrayList<String> getCartSellerUsernames() {
        return cartSellerUsernames;
    }

    public void setCartSellerUsernames(ArrayList<String> cartSellerUsernames) {
        this.cartSellerUsernames = cartSellerUsernames;
    }

    public boolean checkCartStatus() {
        // reading and displaying number of items in cart
        // cart data is stored
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            Product cartProduct = cartItems.get(i);
            for (Product listProduct : itemsForSale) {
                boolean outOfStock = listProduct.getName().equals(cartProduct.getName());
                outOfStock =
                    outOfStock && listProduct.getStore().getName()
                        .equals(cartProduct.getStore().getName());
                outOfStock = outOfStock && listProduct.getPrice() == cartProduct.getPrice();
                outOfStock =
                    outOfStock && listProduct.getDescription().equals(cartProduct.getDescription());
                if (outOfStock && listProduct.getQuantity() < cartProduct.getQuantity()) {
                    toRemove.add(i);
                }
            }
        }
        boolean removed = false;
        for (int i : toRemove) {
            removed = true;
            cartItems.remove(i);
            cartSellerUsernames.remove(i);
            displayInformationMessage(MarketPlace.CART_REM);
        }
        cartLength = cartItems.size();
        return removed;
    }

    public void displayCart() {
        if (cartItems == null || cartItems.size() == 0) {
            displayInformationMessage("You have 0 items in your cart");
            return;
        }
        JButton[] deletes = new JButton[cartItems.size()];
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        final double[] totalPrice = {0};
        JButton checkout = new JButton("Proceed to Checkout");
        checkout.setFont(new Font("Arial", Font.PLAIN, 22));
        JFrame cartFrame = new JFrame("Markey");
        cartFrame.setSize(520, 700);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 4, 5, 4);
        gbc.weightx = 2;
        gbc.gridx = 0;

        JLabel totalLabel = new JLabel(String.format("Total Price: %.2f", totalPrice[0]));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < cartItems.size(); i++) {
            Product p = cartItems.get(i);
            String name = unTruncate(p.getName());
            String storeName = unTruncate(p.getStore().getName());
            String description = unTruncate(p.getDescription());

            JLabel label = new JLabel(String.format("<html>%s<br>Sold by: %s<br>Price: %.2f<br>Description: " +
                    "%s<br>Quantity: " +
                    "%d<br>Final Price: %.2f</html>", name, storeName, p.getPrice(),
                description, p.getQuantity(), p.getPrice() * p.getQuantity()));
            totalPrice[0] += p.getPrice() * p.getQuantity();
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            label.setBorder(BorderFactory.createEtchedBorder());
            panel.add(label, gbc);
            Icon deleteIcon = new ImageIcon("images/delete.png");
            deletes[i] = new JButton(deleteIcon);
            gbc.weightx = 1;
            gbc.gridx = 1;
            panel.add(deletes[i], gbc);
            gbc.weightx = 2;
            gbc.gridx = 0;
            int finalI = i;
            deletes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double toRemove = p.getPrice() * p.getQuantity();
                    int index = cartItems.indexOf(p);
                    cartItems.remove(p);
                    cartSellerUsernames.remove(index);
                    try {
                        methods.storeCart(cartItems, cartSellerUsernames); // todo
                        totalPrice[0] = totalPrice[0] - toRemove;
                        totalLabel.setText(String.format("Total Price: %.2f", totalPrice[0]));
                        cartLength = cartItems.size();
                        cartLabel.setText(String.valueOf(cartLength));
                        label.setVisible(false);
                        deletes[finalI].setVisible(false);
                        if (cartLength == 0) {
                            checkout.setEnabled(false);
                        }
                    } catch (CartNotTrackableException ex) {
                        displayErrorMessage(ex.getMessage());
                    }
                }
            });
        }
        totalLabel.setText(String.format("Total Price: %.2f", totalPrice[0]));
        panel.add(totalLabel, gbc);
        gbc.weightx = 3;
        gbc.gridx = 0;

        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    methods.checkout(cartItems, cartSellerUsernames); // todo
                    cartItems = new ArrayList<>();
                    cartSellerUsernames = new ArrayList<>();
                    cartLength = 0;
                    cartLabel.setText(String.valueOf(cartLength));
                    cartFrame.dispatchEvent(new WindowEvent(cartFrame, WindowEvent.WINDOW_CLOSING));
                    displayPlainMessage("<html>Order Successful!<br>Thank you for purchasing using Markey :)</html>");
                } catch (IOException ex) {
                    displayErrorMessage("Something went wrong :(");
                }
            }
        });

        panel.add(checkout, gbc);
        JScrollPane scrollPane = new JScrollPane(panel);
        cartFrame.add(scrollPane);
        cartFrame.setVisible(true);
    }

    public String unTruncate(String name) {
        if (name.length() > 30) {
            name = name.substring(0, 30) + "-<br>" + unTruncate(name.substring(30));
        }
        return name;
    }

    public void productPage(Product p) {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo

        String name = unTruncate(p.getName());
        String storeName = unTruncate(p.getStore().getName());
        String description = unTruncate(p.getDescription());

        JLabel label = new JLabel(String.format("<html>%s<br>Sold by: %s<br>Price: %.2f<br>Description:%s</html>",
            name, storeName, p.getPrice(),
            description));

        label.setFont(new Font("Arial", Font.PLAIN, 22));

        JFrame productFrame = new JFrame("Markey");
        JPanel panel = new JPanel();
        panel.add(label);
        Icon icon = new ImageIcon("images/cart.png");
        JButton addToCart = new JButton(icon);
        addToCart.setPreferredSize(new Dimension(36, 36));
        panel.add(addToCart);
        productFrame.add(panel, BorderLayout.NORTH);

        JTextField quantityField = new HintTextField("Enter quantity");
        quantityField.setColumns("Enter Quantity  ".length());
        quantityField.setFont(new Font("Arial", Font.PLAIN, 22));
        JPanel panel1 = new JPanel();
        panel1.add(quantityField);
        productFrame.add(panel1, BorderLayout.SOUTH);
        productFrame.setSize(600, 200);

        productFrame.setVisible(true);

        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quantity = quantityField.getText().toString();
                try {
                    int q = Integer.parseInt(quantity);
                    if (q <= 0) {
                        displayErrorMessage("Quantity cannot be 0 or negative");
                    } else if (q > p.getQuantity()) {
                        throw new NotInStockException("Item not in stock :(");
                    } else {
                        Product cartProduct =
                            new Product(p.getName(), p.getStore(), q, p.getPrice(), p.getDescription());
                        cartItems.add(cartProduct);
                        cartSellerUsernames.add(sellerUsernames.get(itemsForSale.indexOf(p)));
                        int forRemove = cartItems.size() - 1;
                        try {
                            methods.storeCart(cartItems, cartSellerUsernames); // todo
                            cartLength = cartItems.size();
                            cartLabel.setText(String.valueOf(cartLength));
                            displayInformationMessage(MarketPlace.ADDED_TO_CART);
                        } catch (CartNotTrackableException exception1) {
                            cartItems.remove(forRemove);
                            cartSellerUsernames.remove(forRemove);
                            displayInformationMessage(exception1.getMessage());
                        }
                    }
                } catch (NumberFormatException exception) {
                    displayErrorMessage("Please enter a quantity");
                } catch (NotInStockException ex) {
                    displayErrorMessage(ex.getMessage());
                }
            }
        });
    }

    public void displayPurchaseHistory() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        ArrayList<Product> purchases = methods.viewPurchaseHistory(); // todo
        if (purchases == null) {
            displayInformationMessage("You haven't made any purchases yet");
        } else if (purchases.isEmpty()) {
            displayErrorMessage("Something went wrong :(");
        } else {
            JFrame frame = new JFrame("Markey");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            for (Product purchase : purchases) {
                String name = unTruncate(purchase.getName());
                String storeName = unTruncate(purchase.getStore().getName());
                String description = unTruncate(purchase.getDescription());
                JLabel purchaseLabel = new JLabel(String.format("<html><br>%s<br>Sold by: %s<br>Price %" +
                        ".2f<br>Description: %s<br>Quantity: %d<br>Final Price: %.2f<br></html>", name, storeName,
                    purchase.getPrice(), description, purchase.getQuantity(),
                    (purchase.getPrice() * purchase.getQuantity())), SwingConstants.CENTER);
                purchaseLabel.setBorder(BorderFactory.createBevelBorder(0));
                purchaseLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(purchaseLabel);
            }
            frame.setSize(520, 700);
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

    public void customerCsv() {
        JFrame frame = new JFrame("Markey");
        frame.setSize(1600, 900);
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        try {
            StringBuilder stringBuilder = methods.customerCsvExport(); // todo
            if (stringBuilder == null) {
                displayInformationMessage("Please try again later");
            } else {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!fileName.endsWith(".csv")) {
                        fileName = fileName.concat(".csv");
                    }
                    PrintWriter writer = new PrintWriter(fileName);
                    writer.write(stringBuilder.toString());
                    writer.close();
                }
            }
        } catch (IOException e) {
            displayErrorMessage("Something went wrong");
        }
    }

    public ArrayList<Product> getSellerItems() {
        return sellerItems;
    }

    public void setSellerItems(ArrayList<Product> sellerItems) {
        this.sellerItems = sellerItems;
    }

    public ArrayList<Store> getSellerStores() {
        return sellerStores;
    }

    public void setSellerStores(ArrayList<Store> sellerStores) {
        this.sellerStores = sellerStores;
    }

    public void seeListings() {
        if (sellerItems.isEmpty()) {
            displayInformationMessage("You don't have anything for sale");
        } else {
            JFrame frame = new JFrame("Markey");
            JPanel panel = new JPanel(new GridLayout(0, 1));
            for (Product listing : sellerItems) {
                String name = unTruncate(listing.getName());
                String storeName = unTruncate(listing.getStore().getName());
                String description = unTruncate(listing.getDescription());
                JLabel listingLabel = new JLabel(String.format("<html><br>%s<br>Sold by: %s<br>Price %" +
                        ".2f<br>Description: %s<br>Quantity: %d<br>Final Price: %.2f<br></html>", name, storeName,
                    listing.getPrice(), description, listing.getQuantity(),
                    (listing.getPrice() * listing.getQuantity())), SwingConstants.CENTER);
                listingLabel.setBorder(BorderFactory.createEtchedBorder(0));
                listingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(listingLabel);
            }
            frame.setSize(520, 700);
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

    public void openStore() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        String storeName = displayInputMessage("Enter store name");
        if (storeName == null) {
            return;
        }
        while (storeName.contains("_") || storeName.contains(";") || storeName.contains(",") || storeName.isEmpty()) {
            displayErrorMessage("<html>Store name cannot be empty<br>Store name cannot contain '_' or ';' or '," +
                "'</html>");
            storeName = displayInputMessage("Enter store name");
            if (storeName == null) {
                return;
            }
        }
        boolean storeExists = false;
        for (Store value : sellerStores) {
            if (value.getName().equals(storeName)) {
                storeExists = true;
                break;
            }
        }
        if (storeExists) {
            displayErrorMessage("You already have a store of the same name");
            return;
        }

        displayInformationMessage("<html>You need to have at least one item on sale<br>for a store to remain " +
            "open<br>A store with nothing for sale<br>will be automatically closed</html>");

        JFrame frame = new JFrame("Markey");
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(6, 6, 6, 6);

        JTextField prodNameText = new HintTextField("Item Name");
        prodNameText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodNameText.setColumns(20);
        JTextField prodPriceText = new HintTextField("Item price");
        prodPriceText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodPriceText.setColumns(20);
        JTextField prodDescriptionText = new HintTextField("Item Description");
        prodDescriptionText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodDescriptionText.setColumns(20);
        JTextField prodQuantityText = new HintTextField("Quantity available in stock");
        prodQuantityText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodQuantityText.setColumns(20);
        JButton confirmButton = new JButton("Create listing");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 18));

        panel.add(prodNameText, gbc);
        panel.add(prodPriceText, gbc);
        panel.add(prodDescriptionText, gbc);
        panel.add(prodQuantityText, gbc);
        panel.add(confirmButton, gbc);

        frame.add(panel);
        frame.setVisible(true);

        String finalStoreName = storeName;
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prodName = prodNameText.getText().toString();
                if (prodName.contains("_") || prodName.contains(";")) {
                    displayErrorMessage("Item name cannot contain '_' or ';'");
                    return;
                }
                String prodPriceString = prodPriceText.getText().toString();
                double prodPrice = 0;
                try {
                    prodPrice = Double.parseDouble(prodPriceString);
                    if (prodPrice <= 0) {
                        displayErrorMessage("Price cannot be 0 or lower");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    displayErrorMessage("Enter a number for price");
                    return;
                }
                String prodDescription = prodDescriptionText.getText().toString();
                if (prodDescription.contains("_") || prodDescription.contains(";")) {
                    displayErrorMessage("Item description cannot contain '_' or ';'");
                    return;
                }
                int prodQuantity = 0;
                String prodQuantityString = prodQuantityText.getText().toString();
                try {
                    prodQuantity = Integer.parseInt(prodQuantityString);
                } catch (NumberFormatException ex) {
                    displayErrorMessage("Enter a number for quantity");
                    return;
                }
                if (prodQuantity <= 0) {
                    displayErrorMessage("Quantity cannot be 0 or lower");
                    return;
                }
                if (prodName.isEmpty() || prodDescription.isEmpty()) {
                    displayErrorMessage("Please enter required details");
                    return;
                }
                Store store = new Store(finalStoreName);
                sellerStores.add(store);
                sellerItems.add(new Product(prodName, store, prodQuantity, prodPrice, prodDescription));
                try {
                    methods.createProduct(prodName, store, prodQuantity, prodPrice, prodDescription); // todo
                } catch (IOException ex) {
                    displayErrorMessage("Something went wrong :(");
                    return;
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                displayPlainMessage("<html><b>Thank you for using Markey</b><br>Listing successfully created");
            }
        });
    }

    public void addProduct() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        JFrame frame = new JFrame("Markey");
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel chooseStore = new JLabel("Choose store:");
        chooseStore.setFont(new Font("Arial", Font.PLAIN, 16));
        JComboBox<Store> prodStoreDrop = new JComboBox<>(sellerStores.toArray(new Store[0]));
        prodStoreDrop.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField prodNameText = new HintTextField("Item Name");
        prodNameText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodNameText.setColumns(20);
        JTextField prodPriceText = new HintTextField("Item price");
        prodPriceText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodPriceText.setColumns(20);
        JTextField prodDescriptionText = new HintTextField("Item Description");
        prodDescriptionText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodDescriptionText.setColumns(20);
        JTextField prodQuantityText = new HintTextField("Quantity available in stock");
        prodQuantityText.setFont(new Font("Arial", Font.PLAIN, 18));
        prodQuantityText.setColumns(20);
        JButton confirmButton = new JButton("Create listing");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 18));

        gbc.insets = new Insets(6, 6, 3, 6);
        panel.add(chooseStore, gbc);
        gbc.insets = new Insets(3, 6, 6, 6);
        panel.add(prodStoreDrop, gbc);
        gbc.insets = new Insets(6, 6, 6, 6);
        panel.add(prodNameText, gbc);
        panel.add(prodPriceText, gbc);
        panel.add(prodDescriptionText, gbc);
        panel.add(prodQuantityText, gbc);
        panel.add(confirmButton, gbc);

        frame.add(panel);
        frame.setVisible(true);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Store store = (Store) prodStoreDrop.getSelectedItem();

                String prodName = prodNameText.getText().toString();
                if (prodName.contains("_") || prodName.contains(";")) {
                    displayErrorMessage("Item name cannot contain '_' or ';'");
                    return;
                }
                String prodPriceString = prodPriceText.getText().toString();
                double prodPrice = 0;
                try {
                    prodPrice = Double.parseDouble(prodPriceString);
                    if (prodPrice <= 0) {
                        displayErrorMessage("Price cannot be 0 or lower");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    displayErrorMessage("Enter a number for price");
                    return;
                }
                String prodDescription = prodDescriptionText.getText().toString();
                if (prodDescription.contains("_") || prodDescription.contains(";")) {
                    displayErrorMessage("Item description cannot contain '_' or ';'");
                    return;
                }
                int prodQuantity = 0;
                String prodQuantityString = prodQuantityText.getText().toString();
                try {
                    prodQuantity = Integer.parseInt(prodQuantityString);
                } catch (NumberFormatException ex) {
                    displayErrorMessage("Enter a number for quantity");
                    return;
                }
                if (prodQuantity <= 0) {
                    displayErrorMessage("Quantity cannot be 0 or lower");
                    return;
                }
                if (prodName.isEmpty() || prodDescription.isEmpty()) {
                    displayErrorMessage("Please enter required details");
                    return;
                }
                sellerStores.add(store);
                sellerItems.add(new Product(prodName, store, prodQuantity, prodPrice, prodDescription));
                try {
                    methods.createProduct(prodName, store, prodQuantity, prodPrice, prodDescription); // todo
                } catch (IOException ex) {
                    displayErrorMessage("Something went wrong :(");
                    return;
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                displayPlainMessage("<html><b>Thank you for using Markey</b><br>Listing successfully created");
            }
        });
    }

    public void removeProduct() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        JFrame frame = new JFrame("Markey");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = SwingConstants.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        for (Product product : sellerItems) {
            JLabel productLabel = new JLabel(String.format(
                "<html><br>%s<br>Store: %s<br>Price: %.2f<br>Description: %s<br>Available in stock: %d<br></html>",
                unTruncate(product.getName()), unTruncate(product.getStore().getName()),
                product.getPrice(), unTruncate(product.getDescription()), product.getQuantity()),
                SwingConstants.CENTER);
            productLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            productLabel.setBorder(BorderFactory.createBevelBorder(0));
            final String checkString = username + ";" + product.toString();
            JButton deleteButton = new JButton(new ImageIcon("images/delete.png"));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        methods.deleteProduct(checkString); // todo
                    } catch (IOException ex) {
                        displayErrorMessage("Something went wrong :(");
                        return;
                    }
                    sellerItems.remove(product);
                    productLabel.setVisible(false);
                    deleteButton.setVisible(false);
                }
            });
            gbc.gridx = 0;
            panel.add(productLabel, gbc);
            gbc.gridx = 1;
            panel.add(deleteButton, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.setSize(500, 700);
        frame.setVisible(true);
    }

    public void editProduct() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        JFrame frame = new JFrame("Markey");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = SwingConstants.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        for (Product product : sellerItems) {
            JLabel productLabel = new JLabel(String.format(
                "<html><br>%s<br>Store: %s<br>Price: %.2f<br>Description: %s<br>Available in stock: %d<br></html>",
                unTruncate(product.getName()), unTruncate(product.getStore().getName()),
                product.getPrice(), unTruncate(product.getDescription()), product.getQuantity()),
                SwingConstants.CENTER);
            productLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            productLabel.setBorder(BorderFactory.createBevelBorder(0));
            final String checkString = username + ";" + product.toString();
            JButton editButton = new JButton(new ImageIcon("images/edit.png"));
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Markey");
                    frame.setSize(600, 400);
                    JPanel panel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.VERTICAL;
                    gbc.gridx = 0;
                    gbc.weightx = 1;
                    gbc.insets = new Insets(6, 6, 6, 6);

                    JTextField prodNameText = new HintTextField("Item Name");
                    prodNameText.setFont(new Font("Arial", Font.PLAIN, 18));
                    prodNameText.setColumns(20);
                    prodNameText.setText(product.getName());
                    JTextField prodPriceText = new HintTextField("Item price");
                    prodPriceText.setFont(new Font("Arial", Font.PLAIN, 18));
                    prodPriceText.setColumns(20);
                    prodPriceText.setText(String.format("%.2f", product.getPrice()));
                    JTextField prodDescriptionText = new HintTextField("Item Description");
                    prodDescriptionText.setFont(new Font("Arial", Font.PLAIN, 18));
                    prodDescriptionText.setColumns(20);
                    prodDescriptionText.setText(product.getDescription());
                    JTextField prodQuantityText = new HintTextField("Quantity available in stock");
                    prodQuantityText.setFont(new Font("Arial", Font.PLAIN, 18));
                    prodQuantityText.setColumns(20);
                    prodQuantityText.setText(String.valueOf(product.getQuantity()));
                    JButton confirmButton = new JButton("Edit listing");
                    confirmButton.setFont(new Font("Arial", Font.PLAIN, 18));

                    panel.add(prodNameText, gbc);
                    panel.add(prodPriceText, gbc);
                    panel.add(prodDescriptionText, gbc);
                    panel.add(prodQuantityText, gbc);
                    panel.add(confirmButton, gbc);

                    frame.add(panel);
                    frame.setVisible(true);

                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String prodName = prodNameText.getText().toString();
                            if (prodName.contains("_") || prodName.contains(";")) {
                                displayErrorMessage("Item name cannot contain '_' or ';'");
                                return;
                            }
                            String prodPriceString = prodPriceText.getText().toString();
                            double prodPrice = 0;
                            try {
                                prodPrice = Double.parseDouble(prodPriceString);
                                if (prodPrice <= 0) {
                                    displayErrorMessage("Price cannot be 0 or lower");
                                    return;
                                }
                            } catch (NumberFormatException ex) {
                                displayErrorMessage("Enter a number for price");
                                return;
                            }
                            String prodDescription = prodDescriptionText.getText().toString();
                            if (prodDescription.contains("_") || prodDescription.contains(";")) {
                                displayErrorMessage("Item description cannot contain '_' or ';'");
                                return;
                            }
                            int prodQuantity = 0;
                            String prodQuantityString = prodQuantityText.getText().toString();
                            try {
                                prodQuantity = Integer.parseInt(prodQuantityString);
                            } catch (NumberFormatException ex) {
                                displayErrorMessage("Enter a number for quantity");
                                return;
                            }
                            if (prodQuantity <= 0) {
                                displayErrorMessage("Quantity cannot be 0 or lower");
                                return;
                            }
                            if (prodName.isEmpty() || prodDescription.isEmpty()) {
                                displayErrorMessage("Please enter required details");
                                return;
                            }
                            Product replaceProduct = new Product(prodName, product.getStore(), prodQuantity, prodPrice,
                                prodDescription);
                            String replaceString = username + ";" + replaceProduct.toString();

                            try {
                                methods.editProduct(checkString, replaceString); // todo
                            } catch (IOException ex) {
                                displayErrorMessage("Something went wrong :(");
                                return;
                            }
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            displayInformationMessage("Edit successful");
                            productLabel.setText(String.format(
                                "<html><br>%s<br>Store: %s<br>Price: %.2f<br>Description: %s<br>Available in stock: " +
                                    "%d<br></html>",
                                unTruncate(replaceProduct.getName()), unTruncate(replaceProduct.getStore().getName()),
                                replaceProduct.getPrice(), unTruncate(replaceProduct.getDescription()),
                                replaceProduct.getQuantity()));
                        }
                    });
                }
            });
            gbc.gridx = 0;
            panel.add(productLabel, gbc);
            gbc.gridx = 1;
            panel.add(editButton, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.setSize(500, 700);
        frame.setVisible(true);
    }

    public void seeCarts() {
        MarketPlaceMethods methods = new MarketPlaceMethods(username); // todo
        try {
            ArrayList<ArrayList<String>> data = methods.seeCarts(); // todo
            JFrame frame = new JFrame("Markey");
            frame.setSize(1600, 900);
            JPanel panel = new JPanel(new GridLayout(0, 3));
            for (ArrayList<String> cart : data) {
                String datum = "<html>";
                for (String line : cart) {
                    datum = datum + unTruncate(line) + "<br>";
                }
                datum = datum + "</html>";
                JLabel cartLabel = new JLabel(datum);
                cartLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                JButton button = new JButton(cart.get(0));
                button.setFont(new Font("Arial", Font.PLAIN, 22));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame frame1 = new JFrame("Markey");
                        JPanel panel1 = new JPanel();
                        panel1.add(cartLabel);
                        JScrollPane scrollPane = new JScrollPane(panel1);
                        frame1.add(scrollPane);
                        frame1.setSize(600, 300);
                        frame1.setVisible(true);
                    }
                });
                panel.add(button);
            }
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        } catch (IOException e) {
            displayErrorMessage("Something went wrong :(");
        }
    }
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MarketPlaceGUI implements Runnable {

    public static final String CART_REM = "An item was removed from your cart because it was out of stock";
    public static final String ADDED_TO_CART = "Added to your cart :)";


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
            } else if (e.getSource() == dbButton) {
                CustomerDashboardGUI cdGui = new CustomerDashboardGUI(username);
                cdGui.main();
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
            } else if (e.getSource() == copyCsvButton) {
                sellerCsv();
            } else if (e.getSource() == copyDbButton) {
                SellerDashboardGUI sellerDashboardGUI = new SellerDashboardGUI(username);
                sellerDashboardGUI.main();
            }
        }
    };


    public void main(boolean customer, String username) {
        MarketPlaceGUI gui = new MarketPlaceGUI();
        gui.setCustomer(customer);
        gui.setUsername(username);

        // reading products.txt
        Listing products = Decoder.decodeListing(Client.main("MPM;" + username + ";RPT"));
        // methods.readProductsTxt();
        gui.setItemsForSale(products.getProducts());
        gui.setSellerUsernames(products.getSellers());

        if (customer) {
            // reading and updating carts
            gui.setCartItems(new ArrayList<>());
            gui.setCartSellerUsernames(new ArrayList<>());
            try {
                String reply = Client.main("MPM;" + username + ";RCC");
                if (reply.startsWith("ERROR-CNT")) {
                    throw new CartNotTrackableException(reply.substring(reply.indexOf("ERROR-CNT-") + 10));
                }
                Listing readCart = Decoder.decodeListing(reply);
                gui.setCartItems(readCart.getProducts());
                gui.setCartSellerUsernames(readCart.getSellers());
                boolean removed = gui.checkCartStatus();
                String productsString = "";
                String sellers = "";
                for (Product product : gui.getCartItems()) {
                    productsString = productsString + product.toString() + "___";
                }
                if (productsString.endsWith("___")) {
                    productsString = productsString.substring(0, productsString.length() - 3);
                }
                for (String seller : gui.getCartSellerUsernames()) {
                    sellers = sellers + seller + ",";
                }
                if (sellers.endsWith(",")) {
                    sellers = sellers.substring(0, sellers.length() - 1);
                }
                if (removed) {
                    String reply1 = Client.main("MPM;" + username + ";SC{" + productsString + ";" + sellers +
                        "}");
                }
            } catch (Exception e) {
                if (!e.getMessage().equals("Your cart is empty") && !(e instanceof IndexOutOfBoundsException)) {
                    gui.displayErrorMessage(e.getMessage());
                }
            }
        } else {
            // seller
            gui.setSellerItems(new ArrayList<>());
            gui.setSellerStores(new ArrayList<>());
            ProductsAndStores productsAndStores =
                Decoder.decodeProductsAndStores(Client.main("MPM;" + username + ";RS"));
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

        refreshIcon = new ImageIcon("images/refresh.png");
        refreshButton = new JButton(refreshIcon);
        refreshButton.setToolTipText("<html>refresh</html>");
        refreshButton.setPreferredSize(new Dimension(36, 36));

        searchIcon = new ImageIcon("images/search.png");
        searchButton = new JButton(searchIcon);
        searchButton.setPreferredSize(new Dimension(36, 36));
        searchField = new HintTextField("search");
        searchField.setColumns(16);
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
        dbButton.addActionListener(customerListener);

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
        copyDbButton.addActionListener(sellerListener);

        copyCsvButton = new JButton(".csv");
        copyCsvButton.setFont(new Font("Arial", Font.PLAIN, 22));
        copyCsvButton.setToolTipText("<html> import or export csv file with your data</html>");
        copyCsvButton.addActionListener(sellerListener);

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

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Listing products = Decoder.decodeListing(Client.main("MPM;" + username + ";RPT"));

                itemsForSale = products.getProducts();
                sellerUsernames = products.getSellers();

                listingPanel.repaint();
                listingPanel.removeAll();

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
                    if (customer) {
                        button.addActionListener(productListener);
                    }
                }
                if (customer) {
                    boolean removed = checkCartStatus();
                    if (removed) {
                        String productsString = "";
                        String sellers = "";
                        for (Product product : cartItems) {
                            productsString = productsString + product.toString() + "___";
                        }
                        if (productsString.endsWith("___")) {
                            productsString = productsString.substring(0, productsString.length() - 3);
                        }
                        for (String seller : cartSellerUsernames) {
                            sellers = sellers + seller + ",";
                        }
                        if (sellers.endsWith(",")) {
                            sellers = sellers.substring(0, sellers.length() - 1);
                        }
                        String reply1 = Client.main("MPM;" + username + ";SC{" + productsString + ";" + sellers +
                            "}");
                    }
                    cartLabel.setText(String.valueOf(cartLength));
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
            displayInformationMessage(CART_REM);
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
                        String productsString = "";
                        String sellers = "";
                        for (Product product : cartItems) {
                            productsString = productsString + product.toString() + "___";
                        }
                        if (productsString.endsWith("___")) {
                            productsString = productsString.substring(0, productsString.length() - 3);
                        }
                        for (String seller : cartSellerUsernames) {
                            sellers = sellers + seller + ",";
                        }
                        if (sellers.endsWith(",")) {
                            sellers = sellers.substring(0, sellers.length() - 1);
                        }
                        String reply1 =
                            Client.main("MPM;" + username + ";SC{" + productsString + ";" + sellers + "}");
                        // methods.storeCart(cartItems, cartSellerUsernames);
                        if (reply1.startsWith("ERROR-CNT")) {
                            throw new CartNotTrackableException(reply1.substring(
                                reply1.indexOf("ERROR-CNT-") + 10)
                            );
                        }
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
                    Listing listing = Decoder.decodeListing(Client.main("MPM;" + username + ";RPT"));
                    itemsForSale = listing.getProducts();
                    sellerUsernames = listing.getSellers();
                    boolean removed = checkCartStatus();
                    String productsString = "";
                    String sellers = "";
                    for (Product product : cartItems) {
                        productsString = productsString + product.toString() + "___";
                    }
                    if (productsString.endsWith("___")) {
                        productsString = productsString.substring(0, productsString.length() - 3);
                    }
                    for (String seller : cartSellerUsernames) {
                        sellers = sellers + seller + ",";
                    }
                    if (sellers.endsWith(",")) {
                        sellers = sellers.substring(0, sellers.length() - 1);
                    }
                    if (cartItems.isEmpty()) {
                        String ignored = Client.main("MPM;" + username + ";SC{" + productsString + ";" + sellers + "}");
                        return;
                    }
                    String reply1 =
                        Client.main("MPM;" + username + ";COUT{" + productsString + ";" + sellers + "}");
                    if (reply1.startsWith("ERROR")) {
                        throw new IOException(reply1);
                    }
                    // methods.checkout(cartItems, cartSellerUsernames);
                    cartItems = new ArrayList<>();
                    cartSellerUsernames = new ArrayList<>();
                    cartLength = 0;
                    cartLabel.setText(String.valueOf(cartLength));
                    cartFrame.dispatchEvent(new WindowEvent(cartFrame, WindowEvent.WINDOW_CLOSING));
                    displayPlainMessage("<html>Order Successful!<br>Thank you for purchasing using Markey :)</html>");
                    refreshButton.doClick();
                } catch (IOException ex) {
                    displayErrorMessage("Something went wrong :(");
                    displayErrorMessage(ex.getMessage());
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

        String name = unTruncate(p.getName());
        String storeName = unTruncate(p.getStore().getName());
        String description = unTruncate(p.getDescription());

        JLabel label = new JLabel(String.format("<html>%s<br>Sold by: %s<br>Price: %" +
                ".2f<br>Description:%s<br>Available in stock: %d</html>",
            name, storeName, p.getPrice(),
            description, p.getQuantity()));

        label.setFont(new Font("Arial", Font.PLAIN, 22));

        JFrame productFrame = new JFrame("Markey");
        JPanel panel = new JPanel();
        panel.add(label);
        Icon listIcon = new ImageIcon("images/list.png");
        JButton seeReviews = new JButton(listIcon);
        seeReviews.setToolTipText("<html>See reviews</html>");
        panel.add(seeReviews);

        JTextField quantityField = new HintTextField("Enter quantity");
        quantityField.setColumns("Enter Quantity  ".length());
        quantityField.setFont(new Font("Arial", Font.PLAIN, 22));
        JPanel panel1 = new JPanel();
        panel1.add(quantityField);
        Icon icon = new ImageIcon("images/cart.png");
        JButton addToCart = new JButton(icon);
        addToCart.setPreferredSize(new Dimension(36, 36));
        addToCart.setToolTipText("<html>Add to cart</html>");
        productFrame.add(panel, BorderLayout.NORTH);
        panel1.add(addToCart);
        productFrame.add(panel1, BorderLayout.SOUTH);
        productFrame.setSize(600, 300);

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
                            String productsString = "";
                            String sellers = "";
                            for (Product product : cartItems) {
                                productsString = productsString + product.toString() + "___";
                            }
                            if (productsString.endsWith("___")) {
                                productsString = productsString.substring(0, productsString.length() - 3);
                            }
                            for (String seller : cartSellerUsernames) {
                                sellers = sellers + seller + ",";
                            }
                            if (sellers.endsWith(",")) {
                                sellers = sellers.substring(0, sellers.length() - 1);
                            }
                            String reply1 =
                                Client.main("MPM;" + username + ";SC{" + productsString + ";" + sellers + "}");
                            // methods.storeCart(cartItems, cartSellerUsernames);
                            if (reply1.startsWith("ERROR-CNT")) {
                                throw new CartNotTrackableException(reply1.substring(
                                    reply1.indexOf("ERROR-CNT-") + 10)
                                );
                            }
                            cartLength = cartItems.size();
                            cartLabel.setText(String.valueOf(cartLength));
                            displayInformationMessage(ADDED_TO_CART);
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
        seeReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviews(p);
            }
        });
    }

    public void reviews(Product p) {
        JFrame reviewsFrame = new JFrame("Markey");
        reviewsFrame.setSize(500, 700);
        try {
            String reply = Client.main("MPM;" + username + ";RR;" + p.toString());
            if (reply.startsWith("ERROR")) {
                throw new IOException();
            }
            ArrayList<String> reviews = Decoder.decodeReviews(reply);
            JPanel revPanel = new JPanel(new GridLayout(0, 1));
            for (String line : reviews) {
                JLabel label1 = new JLabel("<html>" + line + "</html>");
                label1.setFont(new Font("Arial", Font.PLAIN, 20));
                label1.setBorder(BorderFactory.createEtchedBorder());
                revPanel.add(label1);
            }
            JScrollPane scrollPane = new JScrollPane(revPanel);
            reviewsFrame.add(scrollPane);
            reviewsFrame.setVisible(true);
        } catch (IOException ex) {
            displayErrorMessage("Something went wrong");
        }
    }

    public void displayPurchaseHistory() {
        String reply = Client.main("MPM;" + username + ";VPH");
        ArrayList<Product> purchases = null;
        if (reply.isEmpty()) {
            purchases = new ArrayList<>();
        } else if (reply.equals("ERROR502")) {
            displayErrorMessage(reply);
        } else {
            purchases = Decoder.decodeProducts(reply);
        }
        if (purchases == null) {
            displayInformationMessage("You haven't made any purchases yet");
        } else if (purchases.isEmpty()) {
            displayErrorMessage("Something went wrong :(");
        } else {
            JFrame frame = new JFrame("Markey");
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 4, 4, 4);
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
                gbc.gridx = 0;
                panel.add(purchaseLabel, gbc);
                JButton reviewButton = new JButton("Write Review");
                reviewButton.setFont(new Font("Arial", Font.PLAIN, 14));
                gbc.gridx = 1;
                panel.add(reviewButton, gbc);
                reviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame reviewFrame = new JFrame("Markey");
                        JPanel reviewPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
                        JLabel starLabel = new JLabel("Stars:");
                        starLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        starLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                        JPanel starPanel = new JPanel();
                        JRadioButton[] stars = new JRadioButton[5];
                        for (int i = 0; i < stars.length; i++) {
                            int finalI = i;
                            stars[i] = new JRadioButton();
                            stars[i].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    for (int j = 0; j <= finalI; j++) {
                                        stars[j].setSelected(true);
                                    }
                                    for (int j = finalI + 1; j < stars.length; j++) {
                                        stars[j].setSelected(false);
                                    }

                                }
                            });
                            starPanel.add(stars[i]);
                        }
                        JTextField reviewText = new HintTextField("Write your review...");
                        reviewText.setFont(new Font("Arial", Font.PLAIN, 18));
                        reviewText.setColumns(35);
                        JButton sendReviewButton = new JButton("Ok");
                        sendReviewButton.setFont(new Font("Arial", Font.PLAIN, 18));
                        reviewPanel.add(starLabel, gridBagConstraints);
                        reviewPanel.add(starPanel, gridBagConstraints);
                        reviewPanel.add(reviewText, gridBagConstraints);
                        reviewPanel.add(sendReviewButton, gridBagConstraints);
                        reviewFrame.add(reviewPanel);
                        reviewFrame.setSize(600, 200);
                        reviewFrame.setVisible(true);
                        sendReviewButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int starReview = 0;
                                for (int i = stars.length - 1; i >= 0; i--) {
                                    if (stars[i].isSelected()) {
                                        starReview = i + 1;
                                        break;
                                    }
                                }
                                String starReviewString = "";
                                for (int i = 0; i < starReview; i++) {
                                    starReviewString = starReviewString + "* ";
                                }
                                String review = reviewText.getText().toString();
                                try {
                                    String reply1 =
                                        Client.main(
                                            "MPM;" + username + ";RW;" + purchase.toString() + ";" + starReviewString +
                                                ";" + review);
                                    if (reply1.startsWith("ERROR")) {
                                        throw new IOException();
                                    }
                                    reviewFrame.dispatchEvent(new WindowEvent(reviewFrame, WindowEvent.WINDOW_CLOSING));
                                    displayPlainMessage("Reviewed successfully");
                                } catch (IOException ex) {
                                    displayErrorMessage("Something went wrong :(");
                                }
                            }
                        });
                    }
                });
            }
            frame.setSize(560, 700);
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

    public void customerCsv() {
        JFrame frame = new JFrame("Markey");
        frame.setSize(1600, 900);
        try {
            String reply = Client.main("MPM;" + username + ";CCSV");
            StringBuilder stringBuilder;
            if (reply == null || reply.equals("null")) {
                stringBuilder = null;
            } else if (reply.startsWith("ERROR")) {
                throw new IOException();
            } else {
                stringBuilder = Decoder.decodeCsvString(reply);
            }
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
            JPanel panel = new JPanel(new GridLayout(0, 2));
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
                Icon listIcon = new ImageIcon("images/list.png");
                JButton seeReviewsButton = new JButton(listIcon);
                panel.add(seeReviewsButton);
                seeReviewsButton.setToolTipText("<html> See reviews </html>");
                seeReviewsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reviews(listing);
                    }
                });
            }
            frame.setSize(600, 700);
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

    public void openStore() {
        String storeName = displayInputMessage("Enter store name");
        if (storeName == null) {
            return;
        }
        while (storeName.contains("_") || storeName.contains(";") || storeName.isEmpty()) {
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
                    Product product = new Product(prodName, store, prodQuantity, prodPrice, prodDescription);
                    String reply = Client.main("MPM;" + username + ";CP;" + product.toString());
                    if (reply.startsWith("ERROR")) {
                        throw new IOException();
                    }
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
                    Product product = new Product(prodName, store, prodQuantity, prodPrice, prodDescription);
                    String reply = Client.main("MPM;" + username + ";CP;" + product.toString());
                    if (reply.startsWith("ERROR")) {
                        throw new IOException();
                    }
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
                        String reply = Client.main("MPM___" + username + "___RP___" + checkString);
                        if (reply.startsWith("ERROR")) {
                            throw new IOException();
                        }
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
                                String reply =
                                    Client.main("MPM___" + username + "___EP___" + checkString + "___" + replaceString);
                                if (reply.startsWith("ERROR")) {
                                    throw new IOException();
                                }
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
        try {
            String reply = Client.main("MPM;" + username + ";SEEC");
            if (reply.startsWith("ERROR")) {
                throw new IOException();
            }
            ArrayList<ArrayList<String>> data = Decoder.decodeArrayListOfArrayLists(reply);
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

    public void sellerCsv() {

        JFrame frame = new JFrame("Markey");
        JPanel panel = new JPanel();
        JRadioButton importButton = new JRadioButton("Create listings from a csv file");
        importButton.setFont(new Font("Arial", Font.PLAIN, 20));
        JRadioButton exportButton = new JRadioButton("Download your listings' data as a csv file");
        exportButton.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(importButton);
        panel.add(exportButton);
        frame.add(panel, BorderLayout.NORTH);
        JButton okButton = new JButton("Confirm");
        okButton.setFont(new Font("Arial", Font.PLAIN, 20));
        JPanel panel1 = new JPanel();
        panel1.add(okButton);
        frame.add(panel1, BorderLayout.SOUTH);
        frame.setSize(800, 150);
        frame.setVisible(true);

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportButton.setSelected(false);
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importButton.setSelected(false);
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (exportButton.isSelected()) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showSaveDialog(frame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Product p : sellerItems) {
                            stringBuilder.append(p.getStore().getName());
                            stringBuilder.append(',');
                            stringBuilder.append(p.getName());
                            stringBuilder.append(',');
                            stringBuilder.append(p.getDescription());
                            stringBuilder.append(',');
                            stringBuilder.append(p.getPrice());
                            stringBuilder.append(',');
                            stringBuilder.append(p.getQuantity());
                            stringBuilder.append('\n');
                        }
                        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                        if (!fileName.endsWith(".csv")) {
                            fileName = fileName.concat(".csv");
                        }
                        try {
                            PrintWriter writer = new PrintWriter(fileName);
                            writer.write(stringBuilder.toString());
                            writer.close();
                            displayPlainMessage("<html>File saved<br>The csv file is in the following " +
                                "format:<br>|store_name|item_name|item_description|item_price|item_in_stock|");
                        } catch (FileNotFoundException ex) {
                            displayErrorMessage("Something went wrong :(");
                        }
                    }
                } else if (importButton.isSelected()) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    displayPlainMessage("<html>Make sure that the csv file is in the following " +
                        "format:<br>|store_name|item_name|item_description|item_price|item_in_stock|<br>Also make " +
                        "sure that no element has any commas \",\"<br>Also please make sure that you have no headings" +
                        " for the columns");
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showOpenDialog(frame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                        ArrayList<Product> newProducts = new ArrayList<>();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(fileName));
                            String line = br.readLine();
                            while (line != null) {
                                String[] prodDetails = line.split(",", -1);
                                Store store = new Store(prodDetails[0]);
                                String name = prodDetails[1];
                                String description = prodDetails[2];
                                double price = Double.parseDouble(prodDetails[3]);
                                int quantity = Integer.parseInt(prodDetails[4]);
                                newProducts.add(new Product(name, store, quantity, price, description));
                                line = br.readLine();
                            }
                            br.close();
                            String pLine = "";
                            for (Product p : newProducts) {
                                pLine = pLine + p.toString() + "___";
                            }
                            if (pLine.endsWith("___")) {
                                pLine = pLine.substring(0, pLine.length() - 3);
                            }
                            String reply = Client.main("MPM;" + username + ";SelCSVI;" + pLine);
                            if (reply.startsWith("ERROR")) {
                                throw new IOException(reply);
                            }
                            displayPlainMessage("Import successful!");
                        } catch (Exception ex) {
                            displayErrorMessage("Something went wrong :(");
                            displayErrorMessage(ex.getMessage());
                        }
                    }
                }
            }
        });
    }
}

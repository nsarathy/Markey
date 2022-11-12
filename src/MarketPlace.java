import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MarketPlace {
    public static final String PRODUCT_DISPLAY = "\n%d.\t%s\t\tSold by: %s\t\tPrice: %.2f\n";
    public static final String SELECTED_PRODUCT_DISPLAY = "\n%s\t\tSold by: %s\t\tPrice: %.2f\nAvailable in stock: %d\nDescription: %s\n";
    public static final String CART_END = "\n\nTotal Price: %.2f\n\nEnter '$' to checkout\nEnter 2 to remove item number 2\nEnter '?' to exit cart\n";
    public static final String[] BUTTONS = {"#", "<", ">", "<>", "?", "@", "++", "--", "*", "csv", "db"};
    public static final String BUTTONS_PROMPT = "\nEnter '%s' to %s";
    public static final String ADD_TO_CART = "\nEnter '2' to add item number 2 to your cart";
    public static final String QUANTITY_PROMPT = "Enter quantity for the selected item ('?' to exit)";
    public static final String QUANTITY_SHOW = "Quantity: %d";
    public static final String ADDED_TO_CART = "Added to your cart :)";
    public static final String NO_LISTINGS = "There is nothing for sale :(";
    public static final String CART_LENGTH = "\nYou have %d items in your cart\n";
    public static final String CART_LENGTH_1 = "\nYou have %d item in your cart\n";
    public static final String SELLER_NOTHING = "You do not having anything for sale";
    public static final String OPEN_STORE = "Enter a name for your store";
    public static final String MANDATORY_PRODUCT = "It is mandatory to have at least one product on sale when opening a new store";
    public static final String PROD_NAME = "Enter name of product";
    public static final String PROD_STORE = "Choose a store for your item";
    public static final String PROD_QUANTITY = "Enter the quantity of this item available in stock";
    public static final String PROD_PRICE = "Enter the price of this item";
    public static final String PROD_DESCRIPTION = "Enter the description of this item";
    public static final String DELETE_LISTING = "\nEnter 2 to remove 2nd item from listings";
    public static final String DELETE_FAIL = "Deletion failed :(";
    public static final String DELETE_SUCCESS = "Deletion successful :)";
    public static final String STORE_EXISTS = "You have already opened a store of the same name";
    public static final String EDIT_PROMPT = "Enter 2 to edit 2nd item";
    public static final String EDIT_FAIL = "Edit unsuccessful :(";
    public static final String EDIT_SUCCESS = "Edit successful :)";
    public static final String EDIT_WHAT = "\nEnter\n'1' to edit name\n'2' to edit price\n'3' to edit description\n'4' to edit availability in stock";
    public static final int[] EDIT_THAT = {1, 2, 3, 4};
    public static final String CART_REM = "An item was removed from your cart because it was out of stock";
    private final String username;
    private final String password;

    public MarketPlace(String username, String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * TODO: Let customers proceed to checkout cart (uncomment)
     * todo: dashboards
     */

    public void main(boolean customer) {
        // if @param customer is false, don't let user do first 4 todos
        // Let user do those if @param customer is true
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> cartItems = new ArrayList<>();
        ArrayList<String> cartSellerUsernames = new ArrayList<>();
        try {
            // Reading products in listings
            FileReader frProducts = new FileReader("Products.txt");
            BufferedReader brProducts = new BufferedReader(frProducts);
            ArrayList<Product> listings = new ArrayList<>();
            ArrayList<String> sellerUsernames = new ArrayList<>();
            final ArrayList<Product> originalListings;
            final ArrayList<String> originalSellerUsernames;
            String line = brProducts.readLine();
            while (line != null) {
                String[] sellerAndProduct = line.split(";", 2);
                String[] productDetails = sellerAndProduct[1].split("_", -1);
                sellerUsernames.add(sellerAndProduct[0]);
                Store store = new Store(productDetails[1]);
                int quantity = Integer.parseInt(productDetails[2]);
                double price = Double.parseDouble(productDetails[3]);
                listings.add(new Product(productDetails[0], store, quantity, price, productDetails[4]));
                line = brProducts.readLine();
            }
            // for de-sort
            originalListings = listings;
            originalSellerUsernames = sellerUsernames;
            // main loop for the program
            listingDisplay:
            while (true) {
                System.out.println("Markey");
                System.out.println();
                if (!customer) {
                    FileReader frProducts2 = new FileReader("Products.txt");
                    BufferedReader brProducts2 = new BufferedReader(frProducts2);
                    listings = new ArrayList<>();
                    sellerUsernames = new ArrayList<>();
                    line = brProducts2.readLine();
                    while (line != null) {
                        String[] sellerAndProduct = line.split(";", 2);
                        String[] productDetails = sellerAndProduct[1].split("_", -1);
                        sellerUsernames.add(sellerAndProduct[0]);
                        Store store = new Store(productDetails[1]);
                        int quantity = Integer.parseInt(productDetails[2]);
                        double price = Double.parseDouble(productDetails[3]);
                        listings.add(new Product(productDetails[0], store, quantity, price, productDetails[4]));
                        line = brProducts2.readLine();
                    }
                }
                // Displaying listings
                if (listings.isEmpty()) {
                    System.out.println(NO_LISTINGS);
                    if (customer) {
                        break listingDisplay;
                    }
                }
                for (Product product : listings) {
                    displayProduct(product, listings.indexOf(product));
                }
                System.out.println();
                if (customer) {
                    // reading and displaying number of items in cart
                    // cart data is stored
                    try {
                        Listing readCart = readCart();
                        cartItems = readCart.getProducts();
                        cartSellerUsernames = readCart.getSellers();
                        ArrayList<Integer> toRemove = new ArrayList<>();
                        for (int i = 0; i < cartItems.size(); i++) {
                            Product cartProduct = cartItems.get(i);
                            for (Product listProduct : listings) {
                                boolean outOfStock = listProduct.getName().equals(cartProduct.getName());
                                outOfStock = outOfStock && listProduct.getStore().getName().equals(cartProduct.getStore().getName());
                                outOfStock = outOfStock && listProduct.getPrice() == cartProduct.getPrice();
                                outOfStock = outOfStock && listProduct.getDescription().equals(cartProduct.getDescription());
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
                            System.out.println(CART_REM);
                        }
                        if (removed) {
                            storeCart(cartItems, cartSellerUsernames);
                        }
                        if (cartItems.size() == 1) {
                            System.out.printf(CART_LENGTH_1, cartItems.size());
                        } else {
                            System.out.printf(CART_LENGTH, cartItems.size());
                        }
                    } catch (CartNotTrackableException e) {
                        System.out.println(e.getMessage());
                    }
                    // displaying actions for each input
                    System.out.println(ADD_TO_CART);
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[0], "view your cart");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[1], "sort listings by cost (low to high)");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[2], "sort listings by cost (high to low)");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[3], "de-sort");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[5], "view your purchase history");
                } else {
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[5], "open a new Store");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[6], "list a new item for sale");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[7], "remove an item from sale");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[8], "edit an item that's on sale");
                }
                System.out.printf(BUTTONS_PROMPT, BUTTONS[9], "get a csv file with data");
                System.out.printf(BUTTONS_PROMPT, BUTTONS[10], "go to dashboard");
                System.out.printf(BUTTONS_PROMPT, BUTTONS[4], "exit");
                System.out.println();
                String action = scanner.nextLine();
                // carrying out the actions
                // todo dashboard
                if (action.equalsIgnoreCase(BUTTONS[4])) {
                    break listingDisplay;
                } else if (customer) {
                    if (action.equalsIgnoreCase(BUTTONS[0])) {
                        viewCart(cartItems, cartSellerUsernames, scanner);
                    } else if (action.equalsIgnoreCase(BUTTONS[1])) {
                        Listing listing = sortLowToHigh(listings, sellerUsernames);
                        listings = listing.getProducts();
                        sellerUsernames = listing.getSellers();
                    } else if (action.equalsIgnoreCase(BUTTONS[2])) {
                        Listing listing = sortHighToLow(listings, sellerUsernames);
                        listings = listing.getProducts();
                        sellerUsernames = listing.getSellers();
                    } else if (action.equalsIgnoreCase(BUTTONS[3])) {
                        listings = originalListings;
                        sellerUsernames = originalSellerUsernames;
                    } else if (action.equalsIgnoreCase(BUTTONS[5])) {
                        viewPurchaseHistory();
                    } else if (action.equalsIgnoreCase(BUTTONS[9])) {
                        var export = new ImportExport(username, password);
                        export.customerExport();
                    } else {
                        try {
                            int itemNumber = Integer.parseInt(action);
                            // Adding an item to cart
                            if (itemNumber > 0 && itemNumber <= listings.size()) {
                                Product product = listings.get(itemNumber - 1);
                                displaySelectedProduct(product);
                                System.out.println(QUANTITY_PROMPT);
                                String cartAction = scanner.nextLine();
                                if (cartAction.equalsIgnoreCase("?")) {
                                    continue listingDisplay;
                                } else {
                                    try {
                                        int quantity = Integer.parseInt(cartAction);
                                        if (quantity <= 0) {
                                            System.out.println("Quantity cannot be 0 or negative");
                                            continue listingDisplay;
                                        }
                                        if (quantity > product.getQuantity()) {
                                            throw new NotInStockException("Item not in stock :(");
                                        } else {
                                            Product cartProduct = new Product(product.getName(), product.getStore(), quantity, product.getPrice(), product.getDescription());
                                            cartItems.add(cartProduct);
                                            cartSellerUsernames.add(sellerUsernames.get(listings.indexOf(product)));
                                            int forRemove = cartItems.size() - 1;
                                            try {
                                                storeCart(cartItems, cartSellerUsernames);
                                                System.out.println(ADDED_TO_CART);
                                            } catch (CartNotTrackableException e) {
                                                cartItems.remove(forRemove);
                                                cartSellerUsernames.remove(forRemove);
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                    } catch (NotInStockException e) {
                                        System.out.println(e.getMessage());
                                    } catch (NumberFormatException e) {
                                        continue listingDisplay;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            continue listingDisplay;
                        }
                    }
                } else if (!customer) {
                    // if a store doesn't have something on sale, the store is deleted
                    boolean listed = false;
                    ArrayList<Product> products = new ArrayList<>();
                    ArrayList<Store> stores = new ArrayList<>();
                    for (int i = 0; i < listings.size(); i++) {
                        String sellerUsername = sellerUsernames.get(i);
                        if (sellerUsername.equals(username)) {
                            listed = true;
                            products.add(listings.get(i));
                            Store sellerStore = listings.get(i).getStore();
                            boolean sameName = false;
                            for (Store value : stores) {
                                if (value.getName().equals(sellerStore.getName())) {
                                    sameName = true;
                                    break;
                                }
                            }
                            if (!sameName) {
                                stores.add(sellerStore);
                            }
                        }
                    }
                    Seller seller;
                    if (listed) {
                        seller = new Seller(username, password, stores, products);
                        try {
                            FileReader frSeller = new FileReader("Stores.txt");
                            BufferedReader brSeller = new BufferedReader(frSeller);
                            // read actions
                            if (action.equalsIgnoreCase(BUTTONS[5])) {
                                System.out.println(OPEN_STORE);
                                String storeName = scanner.nextLine();
                                while (storeName.contains("_") || storeName.contains(";") || storeName.contains(",")) {
                                    System.out.println("Store name cannot contain '_' or ';' or ','");
                                    System.out.println(PROD_NAME);
                                    storeName = scanner.nextLine();
                                }
                                boolean storeExists = false;
                                for (Store value : stores) {
                                    if (value.getName().equals(storeName)) {
                                        storeExists = true;
                                        break;
                                    }
                                }
                                if (storeExists) {
                                    System.out.println(STORE_EXISTS);
                                } else {
                                    Store store = new Store(storeName);
                                    System.out.println(MANDATORY_PRODUCT);
                                    System.out.println(PROD_NAME);
                                    String prodName = scanner.nextLine();
                                    while (prodName.contains("_") || prodName.contains(";")) {
                                        System.out.println("Item name cannot contain '_' or ';'");
                                        System.out.println(PROD_NAME);
                                        prodName = scanner.nextLine();
                                    }
                                    System.out.println(PROD_QUANTITY);
                                    int prodQuantity = Integer.parseInt(scanner.nextLine());
                                    while (prodQuantity <= 0) {
                                        System.out.println("Quantity cannot be 0 or negative");
                                        System.out.println(PROD_QUANTITY);
                                        prodQuantity = Integer.parseInt(scanner.nextLine());
                                    }
                                    System.out.println(PROD_PRICE);
                                    double prodPrice = Double.parseDouble(scanner.nextLine());
                                    while (prodPrice <= 0) {
                                        System.out.println("Price cannot be 0 or negative");
                                        System.out.println(PROD_PRICE);
                                        prodPrice = Double.parseDouble(scanner.nextLine());
                                    }
                                    System.out.println(PROD_DESCRIPTION);
                                    String prodDescription = scanner.nextLine();
                                    while (prodDescription.contains("_") || prodDescription.contains(";")) {
                                        System.out.println("Description cannot contain '_' or ';'");
                                        System.out.println(PROD_NAME);
                                        prodDescription = scanner.nextLine();
                                    }
                                    seller = new Seller(username, password, stores, products);
                                    seller.createStore(storeName);
                                    seller.createProduct(prodName, store, prodQuantity, prodPrice, prodDescription);
                                }
                            } else if (action.equalsIgnoreCase(BUTTONS[6])) { // add
                                System.out.println(PROD_NAME);
                                String prodName = scanner.nextLine();
                                while (prodName.contains("_") || prodName.contains(";")) {
                                    System.out.println("Item name cannot contain '_' or ';'");
                                    System.out.println(PROD_NAME);
                                    prodName = scanner.nextLine();
                                }
                                System.out.println(PROD_STORE);
                                for (int i = 0; i < stores.size(); i++) {
                                    Store value = stores.get(i);
                                    System.out.println((i + 1) + ". " + value.getName());
                                }
                                int prodStoreChoose = Integer.parseInt(scanner.nextLine());
                                prodStoreChoose = prodStoreChoose - 1;
                                Store prodStore = stores.get(prodStoreChoose);
                                System.out.println(PROD_QUANTITY);
                                int prodQuantity = Integer.parseInt(scanner.nextLine());
                                while (prodQuantity <= 0) {
                                    System.out.println("Quantity cannot be 0 or negative");
                                    System.out.println(PROD_QUANTITY);
                                    prodQuantity = Integer.parseInt(scanner.nextLine());
                                }
                                System.out.println(PROD_PRICE);
                                double prodPrice = Double.parseDouble(scanner.nextLine());
                                while (prodPrice <= 0) {
                                    System.out.println("Price cannot be 0 or negative");
                                    System.out.println(PROD_PRICE);
                                    prodPrice = Double.parseDouble(scanner.nextLine());
                                }
                                System.out.println(PROD_DESCRIPTION);
                                String prodDescription = scanner.nextLine();
                                while (prodDescription.contains("_") || prodDescription.contains(";") || prodDescription.contains("$")) {
                                    System.out.println("Description cannot contain '_' or ';' or '$'");
                                    System.out.println(PROD_NAME);
                                    prodDescription = scanner.nextLine();
                                }
                                seller = new Seller(username, password, stores, products);
                                seller.createProduct(prodName, prodStore, prodQuantity, prodPrice, prodDescription);
                            } else if (action.equalsIgnoreCase(BUTTONS[7])) { // delete
                                for (int i = 0; i < products.size(); i++) {
                                    Product value = products.get(i);
                                    displayProduct(value, i);
                                }
                                System.out.println(DELETE_LISTING);
                                int delIndex = Integer.parseInt(scanner.nextLine());
                                delIndex = delIndex - 1;
                                Product toDelete = products.get(delIndex);
                                seller.deleteProduct(delIndex);
                                int lineOfDeletion;
                                for (lineOfDeletion = 0; lineOfDeletion < listings.size(); lineOfDeletion++) {
                                    Product value = listings.get(lineOfDeletion);
                                    if (value.toString().equals(toDelete.toString())) {
                                        break;
                                    }
                                }
                                // editing Products.txt
                                int reachingDeletion = 0;
                                ArrayList<String> theListingsLines = new ArrayList<>();
                                String checkLine = username + ";" + toDelete.toString();
                                boolean deleted = false;
                                try {
                                    FileReader frDelete = new FileReader("Products.txt");
                                    BufferedReader brDelete = new BufferedReader(frDelete);
                                    String eachLine = brDelete.readLine();
                                    while (eachLine != null) {
                                        if (!eachLine.equals(checkLine)) {
                                            theListingsLines.add(eachLine);
                                        } else {
                                            deleted = true;
                                        }
                                        eachLine = brDelete.readLine();
                                    }
                                    brDelete.close();
                                    frDelete.close();
                                    FileWriter fwDelete = new FileWriter("Products.txt");
                                    BufferedWriter bwDelete = new BufferedWriter(fwDelete);
                                    boolean first = true;
                                    for (String writeLine : theListingsLines) {
                                        if (!writeLine.isEmpty()) {
                                            // pwDelete.println(writeLine);
                                            if (!first) {
                                                bwDelete.write("\n" + writeLine);
                                            } else {
                                                bwDelete.write(writeLine);
                                                first = false;
                                            }
                                        }
                                    }
                                    bwDelete.close();
                                    fwDelete.close();
                                    if (!deleted) {
                                        System.out.println(DELETE_FAIL);
                                    } else {
                                        System.out.println(DELETE_SUCCESS);
                                    }
                                } catch (Exception e) {
                                    System.out.println(DELETE_FAIL);
                                }
                            } else if (action.equalsIgnoreCase(BUTTONS[8])) { // edit
                                for (int i = 0; i < products.size(); i++) {
                                    displayProduct(products.get(i), i);
                                }
                                System.out.println(EDIT_PROMPT);
                                int editIndex = Integer.parseInt(scanner.nextLine());
                                editIndex = editIndex - 1;
                                Product toEdit = products.get(editIndex);
                                Product tempProd = new Product(toEdit.getName(), toEdit.getStore(), toEdit.getQuantity(), toEdit.getPrice(), toEdit.getDescription());
                                ArrayList<String> afterEdit = new ArrayList<>();
                                try {
                                    FileReader frEdit = new FileReader("Products.txt");
                                    BufferedReader brEdit = new BufferedReader(frEdit);
                                    String eachLine = brEdit.readLine();
                                    while (eachLine != null) {
                                        afterEdit.add(eachLine);
                                        eachLine = brEdit.readLine();
                                    }
                                    brEdit.close();
                                    frEdit.close();
                                    displaySelectedProduct(toEdit);
                                    System.out.println(EDIT_WHAT);
                                    int editWhat = Integer.parseInt(scanner.nextLine());
                                    if (editWhat == EDIT_THAT[0]) {
                                        System.out.println("Enter new name");
                                        String prodName = scanner.nextLine();
                                        while (prodName.contains("_") || prodName.contains(";")) {
                                            System.out.println("Item name cannot contain '_' or ';'");
                                            System.out.println(PROD_NAME);
                                            prodName = scanner.nextLine();
                                        }
                                        toEdit.setName(prodName);
                                    } else if (editWhat == EDIT_THAT[1]) {
                                        System.out.println("Enter new price");
                                        double newPrice = Double.parseDouble(scanner.nextLine());
                                        while (newPrice <= 0) {
                                            System.out.println("Price cannot be 0 or negative");
                                            System.out.println("Enter new price");
                                            newPrice = Double.parseDouble(scanner.nextLine());
                                        }
                                        toEdit.setPrice(newPrice);
                                    } else if (editWhat == EDIT_THAT[2]) {
                                        System.out.println("Enter new description");
                                        String prodDescription = scanner.nextLine();
                                        while (prodDescription.contains("_") || prodDescription.contains(";") || prodDescription.contains("$")) {
                                            System.out.println("Description cannot contain '_' or ';' or '$'");
                                            System.out.println(PROD_NAME);
                                            prodDescription = scanner.nextLine();
                                        }
                                        toEdit.setDescription(prodDescription);
                                    } else if (editWhat == EDIT_THAT[3]) {
                                        System.out.println("Enter new quantity");
                                        int newQuantity = Integer.parseInt(scanner.nextLine());
                                        while (newQuantity <= 0) {
                                            System.out.println("Quantity cannot be 0 or negative");
                                            System.out.println("Enter new quantity");
                                            newQuantity = Integer.parseInt(scanner.nextLine());
                                        }
                                        toEdit.setQuantity(newQuantity);
                                    }
                                    boolean edited = false;
                                    for (int i = 0; i < afterEdit.size(); i++) {
                                        String[] check0 = afterEdit.get(i).split(";", -1);
                                        if (check0[1].equals(tempProd.toString())) {
                                            edited = true;
                                            afterEdit.set(i, username + ";" + toEdit.toString());
                                        }
                                    }
                                    FileOutputStream fosEdit = new FileOutputStream("Products.txt");
                                    PrintWriter pwEdit = new PrintWriter(fosEdit);
                                    for (String value : afterEdit) {
                                        pwEdit.println(value);
                                    }
                                    pwEdit.close();
                                    fosEdit.close();
                                    if (edited) {
                                        System.out.println(EDIT_SUCCESS);
                                    } else {
                                        System.out.println(EDIT_FAIL);
                                    }
                                } catch (Exception e) {
                                    System.out.println(EDIT_FAIL);
                                }
                            } else if (action.equalsIgnoreCase(BUTTONS[9])) {
                                System.out.println("Enter\n'1' to export\n'2' to import");
                                int choice = 0;
                                while (true) {
                                    try {
                                        choice = Integer.parseInt(scanner.nextLine());
                                        if (choice != 1 && choice != 2) {
                                            throw new Exception();
                                        }
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Enter 1 or 2");
                                    }
                                }
                                var importExport = new ImportExport(username, password);
                                if (choice == 1) {
                                    importExport.sellerExport(seller);
                                } else {
                                    importExport.sellerImport(seller);
                                }
                            }
                            brSeller.close();
                            frSeller.close();
                        } catch (Exception e) {
                            System.out.println("Something went wrong :(\nPlease try again later");
                        }
                    } else {
                        System.out.println(SELLER_NOTHING);
                        // mandatory to have at least one product
                        // prompt to open store
                        System.out.println(OPEN_STORE);
                        String storeName = scanner.nextLine();
                        while (storeName.contains("_") || storeName.contains(";") || storeName.contains(",")) {
                            System.out.println("Store name cannot contain '_' or ';' or ','");
                            System.out.println(PROD_NAME);
                            storeName = scanner.nextLine();
                        }
                        Store store = new Store(storeName);
                        System.out.println(MANDATORY_PRODUCT);
                        System.out.println(PROD_NAME);
                        String prodName = scanner.nextLine();
                        while (prodName.contains("_") || prodName.contains(";")) {
                            System.out.println("Item name cannot contain '_' or ';'");
                            System.out.println(PROD_NAME);
                            prodName = scanner.nextLine();
                        }
                        System.out.println(PROD_QUANTITY);
                        int prodQuantity = Integer.parseInt(scanner.nextLine());
                        while (prodQuantity <= 0) {
                            System.out.println("Quantity cannot be 0 or negative");
                            System.out.println(PROD_QUANTITY);
                            prodQuantity = Integer.parseInt(scanner.nextLine());
                        }
                        System.out.println(PROD_PRICE);
                        double prodPrice = Double.parseDouble(scanner.nextLine());
                        while (prodPrice <= 0) {
                            System.out.println("Price cannot be 0 or negative");
                            System.out.println(PROD_PRICE);
                            prodPrice = Double.parseDouble(scanner.nextLine());
                        }
                        System.out.println(PROD_DESCRIPTION);
                        String prodDescription = scanner.nextLine();
                        while (prodDescription.contains("_") || prodDescription.contains(";")) {
                            System.out.println("Description cannot contain '_' or ';'");
                            System.out.println(PROD_NAME);
                            prodDescription = scanner.nextLine();
                        }
                        seller = new Seller(username, password, stores, products);
                        seller.createStore(storeName);
                        seller.createProduct(prodName, store, prodQuantity, prodPrice, prodDescription);
                    }
                }
            }
            brProducts.close();
            frProducts.close();
            // farewell message
            System.out.println("Thank you for using Markey! Have a nice day :)");
        } catch (Exception e) {
            System.out.println("Something went wrong :(\nPlease try again later");
        }
    }

    public void displayProduct(Product product, int index) {
        String name = product.getName();
        String storeName = product.getStore().getName();
        double price = product.getPrice();
        System.out.printf(PRODUCT_DISPLAY, (index + 1), name, storeName, price);
    }

    // product page
    public void displaySelectedProduct(Product product) {
        String name = product.getName();
        String storeName = product.getStore().getName();
        double price = product.getPrice();
        int quantity = product.getQuantity();
        String description = product.getDescription();
        System.out.printf(SELECTED_PRODUCT_DISPLAY, name, storeName, price, quantity, description);
    }

    public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames) {

        // clearing cart
        try {
            FileReader frCart = new FileReader("carts.txt");
            BufferedReader brCart = new BufferedReader(frCart);
            ArrayList<String> lines = new ArrayList<>();
            String line = brCart.readLine();
            while (line != null) {
                lines.add(line);
                line = brCart.readLine();
            }
            brCart.close();
            frCart.close();
            FileOutputStream fosCart = new FileOutputStream("carts.txt");
            PrintWriter pwCart = new PrintWriter(fosCart);
            for (String value : lines) {
                String[] splitValue = value.split(";", -1);
                if (splitValue[0].equals(username)) {
                    pwCart.println(username + ";;");
                } else {
                    pwCart.println(value);
                }
            }
            pwCart.close();
            fosCart.close();
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        // customer stats
        // todo debug when store data already there
        try {
            FileReader frCustomer = new FileReader("CustomerStatistics.txt");
            BufferedReader brCustomer = new BufferedReader(frCustomer);
            ArrayList<String> lines = new ArrayList<>();
            String line = brCustomer.readLine();
            while (line != null) {
                lines.add(line);
                line = brCustomer.readLine();
            }
            brCustomer.close();
            frCustomer.close();
            int i = 0;
            for (String sellerUsername : sellerUsernames) {
                boolean changed = false;
                int j = 0;
                inner:
                for (String eachLine : lines) {
                    String[] splitEachLine = eachLine.split(";", -1);
                    String finalLine = splitEachLine[0] + ";";
                    if (splitEachLine[0].equals(sellerUsername)) {
                        String storeName = proceedToCheckout.get(i).getStore().getName();
                        String[] storeAndSales = splitEachLine[1].split("___");
                        for (String eachStore : storeAndSales) {
                            String[] storeAndSale = eachStore.split("_", -1);
                            if (storeAndSale[0].equals(storeName)) {
                                int oldQuantity = Integer.parseInt(storeAndSale[1]);
                                int newQuantity = oldQuantity + proceedToCheckout.get(i).getQuantity();
                                finalLine = finalLine + storeAndSale[0] + "_" + newQuantity + "___";
                                changed = true;
                            } else {
                                finalLine = finalLine + eachStore + "___";
                            }
                        }
                        if (!changed) {
                            finalLine = finalLine + proceedToCheckout.get(i).getStore().getName() + "_" + proceedToCheckout.get(i).getQuantity() + "___";
                            changed = true;
                        }
                    }
                    if (finalLine.endsWith("___")) {
                        finalLine = finalLine.substring(0, finalLine.length() - 3);
                    }
                    if (changed) {
                        lines.set(j, finalLine);
                        break inner;
                    }
                    j++;
                }
                if (!changed) {
                    lines.add(sellerUsername + ";" + proceedToCheckout.get(i).getStore().getName() + "_" + proceedToCheckout.get(i).getQuantity());
                }
                i++;
            }
            FileOutputStream fosCustomer = new FileOutputStream("CustomerStatistics.txt");
            PrintWriter pwCustomer = new PrintWriter(fosCustomer);
            for (String eachLine : lines) {
                pwCustomer.println(eachLine);
            }
            pwCustomer.close();
            fosCustomer.close();
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        // updating Products.txt
        try {
            FileReader frProducts = new FileReader("Products.txt");
            BufferedReader brProducts = new BufferedReader(frProducts);
            ArrayList<String> lines = new ArrayList<>();
            String line = brProducts.readLine();
            while (line != null) {
                boolean added = false;
                for (int i = 0; i < proceedToCheckout.size(); i++) {
                    Product product = proceedToCheckout.get(i);
                    String seller = sellerUsernames.get(i);
                    String[] sellerAndProduct = line.split(";", -1);
                    boolean same = sellerAndProduct[0].equals(seller);
                    String[] productString = sellerAndProduct[1].split("_", -1);
                    Product newProduct = new Product(productString[0],
                            new Store(productString[1]),
                            Integer.parseInt(productString[2]),
                            Double.parseDouble(productString[3]),
                            productString[4]
                    );
                    same = same && product.getName().equals(newProduct.getName());
                    same = same && product.getStore().getName().equals(newProduct.getStore().getName());
                    same = same && product.getPrice() == newProduct.getPrice();
                    same = same && product.getDescription().equals(newProduct.getDescription());
                    if (same) {
                        newProduct.setQuantity(newProduct.getQuantity() - product.getQuantity());
                        if (newProduct.getQuantity() > 0) {
                            lines.add(seller + ";" + newProduct.toString());
                            added = true;
                            break;
                        }
                    }
                    // debug
                    for (String value : lines) {
                        System.out.println(value);
                    }
                    System.out.println();
                }
                if (!added) {
                    lines.add(line);
                }
                line = brProducts.readLine();
            }
            brProducts.close();
            frProducts.close();
            FileOutputStream fosProducts = new FileOutputStream("Products.txt");
            PrintWriter pwProducts = new PrintWriter(fosProducts);
            for (String eachLine : lines) {
                pwProducts.println(eachLine);
            }
            pwProducts.close();
            fosProducts.close();
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        /*
        var cart = new Cart(proceedToCheckout, sellerUsernames, username);
        cart.updateCustomerPurchaseHistory();
        cart.updateSellerStatistics();
         */

    }

    // Displaying cart
    // Actions : remove item, exit cart, proceed to checkout
    public void viewCart(ArrayList<Product> currentCart, ArrayList<String> currentCartSellers, Scanner scanner) {
        boolean stepFound = false;
        while (!stepFound) {
            if (currentCart.size() == 0) {
                System.out.println("Cart is empty");
                return;
            }
            double totalPrice = 0;
            for (Product product : currentCart) {
                displayProduct(product, currentCart.indexOf(product));
                System.out.printf(QUANTITY_SHOW, product.getQuantity());
                totalPrice += (product.getPrice() * product.getQuantity());
            }
            System.out.printf(CART_END, totalPrice);
            String nextStep = scanner.nextLine();
            if (nextStep.equalsIgnoreCase("$")) {
                checkout(currentCart, currentCartSellers);
                stepFound = true;
            } else if (nextStep.equalsIgnoreCase("?")) {
                stepFound = true;
            } else {
                try {
                    int removeIndex = Integer.parseInt(nextStep);
                    if (removeIndex > 0 && removeIndex <= currentCart.size()) {
                        stepFound = true;
                        currentCart.remove(removeIndex - 1);
                        currentCartSellers.remove(removeIndex - 1);
                        storeCart(currentCart, currentCartSellers);
                        System.out.println("Item removed");
                    }
                } catch (Exception e) {
                    stepFound = false;
                }
            }
        }
    }

    // Sorting using bubble sort method
    public Listing sortLowToHigh(ArrayList<Product> list, ArrayList<String> sellers) {
        Product[] array = list.toArray(new Product[0]);
        String[] sellerArray = sellers.toArray(new String[0]);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                double priceJ = array[j].getPrice();
                double priceJPlus1 = array[j + 1].getPrice();
                if (priceJ > priceJPlus1) {
                    Product temporary = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temporary;
                    String tempString = sellerArray[j];
                    sellerArray[j] = sellerArray[j + 1];
                    sellerArray[j + 1] = tempString;
                }
            }
        }
        list = new ArrayList<>(Arrays.asList(array));
        sellers = new ArrayList<>(Arrays.asList(sellerArray));
        return new Listing(list, sellers);
    }

    public Listing sortHighToLow(ArrayList<Product> list, ArrayList<String> sellers) {
        Product[] array = list.toArray(new Product[0]);
        String[] sellerArray = sellers.toArray(new String[0]);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                double priceJ = array[j].getPrice();
                double priceJPlus1 = array[j + 1].getPrice();
                if (priceJ < priceJPlus1) {
                    Product temporary = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temporary;
                    String tempString = sellerArray[j];
                    sellerArray[j] = sellerArray[j + 1];
                    sellerArray[j + 1] = tempString;
                }
            }
        }
        list = new ArrayList<>(Arrays.asList(array));
        sellers = new ArrayList<>(Arrays.asList(sellerArray));
        return new Listing(list, sellers);
    }

    public void viewPurchaseHistory() {
        System.out.println("Your purchases:");
        // Use Customer.displayPurchases
        boolean exists = false;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            FileReader frHistory = new FileReader("CustomerPurchaseHistory.txt");
            BufferedReader brHistory = new BufferedReader(frHistory);
            ArrayList<String> lines = new ArrayList<>();
            String line = brHistory.readLine();
            while (line != null) {
                lines.add(line);
                line = brHistory.readLine();
            }
            brHistory.close();
            frHistory.close();
            for (String value : lines) {
                String[] checkUser = value.split(";", -1);
                if (checkUser[0].equals(username)) {
                    exists = true;
                    String[] products = checkUser[1].split("___", -1);
                    for (String val : products) {
                        String[] product = val.split("_", -1);
                        productList.add(new Product(product[0],
                                new Store(product[1]),
                                Integer.parseInt(product[2]),
                                Double.parseDouble(product[3]),
                                product[4].split("\\$", -1)[0]
                        ));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        if (!exists) {
            System.out.println("You have not made any purchases yet");
        } else {
            Customer customer = new Customer(username, password, productList.toArray(productList.toArray(new Product[0])));
            customer.displayPurchases();
        }
    }

    // Writing user's cart to a txt file
    public void storeCart(ArrayList<Product> currentCart, ArrayList<String> currentSellers) throws CartNotTrackableException {
        String finalLine = username + ";";
        for (Product product : currentCart) {
            finalLine = finalLine + product.toString() + "___";
        }
        if (finalLine.endsWith("___")) {
            finalLine = finalLine.substring(0, finalLine.length() - 3);
        }
        finalLine = finalLine + ";";
        for (String seller : currentSellers) {
            finalLine = finalLine + seller + "___";
        }
        if (finalLine.endsWith("___")) {
            finalLine = finalLine.substring(0, finalLine.length() - 3);
        }
        try {
            ArrayList<String> toAppend = new ArrayList<>();
            FileReader fr = new FileReader("carts.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean flag = true;
            String line = br.readLine();
            while (line != null) {
                String[] toGetCustomer = line.split(";", -1);
                if (toGetCustomer[0].equals(username)) {
                    flag = false;
                    toAppend.add(finalLine);
                    line = br.readLine();
                    continue;
                }
                toAppend.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
            if (flag) {
                toAppend.add(finalLine);
            }
            FileOutputStream fos = new FileOutputStream("carts.txt");
            PrintWriter pw = new PrintWriter(fos);
            for (String eachLine : toAppend) {
                pw.println(eachLine);
            }
            pw.close();
            fos.close();
        } catch (IOException e) {
            throw new CartNotTrackableException("Something went wrong - Could not add to cart :(");
        }
    }

    public Listing readCart() throws CartNotTrackableException {
        try {
            ArrayList<Product> cartProducts = new ArrayList<>();
            ArrayList<String> cartSellers = new ArrayList<>();
            FileReader fr = new FileReader("carts.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean flag = true;
            String line = br.readLine();
            while (line != null) {
                String[] toGetCustomer = line.split(";", -1);
                if (toGetCustomer[0].equals(username)) {
                    flag = false;
                    String[] products = toGetCustomer[1].split("___", -1);
                    String[] sellers = toGetCustomer[2].split("___", -1);
                    if (!products[0].isEmpty()) {
                        for (String value : products) {
                            String[] productDetails = value.split("_", -1);
                            Store store = new Store(productDetails[1]);
                            int quantity = Integer.parseInt(productDetails[2]);
                            double price = Double.parseDouble(productDetails[3]);
                            cartProducts.add(new Product(productDetails[0], store, quantity, price, productDetails[4]));
                        }
                    }
                    if (!sellers[0].isEmpty()) {
                        cartSellers = new ArrayList<>(Arrays.asList(sellers));
                    }
                    break;
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
            if (flag) {
                throw new CartNotTrackableException("Your cart is empty");
            }
            return new Listing(cartProducts, cartSellers);
        } catch (IOException | NumberFormatException e) {
            throw new CartNotTrackableException("Unable to find cart");
        }
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MarketPlace {
    public static final String PRODUCT_DISPLAY = "\n%d.\t%s\tSold by: %s\tPrice: %.2f\n";
    public static final String SELECTED_PRODUCT_DISPLAY = "\n%s\tSold by: %s\tPrice: %.2f\nAvailable in stock: %d\nDescription: %s\n";
    public static final String CART_END = "\n\nTotal Price: %.2f\n\nEnter '$' to checkout\nEnter '?' to exit cart\n";
    public static final String[] BUTTONS = {"#", "<", ">", "<>", "?"};
    public static final String BUTTONS_PROMPT = "\nEnter '%s' to %s";
    public static final String ADD_TO_CART = "\nEnter '2' to add item number 2 to your cart";
    public static final String QUANTITY_PROMPT = "Enter quantity for the selected item ('?' to exit)";
    public static final String QUANTITY_SHOW = "Quantity: %d";
    public static final String ADDED_TO_CART = "Added to your cart :)";
    public static final String NO_LISTINGS = "There is nothing for sale :(";
    private String username;

    public MarketPlace(String username) {
        this.username = username;
    }

    // TODO: psvm method for testing purposes only, delete later
    public static void main(String[] args) {
        MarketPlace marketPlace = new MarketPlace("testUser");
        marketPlace.main(true);
    }

    /**
     * TODO: view purchase history
     * TODO: store cart
     * TODO: Let customers proceed to checkout cart (uncomment)
     * TODO: Use methods from Seller.java to create, edit or remove products/stores
     */

    public void main(boolean customer) {
        // if @param customer is false, don't let user do first 4 todos
        // Let user do those if @param customer is true
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> cartItems = new ArrayList<>();
        ArrayList<String> cartSellerUsernames = new ArrayList<>();
        // TODO: read stored cart
        try {
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
            originalListings = listings;
            originalSellerUsernames = sellerUsernames;
            listingDisplay:
            while (true) {
                // Displaying listings
                if (listings.isEmpty()) {
                    System.out.println(NO_LISTINGS);
                    break listingDisplay;
                }
                for (Product product : listings) {
                    displayProduct(product, listings.indexOf(product));
                }
                System.out.println();
                if (customer) {
                    System.out.println(ADD_TO_CART);
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[0], "view your cart");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[1], "sort listings by cost (low to high)");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[2], "sort listings by cost (high to low)");
                    System.out.printf(BUTTONS_PROMPT, BUTTONS[3], "de-sort");
                }
                System.out.printf(BUTTONS_PROMPT, BUTTONS[4], "exit");
                System.out.println();
                String action = scanner.nextLine();
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
                    } else {
                        try {
                            int itemNumber = Integer.parseInt(action);
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
                }

            }
            brProducts.close();
            frProducts.close();
            System.out.println("Thank you for using Markey! Have a nice day :)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // test purposes TODO: remove next line
            e.printStackTrace();
        }
    }

    public void displayProduct(Product product, int index) {
        String name = product.getName();
        String storeName = product.getStore().getName();
        double price = product.getPrice();
        System.out.printf(PRODUCT_DISPLAY, (index + 1), name, storeName, price);
    }

    public void displaySelectedProduct(Product product) {
        String name = product.getName();
        String storeName = product.getStore().getName();
        double price = product.getPrice();
        int quantity = product.getQuantity();
        String description = product.getDescription();
        System.out.printf(SELECTED_PRODUCT_DISPLAY, name, storeName, price, quantity, description);
    }

    public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames) {
        var cart = new Cart(proceedToCheckout, sellerUsernames, username);
        // TODO: uncomment after testing
        /*
        cart.buy();
         */
    }

    public void viewCart(ArrayList<Product> currentCart, ArrayList<String> currentCartSellers, Scanner scanner) {
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
        boolean stepFound = false;
        while (!stepFound) {
            String nextStep = scanner.nextLine();
            if (nextStep.equalsIgnoreCase("$")) {
                checkout(currentCart, currentCartSellers);
                stepFound = true;
            } else if (nextStep.equalsIgnoreCase("?")) {
                stepFound = true;
            }
        }
        // TODO: remove items from cart
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
        // TODO: this after Oh gets done with Cart and Purchase
    }

    public void storeCart(ArrayList<Product> currentCart, ArrayList<String> currentSellers) throws CartNotTrackableException {
        // username TODO: txt file write
    }
    // TODO: read stored cart


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

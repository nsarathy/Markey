import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MarketPlace {
    public static final String PRODUCT_DISPLAY = "\n%s\t%s\tSold by: %s\tPrice: %.2f\n";
    public static final String SELECTED_PRODUCT_DISPLAY = "\n%s\tSold by: %s\tPrice: %.2f\nAvailable in stock: %d\nDescription: %s\nHas been selected\n";
    public static final String CART_END = "\nTotal Price: %.2f\nEnter '$' to checkout\nEnter '?' to exit cart\n";
    public static final String[] BUTTONS = {"#", "<", ">", "?"};
    public static final String BUTTONS_PROMPT = "\nEnter '%s' to %s";
    public static final String ADD_TO_CART = "\nEnter '2' to add item number 2 to your cart";
    public static final String QUANTITY_PROMPT = "Enter quantity for the selected item ('?' to exit)";
    public static final String QUANTITY_SHOW = "Quantity: %d";
    public static final String ADDED_TO_CART = "Has been added to your cart :)";
    public static final String NO_LISTINGS = "There is nothing for sale :(";
    private String username;

    /**
     * TODO: Display listed products
     * TODO: Let user select product to add to cart with a specific quantity [Display product information before prompting for quantity]
     * TODO: view cart
     * TODO: view purchase history
     * TODO: Let user sort listings based on cost
     * TODO: If user is a seller don't let them do above 4 steps
     * check for errors like not in stock
     * TODO: Sorting: by cost [low to high & high to low]
     * TODO: Let customers proceed to checkout cart
     * TODO: Let user exit whenever (loop dashboard)
     */

    public void main(boolean customer) {
        // if @param customer is false, don't let user do first 4 todos
        // Let user do those if @param customer is true
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> cartItems = new ArrayList<>();
        ArrayList<String> cartSellerUsernames = new ArrayList<>();
        try {
            FileReader frProducts = new FileReader("Products.txt");
            BufferedReader brProducts = new BufferedReader(frProducts);
            ArrayList<Product> listings = new ArrayList<>();
            ArrayList<String> sellerUsernames = new ArrayList<>();
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
                }
                System.out.printf(BUTTONS_PROMPT, BUTTONS[3], "exit");
                String action = scanner.nextLine();
                if (action.equalsIgnoreCase(BUTTONS[3])) {
                    break listingDisplay;
                } else if (customer) {
                    if (action.equalsIgnoreCase(BUTTONS[0])) {
                        viewCart(cartItems, scanner);
                    } else if (action.equalsIgnoreCase(BUTTONS[1])) {
                        sortLowToHigh(listings);
                    } else if (action.equalsIgnoreCase(BUTTONS[2])) {
                        sortHighToLow(listings);
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
        System.out.printf(PRODUCT_DISPLAY, (index + 1) + ".", name, storeName, price);
    }

    public void displaySelectedProduct(Product product) {
        String name = product.getName();
        String storeName = product.getStore().getName();
        double price = product.getPrice();
        System.out.printf(SELECTED_PRODUCT_DISPLAY, name, storeName, price);
    }

    public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames) {
        var cart = new Cart(proceedToCheckout, sellerUsernames);
        cart.buy();
    }

    public void viewCart(ArrayList<Product> currentCart, ArrayList<String> currentCartSellers, Scanner scanner) {
        if (currentCart.size() == 0) {
            System.out.println("Cart is empty");
        }
        double totalPrice = 0;
        for (Product product : currentCart) {
            displayProduct(product, currentCart.indexOf(product));
            totalPrice += product.getPrice();
        }
        System.out.printf(CART_END, totalPrice);
        boolean stepFound = false;
        while (!stepFound) {
            String nextStep = scanner.nextLine();
            if (nextStep.equalsIgnoreCase("$")) {
                checkout(currentCart, currentCartSellers);
            } else if (nextStep.equalsIgnoreCase("?")) {
                stepFound = true;
            }
        }
    }


    // Sorting using bubble sort method
    public void sortLowToHigh(ArrayList<Product> list) {

    }

    public void sortHighToLow(ArrayList<Product> list) {

    }

    public void viewPurchaseHistory() {
        // TODO: this
    }
}

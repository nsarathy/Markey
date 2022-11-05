import java.util.ArrayList;
import java.util.Scanner;

public class MarketPlace {
    public static final String PRODUCT_DISPLAY = "\n%s\nSold by: %s\nPrice: %.2f\n";
    public static final String CART_END = "\nTotal Price: %.2f\nEnter 'buy' to checkout\nEnter 'exit' to exit cart\n";
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
    public void checkout(ArrayList<Product> proceedToCheckout) {
        var cart = new Cart(proceedToCheckout);
        cart.buy();
    }

    public void main(boolean customer) {
        // if @param customer is false, don't let user do first 4 todos
        // Let user do those if @param customer is true
        ArrayList<Product> proceedToCheckout = new ArrayList<>();

    }

    public void viewCart(ArrayList<Product> currentCart, Scanner scanner) {
        double totalPrice = 0;
        for (Product product : currentCart) {
            String name = product.getName();
            String storeName = product.getStore().getName();
            double price = product.getPrice();
            totalPrice += price;
            System.out.printf(PRODUCT_DISPLAY, name, storeName, price);
        }
        System.out.printf(CART_END, totalPrice);
        boolean stepFound = false;
        while (!stepFound) {
            String nextStep = scanner.nextLine();
            if (nextStep.equalsIgnoreCase("buy")) {
                checkout(currentCart);
            } else if (nextStep.equalsIgnoreCase("exit")) {
                stepFound = true;
            }
        }
    }

    public void viewPurchaseHistory() {
        // Using username, get purchase history from
    }
}

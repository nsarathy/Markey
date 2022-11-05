import java.util.ArrayList;

public class MarketPlace {
    private String username;
    Cart cart;
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
        // create arraylist of Products
        // initiliaze Cart object with the arraylist
    }

    public void main(boolean customer) {
        // if @param customer is false, don't let user do first 4 todos
        // Let user do those if @param customer is true
        ArrayList<Product> proceedToCheckout = new ArrayList<>();

    }

    public void viewCart(ArrayList<Product> currentCart) {
        // display every product in currentCart [pass proceedToCheckout from main()]
    }

    public void viewPurchaseHistory() {
        // Using username, get purchase history from
    }
}

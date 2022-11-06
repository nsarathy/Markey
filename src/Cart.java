import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> productsToBuy;
    private ArrayList<String> sellerUsernames;
    private String customerUsername;
    public Cart(ArrayList<Product> productsToBuy, ArrayList<String> sellerUsernames, String customerUsername) {
        this.productsToBuy = productsToBuy;
        this.sellerUsernames = sellerUsernames;
        this.customerUsername = customerUsername;
    }
    // buy() method
    /*
    add purchased items to customer history
    remove products from seller (reduce quantity by the amount purchased by customer)
    create a Purchase object for each product and call updateSellerStatistics & updateCustomerStatistics
    Product of index i in productsToBuy, has the seller username at index i in sellerUsernames
     */
}
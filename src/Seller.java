import java.util.ArrayList;

public class Seller extends Account{
    ArrayList<Store> stores;
    ArrayList<Product> products;
    // TODO: getters and setters

    
    public void createProduct(/* params */) {
        // initialize a product
        // add created product to products
        // update Products.txt
    }
    public void createStore(/* params */) {
        // initialize a store
        // add created store to stores
        // Update Stores.txt
    }

    // Sellers can editing: Using getters and setters

    // deleting
    public void deleteProduct(int index) {
        // remove product of @param index from products
    }

    // TODO: methods for displaying stores and products

    // TODO: equals() method checks only username
}

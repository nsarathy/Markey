import java.lang.reflect.Array;
import java.util.ArrayList;

public class Seller extends Account{
    ArrayList<Store> stores;
    ArrayList<Product> products;

    public  Seller (String username, String password, ArrayList<Store> stores, ArrayList<Product> products) {
        super(username, password);
        this.stores = stores;
        this.products = products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

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

    public void deleteProduct(int index) {
        this.products.remove(index);
    }

    // TODO: methods for displaying stores and products
    // TODO: equals() method checks only username
}

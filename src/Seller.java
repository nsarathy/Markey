import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Seller extends Account{
    ArrayList<Store> stores;
    ArrayList<Product> products;

    public Seller (String username, String password, ArrayList<Store> stores, ArrayList<Product> products) {
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

    public void createProduct(String name, Store store, int quantity, double price, String description) {
        // initialize a product
        Product newProduct = new Product(name, store, quantity, price, description);
        // add created product to products
        this.products.add((newProduct));
        // update Products.txt
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter("Products.txt"));
            bfw.write(this.getUsername() + ";" + newProduct.toString());
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

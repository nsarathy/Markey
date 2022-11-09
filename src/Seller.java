import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
            BufferedWriter bfw = new BufferedWriter(new FileWriter("Products.txt", true));
            bfw.write(this.getUsername() + ";" + newProduct.toString());
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createStore(String storeName) {
        int indexOf = 0;
        String sellerName = this.getUsername();
        // initialize a store
        Store newStore = new Store(storeName);
        // add created store to stores
        this.stores.add(newStore);
        // Update Stores.txt
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"));
            BufferedWriter bfw = new BufferedWriter(new FileWriter("Stores.txt", true));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                indexOf = line.indexOf(";");
                if (sellerName.equals(line.substring(0, indexOf))) {
                    bfw.write(line + "," + storeName);
                }
            }
            bfr.close();
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int index) {
        this.products.remove(index);
    }

    // TODO: equals() method checks only username
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ( !(obj instanceof Seller)) {
            return false;
        }
        Seller p = (Seller) obj;
        return (p.getUsername().equals(this.getUsername()));
    }
}

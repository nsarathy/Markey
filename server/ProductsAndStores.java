import java.util.ArrayList;

public class ProductsAndStores {
    private ArrayList<Product> products;
    private ArrayList<Store> stores;
    public ProductsAndStores(ArrayList<Product> products, ArrayList<Store> stores) {
        this.products = products;
        this.stores = stores;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }
}

import java.util.ArrayList;

public class Listing {
    private ArrayList<Product> products;
    private ArrayList<String> sellers;
    public Listing(ArrayList<Product> products, ArrayList<String> sellers) {
        this.products = products;
        this.sellers = sellers;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<String> getSellers() {
        return sellers;
    }

    public void setSellers(ArrayList<String> sellers) {
        this.sellers = sellers;
    }
}

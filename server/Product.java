/**
 * Product
 * <p>
 * Blueprint of a product
 *
 * @author shin369
 * @version 12/11/2022
 */
public class Product {
    private String name;
    private Store store;
    private int quantity;
    private double price;
    private String description;

    public Product(String name, Store store, int quantity, double price, String description) {
        this.name = name;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s_%s_%d_%.2f_%s", name, store.getName(), quantity, price, description);
    }
}

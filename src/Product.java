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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public String toString() {
        String return1 = this.name + "_" + this.store + "_" + 
                this.quantity + "_" + this.price + "_" + this.description;
        return return1;
    }
}

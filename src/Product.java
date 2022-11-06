public class Product {
    private String name;
    private Store store;
    private int quantity;
    private double price;
    private String description;
    // TODO: getters and setters
    // branch Neel:
    public Product(String name, Store store, int quantity, double price, String description) {
        this.name = name;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

}

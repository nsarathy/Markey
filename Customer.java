/**
 * Customer 
 * 
 * Customer class extends the Account class, while also holding specific values for a customer, such as the products 
 * array that holds the products sold, and a display purchases method which shows the purchased items in the terminal.
 * 
 * @author Yudon Shin, lab sec L-24
 * 
 * @version November 13, 2022
 */
public class Customer extends Account {
    Product[] purchases;

    //constructor for Customer
    public Customer(String username, String password, Product[] purchases) {
        super(username, password);
        this.purchases = purchases;
    }

    public Product[] getPurchases() {
        return purchases;
    }

    //getters and setters
    public void setPurchases(Product[] purchases) {
        this.purchases = purchases;
    }

    public void displayPurchases() { //method displaying purchases
        for (Product product : this.purchases) {
            System.out.printf("\n%s\t\tSold by: %s\t\tPrice: %.2f\tQuantity: %d\nDescription: %s\n",
                    product.getName(),
                    product.getStore().getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getDescription());
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer p = (Customer) obj;
        return (p.getUsername().equals(this.getUsername()));
    }

}

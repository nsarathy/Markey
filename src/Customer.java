public class Customer extends Account{
    Product[] purchases;

    //constructor for Customer
    public Customer(String username, String password, Product[] purchases) {
        super(username, password);
        this.purchases = purchases;
    }

    //getters and setters
    public void setPurchases(Product[] purchases) {
        this.purchases = purchases;
    }

    public Product[] getPurchases() {
        return purchases;
    }
    public void displayPurchases() { //method displaying purchases
        for(int i = 0; i < this.purchases.length; i++) {
            Product product = this.purchases[i];
            System.out.printf("\n%s\t\tSold by: %s\t\tPrice: %.2f\tQuantity: %d\nDescription: %s\n",
                    product.getName(),
                    product.getStore().getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getDescription());
            System.out.print(this.purchases[i]);
            if (i == this.purchases.length - 1) {

            } else {
                System.out.print(this.purchases[i] + ", ");
            }
        }
    }

    // TODO: equals() checks only
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ( !(obj instanceof Customer)) {
            return false;
        }
        Customer p = (Customer) obj;
        return (p.getUsername().equals(this.getUsername()));
    }

}

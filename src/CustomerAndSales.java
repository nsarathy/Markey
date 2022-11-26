import java.util.List;

public class CustomerAndSales {
    private final List<Integer> sales;
    private final List<String> customer;

    public CustomerAndSales(List<String> customer, List<Integer> sales) {
        this.sales = sales;
        this.customer = customer;
    }

    public List<Integer> getSales() {
        return sales;
    }

    public List<String> getCustomer() {
        return customer;
    }
}

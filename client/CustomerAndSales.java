import java.util.List;

/**
 * CustomerAndSales
 * <p>
 * list of sales and list of customers
 *
 * @author park1504
 * @version 12/11/2022
 */
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

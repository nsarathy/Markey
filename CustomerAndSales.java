import java.util.List;
/**
 * 
 *  This class is used to store data from SellerDashboard.java
 *  and stores the values of the customer's names and sales from
 *  that particular customer. This is mainly used to call from after
 *  making change to the order of list (sorting).
 * 
 * 
 * @author Lewis Park, lab sec L-24
 * @version November 11th, 2022
 * 
 * 
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

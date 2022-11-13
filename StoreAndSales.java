import java.util.List;
/**
 *
 *  Store and Sales
 *
 *  This class is used to store data from CustomerDashboard.java
 *  and stores the values of the store's names and sales from
 *  that particular store. This is mainly used to call from after
 *  making change to the order of list (sorting).
 *
 *
 * @author Lewis Park, lab sec L-24
 * @version November 13th, 2022
 *
 *
 */

public class StoreAndSales {
    private final List<Integer> sales;
    private final List<String> stores;

    public StoreAndSales(List<Integer> sales, List<String> stores) {
        this.sales = sales;
        this.stores = stores;
    }

    public List<Integer> getSales() {
        return sales;
    }

    public List<String> getStores() {
        return stores;
    }
}

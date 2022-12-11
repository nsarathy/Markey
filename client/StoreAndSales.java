import java.util.List;

/**
 * StoreAndSales
 * <p>
 * list of sales and list of stores
 *
 * @author park1504
 * @version 11/13/2022
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

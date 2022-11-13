import java.util.List;

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

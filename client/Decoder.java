import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Decoder {
    public static List<String> decodeList(String s) {
        String[] array = s.split("\\\\/", -1);
        return new ArrayList<>(List.of(array));
    }

    public static Listing decodeListing(String s) {
        String[] array = s.split("\\\\/", -1);
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> sellers = new ArrayList<>();
        for (String value : array) {
            String[] prodAndSeller = value.split(";", -1);
            String[] prod = prodAndSeller[0].split("_");
            Product product = new Product(prod[0], new Store(prod[1]), Integer.parseInt(prod[2]),
                Double.parseDouble(prod[3]), prod[4]);
            products.add(product);
            sellers.add(prodAndSeller[1]);
        }
        return new Listing(products, sellers);
    }

    public static ArrayList<Product> decodeProducts(String s) {
        ArrayList<Product> products = new ArrayList<>();
        String[] prods = s.split("___", -1);
        for (String prod : prods) {
            String[] prodDetails = prod.split("_", -1);
            products.add(new Product(prodDetails[0], new Store(prodDetails[1]), Integer.parseInt(prodDetails[2]),
                Double.parseDouble(prodDetails[3]), prodDetails[4]));
        }
        return products;
    }

    public static ProductsAndStores decodeProductsAndStores(String s) {
        if (s == null || s.equals("null")) {
            return null;
        }
        String[] p$s = s.split(";");
        ArrayList<Product> products = decodeProducts(p$s[0]);
        String[] stores = p$s[1].split("___", -1);
        ArrayList<Store> storeArrayList = new ArrayList<>();
        for (String value : stores) {
            storeArrayList.add(new Store(value));
        }
        return new ProductsAndStores(products, storeArrayList);
    }

    public static StringBuilder decodeCsvString(String s) {
        StringBuilder sb = new StringBuilder();
        String[] lines = s.split("___", -1);
        for (String line : lines) {
            String[] elements = line.split(",", -1);
            for (String element : elements) {
                sb.append(element);
                sb.append(',');
            }
            sb.append('\n');
        }
        return sb;
    }

    public static ArrayList<ArrayList<String>> decodeArrayListOfArrayLists(String s) {
        String[] big = s.split("\\\\/", -1);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String value : big) {
            String[] small = value.split("\\\\n", -1);
            ArrayList<String> element = new ArrayList<>();
            Collections.addAll(element, small);
            result.add(element);
        }
        return result;
    }

    public static ArrayList<String> decodeReviews(String s) {
        String[] array = s.split("\\\\/", -1);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, array);
        return result;
    }
}

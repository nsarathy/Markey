import java.util.ArrayList;
import java.util.List;

public class Encoder {
    public static String encodeList(List<String> list) {
        String reply = "";
        for (String value : list) {
            reply = reply + value + "\\/";
        }
        if (reply.endsWith("\\/")) {
            reply = reply.substring(0, reply.length() - 2);
        }
        return reply;
    }

    public static String encodeListing(Listing listing) {
        ArrayList<Product> products = listing.getProducts();
        ArrayList<String> sellers = listing.getSellers();
        String result = "";
        for (int i = 0; i < products.size(); i++) {
            result = result + products.get(i).toString() + ";" + sellers.get(i) + "\\/";
        }
        if (result.endsWith("\\/")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static String encodeProducts(ArrayList<Product> products) {
        String result = "";
        for (Product product : products) {
            result = result + product.toString() + "___";
        }
        if (result.endsWith("___")) {
            result = result.substring(0, result.length() - 3);
        }
        return result;
    }

    public static String encodeProductsAndStores(ProductsAndStores productsAndStores) {
        ArrayList<Product> products = productsAndStores.getProducts();
        ArrayList<Store> stores = productsAndStores.getStores();
        String result = encodeProducts(products);
        result = result + ";";
        for (Store store : stores) {
            result = result + store.getName() + "___";
        }
        if (result.endsWith("___")) {
            result = result.substring(0, result.length() - 3);
        }
        return result;
    }

    public static String encodeArrayListOfArrayLists(ArrayList<ArrayList<String>> arrayList) {
        String result = "";
        for (ArrayList<String> arrayList1 : arrayList) {
            for (String value : arrayList1) {
                result = result + value + "\\n";
            }
            if (result.endsWith("\\n")) {
                result = result.substring(0, result.length() - 2);
            }
            result = result + "\\/";
        }
        if (result.endsWith("\\/")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static String encodeReviews(ArrayList<String> arrayList) {
        String result = "";
        for (String review : arrayList) {
            result = result + review + "\\/";
        }
        if (result.endsWith("\\/")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}

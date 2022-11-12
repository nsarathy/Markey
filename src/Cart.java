import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> productsToBuy;
    private ArrayList<String> sellerUsernames;
    private String customerUsername;
    public Cart(ArrayList<Product> productsToBuy, ArrayList<String> sellerUsernames, String customerUsername) {
        this.productsToBuy = productsToBuy;
        this.sellerUsernames = sellerUsernames;
        this.customerUsername = customerUsername;
    }

    public void updateCustomerPurchaseHistory() {

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < productsToBuy.size(); i++) {
            list.add(productsToBuy.get(i).toString() + "$" + sellerUsernames.get(i));
        }

        String purchaseHistoryLine = customerUsername + ";";

        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                purchaseHistoryLine = purchaseHistoryLine + list.get(i);
            } else {
                purchaseHistoryLine = purchaseHistoryLine + list.get(i) + "___";
            }
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("CustomerPurchaseHistory.txt", true));
            bw.write(purchaseHistoryLine);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSellerStatistics() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("SellerStatistics.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("SellerStatistics.txt"));
            ArrayList<String> list = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            if (list.size() == 0) { 
                list.add("seller;description;customer");
            }

            String sellerName;
            String customerName;
            String result = "";

            for (int i = 0; i < productsToBuy.size(); i++) {
                sellerName = list.get(i).substring(0, list.get(i).indexOf(";"));
                customerName = list.get(i).substring(list.get(i).lastIndexOf(";") + 1);
                if (sellerName.equals(sellerUsernames.get(i)) && customerName.equals(customerUsername)) {
                    result = result + list.get(i).substring(0, list.get(i).lastIndexOf(";"));
                    result = result + productsToBuy.get(i).toString() + "___;" + customerUsername;
                    list.set(i, result);
                } else {
                    if (i == 0 && sellerName.equals("seller") && customerName.equals("customer")) { 
                        list.set(0, sellerUsernames.get(i) + ";" + productsToBuy.get(i).toString() + "___;" + customerUsername);
                    } else {
                        list.add(sellerUsernames.get(i) + ";" + productsToBuy.get(i).toString() + "___;" + customerUsername);
                    }
                }
            }

            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i));
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

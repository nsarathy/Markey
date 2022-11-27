import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Cart
 * <p>
 * Updates txt files
 *
 * @author Sehonp05, nsarathy
 * @version 11/13/2022
 */
public class Cart {
    private ArrayList<Product> productsToBuy;
    private ArrayList<String> sellerUsernames;
    private String customerUsername;

    public Cart(ArrayList<Product> productsToBuy,
                ArrayList<String> sellerUsernames,
                String customerUsername) {
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
            FileReader fr = new FileReader("CustomerPurchaseHistory.txt");
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> lines = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();

            lines.add(purchaseHistoryLine);

            FileOutputStream fos = new FileOutputStream("CustomerPurchaseHistory.txt");
            PrintWriter pw = new PrintWriter(fos);
            for (String eachLine : lines) {
                pw.println(eachLine);
            }
            pw.close();
            fos.close();

        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }

        /*

        try {
            BufferedWriter bw = new BufferedWriter(
                new FileWriter("CustomerPurchaseHistory.txt", true)
            );
            bw.write(purchaseHistoryLine+"\n");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }

    public void updateSellerStatistics() {

        try {
            BufferedReader br = new BufferedReader(
                new FileReader("SellerStatistics.txt")
            );
            ArrayList<String> list = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
            br.close();

            if (list.size() > 0) {
                for (int i = 0; i < productsToBuy.size(); i++) {
                    Product product = productsToBuy.get(i);
                    String sellerUsername = sellerUsernames.get(i);
                    boolean added = false;
                    for (int j = 0; j < list.size(); j++) {
                        String eachLine = list.get(j);
                        String[] details = eachLine.split(";", -1);
                        String sellerName = details[0];
                        String customerName = details[2];
                        if (sellerName.equals(sellerUsername) &&
                            customerName.equals(this.customerUsername)) {
                            added = true;
                            list.set(j, sellerName +
                                ";" +
                                details[1] +
                                "___" +
                                product.toString() +
                                ";" +
                                customerName
                            );
                        }
                    }
                    if (!added) {
                        list.add(sellerUsername +
                            ";" +
                            product.toString() +
                            ";" +
                            customerUsername
                        );
                    }
                }
            } else {
                for (int i = 0; i < productsToBuy.size(); i++) {
                    list.add(sellerUsernames.get(i) +
                        ";" +
                        productsToBuy.get(i).toString() +
                        ";" +
                        customerUsername
                    );
                }
            }


            /*
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
                    result = result +
                    list.get(i).substring(0,
                    list.get(i).lastIndexOf(";"));
                    result = result +
                    productsToBuy.get(i).toString() +
                    "___;" +
                    customerUsername;
                    list.set(i, result);
                } else {
                    if (i == 0 && sellerName.equals("seller") &&
                    customerName.equals("customer")) {
                        list.set(0, sellerUsernames.get(i) +
                        ";" + productsToBuy.get(i).toString() +
                        "___;" + customerUsername
                        );
                    } else {
                        list.add(sellerUsernames.get(i) +
                        ";" +
                        productsToBuy.get(i).toString() +
                        "___;" +
                        customerUsername
                        );
                    }
                }
            }
             */

            PrintWriter bw = new PrintWriter(
                new FileOutputStream("SellerStatistics.txt")
            );
            for (String s : list) {
                bw.println(s);
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
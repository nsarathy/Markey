import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * CustomerDashboardMethods
 * <p>
 * File handling for Customer Dashboard
 *
 * @author nsarathy
 * @version 12/11/2022
 */
public class CustomerDashboardMethods {
    public static List<String> readPurchaseHistory() throws IOException {
        //first method will be to get which lines belong to the customer
        List<String> historyList = new ArrayList<String>();


        File f = new File("CustomerPurchaseHistory.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        if (f == null || !(f.exists())) {
            throw new FileNotFoundException();
        }

        String line = bfr.readLine();

        while (line != null) {
            historyList.add(line);
            line = bfr.readLine();
        }

        bfr.close();
        fr.close();

        return historyList;
    }

    public static List<String> readCustomerStats() throws IOException {
        List<String> sellerStoreInfo = new ArrayList<String>();


        File f = new File("CustomerStatistics.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        if (f == null || !(f.exists())) {
            throw new FileNotFoundException();
        }

        String line = bfr.readLine();

        while (line != null) {
            sellerStoreInfo.add(line);
            line = bfr.readLine();
        }

        bfr.close();
        fr.close();

        return sellerStoreInfo;
    }
}

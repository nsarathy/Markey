import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDashboardMethods {
    public static List<String> readPurchaseHistory() throws IOException {
        //first method will be to get which lines belong to the customer
        List<String> historyList = new ArrayList<String>();
        int count = 0;


        File f = new File("CustomerPurchaseHistory.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        if (f == null || !(f.exists())) {
            throw new FileNotFoundException();
        }

        String line = bfr.readLine();

        while (line != null) {
            count++;
            historyList.add(line);
            line = bfr.readLine();
        }

        return historyList;
    }

    public static List<String> readCustomerStats() throws IOException {
        List<String> sellerStoreInfo = new ArrayList<String>();
        int count = 0;


        File f = new File("CustomerStatistics.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        if (f == null || !(f.exists())) {
            throw new FileNotFoundException();
        }

        String line = bfr.readLine();

        while (line != null) {
            count++;
            sellerStoreInfo.add(line);
            line = bfr.readLine();
        }

        return sellerStoreInfo;
    }
}
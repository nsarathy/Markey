import java.util.*;
import java.io.*;


public class CustomerDashboard {
    // TODO: Just display CustomerStatistics.txt
    // CustomerStatistics: Data will include a list of stores by number of products sold
    // Let customer sort by most products sold to the least products sold and vice versa
    // a list of stores by the products purchased by that particular customer. -> Call MarketPlace [viewPurchaseHistory()]
    // Use Scanner to provide an interface for user to do any of the above


    public void customerInterface() throws IOException {
        CustomerDashboard sd = new CustomerDashboard();
        String[] customerNames = sd.readCustomerStats();
        int count = customerNames.length;
        for (int i = 0; i < count; i++) {
            System.out.println("-------------------");
            System.out.println(customerNames[i]);
        }
    }

    public void displayCustomerStatistics() {
        //first get all the elements of the customer's history from probably both
        //customerStatistics.txt and PurchaseHistory.txt not sure yet, but need to
        //format and display the total customer stats.
        
    }

    public void sortProducts() {
        //currently either looking at the full list of all total products sold
        //and sorting by the amount sold. 
        
    }

    public String[] readCustomerStats() throws FileNotFoundException, IOException {
        //this method is not done yet, need to read more just don't know the format yet.
        //need to read all the information and either add it to the string array or
        //probably doing another way is more beneficial. Maybe getters and setters.
        List<String> customerStats = new ArrayList<String>();
        int count = 0;

        try {
            File f = new File("CustomerStatistics.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            if (f == null || !(f.exists())) {
                throw new FileNotFoundException();
            }

            String line = bfr.readLine();

            while (line != null) {
                count++;
                customerStats.add(line);
                line = bfr.readLine();
            }
            String[] customerStatsArr = new String[customerStats.size()];
            customerStatsArr = customerStats.toArray(customerStatsArr);
            String[] customerNames = new String[count];
            for (int i = 0; i < customerNames.length; i++) {
                ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(customerStatsArr[i].split(";")));
                customerNames[i] = collectedData.get(0);
            }

            return customerNames;

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }



}

import java.util.*;
import java.io.*;


public class SellerDashboard {
    /**
     * TODO: A method to Display SellerStatistics.txt : Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
     * TODO: Let seller sort items based on most sold to least sold
     * TODO: Display stores and products and use Scanner to provide an interface for user to do any of the above
     */


    //method used to display the interface the seller user can interact with and call the other method
    public void sellerInterface() throws IOException {
        SellerDashboard sd = new SellerDashboard();
        String[] sellerNames = sd.readSellerStats();
        int count = sellerNames.length;
        for (int i = 0; i < count; i++) {
            System.out.println("-------------------");
            System.out.println(sellerNames[i]);
        }
    }

    public void displaySellerStats() {

    }

    public void sortProducts() {

    }

    public String[] readSellerStats() throws FileNotFoundException, IOException {
        List<String> sellerStats = new ArrayList<String>();
        int count = 0;

        try {
            File f = new File("SellerStatistics.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            if (f == null || !(f.exists())) {
                throw new FileNotFoundException();
            }

            String line = bfr.readLine();

            while (line != null) {
                count++;
                sellerStats.add(line);
                line = bfr.readLine();
            }
            String[] sellerStatsArr = new String[sellerStats.size()];
            sellerStatsArr = sellerStats.toArray(sellerStatsArr);
            String[] sellerNames = new String[count];
            for (int i = 0; i < sellerNames.length; i++) {
                ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(sellerStatsArr[i].split(";")));
                sellerNames[i] = collectedData.get(0);
            }

            return sellerNames;

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}

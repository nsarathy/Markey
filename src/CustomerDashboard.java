import java.util.*;
import java.io.*;


public class CustomerDashboard {
    // TODO: Just display CustomerStatistics.txt
    // CustomerStatistics: Data will include a list of stores by number of products sold
    // Let customer sort by most products sold to the least products sold and vice versa
    // a list of stores by the products purchased by that particular customer. -> Call MarketPlace [viewPurchaseHistory()]
    // Use Scanner to provide an interface for user to do any of the above

    private String customerUsername;
    private String[] stores;

    //constructor
    public CustomerDashboard(String customerUsername) {
        this.customerUsername = customerUsername;
    }


    public void main() {
        //this method will contain what the user can do with all these methods, displaying
        //manipulating and sorting the products with the sort method

    }

    public void customerInterface() throws IOException {
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

    public void readCustomerStats() throws FileNotFoundException, IOException {
        //this method is not done yet, need to read more just don't know the format yet.
        //need to read all the information and either add it to the string array or
        //probably doing another way is more beneficial. Maybe getters and setters.
        List<String> sellerStoreInfo = new ArrayList<String>();
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
                sellerStoreInfo.add(line);
                line = bfr.readLine();
            }

            //first we make the extended sellerStoreInfo into the bite sized
            //information that we want
            String[] storeInfoStr = new String[sellerStoreInfo.size()];
            storeInfoStr = sellerStoreInfo.toArray(storeInfoStr);
            List<String> splitByType = new ArrayList<>();
            String[] sellerNames = new String[count];

            //first split is by the ";" where it splits the sellers names
            //and their respective stores and their sales number
            for (int i = 0; i < count; i++) {
                ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(storeInfoStr[i].split(";")));
                sellerNames[i] = collectedData.get(0);
                splitByType.add(collectedData.get(1));
            }

            String[] specificStoreStr = new String[splitByType.size()];
            specificStoreStr = splitByType.toArray(specificStoreStr);
            List<String> splitByStore = new ArrayList<>();

            //this next split, splits between each specific store
            for (int i = 0; i < splitByType.size(); i++) {
                ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(specificStoreStr[i].split("___")));
                int storeNum = i + 1;
                splitByStore.add("Store: " + storeNum);

                //need to use this part to find out how many stores each seller has and then split them
                int sizeOfTypes = splitByType.get(i).length() - splitByType.get(i).replaceAll("___","").length();
                int wantedNum = sizeOfTypes / 3;

                // this adds the each store into splitByStore
                for (int q = 0; q < wantedNum + 1; q++) {
                    splitByStore.add(collectedData.get(q));
                }
            }

            //we should have two main array list by now
            // first we have seller name split with all stores
            // second we should have each store and their sales number together



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

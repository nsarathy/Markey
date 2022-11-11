import java.util.*;
import java.io.*;


public class CustomerDashboard {
    // TODO: Just display CustomerStatistics.txt
    // CustomerStatistics: Data will include a list of stores by number of products sold
    // Let customer sort by most products sold to the least products sold and vice versa
    // a list of stores by the products purchased by that particular customer. -> Call MarketPlace [viewPurchaseHistory()]
    // Use Scanner to provide an interface for user to do any of the above

    private String customerUsername;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CustomerDashboard cd = new CustomerDashboard("testUser");
        cd.main();

    }

    //constructor
    public CustomerDashboard(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void customerInterface() throws IOException {
    }

    public void displayCustomerStatistics() {
        //first get all the elements of the customer's history from probably both
        //customerStatistics.txt and PurchaseHistory.txt not sure yet, but need to
        //format and display the total customer stats.

    }

    public void sortPurchaseHistoryList() {

    }

    public List<String> readCustomerStats() throws FileNotFoundException, IOException {
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

            return sellerStoreInfo;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> typeSplitter() throws FileNotFoundException, IOException {
        //first we make the extended sellerStoreInfo into the bite sized
        //information that we want
        List<String> sellerStoreInfo = readCustomerStats();
        String[] storeInfoStr = new String[sellerStoreInfo.size()];
        storeInfoStr = sellerStoreInfo.toArray(storeInfoStr);
        List<String> splitByType = new ArrayList<>();

        //first split is by the ";" where it splits the sellers names
        //and their respective stores and their sales number
        for (int i = 0; i < sellerStoreInfo.size(); i++) {
            ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(storeInfoStr[i].split(";")));
            splitByType.add(collectedData.get(1));
        }

        return splitByType;
    }

    public String[] getSellerNames() throws FileNotFoundException, IOException {
        List<String> sellerStoreInfo = readCustomerStats();
        String[] storeInfoStr = new String[sellerStoreInfo.size()];
        storeInfoStr = sellerStoreInfo.toArray(storeInfoStr);
        String[] sellerNames = new String[sellerStoreInfo.size()];

        for (int i = 0; i < sellerStoreInfo.size(); i++) {
            ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(storeInfoStr[i].split(";")));
            sellerNames[i] = collectedData.get(0);
        }

        return sellerNames;

    }

    public List<String> byStoreSplitter() throws FileNotFoundException, IOException {
        List<String> splitByType = typeSplitter();
        String[] specificStoreStr = new String[splitByType.size()];
        specificStoreStr = splitByType.toArray(specificStoreStr);
        List<String> splitByStoreAndSales = new ArrayList<>();

        for (int i = 0; i < splitByType.size(); i++) {
            ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(specificStoreStr[i].split("___")));
            int storeNum = i + 1;
            splitByStoreAndSales.add("Seller: " + (i + 1));

            int sizeOfTypes = splitByType.get(i).length() - splitByType.get(i).replaceAll("___", "").length();
            int wantedNum = sizeOfTypes / 3;

            for (int q = 0; q < wantedNum + 1; q++) {
                splitByStoreAndSales.add(collectedData.get(q));
            }
        }

        return splitByStoreAndSales;

    }

    // format looks like
    //seller
    //seller name
    //storeName_salesNum


    //we should have two main array list by now
    // first we have seller name split with all stores
    // second we should have each store and their sales number together
    //next we need to split again but some how make sure we know which seller it belongs to

    //make a nested for loop, which loops until the seller changes

    public List<String> getStoreAndSales() throws FileNotFoundException, IOException {
        List<String> splitByStoreAndSales = byStoreSplitter();
        String[] specificSalesInfo = new String[splitByStoreAndSales.size()];
        specificSalesInfo = splitByStoreAndSales.toArray(specificSalesInfo);
        List<String> storeAndSales = new ArrayList<>();

        for (int i = 0; i < splitByStoreAndSales.size(); i++) {
            if (splitByStoreAndSales.get(i).contains("_")) {
                List<String> splitterOf = new ArrayList<>(Arrays.asList(specificSalesInfo[i].split("_")));
                storeAndSales.add(splitterOf.get(0));
                storeAndSales.add(splitterOf.get(1));
            } else if (!(splitByStoreAndSales.get(i).contains("_"))) {
            }
        }
        return storeAndSales;

    }

    public List<String> getOnlyStore() throws FileNotFoundException, IOException {
        List<String> splitByStoreAndSales = byStoreSplitter();
        String[] specificSalesInfo = new String[splitByStoreAndSales.size()];
        specificSalesInfo = splitByStoreAndSales.toArray(specificSalesInfo);
        List<String> onlyStore = new ArrayList<>();

        for (int i = 0; i < splitByStoreAndSales.size(); i++) {
            if (splitByStoreAndSales.get(i).contains("_")) {
                List<String> splitterOf = new ArrayList<>(Arrays.asList(specificSalesInfo[i].split("_")));
                onlyStore.add(splitterOf.get(0));
            } else if (!(splitByStoreAndSales.get(i).contains("_"))) {
            }
        }
        return onlyStore;

    }

    public List<String> getOnlySales() throws FileNotFoundException, IOException {
        List<String> splitByStoreAndSales = byStoreSplitter();
        String[] specificSalesInfo = new String[splitByStoreAndSales.size()];
        specificSalesInfo = splitByStoreAndSales.toArray(specificSalesInfo);
        List<String> onlySales = new ArrayList<>();

        for (int i = 0; i < splitByStoreAndSales.size(); i++) {
            if (splitByStoreAndSales.get(i).contains("_")) {
                List<String> splitterOf = new ArrayList<>(Arrays.asList(specificSalesInfo[i].split("_")));
                onlySales.add(splitterOf.get(1));
            } else if (!(splitByStoreAndSales.get(i).contains("_"))) {
            }
        }

        return onlySales;

    }

    public List<Integer> getStoreTotal() throws FileNotFoundException, IOException {
        List<String> splitByType = typeSplitter();
        String[] specificStoreStr = new String[splitByType.size()];
        specificStoreStr = splitByType.toArray(specificStoreStr);
        List<Integer> numberOfStores = new ArrayList<>();

        for (int i = 0; i < splitByType.size(); i++) {
            ArrayList<String> collectedData = new ArrayList<>(Arrays.asList(specificStoreStr[i].split("___")));

            int sizeOfTypes = splitByType.get(i).length() - splitByType.get(i).replaceAll("___", "").length();
            int wantedNum = sizeOfTypes / 3 + 1;

            numberOfStores.add(wantedNum);
        }

        return numberOfStores;

    }

    public void displaySortOptions(int givenSortID) {
        int sortID = givenSortID;

        System.out.println("Would you like to sort this List?");
        if (sortID == 0) {
            System.out.println("1: High to Low");
            System.out.println("2: Low to High");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }

        if (sortID == 1) {
            System.out.println("1: Low to High");
            System.out.println("2: Revert List");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }

        if (sortID == 2) {
            System.out.println("1: High to Low");
            System.out.println("2: Revert List");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }
    }

    //when sorting we can actually just change the way the list arrays are arranged instead of
    //trying to change the way we place them
    public void displayOriginalStores() throws IOException {
        int update = 0;
        String[] sellerNameList = getSellerNames();
        List<String> originalStoreList = getOnlyStore();
        List<String> originalSaleList = getOnlySales();
        List<Integer> storeTotal = getStoreTotal();

        //this is for view the stores
        for (int i = 0; i < sellerNameList.length; i++) {
            int count = 0;
            System.out.println("--------------------");
            System.out.println("Seller: " + sellerNameList[i]);
            for (int q = 0; q < storeTotal.get(i); q++) {
                System.out.print(originalStoreList.get(q + update) + ": ");
                System.out.println(originalSaleList.get(q + update) + " Sales");
                count++;
            }
            update += count;
        }
        System.out.println("--------------------");
        System.out.println();


    }

    public List<String> splitStoreList() throws FileNotFoundException, IOException {
        List<String> storeList = getOnlyStore();
        List<Integer> storeTotal = getStoreTotal();

        List<String> splitterList = new ArrayList<>();
        int update = 0;
        int count = 0;

        for (int i = 0; i < storeTotal.size(); i++) {
            count = 0;
            for (int q = 0; q < storeTotal.get(i); q++) {
                splitterList.add(storeList.get(q + update));
                count++;
            }
            update += count;
            splitterList.add("store end");
        }

        return splitterList;
    }

    public List<String> splitSaleList() throws FileNotFoundException, IOException {
        List<String> saleList = getOnlySales();
        List<Integer> storeTotal = getStoreTotal();

        List<String> splitterList = new ArrayList<>();
        int update = 0;
        int count = 0;

        for (int i = 0; i < storeTotal.size(); i++) {
            count = 0;
            for (int q = 0; q < storeTotal.get(i); q++) {
                splitterList.add(saleList.get(q + update));
                count++;
            }
            update += count;
            splitterList.add("store end");
        }

        return splitterList;
    }


    //sorting the arraylist to be used in the displaySorted method
    public void sortHighLow() throws FileNotFoundException, IOException {
        List<String> originalStoreList = getOnlyStore();
        List<String> originalSaleList = getOnlySales();
        List<Integer> storeTotal = getStoreTotal();

        List<String> htLStoreList = new ArrayList<>();
        List<String> htLSalesList = new ArrayList<>();

        //high to low sort


    }

    //sorting the arraylist to be used in the displaySorted method
    public void sortLowHigh() {

    }

    public void displaySorted() {

    }

    public void main() throws FileNotFoundException, IOException {
        //this method will contain what the user can do with all these methods, displaying
        //manipulating and sorting the products with the sort method
        Scanner input = new Scanner(System.in);
        boolean repeat = true;
        int sortID = 0;
        //sortID 0 = unsorted
        //sortID 1 = high to low
        //sortID 2 = low to high
        final String[] originalSellerNameList = getSellerNames();
        final List<String> originalStoreList = getOnlyStore();
        final List<String> originalSaleList = getOnlySales();
        final List<Integer> storeTotal = getStoreTotal();

        while (repeat) {
            int answer1 = 0;
            do {
                try {
                    System.out.println("*CUSTOMER DASHBOARD*");
                    System.out.println();
                    System.out.println("1. View All Stores");
                    System.out.println("2. View Purchased Stores");
                    System.out.println("3. Exit Customer Dashboard");
                    System.out.println();
                    System.out.print("Enter Option Here: ");
                    answer1 = input.nextInt();
                    input.nextLine();
                    System.out.println();

                    if (answer1 == 1) {
                        break;
                    } else if (answer1 == 2) {
                        break;
                    } else if (answer1 == 3) {
                        repeat = false;
                        break;
                    } else {
                        System.out.println("Please enter valid choice!");
                        System.out.println();
                    }
                } catch (InputMismatchException e) {
                    System.out.println();

                    System.out.println("Please enter valid choice!");
                    System.out.println();
                    input.nextLine();
                }
            } while (answer1 != 1 || answer1 != 2 || answer1 != 3);

            //first print unsorted original list
            if (answer1 == 1) {
                displayOriginalStores();
                displaySortOptions(sortID);
                int wantedSort = input.nextInt();
                input.nextLine();
                System.out.println();
            }


            //need to implement sort feature, probably pull from a method.


        }
    }

}

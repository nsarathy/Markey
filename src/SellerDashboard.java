import java.util.*;
import java.io.*;


public class SellerDashboard {
    /**
     * TODO: A method to Display SellerStatistics.txt : Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
     * TODO: Let seller sort items based on most sold to least sold
     * TODO: Display stores and products and use Scanner to provide an interface for user to do any of the above
     */


    //read the sellerStats
    //display for the specific seller user, all the customer usernames that have bought from them,
    //number of items each customer has bought from the seller, list of products the customer has bought,
    //number of sales for each product the seller has sold.

    //method used to display the interface the seller user can interact with and call the other method

    private String userName;

    public SellerDashboard(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //read the sellerstats file and return String list
    public List<String> readSellerStats() {
        List<String> fullList = new ArrayList<>();

        try {
            File f = new File("SellerStatistics.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            if (f == null || !(f.exists())) {
                throw new FileNotFoundException();
            }

            String line = bfr.readLine();

            while (line != null) {
                fullList.add(line);
                line = bfr.readLine();
            }

            return fullList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //scan the array from method above and scan to see if seller username
    //matches and then make a new array which will only have the values if
    //seller name matches.

    public List<String> matchedList() {
        String sellerName = userName;
        List<String> fullList = readSellerStats();
        List<String> scannedList = new ArrayList<>();

        for (int i = 0; i < fullList.size(); i++) {
            if (fullList.get(i).contains(sellerName)) {
                scannedList.add(fullList.get(i));
            }
        }
        return scannedList;
    }


    //format without the seller name though because we don't need it anymore once
    //it's in the system.
    public List<String> onlyProducts() {
        List<String> scannedList = matchedList();
        List<String> productList = new ArrayList<>();

        for (int i = 0; i < scannedList.size(); i++) {
            List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
            productList.add(collectedData.get(1));
        }
        return productList;
    }

    public List<String> onlyCustomer() {
        List<String> scannedList = matchedList();
        List<String> customer = new ArrayList<>();

        for (int i = 0; i < scannedList.size(); i++) {
            List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
            customer.add((collectedData.get(2)));
        }
        return customer;
    }

    //this method will split the given string into the products and the customer
    //we then need to make a display showing the customer and their purchase
    public void displayPurchased() {

    }

    public void displaySellerDash() {

    }


    public void main() {

    }


}

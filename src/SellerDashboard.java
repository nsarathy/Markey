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

    public void sellerInterface() throws IOException {

    }

    public void displaySellerStats() {

    }

    public void sortProducts() {

    }

    public void readSellerStats() throws FileNotFoundException, IOException {
        ArrayList<String> index = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> customers = new ArrayList<>();
        ArrayList<String> sellers = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        String sellerUsername = userName;
        int count = 0;

        //check if seller is the same as current user
        //if true add customer and their products
        //check which product is from which store
        //list their name product and sales.
        
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
                index.add(line);
                line = bfr.readLine();
            }

            for (int i = 0; i < count; i++) {
                ArrayList<String> sellerNames = new ArrayList<>(Arrays.asList(index.get(i).split(";")));
                if (sellerNames.get(i).equals(sellerUsername)) {
                    
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

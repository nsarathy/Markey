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
        
    }
    
    //scan the array from method above and scan to see if seller username
    //matches and then make a new array which will only have the values if
    //seller name matches.
    
    //format without the seller name though because we don't need it anymore once
    //its in the system.
    public List<String> matchedList() {
        
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

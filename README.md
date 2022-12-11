# Markey
## Neel Sarathy, Yudon Shin, Lewis Park, Tejasvi Nalagundla, Sehyeong Oh
## Yudon Shin - Submitted report on Brightspace
## Neel Sarathy - Submitted Vocareum workspace
## Lewis Park - Submitted Presentation
## Option 3 - Marketplace
Optional feature: Customers can leave reviews associated with specific products from sellers. Other customers can view the reviews after they post. Sellers may view reviews on their products. 
## Instructions to compile and run
Client and Server were developed as 2 separate intellij projects, so some classes may repeat in the folders.
There are 2 folders server and client that contain the classes and files for the client and server sides of the project respectively.
- Compile and run ```Server.java``` in the ```server``` folder to get the server up and running
- Compile and run ```Login.java``` in the ```client``` folder to start the application 
- The GUI components will guide you through using Markey
- <-----------------------------------IMPORTANT---------------------------------> Changes made by seller or customer such as crreating a listing or deleting or editing one aren't immediately reflected in the main page or certain other areas, the user has to refresh (click refresh button [1st button from left in main listing page's top panel) to see changes. The user also has to click the refresh button to see changed made by other users.

## Features:
### Core:
- [x] A centralized page listing available products for purchase
- [x] A product/item will include the name of the product, the store selling it, a description of the product, quantity available for purchase, the price
- [x] The marketplace listing page will show the store, product name, and price of the available goods. Customers can select a specific product to be taken to that product's page, which will include a description and the quantity available. 
- [x] When items are purchased, the quantity available for all users decreases by the amount being purchased. 
### Seller:
- [x] Sellers can create, edit, or delete products associated with their stores. 
- [x] Sellers can view a list of their sales by store, including customer information and revenues from the sale. 
### Customer:
- [x] Customers can view the overall marketplace listing products for sale, search for specific products using terms that match the name, store, or description, and sort the marketplace on price or quantity available. 
- [x] Customers can purchase items from the product page and review a history of their previously purchased items. 
## Selections:
### Files:
- [x] Sellers can import or export products for their stores using a csv file.
- [x] Customers can export a file with their purchase history.  
- [x] Sellers can view a dashboard that lists statistics for each of their stores.
- [x] Data will include a list of customers with the number of items that they have purchased and a list of products with the number of sales.
- [x] Sellers can choose to sort the dashboard.
- [x] Customers can view a dashboard with store and seller information.
- [x] Data will include a list of stores by number of products sold and a list of stores by the products purchased by that particular customer. 
- [x] Customers can choose to sort the dashboard.
### Shopping Cart
- [x] Customers can add products from different stores to a shopping cart to purchase all at once, and can remove any product if they choose to do so. The shopping cart is preserved between sessions, so a customer may choose to sign out and return to make the purchase later.  
- [x] Sellers can view the number of products currently in customer shopping carts, along with the store and details associated with the products. 
## Optional features:
- [x] Customers can leave reviews associated with specific products from sellers. Other customers can view the reviews after they post. Sellers may view reviews on their products. 
## Cuncurrency, Networks, and GUI
- [X] The application must support simultaneous use by multiple users over a network. New content should appear appear as users add it.
- [X] All user interactions must be GUI based.
- [X] Data must persist regardless of whether or not a user is connected. If a user disconnects and reconnects, their data should still be present. 
- [X] Descriptive errors should appear as appropriate. The application should not crash under any circumstances. 


## client
### Account.java
Objects of this class represent an account of a user.
- Instance variables:
-   private String username
    private String password
- parameterized constructor
- getters and setters
- toString()

### CartNotTrackableException.java
This exception is thrown when there's an error reading a cart of a customer

### Client.java
Handles connection to the server and transfer of data between the client and server
Sends a line of specific regex to server as a request, reads server's response

### CreateAccount.java
The methods of this class are called to create an account for a user
To create a new account on Markey
- Instance variables:
    - private String accountType : seller or buyer type
    - private boolean accountSignal : chose to be seller or customer (true if customer)
- Parameterized constructor
- instance variables have getters and setters
- public boolean checkingFields(String username, String password, boolean check) : checks validity of fields
- public boolean checkUsername(String username) : checks if username is already taken
- public boolean checkLength(String username) : checks if username is empty
- public boolean checkIfLetter(String username) : cheks if username has valid characters
- public boolean checkPassword(String password) : checks if password is empty
- public void writeAccount(Account newAccount, boolean check) : updates data with user details
- public void main() : prompts user to input details to create account

### CreateAccountGUI.java
The GUI for creating an account

### Customer.java
Extends Account,java
The objects of this class represent a customer user
- Instance variables :
    - Product[] purchases : array of purchases made by customer
- Parameterized constructor that calls super
- getter and seter for instance variable
- public boolean equals(Object obj) : checks if an object is a particular customer

### CustomerAndSales.java
Objects of this class hold a list of customers and a list of sales
- Instance variables :
    - private final List<Integer> sales : list of integers
    - private final List<String> customer : lst of strings
- Parameterized constructor
- getters and setters

### CustomerDashboard.java
Constitutes the customer's dashboard
- Instance variable
    - private String customerUsername : username of customer
- Parameterized cosntructor
- getter and setter for instance variable
- public List<String> readPurchaseHistory() : reads purchase history of customer
- public List<String> matchCustomerName() throws IOException : to check if customer matches with obtained data
- public List<String> splitByProduct() throws IOException : splits a line to get every product sold
- public void displayOriginalCustomerStatistics() throws IOException : displays data
- public StoreAndSales sortPurchaseHistoryLowHigh() throws IOException : sorts customer's purchases from lowest in quantities bought to highest
- public void displayPurchaseHistoryLowHigh() throws IOException : formats and displays result from previous method
- public StoreAndSales sortPurchaseHistoryHighLow() throws IOException : sorts customer's purchases from highest in quantities bought to lowest
-  public void displayPurchaseHistoryHighLow() throws IOException : formats and displays the result of previous method
- public List<String> readCustomerStats() throws FileNotFoundException, IOException : reads data
- public List<String> typeSplitter() throws FileNotFoundException, IOException : splits a line to get seller and store details
- public String[] getSellerNames() throws FileNotFoundException, IOException : gets seller names
- public List<String> byStoreSplitter() throws FileNotFoundException, IOException : to split a line and get store details
- public List<String> getOnlyStore() throws FileNotFoundException, IOException : get stores that have sold something
- public List<String> getOnlySales() throws FileNotFoundException, IOException : get sales of stores that have sold something
- public List<Integer> getStoreTotal() throws FileNotFoundException, IOException : get a list of the number of stores under each seller
- public void displaySortOptions(int givenSortID) : displays options to sort
- public void displayOriginalStores() throws IOException : display unsorted data
- public void displayHighLowStores() throws IOException : display high to low on sales sorted stores for each seller
- public void displayLowHighStores() throws IOException : display low to high on sales sorted stores for each seller
- public StoreAndSales sortHighLow() throws FileNotFoundException, IOException : sorts the stores of each seller from high to low on sales
- public StoreAndSales sortLowHigh() throws FileNotFoundException, IOException : sorts the stores of each seller from low to high on sales

### CustomerDashboardGUI.java
GUI for the customer dashboard

### Decoder.java
Decodes data received by the client from the server for other classes to access and use

### HintTextField.java
Extends JTextField and shows a hint in the textfield for user

### Listing.java
Objects of this class hold a list of products and their respectie sellers
    - Instance variables:
    private ArrayList<Product> products
    private ArrayList<String> sellers
- Parameterized constructor
- getters and setters

### LoginMethods
It's methods handle data while login
- public static String reader(String username, String password) : checks if username and password matches

### Login.java
For user to login to Markey
- public static void main(String[] args) : prompts user to login or create account, executes MarketPlace when logged in

### LoginGUI.java
GUI for Login

### MarketPlaceGUI.java
Where the sellers and customers perform most of the operations such as buying, listing, etc
GUI for the main listing page

### NotInStockException.java
This exception is thrown when a product being added to a customer's cart is not available in stock

### Product.java
Objects of this class represent a product that's on sale on Markey
- Instance variables:
    private String name
    private Store store
    private int quantity
    private double price
    private String description
- Parameterized constructor
- getters and setters
- toString()

### ProductAndStores.java
Objects of this class contain a list of products and a list of stores
- Instance variables:
    private ArrayList<Product> products
    private ArrayList<Store> stores
- Parameterized constructor
- getters and setters

### PurchaseHistoryView
For customers to view their purchase history

### SellerDashboardGUI.java
Dashboard for a seller

### ServerDetails.java
Interface which has the IP address of the server

### Store.java
Objects of this class represent a store belonging to a seller
- Instance variable: private String name
- Parameterized constructor
- getter and setter
- toString()

### StoreAndSales.java
Objects of this class contain a list of stores and a list of sales
- Instance variables :
    - private final List<Integer> sales : list of integers
    - private final List<String> stores : list of strings
- Parameterized constructor
- getters 

### StoreView
For sellers to view their stores in their dashboard

## server

### Account.java
Objects of this class represent an account of a user.
- Instance variables:
-   private String username
    private String password
- parameterized constructor
- getters and setters
- toString()

### Cart.java
It's methods update data when a purchase is made
- Intance variables:
-   private ArrayList<Product> productsToBuy
    private ArrayList<String> sellerUsernames
    private String customerUsername
- parameterized constructor
- public void updateCustomerPurchaseHistory() : update purchase history
- public void updateSellerStatistics() : update seller statistics

### CartNotTrackableException.java
This exception is thrown when there's an error reading a cart of a customer

### CreateAccountMethods.java
Has methods that handle data when creating an account.
- public static boolean checkUsername(String username) : checks if a username is taken
- public static void writeAccount(Account newAccount, boolean check) : updates data

### CustomerDashboardMethods
It's methods handle data for Customer Dashboard
- public static List<String> readPurchaseHistory() : reads purchase history
- public static List<String> readCustomerStats() : reads customer statistics

### Encoder
Encodes data into a single line of String to send to client

### Listing
Objects of this class hold a list of products and their respectie sellers
- Instance variables:
    private ArrayList<Product> products
    private ArrayList<String> sellers
- Parameterized constructor
- getters and setters

### LoginMethods
It's methods handle data while login
- public static String reader(String username, String password) : checks if username and password matches

### MarketPlaceMethods
It's methods handle data for the main listing page
- Instance variable : private final String username
- Parameterized constructor
- public Listing readProductsTxt() : Reads products on sale
- public Listing readCart() : reads customer's cart
- public void storeCart(ArrayList<Product> currentCart, ArrayList<String> currentSellers) : writes cart data
- public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames) : Updates data when customer checks out
- public ArrayList<Product> viewPurchaseHistory() : reads purchase history
- public StringBuilder customerCsvExport() : reads customer data
- public ProductsAndStores readSeller() : reads seller details
- public void createProduct(String name, Store store, int quantity, double price, String description) : writes product
- public void createProduct(Product newProduct) : writes product
- public void deleteProduct(String checkLine) : deletes product
- public void editProduct(String checkLine, String replaceLine) : edits product
- public ArrayList<ArrayList<String>> seeCarts() : reads all customers' carts
- public void csvImport(ArrayList<Product> products) : reads seller new products
- public void reviewWrite(Product product, String stars, String review) : updates reviews data
- public ArrayList<String> reviewRead(Product p)

### Product
Objects of this class represent a product that's on sale on Markey
- Instance variables:
    private String name
    private Store store
    private int quantity
    private double price
    private String description
- Parameterized constructor
- getters and setters
- toString()

### ProductAndStores
Objects of this class contain a list of products and a list of stores
- Instance variables:
    private ArrayList<Product> products
    private ArrayList<Store> stores
- Parameterized constructor
- getters and setters

### SellerDashboardMethods
Handles data for Seller Dashboard
- public static List<String> readSellerStats() : Reads seller statistics

### Server
Wait for connections from clients and sends data to clients when requested
- Reads a line and sees if it matches with a specific request, once a match is found gets data by calling methods of other classes and sends data to client

### Store
Objects of this class represent a store belonging to a seller
- Instance variable: private String name
- Parameterized constructor
- getter and setter
- toString()
    
## Data (.txt files)
### Accounts
Account details of all users of Markey

### carts
Carts of customers

### CustomerPurchaseHistory
Purchase histories of customers

### CustomerStatistics
Data for customer dashboard

### Products
Everything that's on sale

### SellerStatistics
Data for seller dashboard

### Reviews.txt
Data of reviews made by customers

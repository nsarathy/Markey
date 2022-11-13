# Markey
## CS18000 L24 Group 3: Yudon Shin, Lewis Park, Teju Nalagundla, Sehyeong Oh, Neel Sarathy
## Option 3 Marketplace
Selections: Files, Statistics, Shopping Cart
# Compiling and Running

## Run the `public static void main(String[] args)` method in Login.java

- You will be prompted to enter whether or not you have an account
- If you do not have an account you will be prompted to create one. If you do have an account you will be prompted to login
- While creating account you will have to provide a username consisitng only of letter, a password, and if you're a seller or a buyer. Once you create an account you will move back to login.
- While logging in you will e prompted to enter your username and your password. If they match, you will be logged in.
- Once you log in you will be able to see a list of everything that's for sale on Markey

Based on what you type and enter according to prompts on screen:

- If you are a seller you will be given options to: put up something for sale, remove something from sale, edit something that you had put up for sale, go to your dashboard, import or export items for sale as csv files, and exit.
- If you are a customer you will be given options to view your cart, sort listings, view your purchase history, go to your dashboard, and exit.

- Seller Dashboard and Customer Dashboard include everything mentioned in the handout for these dashboards. Prompts on screen will guide you to do various things like viewing and sorting the data.

## Inputs:
```
Welcome to the Marketplace!
Do you have an existing account?
1. Yes
2. No
```
Enter '1' or '2' accordingly
- 2 (creating account) :
```
Are you a seller or customer?
1. Seller
2. Customer
```

```
Enter desired username: 
```

```
Enter desired password: 
```

```
Checking validity... ... ... 
Account has been created!
```

- 1 (login)
```
LOGIN WINDOW:
Enter Username:
```

```
Enter Password:
```

Now logged in

Once you log in :
```
Markey


1.	Metal Headband		Sold by: WantGor		Price: 3.98

2.	JBL Vibe 200TWs		Sold by: JBL		Price: 29.95

3.	USB-C Wall Charger		Sold by: INCORIC		Price: 9.27

4.	Razer DeathAdder Essential Gaming Mouse		Sold by: Razer		Price: 17.85

5.	Guide to investing in your 20s		Sold by: Puffin Books		Price: 12.99

6.	Wool Socks 5 pairs		Sold by: YSense		Price: 10.99

7.	gg		Sold by: Dunsmore		Price: 5.00

8.	Arnav		Sold by: Dunsmore		Price: 0.69

9.	Colten		Sold by: Oh		Price: 0.69


```

- Customer
```
You have 0 items in your cart

Enter '2' to add item number 2 to your cart

Enter '#' to view your cart
Enter '<' to sort listings by cost (low to high)
Enter '>' to sort listings by cost (high to low)
Enter '<>' to de-sort
Enter '@' to view your purchase history
Enter 'csv' to get a csv file with data
Enter 'db' to go to dashboard
Enter '?' to exit

```
- Seller
```
Enter '@' to open a new Store
Enter '++' to list a new item for sale
Enter '--' to remove an item from sale
Enter '*' to edit an item that's on sale
Enter 'csv' to get a csv file with data
Enter 'db' to go to dashboard
Enter '?' to exit

```


## Account
A class that's a blueprint for an account in Markey
- Instance variables
  - private String username : username of user
  - private String password : password of user
- A parameterized constructor
- the instance variables have getters and setters
- public void toString() : returns Account with format "username;password"

## Cart
It's methods update data once a customer checks out their cart
-Instance variables: 
  - private ArrayList<Product> productsToBuy : list of products customer is buying
  - private ArrayList<String> sellerUsernames : list of sellers corresponding to each product in productsToBuy
  - private String customerUsername : username of custoemr who is checking out their cart
  - parameterized constructor
  - public void updateCustomerPurchaseHistory() : updates customer purchase history
  - public void updateSellerStatistics() : updates data for seller dashboard

## CartNotTrackableException
This exception is thrown when there's an error while trying read or write changes to a customer's cart

## CreateAccount implements Shared
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
  
## Customer extends Account
Blueprint of a customer account
- Instance variables :
  - Product[] purchases : array of purchases made by customer
- Parameterized constructor that calls super
- getter and seter for instance variable
- public boolean equals(Object obj) : checks if an object is a particular customer

## CustomerDashboard implements Shared
A customer's dashboard
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
- public void main() throws FileNotFoundException, IOException : prompts user to input to perform actions

## ImportExport
- Instance variables :
  - private String username : username
  - private String password : password
- Parameterized constructor
- public void customerExport() : exports customer's purchase history to a csv file
- public void sellerExport(Seller seller) : exports seller's products that are on sale to a csv file
- public void sellerImport(Seller seller) : imports products to Markey from a csv file by seller

## Listing
- Instance variables:
  - private ArrayList<Product> products : list of products
  - private ArrayList<String> sellers : list of seller usernames corresponding to products
- Parameterized constructor
- Getters and setters for instance variables

## Login implements Shared
To login
- public static void main(String[] args) : prompts user to login or create account, executes MarketPlace when logged in

## MarketPlace implements Shared
Where almost every action by user is carried out and outsourced
- Instance variables :
  - private final String username : username
  - private final String password : password
- Parameterized constructor
- public void main(boolean customer) : prompts user for inputs to perform various actions based on whether the user is a customer or seller
- public void displayProduct(Product product, int index) : formats and displays a product
- public void displaySelectedProduct(Product product) : formats and displays the product page of a product
- public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames) : when user checks out cart, updates data accordingly
- public void viewCart(ArrayList<Product> currentCart, ArrayList<String> currentCartSellers, Scanner scanner) : lets user view items on cart and proceed to checkout or exit
- public Listing sortLowToHigh(ArrayList<Product> list, ArrayList<String> sellers) : sorts products on sale from low to high on price
- public Listing sortHighToLow(ArrayList<Product> list, ArrayList<String> sellers) : sorts products on sale from high to low on price
- public void viewPurchaseHistory() : lets customer view their purchase history
- public void storeCart(ArrayList<Product> currentCart, ArrayList<String> currentSellers) throws CartNotTrackableException : stores cart of a user, so that it remains the same when user logs out and logs back in
- public Listing readCart() throws CartNotTrackableException : reads stored cart
  
## NotInStockException extends Exception :
Thrown when a customer tries to add a quantity of items more than available on stock

## Product
Blueprint of a product that is sold
- Instance variables :
  - private String name : name of product
  - private Store store : store where product is sold
  - private int quantity : quantity available in stock / quantity purchased by customer
  - private double price : price per each
  - private String description : description of product
- Parameterized constructor
- getters and setters for instance variables
- public String toString() : returns in format name_storeName_quantity_price_description

## Seller
Blueprint of a seller account
- Instance variable:
  - ArrayList<Store> stores : list of stores that are open owned by seller (A store that does not have anything on sale will be closed and deleted)
  - ArrayList<Product> products : products on sale by seller
- Parameterized constructor
- getters and setters
- public void createProduct(String name, Store store, int quantity, double price, String description) : creates a new product listing for sale on Markey
- public void createStore(String storeName) : opens a new store on Markey
- public void deleteProduct(int index) : deletes a product
- public boolean equals(Object obj) : checks if an object is a particular seller

## Shared
An interface with Scanner scanner = new Scanner(System.in) for every class that needs to read input to use

## Store
Blueprint of a store owned by a seller on Markey
- Instance variable :
  - private String name
- Parameterized constructor
- getter and setter for instance variable

## StoreAndSales
- Instance variables :
  - private final List<Integer> sales : list of integers
  - private final List<String> stores : list of strings
- Parameterized constructor
- getters 

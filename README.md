# Markey
## CS18000 L24 Group 3: Yudon Shin, Lewis Park, Teju Nalagundla, Sehyeong Oh, Neel Sarathy
## Option 3 Marketplace
Selections: Files, Statistics, Shopping Cart
# Compiling and Running
```
Login.java
```
Is where everything starts. 
- Compile and run Login.java
- You will be prompted to enter whetehr or not you have an account
- If you do not have an account you will be prompted to create one. If you do have an account you will be prompted to login
- While creating account you will have to provide a username consisitng only of letter, a password, and if you're a seller or a buyer. Once you create an account you will move back to login.
- While logging in you will e prompted to enter your username and your password. If they match, you will be logged in.
- Once you log in you will be able to see a list of everything that's for sale on Markey

Based on what you type and enter according to prompts on screen:

- If you are a seller you will be given options to: put up something for sale, remove something from sale, edit something that you had put up for sale, go to your dashboard, import or export items for sale as csv files, and exit.
- If you are a customer you will be given options to view your cart, sort listings, view your purchase history, go to your dashboard, and exit.

- Seller Dashboard and Customer Dashboard include everything mentioned in the handout for these dashboards. Prompts on screen will guide you to do various things like viewing and sorting the data.

## Inputs:
- Customer
- Seller


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

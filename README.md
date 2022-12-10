# Markey
## Neel Sarathy, Yudon Shin, Lewis Park, Tejasvi Nalagundla, Sehyeong Oh
## Yudon Shin - Submitted report on Brightspace
## Neel Sarathy - Submitted Vocareum workspace
## Option 3
Optional feature: Customers can leave reviews associated with specific products from sellers. Other customers can view the reviews after they post. Sellers may view reviews on their products. 
## Instructions to compile and run
Client and Server were developed as 2 separate intellij projects, so some classes may repeat in the folders.
There are 2 folders server and client that contain the classes and files for the client and server sides of the project respectively.
- Compile and run ```Server.java``` in the ```server``` folder to get the server up and running
- Compile and run ```Login.java``` in the ```client``` folder to start the application 
- The GUI components will guide you through using Markey

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

### CartNotTrackableException.java
This exception is thrown when there's an error reading a cart of a customer

### Client.java
Handles connection to the server and transfer of data between the client and server

### CreateAccount.java
The methods of this class are called to create an account for a user

### CreateAccountGUI.java
The GUI for creating an account

### Customer.java
Extends Account,java
The objects of this class represent a customer user

### CustomerAndSales.java
Objects of this class hold a list of customers and a list of sales

### CustomerDashboard.java
Constitutes the customer's dashboard

### CustomerDashboardGUI.java
GUI for the customer dashboard

### Decoder.java
Decodes data received by the client from the server for other classes to access and use

### HintTextField.java
Extends JTextField and shows a hint in the textfield for user

### Listing.java
Objects of this class hold a list of products and their respectie sellers

### Login.java
For user to login to Markey

### LoginGUI.java
GUI for Login

### MarketPlaceGUI.java
Where the sellers and customers perform most of the operations such as buying, listing, etc

### NotInStockException.java
This exception is thrown when a product being added to a customer's cart is not available in stock

### Product.java
Objects of this class represent a product that's on sale on Markey

### ProductAndStores.java
Objects of this class contain a list of products and a list of stores

### PurchaseHistoryView
For customers to view their purchase history

### SellerDashboardGUI.java
Dashboard for a seller

### ServerDetails.java
Interface which has the IP address of the server

### Store.java
Objects of this class represent a store belonging to a seller

### StoreAndSales.java
Objects of this class contain a list of stores and a list of sales

### StoreView
For sellers to view their stores in their dashboard

## server

### Account.java
Objects of this class represent an account of a user.

### Cart.java
It's methods update data when a purchase is made

### CartNotTrackableException.java
This exception is thrown when there's an error reading a cart of a customer

### CreateAccountMethods.java
Has methods that handle data when creating an account.

### CustomerDashboardMethods
It's methods handle data for Customer Dashboard

### Encoder
Encodes data into a single line of String to send to client

### Listing
Objects of this class hold a list of products and their respectie sellers

### marketPlaceMethods
It's methods handle data for the main listing page

### Product
Objects of this class represent a product that's on sale on Markey

### ProductAndStores
Objects of this class contain a list of products and a list of stores

### SellerDashboardMethods
Handles data for Seller Dashboard

### Server
Wait for connections from clients and sends data to clients when requested

### Store
Objects of this class represent a store belonging to a seller

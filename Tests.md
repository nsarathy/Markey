# Tests.md
## Test 1 : Login

### Steps:

1. User launches server and then login application.
2. User clicks "OK" button on welcome popup.
3. User selects "Yes" from drop down menu of options to indicate existing
account and then clicks on the "OK" button.
4. User enters their accurate username and clicks on the "OK" button.
5. User enters their accurate password and clicks on the "OK" button.

### Expected result:
Application verifies the user's username and password along with identifying their
associated account type (seller or customer) and shows
Information message indicating "LOGIN SUCCESSFUL". 

![alt text](https://i.postimg.cc/Gt3fg8bk/Screen-Shot-2022-12-09-at-3-20-38-PM.png)


Upon clicking "OK" acknowledging the information
message application leads them to their associated account type dashboard, i.e., either seller or customer dashboard.

### Test Status:
Passed.

## Test 2 : Create Account

### Steps:

1. User launches the server likewise to that of the steps for the Login Test.
2. User selects "No" on the "Do you have an existing account?" prompt. 
3. User selects the type of account they wish to create from the dropdown menu: "Seller" or "Customer".
4. User enters their desired username that meets all the following conditions: 

* Username field should not be empty
* Username should not contain any special characters or numbers
* Username should not contain any spaces
* Username cannot be pre-existing

5. User enters their desired password twice to avoid typo error that meets all the following conditions: 

* Password field should not be empty 
* The two entered passwords must match

### Expected result: 
Application verifies that both fields pass all condtions and creates their account. 
Information panel notifies that the user will not be sent to the Login process (see Login Test above).

<img width="540" alt="Screenshot 2022-12-09 at 4 04 37 PM" src="https://user-images.githubusercontent.com/112204696/206797110-cc58ff87-63b4-40b6-abe6-e4d0ec9ec393.png">

Upon clicking "OK" acknowledging the information the system will send the user back to the Login Panel.

### Test Status:
Passed.



## Test 3 - Seller open store
1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 2nd button from left in top panel

![alt](https://i.postimg.cc/nh3DJKLw/Screenshot-20221210-030946.png)

4. Input dialog pops up, enter store name

![alt](https://i.postimg.cc/YSjmq4G2/Screenshot-20221210-031100.png)

5. Click "OK"

![alt](https://i.postimg.cc/W3kFgQwQ/Screenshot-20221210-031155.png)

6. Enter product details

![alt](https://i.postimg.cc/PJnL426N/Screenshot-20221210-031311.png)

7. Click "Create listing"

![alt](https://i.postimg.cc/zfMHLJKC/Screenshot-20221210-031425.png)

8. Click 1st button from left in top panel

![alt](https://i.postimg.cc/QdYHPX62/Screenshot-20221210-031500.png)

### Expected Result:
The created product listing appears in main listing page

![alt](https://i.postimg.cc/5N3HB58N/Screenshot-20221210-031558.png)

### Test Status: Passed

## Test 4 - Seller Creates Listing

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/R03NxjyN/Screenshot-20221210-032004.png)

3. Click 3rd button from left in top panel

![alt](https://i.postimg.cc/xTYJNpJ4/Screenshot-20221210-032128.png)

4. Choose store and enter product details in dialog that pops up
5. Click create listing

![alt](https://i.postimg.cc/bY1SJfJn/Screenshot-20221210-032238.png)

6. Click "OK"

![alt](https://i.postimg.cc/hvnjxhjd/Screenshot-20221210-032308.png)

7. Click 1st button from left in top panel

### Expected result:
Created listing appears in main listing page

![alt](https://i.postimg.cc/PxYJP1tH/Screenshot-20221210-032416.png)

### Test Status: Passed

## Test 5

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 4th button from left in top panel

![alt](https://i.postimg.cc/N0WLsdvb/Screenshot-20221210-033431.png)

4. Click delete icon next to product you want to delete
5. Close the delete dialog

![alt](https://i.postimg.cc/VNbLVX0t/Screenshot-20221210-034101.png)

6. Click 1st button from left in top panel

### Expected Result:
Deleted listing disappears from main listing page

![alt](https://i.postimg.cc/C1TLTdbQ/Screenshot-20221210-034153.png)

### Test Status: Passed

## Test 5 - Seller Edits listing

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 5th button from left in top panel

![alt](https://i.postimg.cc/nVKMxKRY/Screenshot-20221210-034342.png)

4. Click edit icon next to listing you want to edit

![alt](https://i.postimg.cc/xT081kBr/Screenshot-20221210-034505.png)

5. Enter/change product details
6. Click "Edit listing"

![alt](https://i.postimg.cc/7Ym6GHy5/Screenshot-20221210-034550.png)

7. Close Edits dialog
8. Click 1st button from left in top panel

### Expected result:
Product is editied in main listing page

![alt](https://i.postimg.cc/T2c2Fd1t/Screenshot-20221210-034808.png)

### Test Status: Passed

## Test 6 - Seller Sees Customers' carts

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 4th button from right in top panel

![alt](https://i.postimg.cc/prk2FZ4w/Screenshot-20221210-035118.png)

4. Click on a customer ('s username)

### Expected result:
You should be able to see the customer's shopping cart

![alt](https://i.postimg.cc/9Q0fy6WJ/Screenshot-20221210-035242.png)

### Test Status: Passed

## Test 7 - Seller download csv

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 2nd button from right in top panel

![alt](https://i.postimg.cc/7ZRZ9KhD/Screenshot-20221210-035544.png)

4. Choose download option
5. Click Confirm

![alt](https://i.postimg.cc/85Rk1DHP/Screenshot-20221210-035625.png)

6. Navigate to desired file location
7. Enter file name
8. Click save

![alt](https://i.postimg.cc/hvKDvwy8/Screenshot-20221210-035817.png)

### Expected Result:
The file is saved

### Test Status: Passed

## Test 7 - Seller csv upload

1. Login as a seller

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![alt](https://i.postimg.cc/1338JgK0/Screenshot-20221210-030834.png)

3. Click 2nd button from right in top panel

![alt](https://i.postimg.cc/Nft117P5/Screenshot-20221210-084746.png)

4. Choose "Create listings from" option
5. Click Confirm

![Screenshot-20221210-084931.png](https://i.postimg.cc/Y0zLXZyj/Screenshot-20221210-084931.png)]

6. Click "OK"

![Screenshot-20221210-085121.png](https://i.postimg.cc/gJP0f4hh/Screenshot-20221210-085121.png)

7. Navigate to file location
8. Select file
9. Click Open

![Screenshot-20221210-085234.png](https://i.postimg.cc/Gpnr2tpw/Screenshot-20221210-085234.png)

10. Click "OK"
11. Click refresh (1st button from left in top pane(main listing page))

### Expected Result:
Listings for added products appear in main listing page
![Screenshot-20221210-085445.png](https://i.postimg.cc/nhvg3kng/Screenshot-20221210-085445.png)

### Test Status: Passed

## Test 8 - Customer

1. Login as a customer

![alt](https://i.postimg.cc/SxSM31mM/Screenshot-20221210-030634.png)

2. Click "OK"

![Screenshot-20221210-091037.png](https://i.postimg.cc/6546RDKM/Screenshot-20221210-091037.png)

3. Enter search key in search text field (textfield in top panel)
4. Click search button (button right to textfield in top panel)
5. Expected result: main listing page sorts listings accordingly; Test Status: 1/9 Test passed

![Screenshot-20221210-091447.png](https://i.postimg.cc/28Wm3hdJ/Screenshot-20221210-091447.png)

6. Click 8th button from right in top panel
7. Expected result: Products are sorted based on price(low to high); Test Status: 2/9 Test passed

![Screenshot-20221210-091649.png](https://i.postimg.cc/zB9zPjSC/Screenshot-20221210-091649.png)

8. Click 7th button from right in top panel
9. Expected result: Products are sorted based on price(high to low); Test Status: 3/9 Test Passed

![Screenshot-20221210-091858.png](https://i.postimg.cc/LsW2nTWc/Screenshot-20221210-091858.png)

10. Click 6th button from right in top panel
11. Expected result: Products are reverted to original order; Test Status: 4/9 Test passed

![Screenshot-20221210-092324.png](https://i.postimg.cc/3Jt68wV6/Screenshot-20221210-092324.png)

12. Click 5th button from right in top panel
13. Expected result: Products are sorted based on availability in stock; Test Status: 5/9 Test Passed

![Screenshot-20221210-092526.png](https://i.postimg.cc/W4gXkpYs/Screenshot-20221210-092526.png)

14. Click a product listed

![Screenshot-20221210-092746.png](https://i.postimg.cc/8Pc4bmXK/Screenshot-20221210-092746.png)

15. Enter quantity in text field in the bottom and click cart icon next to text field

![Screenshot-20221210-092857.png](https://i.postimg.cc/63zVr8rx/Screenshot-20221210-092857.png)

16. Click "OK"
17. Add another item like this to the cart
18. Notice label in right most in top panel shows number of items in cart
19. Click button next to it (1st button from right in top panel)

![Screenshot-20221210-093109.png](https://i.postimg.cc/htbrr4Rs/Screenshot-20221210-093109.png)

20. Click Delete icon next to item you want to delete from cart
21. Expected result: Item disappears from cart; Test Status: 6/9 Test passed
22. Click "Proceed to checkout"

Expected result:

[![Screenshot-20221210-093433.png](https://i.postimg.cc/SxqLJ0YM/Screenshot-20221210-093433.png)

Test Status: Test passed 7/9

23. Click 3rd button from right in top panel
24. Expected result: You should see customer's purchase history; Test Status: 8/9 Test passed

![Screenshot-20221210-094412.png](https://i.postimg.cc/76Vc51m3/Screenshot-20221210-094412.png)

25. Click 2nd button from right

![Screenshot-20221210-094543.png](https://i.postimg.cc/QxWbPk1f/Screenshot-20221210-094543.png)

26. Navigate to desired location
27. Enter file name
28. Click save
29. Expected result: File should be saved with customer purchases; Test Status: 9/9 Test Passed

### Test Status: Test Passed

## Test 9 - Concurrency

Testing only 1 aspect of concurrency in this test, concurrency should work for other things as well
Create a copy of the client side of this project in a different location in your device in order to emulate multiple users accessing the platform simultaneously.
When the server is up and running, run both clients
Log in as a seller in one client and as a customer in the other
The two tabs should be similar to:

![Screenshot-20221210-095407.png](https://i.postimg.cc/gk8ZPMJB/Screenshot-20221210-095407.png)

1. Create a listing from the seller tab, click refresh in the seller tab
2. The new listing should appear for the seller

![Screenshot-20221210-095938.png](https://i.postimg.cc/pdqj6pTW/Screenshot-20221210-095938.png)

3. Click refresh in the customer tab
4. The new listing should now appear for the customer as well

![Screenshot-20221210-100343.png](https://i.postimg.cc/pXJKFGH5/Screenshot-20221210-100343.png)

### Test Status: Test Passed

## Test 10 : Customer Dashboard (View all Stores)

### Steps:

1. Customer User enters Markey's marketplace.

[![marketplace.png](https://i.postimg.cc/VvHr5MYn/marketplace.png)](https://postimg.cc/JHZ42sHz)

2. User clicks Dashboard button.

[![dasb.png](https://i.postimg.cc/YSk9r81T/dasb.png)](https://postimg.cc/HrZH3wvt)

3. User clicks "View all Stores" button.

[![cdash.png](https://i.postimg.cc/C12Sq4qh/cdash.png)](https://postimg.cc/mz7vxCjJ)

4. User enters "View All Stores" button.

[![allstore.png](https://i.postimg.cc/9MDzK0Pf/allstore.png)](https://postimg.cc/ZBhTyTMk)

5. User clicks "High to Low" button.

[![htl.png](https://i.postimg.cc/vHsHL3KT/htl.png)](https://postimg.cc/pysHPYRM)

6. User clicks "Low to High" button.

[![lth.png](https://i.postimg.cc/rFNyh56J/lth.png)](https://postimg.cc/JGtW0DzD)

7. User clicks "Revert List" button.

[![allstore.png](https://i.postimg.cc/9MDzK0Pf/allstore.png)](https://postimg.cc/ZBhTyTMk)

8. User clicks "EXIT" in Store View window.

[![exit.png](https://i.postimg.cc/Pfzkhh6f/exit.png)](https://postimg.cc/hhjY1FWN)

9. User clicks "EXIT" in Customer Dashboard.

[![cdash.png](https://i.postimg.cc/C12Sq4qh/cdash.png)](https://postimg.cc/mz7vxCjJ)

### Expected result:
Customer Dashboard opens when dashboard button is click in customer marketplace.
"View all Stores" button on Customer Dashboard opens the Store List.
Sorting buttons sort according to their functions.

Customer Dashboard returns to marketplace after exiting.

[![marketplace.png](https://i.postimg.cc/VvHr5MYn/marketplace.png)](https://postimg.cc/JHZ42sHz)

### Test Status:
Passed.

## Test 11 : Customer Dashboard (View Purchase History)

### Steps:

1. Customer User enters Markey's marketplace.

[![marketplace.png](https://i.postimg.cc/VvHr5MYn/marketplace.png)](https://postimg.cc/JHZ42sHz)

2. User clicks Dashboard button.

[![dasb.png](https://i.postimg.cc/YSk9r81T/dasb.png)](https://postimg.cc/HrZH3wvt)

3. User clicks "View all Stores" button.

[![cdash.png](https://i.postimg.cc/C12Sq4qh/cdash.png)](https://postimg.cc/mz7vxCjJ)

4. User enters "View Purchase History" button.

[![revert.png](https://i.postimg.cc/05Tydsf6/revert.png)](https://postimg.cc/Yjx7MTTk)

5. User clicks "High to Low" button.

[![htl2.png](https://i.postimg.cc/tTnBhWqV/htl2.png)](https://postimg.cc/bD80PG2z)

6. User clicks "Low to High" button.

[![lth2.png](https://i.postimg.cc/brj9k5VK/lth2.png)](https://postimg.cc/jwZfrMbX)

7. User clicks "Revert List" button.

[![revert.png](https://i.postimg.cc/05Tydsf6/revert.png)](https://postimg.cc/Yjx7MTTk)

8. User clicks "EXIT" in Store View window.

[![exit.png](https://i.postimg.cc/Pfzkhh6f/exit.png)](https://postimg.cc/hhjY1FWN)

9. User clicks "EXIT" in Customer Dashboard.

[![phdb.png](https://i.postimg.cc/c18zYyC5/phdb.png)](https://postimg.cc/mhBVfnQQ)

### Expected result:
Customer Dashboard opens when dashboard button is click in customer marketplace.
"View all Stores" button on Customer Dashboard opens the Store List.
Sorting buttons sort according to their functions.

Customer Dashboard returns to marketplace after exiting.

[![marketplace.png](https://i.postimg.cc/VvHr5MYn/marketplace.png)](https://postimg.cc/JHZ42sHz)

### Test Status:
Passed.

## Test 12 : Seller Dashboard 

### Steps:

1. Seller User enters Markey's marketplace.

<img width="1499" alt="Screen Shot 2022-12-11 at 8 27 40 PM" src="https://user-images.githubusercontent.com/112426445/206950986-f8cfe93f-6433-4c65-98a6-2e228d807e3c.png">

2. User enters 1 to view the customer list.

<img width="598" alt="Screen Shot 2022-12-11 at 9 45 34 PM" src="https://user-images.githubusercontent.com/112426445/206951269-c82fd6af-3d1e-4cc5-91c6-84106c93c31c.png">

3. User clicks "High to Low" button.

<img width="590" alt="Screen Shot 2022-12-11 at 9 47 24 PM" src="https://user-images.githubusercontent.com/112426445/206951332-0620408b-71eb-40f7-972c-86f2f18bb454.png">

4. User clicks "Low to High" button.

<img width="597" alt="Screen Shot 2022-12-11 at 9 47 46 PM" src="https://user-images.githubusercontent.com/112426445/206951349-cb11f967-472a-4a00-a652-1fee835f6a27.png">

5. User clicks "EXIT" buttin to return to Seller Dashboard.

<img width="1499" alt="Screen Shot 2022-12-11 at 8 27 40 PM" src="https://user-images.githubusercontent.com/112426445/206951368-86138860-94d5-453c-b23e-a27254c8e30a.png">

6. User enters 2 to view the product sales list.

<img width="598" alt="Screen Shot 2022-12-11 at 9 45 34 PM" src="https://user-images.githubusercontent.com/112426445/206951375-b6fe7dd7-55db-4915-92e9-7c4d81da7358.png">

7. User clicks "High to Low" button.

<img width="593" alt="Screen Shot 2022-12-11 at 9 48 17 PM" src="https://user-images.githubusercontent.com/112426445/206951400-ba0cce55-5e2e-452d-835c-9937ba639554.png">

8. User clicks "Low to High" button.

<img width="595" alt="Screen Shot 2022-12-11 at 9 48 43 PM" src="https://user-images.githubusercontent.com/112426445/206951411-2caadf68-7e22-4258-b222-acff74fe36a8.png">

9. User clicks "EXIT" buttin to return to Seller Dashboard.

<img width="1507" alt="Screen Shot 2022-12-11 at 9 49 02 PM" src="https://user-images.githubusercontent.com/112426445/206951440-9f3c3227-0e38-4437-86d7-4416f6d3164b.png">

10. User enters invalid option in the Seller Dashboard.

<img width="597" alt="Screen Shot 2022-12-11 at 9 46 14 PM" src="https://user-images.githubusercontent.com/112426445/206951535-eaf725d4-0228-4c1c-8ead-096ccb13e657.png">

=
### Expected result:
Seller Dashboard opens when dashboard button is clicked in seller marketplace.
User enters 1 to view the customer list and 2 to view the product sales list.
Sorting buttons sort according to their functions.

Seller Dashboard returns to marketplace after exiting.

### Test Status:
Passed.

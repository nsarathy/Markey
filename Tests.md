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

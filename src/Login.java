import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Login {
    // TODO: login/create account interface
    // User will have option to create account, if chosen move to CreateAccount.java
    // Pass username while creating object of MarketPlace
    // Go to MarketPlace.main(false) if seller or MarketPlace.main(true) if customer; or [go to sellerDashboard if seller or customerDashboard if customer]
    // TODO: create exception classes
    // username does not exist
    // wrong password
    // TODO: exit close program completely

    public static void main(String[] args) {
        boolean flag = true;
        String accountType = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Marketplace!");
        System.out.println("Do you have an existing account?\n1. Yes\n2. No");
        String check = sc.nextLine();
        if (check.equals("2")) {
            String blank = null;
            CreateAccount newAcc = new CreateAccount(blank, blank);
            newAcc.main();
        }
        while (flag) {
            System.out.println("\nLOGIN WINDOW:");
            System.out.println("Enter Username:");
            String username = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            boolean exists = false;
            try {
                BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"));
                String line = "";
                while ((line =
                        bfr.readLine()) != null) {
                    int indexOf1 = line.indexOf("_");
                    int indexOf2 = line.indexOf(";");
                    if (!line.equals("")) {
                        if (line.substring(indexOf1 + 1, indexOf2).equals(username) &&
                                line.substring(indexOf2 + 1).equals(password)) {
                            exists = true;
                            if (line.contains("seller")) {
                                accountType = "seller";
                            } else if (line.contains("customer")) {
                                accountType = "customer";
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (exists) {
                System.out.println("LOGIN SUCCESSFUL");
                if (accountType.equals("seller")) {
                    System.out.println("user is seller");
                    flag = false;
                } else if (accountType.equals("customer")) {
                    System.out.println("user is customer");

                    flag = false;
                }
            } else {
                System.out.println("Wrong username or password!\nPlease try again.");
            }
        }
    }
}

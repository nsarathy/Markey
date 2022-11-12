import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Login {

    public static void main(String[] args) {
        boolean flag = true;
        String accountType = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Marketplace!");
        System.out.println("Do you have an existing account?\n1. Yes\n2. No");
        String check = sc.nextLine();
        if (check.equals("2")) {
            CreateAccount newAcc = new CreateAccount(null, null, false);
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
                    MarketPlace newMarket = new MarketPlace(username, password);
                    newMarket.main(false);
                    flag = false;
                } else if (accountType.equals("customer")) {
                    MarketPlace newMarket = new MarketPlace(username, password);
                    newMarket.main(true);
                    flag = false;
                }
            } else {
                System.out.println("Wrong username or password!\nPlease try again.");
            }
        }
    }
}
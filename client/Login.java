import javax.swing.*;

public class Login {

    public static void main(String[] args) {
        boolean flag = true;
        String accountType = "";

        LoginGUI.welcomeDialog();

        LoginGUI.existingAccountDialog();

        //  System.out.println("Welcome to the Marketplace!");
        //System.out.println("Do you have an existing account?\n1. Yes\n2. No");
        // String check = scanner.nextLine();
        if (LoginGUI.check.equals("2")) {
            CreateAccount newAcc = new CreateAccount(null, null, false);
            newAcc.main();
        } else if (LoginGUI.check.equals("1")) {
            while (flag) {
                //System.out.println("\nLOGIN WINDOW:");
                // System.out.println("Enter Username:");
                LoginGUI.usernameDialog();
                String username = LoginGUI.username1;
                if (username == null) {
                    return;
                }
                // System.out.println(username);
                // System.out.println("Enter Password:");
                LoginGUI.passwordDialog();
                String password = LoginGUI.password1;
                if (password == null) {
                    return;
                }

                boolean exists = false;
                try {
                    String line = "LN{" + username + ";" + password + "}";
                    String reply = Client.main(line);
                    if (reply.equals("ERROR502")) {
                        exists = false;
                    } else {
                        int firstIndex = reply.indexOf(";");
                        if (firstIndex == -1) {
                            JOptionPane.showMessageDialog(null, "Server offline",
                                "Markey", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        exists = Boolean.parseBoolean(reply.substring(0, firstIndex));
                        accountType = reply.substring(firstIndex + 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (exists) {
                    // System.out.println("LOGIN SUCCESSFUL");
                    LoginGUI.loginSuccessDialog();
                    if (accountType.equals("seller")) {
                        MarketPlaceGUI newMarket = new MarketPlaceGUI();
                        newMarket.main(false, username);
                        flag = false;
                    } else if (accountType.equals("customer")) {
                        MarketPlaceGUI newMarket = new MarketPlaceGUI();
                        newMarket.main(true, username);
                        flag = false;
                    }
                } else {
                    // System.out.println("Wrong username or password!\nPlease try again.");
                    LoginGUI.loginUnSuccessDialog();
                }
            }
        }
    }
}
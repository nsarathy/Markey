import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.*;

public class CreateAccount {
    // creates account and updates Accounts.txt

    private String accountType;
    private boolean accountSignal;
    public CreateAccount(String username, String password) {
        if (username != null && password != null) {
            System.out.println(isAccountSignal());
            Account newAccount = new Account(username, password);
            writeAccount(newAccount);
        }
    }

    public boolean checkingFields(String username, String password) {
        boolean checkUser = checkUsername(username);
        boolean checkUserLength = checkLength(username);
        boolean checkPass = checkPassword(password);
        boolean isLetter = checkIfLetter(username);

        if (checkUser && checkPass && checkUserLength && isLetter) {
            CreateAccount newAccount = new CreateAccount(username, password);
            return true;
        } else if (!checkUserLength || !checkPass) {
            System.out.println("Username or Password cannot be empty!");
            return false;
        } else if (!isLetter) {
            System.out.println("Username may only contain letters!");
            return false;
        } else if (!checkUser) {
            System.out.println("Username already taken!");
            return false;
        } else {
            return false;
        }
    }

    // TODO: check for all possible errors :
    // username taken, empty username/password fields,
    // Make sure whule creating user, username contains only letters ( to avoid regex errors during other parts of the program)
    public boolean checkUsername(String username) {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                if (!line.equals("")) {
                    int indexOf1 = line.indexOf("_");
                    int indexOf2 = line.indexOf(";");
                    usernames.add(line.substring(indexOf1 + 1, indexOf2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (usernames.contains(username)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLength(String username) {
        if (username.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkIfLetter(String username) {
        boolean result = false;
        for (int i = 0; i < username.length(); i++) {
            result = Character.isLetter(username.charAt(i));
            if (!result) {
                break;
            }
        }
        return result;
    }

    public boolean checkPassword(String password) {
        if (password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setAccountSignal(boolean accountSignal) {
        this.accountSignal = accountSignal;
    }

    public boolean isAccountSignal() {
        return accountSignal;
    }

    public void writeAccount(Account newAccount) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter("Accounts.txt", true));
            if (isAccountSignal()) {
                bfw.write("\n" + "customer_" + newAccount);
            } else {
                bfw.write("\n" + "seller_" + newAccount);
            }
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            while (true) {
                System.out.println("Are you a seller or customer?\n1. Seller\n2. Customer");
                int output = sc.nextInt();
                sc.nextLine();
                if (output == 1) {
                    accountType = "seller";
                    setAccountSignal(false);
                    break;
                } else if (output == 2) {
                    accountType = "customer";
                    setAccountSignal(true);
                    break;
                } else {
                    System.out.println("Enter valid input, 1 or 2!");
                }
            }
            System.out.println("Enter desired username: ");
            String username = sc.nextLine();
            System.out.println("Enter desired password: ");
            String password = sc.nextLine();
            System.out.println("Checking validity... ... ... ");
            if (checkingFields(username, password)) {
                System.out.println("Account has been created!");
                break;
            } else {
                System.out.println("Please re-enter required fields!");
            }
        }
    }
}

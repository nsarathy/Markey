import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CreateAccount {
    // creates account and updates Accounts.txt
    public CreateAccount(String username, String password) {
        boolean checkUser = checkUsername(username);
        boolean checkUserLength = checkLength(username);
        boolean checkPass = checkPassword(password);
        boolean isLetter = checkIfLetter(username);
        //run if methods
        if (checkUser && checkPass && checkUserLength && isLetter) {
            Account newAccount = new Account(username, password);
        } else if (!checkUserLength || !checkPass) {
            System.out.println("Username or Password cannot be empty!");
        } else if (!isLetter) {
            System.out.println("Username may only contain letters!");
        } else if (!checkUser) {
            System.out.println("Username already taken!");
        }
    }

    // TODO: check for all possible errors :
    // username taken, empty username/password fields,
    // Make sure whule creating user, username contains only letters ( to avoid regex errors during other parts of the program)
    public boolean checkUsername(String username) {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                int indexOf = line.indexOf(";");
                usernames.add(line.substring(0, indexOf));
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
}

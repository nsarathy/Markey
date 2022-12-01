import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.*;

public class CreateAccount implements Shared{
    // creates account and updates Accounts.txt

    private String createAccountType;
    private boolean accountSignal;
    private String username;
    private String password;
    private String passwordCheck;

    public void main() {
        CreateAccountGUI.AccountType();
        createAccountType = CreateAccountGUI.accType;
        if (createAccountType.equals("1")) {
            setAccountSignal(false);
        } else if (createAccountType.equals("2")) {
            setAccountSignal(true);
        }
        while (true) {
            CreateAccountGUI.enterUsername();
            username = CreateAccountGUI.newUsername;
            CreateAccountGUI.enterPassword();
            password = CreateAccountGUI.newPassword;
            CreateAccountGUI.enterPasswordCheck();
            passwordCheck = CreateAccountGUI.newPasswordCheck;
            if (checkingFields(username, password, passwordCheck, isAccountSignal())) {
                CreateAccountGUI.successInfo();
                break;
            } else {
                CreateAccountGUI.errorRetry();
            }
        }
    }

    public CreateAccount(String username, String password, boolean check) {
        if (username != null && password != null) {
            Account newAccount = new Account(username, password);
            writeAccount(newAccount, check);
        }
    }

    public boolean checkingFields(String username, String password, String password2, boolean check) {
        boolean checkUser = checkUsername(username);
        boolean checkUserLength = checkUsernameLength(username);
        boolean checkPassLength = checkPasswordLength(password);
        boolean isLetter = checkIfLetter(username);
        boolean checkPasswordEquals = checkPasswordSame(password, password2);
        boolean checkUsernameSpaces = checkUserSpaces(username);

        if (checkUser && checkPassLength && checkUserLength && isLetter && checkPasswordEquals && checkUsernameSpaces) {
            CreateAccount newAccount = new CreateAccount(username, password, check);
            return true;
        }  else if (!checkUsernameSpaces) {
            CreateAccountGUI.errorSpaces();
            return false;
        } else if (!checkUserLength || !checkPassLength) {
            CreateAccountGUI.errorEmpty();
            return false;
        } else if (!isLetter) {
            CreateAccountGUI.errorSpecialCharacters();
            return false;
        } else if (!checkUser) {
            CreateAccountGUI.errorTaken();
            return false;
        } else if (!checkPasswordEquals) {
            CreateAccountGUI.errorPassword();
            return false;
        } else {
            return false;
        }
    }

    public boolean checkUserSpaces(String username) {
        if (username.contains(" ")) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkPasswordSame(String pw1, String pw2) {
        if (pw1.equals(pw2)) {
            return true;
        } else {
            return false;
        }
    }
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

    public boolean checkUsernameLength(String username) {
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

    public boolean checkPasswordLength(String password) {
        if (password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void writeAccount(Account newAccount, boolean check) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter("Accounts.txt", true));
            if (check) {
                bfw.write("\n" + "customer_" + newAccount);
            } else {
                bfw.write("\n" + "seller_" + newAccount);
            }
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //for boolean checking sellers or customers -----
    public void setAccountSignal(boolean accountSignal) {
        this.accountSignal = accountSignal;
    }

    public boolean isAccountSignal() {
        return accountSignal;
    }

}

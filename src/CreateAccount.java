import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CreateAccount implements Shared {
    // creates account and updates Accounts.txt

    private String createAccountType;
    private boolean accountSignal;
    private String username;
    private String password;
    private String passwordCheck;

    public CreateAccount(String username, String password, boolean check) {
        if (username != null && password != null) {
            Account newAccount = new Account(username, password);
            CreateAccountMethods.writeAccount(newAccount, check); // todo
        }
    }

    public void main() {
        CreateAccountGUI.AccountType();
        createAccountType = CreateAccountGUI.accType;
        if (createAccountType == null) {
            return;
        }
        if (createAccountType.equals("1")) {
            setAccountSignal(false);
        } else if (createAccountType.equals("2")) {
            setAccountSignal(true);
        }
        while (true) {
            CreateAccountGUI.enterUsername();
            username = CreateAccountGUI.newUsername;
            if (username == null) {
                return;
            }
            CreateAccountGUI.enterPassword();
            password = CreateAccountGUI.newPassword;
            if (password == null) {
                return;
            }
            CreateAccountGUI.enterPasswordCheck();
            passwordCheck = CreateAccountGUI.newPasswordCheck;
            if (passwordCheck == null) {
                return;
            }
            if (checkingFields(username, password, passwordCheck, isAccountSignal())) {
                CreateAccountGUI.successInfo();
                break;
            } else {
                CreateAccountGUI.errorRetry();
            }
        }
        Login.main(new String[0]);
    }

    public boolean checkingFields(String username, String password, String password2, boolean check) {
        boolean checkUser = CreateAccountMethods.checkUsername(username); // todo
        boolean checkUserLength = checkUsernameLength(username);
        boolean checkPassLength = checkPasswordLength(password);
        boolean isLetter = checkIfLetter(username);
        boolean checkPasswordEquals = checkPasswordSame(password, password2);
        boolean checkUsernameSpaces = checkUserSpaces(username);

        if (checkUser && checkPassLength && checkUserLength && isLetter && checkPasswordEquals && checkUsernameSpaces) {
            CreateAccount newAccount = new CreateAccount(username, password, check);
            return true;
        } else if (!checkUsernameSpaces) {
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
        return pw1.equals(pw2);
    }



    public boolean checkUsernameLength(String username) {
        return username.length() != 0;
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



    public boolean isAccountSignal() {
        return accountSignal;
    }

    //for boolean checking sellers or customers -----
    public void setAccountSignal(boolean accountSignal) {
        this.accountSignal = accountSignal;
    }

}
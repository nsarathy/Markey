import javax.swing.*;


public class CreateAccountGUI {

    static String accType = "";
    static String newUsername = "";
    static String newPassword = "";
    static String newPasswordCheck = "";

    public static void AccountType() {
        String accTypeArray[] = {"Seller", "Customer"};

        String option =
            (String) JOptionPane.showInputDialog(null, "Would you like to create a Seller or Customer Account?",
                "Create New Account",
                JOptionPane.PLAIN_MESSAGE, null, accTypeArray, null);
        if (option == null) {
            accType = null;
        } else if (option.equals(accTypeArray[0])) {
            accType = "1";
        } else if (option.equals(accTypeArray[1])) {
            accType = "2";
        }
    }

    public static void enterUsername() {
        newUsername = JOptionPane.showInputDialog(
            null, "Enter new Username:", "Create New Account",
            JOptionPane.QUESTION_MESSAGE);
    }

    public static void enterPassword() {
        newPassword = JOptionPane.showInputDialog(
            null, "Enter new Password:", "Create New Account",
            JOptionPane.QUESTION_MESSAGE);
    }

    public static void enterPasswordCheck() {
        newPasswordCheck = JOptionPane.showInputDialog(
            null, "Re-Enter new Password (to reduce typo error):", "Create New Account",
            JOptionPane.QUESTION_MESSAGE);
    }

    public static void errorSpecialCharacters() {
        JOptionPane.showMessageDialog(null, "Username field should not include any special characters",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorSpaces() {
        JOptionPane.showMessageDialog(null, "Username field should not include any spaces",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorTaken() {
        JOptionPane.showMessageDialog(null, "Username is already taken",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorPassword() {
        JOptionPane.showMessageDialog(null, "Passwords do not match",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorEmpty() {
        JOptionPane.showMessageDialog(null, "Fields cannot be left empty",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void errorRetry() {
        JOptionPane.showMessageDialog(null, "Please re-enter required fields",
            "Create New Account", JOptionPane.ERROR_MESSAGE);
    }

    public static void successInfo() {
        JOptionPane.showMessageDialog(null, "ACCOUNT CREATED SUCCESSFULLY - Returning to Marketplace",
            "Create New Account", JOptionPane.INFORMATION_MESSAGE);
    }
}
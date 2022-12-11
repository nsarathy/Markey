import javax.swing.*;

/**
 * LoginGUI
 * <p>
 * GUI for login
 *
 * @author tnallagu
 * @version 12/11/2022
 */

public class LoginGUI {


    //public static Object displayDialog;


    static String check = "";
    static String username1 = "";
    static String password1 = "";

    public static void welcomeDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to Markey!",
            "Markey", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/markey.png"));
    }

    public static void existingAccountDialog() {
        String[] options = {"  Yes  ", "  No  "};

        String option = (String) JOptionPane.showInputDialog(null,
            "Do you have an existing account?", "Markey",
            JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (option != null) {
            if (option.equals(options[0])) {
                check = "1";
            } else if (option.equals(options[1])) {
                check = "2";

            }
        }


    }

    // System.out.println("Enter port number");
    // int portNumber = scan.nextInt();

    public static void usernameDialog() {

        username1 = JOptionPane.showInputDialog(null,
            "Enter Username:", " LOGIN WINDOW:",
            JOptionPane.QUESTION_MESSAGE);

    }

    public static void passwordDialog() {

        password1 = JOptionPane.showInputDialog(
            null, "Enter Password:", " LOGIN WINDOW:",
            JOptionPane.QUESTION_MESSAGE);
    }

    public static void loginSuccessDialog() {
        JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL",
            "Markey", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void loginUnSuccessDialog() {
        JOptionPane.showMessageDialog(null,
            "Wrong username or password!\nPlease try again.",
            "Markey", JOptionPane.ERROR_MESSAGE);
    }


}

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class LoginGUI {


    //public static Object displayDialog;


    public static void welcomeDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to the Marketplace!",
                "Marketplace", JOptionPane.INFORMATION_MESSAGE);
    }

    static String check = "";

    public static void existingAccountDialog() {
        String[] options = {"1. Yes", "2. No"};

        String option = (String) JOptionPane.showInputDialog(null, "Do you have an existing account?", "Marketplace",
                JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (option.equals(options[0])) {
            check = "1";
        } else if (option.equals(options[1])) {
            check = "2";

        }


    }

    static String username1 = "";

    public static void usernameDialog() {

        username1 = JOptionPane.showInputDialog(null,
                "Enter Username:", " LOGIN WINDOW:",
                JOptionPane.QUESTION_MESSAGE);

    }

    // System.out.println("Enter port number");
    // int portNumber = scan.nextInt();

    static String password1 = "";

    public static void passwordDialog() {

        password1 = JOptionPane.showInputDialog(
                null, "Enter Password:", " LOGIN WINDOW:",
                JOptionPane.QUESTION_MESSAGE);
    }

    public static void loginSuccessDialog() {
        JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL",
                "Marketplace", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void loginUnSuccessDialog() {
        JOptionPane.showMessageDialog(null, "Wrong username or password!\nPlease try again.",
                "Marketplace", JOptionPane.INFORMATION_MESSAGE);
    }


}

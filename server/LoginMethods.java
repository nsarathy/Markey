import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * LoginMethods
 * <p>
 * File handling for Login
 *
 * @author nsarathy
 * @version 12/11/2022
 */
public class LoginMethods {
    public static String reader(String username, String password) throws IOException {
        boolean exists = false;
        String accountType = "";
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
                    if (line.substring(0, indexOf1).equals("seller")) {
                        accountType = "seller";
                    } else if (line.substring(0, indexOf1).equals("customer")) {
                        accountType = "customer";
                    }
                }
            }

        }
        bfr.close();
        return exists + ";" + accountType;
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CreateAccountMethods {
    public static boolean checkUsername(String username) {
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

        return !usernames.contains(username);
    }

    public static void writeAccount(Account newAccount, boolean check) {
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
}

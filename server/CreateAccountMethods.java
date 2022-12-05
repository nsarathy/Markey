import java.io.*;
import java.util.ArrayList;

public class CreateAccountMethods {
    public static boolean checkUsername(String username) throws IOException {
        ArrayList<String> usernames = new ArrayList<>();

        BufferedReader bfr = new BufferedReader(new FileReader("Accounts.txt"));
        String line = "";
        while ((line = bfr.readLine()) != null) {
            if (!line.equals("")) {
                int indexOf1 = line.indexOf("_");
                int indexOf2 = line.indexOf(";");
                usernames.add(line.substring(indexOf1 + 1, indexOf2));
            }
        }


        return !usernames.contains(username);
    }

    public static void writeAccount(Account newAccount, boolean check) throws IOException {

        BufferedWriter bfw = new BufferedWriter(new FileWriter("Accounts.txt", true));
        if (check) {
            bfw.write("\n" + "customer_" + newAccount.toString());
        } else {
            bfw.write("\n" + "seller_" + newAccount.toString());
        }
        bfw.close();
    }
}

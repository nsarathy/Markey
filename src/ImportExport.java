import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ImportExport {
    private String username;
    private String password;

    public ImportExport(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void customerExport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file path for the csv file");
        String filePath = scanner.nextLine();
        boolean exists = false;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            FileReader frHistory = new FileReader("CustomerPurchaseHistory.txt");
            BufferedReader brHistory = new BufferedReader(frHistory);
            ArrayList<String> lines = new ArrayList<>();
            String line = brHistory.readLine();
            while (line != null) {
                lines.add(line);
                line = brHistory.readLine();
            }
            brHistory.close();
            frHistory.close();
            for (String value : lines) {
                String[] checkUser = value.split(";", -1);
                if (checkUser[0].equals(username)) {
                    exists = true;
                    String[] products = checkUser[1].split("___", -1);
                    for (String val : products) {
                        String[] product = val.split("_", -1);
                        productList.add(new Product(product[0],
                                new Store(product[1]),
                                Integer.parseInt(product[2]),
                                Double.parseDouble(product[3]),
                                product[4].split("\\$", -1)[0]
                        ));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        if (!exists) {
            System.out.println("You have not made any purchases yet");
        } else {
            try {
                FileWriter fw = new FileWriter(filePath);
                CSVWriter cw = new CSVWriter(fw);
            } catch (Exception e) {
                System.out.println("Something went wrong :(");
            }
        }
    }
}

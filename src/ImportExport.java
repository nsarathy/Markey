import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
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
                    try (PrintWriter writer = new PrintWriter(filePath)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Item");
                        sb.append(',');
                        sb.append("Store");
                        sb.append(',');
                        sb.append("Quantity");
                        sb.append(',');
                        sb.append("Price");
                        sb.append(',');
                        sb.append("Description");
                        sb.append(',');
                        sb.append('\n');
                        for (String val : products) {
                            String[] product = val.split("_", -1);
                            product[4] = product[4].split("\\$", -1)[0];
                            for (String eachValue : product) {
                                sb.append(eachValue);
                                sb.append(',');
                            }
                            sb.append('\n');
                        }
                        writer.write(sb.toString());
                    } catch (Exception e) {
                        System.out.println("Something went wrong :(");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
        if (!exists) {
            System.out.println("You have not made any purchases yet");
        } else {
            System.out.println("File saved");
        }
    }

    public void sellerExport(Seller seller) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file path for csv file");
        String filePath = scanner.nextLine();
        ArrayList<Product> products = seller.getProducts();
        try (PrintWriter writer = new PrintWriter(filePath)) {
            StringBuilder sb = new StringBuilder();
            for (Product product : products) {
                sb.append(product.getStore().getName());
                sb.append(',');
                sb.append(product.getName());
                sb.append(',');
                sb.append(product.getPrice());
                sb.append(',');
                sb.append(product.getQuantity());
                sb.append(',');
                sb.append(product.getDescription());
                sb.append('\n');
            }
            writer.write(sb.toString());
            System.out.println("File saved");
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
    }

    public void sellerImport(Seller seller) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Make sure the csv file is in this format:");
        System.out.println("storeName,itemName,price,quantity,description");
        System.out.println("Make sure that no element has commas ','");
        System.out.println("Enter file path to cs file");
        String filePath = scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                String[] prod = line.split(",");
                seller.createProduct(prod[1],
                        new Store(prod[0]),
                        Integer.parseInt(prod[3]),
                        Double.parseDouble(prod[2]),
                        prod[4]
                );
                line = br.readLine();
            }
            System.out.println("Import successful");
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
        }
    }
}

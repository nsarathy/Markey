import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SellerDashboardGUI extends JComponent implements Runnable, Shared {

    JTextArea sellerMenu;
    JTextField listSelection;
    JButton listSelectionButton;
    JTextArea optionDescription;
    JButton highToLow;
    JButton lowToHigh;
    JButton exit;
    private String userName;
    public SellerDashboardGUI(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void run() {
        boolean repeat = true;
        int sortID = 0;

        String sellerboardMenu = "1. View Customer List\n2. View Product Sales List\n3. Exit Customer Dashboard";

        CustomerAndSales cs = sortCustomerListLowHigh();
        List<String> customers = cs.getCustomer();
        List<Integer> sales = cs.getSales();

        JFrame frame1 = new JFrame("Seller Dashboard");
        Container frame1Content = frame1.getContentPane();
        frame1Content.setLayout(new BorderLayout());

        JFrame frame2 = new JFrame("Seller Dashboard");
        Container frame2Content = frame2.getContentPane();
        frame2Content.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        sellerMenu = new JTextArea(sellerboardMenu);
        sellerMenu.setEditable(false);
        menuPanel.add(sellerMenu);
        frame1Content.add(menuPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();

        listSelection = new JTextField("", 1);
        listSelectionButton = new JButton("Enter");
        buttonPanel.add(listSelection);
        buttonPanel.add(listSelectionButton);
        listSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selection = Integer.parseInt(listSelection.getText());
                    if (selection != 1 && selection != 2 && selection != 3) {
                        JOptionPane.showMessageDialog(null,
                            "Please enter valid choice!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    } else if (selection == 3) {
                        frame1.setVisible(false);
                    } else {

                        JPanel descriptionPanel = new JPanel();
                        optionDescription = new JTextArea("");

                        JPanel sortPanel = new JPanel();
                        highToLow = new JButton("High to Low");
                        lowToHigh = new JButton("Low to High");
                        exit = new JButton("Exit");
                        if (selection == 1) {
                            String customerList = "********************\nCustomer List\n--------------------\n";
                            customerList = customerList + displayOriginalCustomerList();
                            optionDescription.setText(customerList);

                            highToLow.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    optionDescription.setText(displayCustomerListHighLow());
                                }
                            });
                            lowToHigh.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    optionDescription.setText(displayCustomerListLowHigh());
                                }
                            });
                        } else if (selection == 2) {
                            List<String> products = onlyProducts();
                            ArrayList<Product> products1 = new ArrayList<>();
                            for (String value : products) {
                                String[] products2 = value.split("___");
                                for (String each : products2) {
                                    String[] productDetails = each.split("_");
                                    products1.add(new Product(
                                        productDetails[0],
                                        new Store(productDetails[1]),
                                        Integer.parseInt(productDetails[2]),
                                        Double.parseDouble(productDetails[3]),
                                        productDetails[4]
                                    ));
                                }
                            }
                            for (int i = 0; i < products1.size(); i++) {
                                Product product0 = products1.get(i);
                                if (product0 != null) {
                                    for (int j = i + 1; j < products1.size(); j++) {
                                        Product product01 = products1.get(j);
                                        if (product01 != null && product01.getName().equals(product0.getName()) &&
                                            product01.getStore().getName().equals(product0.getStore().getName())) {
                                            int q = product01.getQuantity() + product0.getQuantity();
                                            product0.setQuantity(q);
                                            products1.set(i, product0);
                                            products1.set(j, null);
                                        }
                                    }
                                }
                            }
                            for (int i = 0; i < products1.size(); i++) {
                                if (products1.get(i) == null) {
                                    products1.remove(i);
                                    i--;
                                }
                            }
                            String productList = "********************\nProduct List\n--------------------\n";
                            productList = productList + displayProducts(products1);
                            optionDescription.setText(productList);

                            highToLow.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    optionDescription.setText(displayProducts(sortProductsHighLow(products1)));
                                }
                            });
                            lowToHigh.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    optionDescription.setText(displayProducts(sortProductsLowHigh(products1)));
                                }
                            });
                        }
                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame2.setVisible(false);
                            }
                        });
                        descriptionPanel.add(optionDescription);
                        sortPanel.add(highToLow);
                        sortPanel.add(lowToHigh);
                        sortPanel.add(exit);
                        frame2Content.add(descriptionPanel, BorderLayout.CENTER);
                        frame2Content.add(sortPanel, BorderLayout.SOUTH);

                        frame2.setSize(600, 400);
                        frame2.setLocationRelativeTo(null);
                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        frame1.setVisible(false);
                        frame2.setVisible(true);
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                        "Please enter 1, 2, or 3!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame1Content.add(buttonPanel, BorderLayout.SOUTH);

        frame1.setSize(600, 400);
        frame1.setLocationRelativeTo(null);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(true);
    }

    public void main() {
        SwingUtilities.invokeLater(new SellerDashboardGUI(userName));
    }

    // todo server
    public List<String> readSellerStats() {
        List<String> fullList = new ArrayList<>();

        try {
            File f = new File("SellerStatistics.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            if (f == null || !(f.exists())) {
                throw new FileNotFoundException();
            }

            String line = bfr.readLine();

            while (line != null) {
                fullList.add(line);
                line = bfr.readLine();
            }

            return fullList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> matchedList() {
        String sellerName = userName;
        List<String> fullList = readSellerStats();
        List<String> scannedList = new ArrayList<>();

        for (int i = 0; i < fullList.size(); i++) {
            if (fullList.get(i).contains(sellerName)) {
                scannedList.add(fullList.get(i));
            }
        }
        return scannedList;
    }

    public List<String> onlyProducts() {
        List<String> scannedList = matchedList();
        List<String> productList = new ArrayList<>();
        String finalQuantity = "";

        for (int i = 0; i < scannedList.size(); i++) {
            List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
            productList.add(collectedData.get(1));
        }
        return productList;
    }

    public List<String> customerAndSales() {
        List<String> scannedList = matchedList();
        List<String> customer = new ArrayList<>();

        for (int i = 0; i < scannedList.size(); i++) {
            List<String> collectedData = new ArrayList<>(Arrays.asList(scannedList.get(i).split(";")));
            List<String> productData = new ArrayList<>(Arrays.asList(scannedList.get(i).split("___")));
            List<String> quantityData = new ArrayList<>(Arrays.asList(scannedList.get(i).split("_")));
            int update = 0;
            int finalConvert = 0;
            String send = "";

            customer.add((collectedData.get(2)));

            for (int q = 0; q < productData.size(); q++) {
                String strNum = quantityData.get(2 + update);
                int convert = Integer.parseInt(strNum);
                finalConvert += convert;
                send = Integer.toString(finalConvert);
                update += 7;
            }

            customer.add(send);
        }
        return customer;
    }

    public List<String> formatCustomerAndSales() {
        List<String> customerAndSales = customerAndSales();
        List<String> newFormat = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < customerAndSales.size(); i++) {
            count = i;
            if ((count ^ 1) == i + 1) {
                newFormat.add(customerAndSales.get(i) + "_" + customerAndSales.get(i + 1));
            }
        }

        return newFormat;
    }

    public List<String> formatSales() {
        List<String> customerAndSales = customerAndSales();
        List<String> newFormat = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < customerAndSales.size(); i++) {
            count = i;
            if (!((count ^ 1) == i + 1)) {
                newFormat.add(customerAndSales.get(i));
            }
        }

        return newFormat;
    }

    public List<String> formatCustomers() {
        List<String> customerAndSales = customerAndSales();
        List<String> newFormat = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < customerAndSales.size(); i++) {
            count = i;
            if ((count ^ 1) == i + 1) {
                newFormat.add(customerAndSales.get(i));
            }
        }

        return newFormat;
    }

    public List<Integer> formatListLength() {
        List<String> customerAndSales = formatCustomerAndSales();
        List<Integer> newFormat = new ArrayList<>();
        for (int i = 0; i < customerAndSales.size(); i++) {
            newFormat.add(1);
        }
        return newFormat;
    }

    public CustomerAndSales sortCustomerListHighLow() {
        List<String> originalSaleList = formatSales();
        List<String> originalCustomerList = formatCustomers();
        List<Integer> saleListToInt = new ArrayList<>();


        for (String s : originalSaleList) {
            saleListToInt.add(Integer.parseInt(s));
        }

        List<String> customerList = new ArrayList<>();
        List<Integer> salesList = new ArrayList<>();
        customerList.addAll(originalCustomerList);
        salesList.addAll(saleListToInt);

        for (int i = 0; i < salesList.size(); i++) {
            for (int j = 0; j < salesList.size() - i - 1; j++) {
                if (salesList.get(j) < salesList.get(j + 1)) {
                    int temp = salesList.get(j);
                    salesList.set(j, salesList.get(j + 1));
                    salesList.set(j + 1, temp);
                    String tempString = customerList.get(j);
                    customerList.set(j, customerList.get(j + 1));
                    customerList.set(j + 1, tempString);
                }
            }
        }

        return new CustomerAndSales(customerList, salesList);
    }

    public CustomerAndSales sortCustomerListLowHigh() {
        List<String> originalSaleList = formatSales();
        List<Integer> saleListToInt = new ArrayList<>();


        for (String s : originalSaleList) {
            saleListToInt.add(Integer.parseInt(s));
        }

        List<String> customerList = new ArrayList<>();
        List<Integer> salesList = new ArrayList<>();
        customerList.addAll(formatCustomers());
        salesList.addAll(saleListToInt);

        for (int i = 0; i < salesList.size(); i++) {
            for (int j = 0; j < salesList.size() - i - 1; j++) {
                if (salesList.get(j) > salesList.get(j + 1)) {
                    int temp = salesList.get(j);
                    salesList.set(j, salesList.get(j + 1));
                    salesList.set(j + 1, temp);
                    String tempString = customerList.get(j);
                    customerList.set(j, customerList.get(j + 1));
                    customerList.set(j + 1, tempString);
                }
            }
        }

        return new CustomerAndSales(customerList, salesList);
    }

    public ArrayList<Product> sortProductsHighLow(ArrayList<Product> products1) {
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(products1);
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < products.size() - i - 1; j++) {
                if (products.get(j).getQuantity() < products.get(j + 1).getQuantity()) {
                    Product temp = products.get(j);
                    products.set(j, products.get(j + 1));
                    products.set(j + 1, temp);
                }
            }
        }
        ;
        return products;
    }

    public ArrayList<Product> sortProductsLowHigh(ArrayList<Product> products1) {
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(products1);
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < products.size() - i - 1; j++) {
                if (products.get(j).getQuantity() > products.get(j + 1).getQuantity()) {
                    Product temp = products.get(j);
                    products.set(j, products.get(j + 1));
                    products.set(j + 1, temp);
                }
            }
        }
        ;
        return products;
    }

    // Display: GUI needed
    public String displayOriginalCustomerList() {
        List<String> customerAndSales = customerAndSales();
        int count = 0;
        String originalCustomerList = "";
        //this is for viewing the stores
        for (int i = 0; i < customerAndSales.size(); i++) {
            count = i;
            if ((count ^ 1) == i + 1) {
                originalCustomerList = originalCustomerList + "Customer: " + customerAndSales.get(i) + "\n";
            } else {
                originalCustomerList =
                    originalCustomerList + "Items Purchased: " + customerAndSales.get(i) + "\n--------------------\n";
            }
        }
        return originalCustomerList;
    }

    public String displayCustomerListHighLow() {

        CustomerAndSales alteredStores = sortCustomerListHighLow();
        List<String> justStores = new ArrayList<>();
        List<String> highLowStores = alteredStores.getCustomer();
        List<Integer> highLowSales = alteredStores.getSales();

        for (int j = 0; j < highLowStores.size(); j++) {
            List<String> dataCollected = new ArrayList<>(Arrays.asList(highLowStores.get(j).split("_")));
            justStores.add(dataCollected.get(0));
        }

        String customerListHighLow = "";

        for (int i = 0; i < highLowStores.size(); i++) {
            customerListHighLow =
                customerListHighLow + "--------------------\n" + "Customer: " + justStores.get(i) + "\n"
                    + justStores.get(i) + ": \n" + highLowSales.get(i) + " Sales" + "\n";

        }
        customerListHighLow = customerListHighLow + "--------------------\n";

        return customerListHighLow;
    }

    public String displayCustomerListLowHigh() {

        CustomerAndSales alteredStores = sortCustomerListLowHigh();
        List<String> justStores = new ArrayList<>();
        List<String> lowHighStores = alteredStores.getCustomer();
        List<Integer> lowHighSales = alteredStores.getSales();

        for (String lowHighStore : lowHighStores) {
            List<String> dataCollected = new ArrayList<>(Arrays.asList(lowHighStore.split("_")));
            justStores.add(dataCollected.get(0));
        }

        String customerListLowHigh = "";

        for (int i = 0; i < lowHighStores.size(); i++) {
            int count = 0;
            customerListLowHigh =
                customerListLowHigh + "--------------------\n" + "Customer: " + justStores.get(i) + "\n"
                    + justStores.get(i) + ": \n" + lowHighSales.get(i) + " Sales\n";
        }
        customerListLowHigh = customerListLowHigh + "--------------------\n";

        return customerListLowHigh;
    }

    public String displaySortOptions(int givenSortID) {

        int sortID = givenSortID;
        String sortOptions = "Would you like to sort this List?";

        if (sortID == 0) {
            sortOptions = sortOptions + "\n1: High to Low\n2: Low to High\n3: Exit";
        }

        if (sortID == 1) {
            sortOptions = sortOptions + "\n1: Low to High\n2: Revert List\n3: Exit";
        }

        if (sortID == 2) {
            sortOptions = sortOptions + "\n1: High to Low\n2: Revert List\n3: Exit";
        }

        return sortOptions;
    }

    public String displayProducts(ArrayList<Product> products1) {

        String products = "";
        for (Product product : products1) {
            products = products + "Product: " + product.getName() + "\nStore: " + product.getStore().getName()
                + "\nSales: " + product.getQuantity() + "\n--------------------\n";
        }
        return products;
    }
}
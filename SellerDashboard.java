import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;


public class SellerDashboard implements Shared {


    //read the sellerStats
    //display for the specific seller user, all the customer usernames that have bought from them,
    //number of items each customer has bought from the seller, list of products the customer has bought,
    //number of sales for each product the seller has sold.

    //method used to display the interface the seller user can interact with and call the other method

    private String userName;

    public SellerDashboard(String userName) {
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //scan the array from method above and scan to see if seller username
    //matches and then make a new array which will only have the values if
    //seller name matches.

    //read the sellerstats file and return String list
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

    //format without the seller name though because we don't need it anymore once
    //it's in the system.
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

    //this method will split the given string into the products and the customer
    //we then need to make a display showing the customer and their purchase
    public void displayOriginalCustomerList() {
        List<String> customerAndSales = customerAndSales();
        int count = 0;
        //this is for view the stores
        for (int i = 0; i < customerAndSales.size(); i++) {
            count = i;
            if ((count ^ 1) == i + 1) {
                System.out.println("Customer: " + customerAndSales.get(i));
            } else {
                System.out.println("Items Purchased: " + customerAndSales.get(i));
                System.out.println("--------------------");
            }
        }
        System.out.println();
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

    public void displayCustomerListHighLow() {

        CustomerAndSales alteredStores = sortCustomerListHighLow();
        List<String> justStores = new ArrayList<>();
        List<String> highLowStores = alteredStores.getCustomer();
        List<Integer> highLowSales = alteredStores.getSales();

        for (int j = 0; j < highLowStores.size(); j++) {
            List<String> dataCollected = new ArrayList<>(Arrays.asList(highLowStores.get(j).split("_")));
            justStores.add(dataCollected.get(0));
        }

        for (int i = 0; i < highLowStores.size(); i++) {
            System.out.println("--------------------");
            System.out.println("Customer: " + justStores.get(i));


            System.out.print(justStores.get(i) + ": ");
            System.out.println(highLowSales.get(i) + " Sales");


        }

        System.out.println("--------------------");
        System.out.println();
    }

    public void displayCustomerListLowHigh() {

        CustomerAndSales alteredStores = sortCustomerListLowHigh();
        List<String> justStores = new ArrayList<>();
        List<String> lowHighStores = alteredStores.getCustomer();
        List<Integer> lowHighSales = alteredStores.getSales();

        for (String lowHighStore : lowHighStores) {
            List<String> dataCollected = new ArrayList<>(Arrays.asList(lowHighStore.split("_")));
            justStores.add(dataCollected.get(0));
        }

        for (int i = 0; i < lowHighStores.size(); i++) {
            int count = 0;
            System.out.println("--------------------");
            System.out.println("Customer: " + justStores.get(i));


            System.out.print(justStores.get(i) + ": ");
            System.out.println(lowHighSales.get(i) + " Sales");
        }

        System.out.println("--------------------");
        System.out.println();
    }

    public void displaySortOptions(int givenSortID) {
        int sortID = givenSortID;

        System.out.println("Would you like to sort this List?");
        if (sortID == 0) {
            System.out.println("1: High to Low");
            System.out.println("2: Low to High");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }

        if (sortID == 1) {
            System.out.println("1: Low to High");
            System.out.println("2: Revert List");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }

        if (sortID == 2) {
            System.out.println("1: High to Low");
            System.out.println("2: Revert List");
            System.out.println("3: Exit");
            System.out.println();
            System.out.print("Enter Here: ");
        }
    }

    public void main() {
        boolean repeat = true;
        int sortID = 0;

        CustomerAndSales cs = sortCustomerListLowHigh();
        List<String> customers = cs.getCustomer();
        List<Integer> sales = cs.getSales();


        while (repeat) {
            int answer1 = 0;
            do {
                try {
                    sortID = 0;
                    System.out.println("*SELLER DASHBOARD*");
                    System.out.println();
                    System.out.println("1. View Customer List");
                    System.out.println("2. View Product Sales List");
                    System.out.println("3. Exit Customer Dashboard");
                    System.out.println();
                    System.out.print("Enter Option Here: ");
                    answer1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println();

                    if (answer1 == 1) {
                        break;
                    } else if (answer1 == 2) {
                        break;
                    } else if (answer1 == 3) {
                        repeat = false;
                        break;
                    } else {
                        System.out.println("Please enter valid choice!");
                        System.out.println();
                    }
                } catch (InputMismatchException e) {
                    System.out.println();

                    System.out.println("Please enter valid choice!");
                    System.out.println();
                    scanner.nextLine();
                }
            } while (answer1 != 1 || answer1 != 2 || answer1 != 3);

            if (answer1 == 1) {
                int wantedSort = 0;
                System.out.println("********************");
                System.out.println("Customer List");
                System.out.println("--------------------");
                while (true) {
                    if (sortID == 0) {
                        displayOriginalCustomerList();
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    } else if (sortID == 1) {
                        displayCustomerListHighLow();
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    } else if (sortID == 2) {
                        displayCustomerListLowHigh();
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    }

                    if (sortID == 0) {
                        if (wantedSort == 1) {
                            sortID = 1;

                        } else if (wantedSort == 2) {
                            sortID = 2;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    } else if (sortID == 1) {
                        if (wantedSort == 1) {
                            sortID = 2;
                        } else if (wantedSort == 2) {
                            sortID = 0;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    } else if (sortID == 2) {
                        if (wantedSort == 1) {
                            sortID = 1;
                        } else if (wantedSort == 2) {
                            sortID = 0;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    }
                }

            } else if (answer1 == 2) {
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
                for (int i = 0; i < products1.size(); i++) {
                    if (products1.get(i) == null) {
                        products1.remove(i);
                        i--;
                    }
                }
                while (true) {
                    int wantedSort = 0;
                    System.out.println("********************");
                    System.out.println("Product List");
                    System.out.println("--------------------");
                    if (sortID == 0) {
                        displayProducts(products1);
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    } else if (sortID == 1) {
                        displayProducts(sortProductsHighLow(products1));
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    } else if (sortID == 2) {
                        displayProducts(sortProductsLowHigh(products1));
                        displaySortOptions(sortID);
                        wantedSort = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println();
                    }

                    if (sortID == 0) {
                        if (wantedSort == 1) {
                            sortID = 1;

                        } else if (wantedSort == 2) {
                            sortID = 2;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    } else if (sortID == 1) {
                        if (wantedSort == 1) {
                            sortID = 2;
                        } else if (wantedSort == 2) {
                            sortID = 0;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    } else if (sortID == 2) {
                        if (wantedSort == 1) {
                            sortID = 1;
                        } else if (wantedSort == 2) {
                            sortID = 0;

                        } else if (wantedSort == 3) {
                            System.out.println();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void displayProducts(ArrayList<Product> products1) {

        for (Product product : products1) {
            System.out.println("Product: " + product.getName());
            System.out.println("Store: " + product.getStore().getName());
            System.out.println("Sales: " + product.getQuantity());
            System.out.println("--------------------");
        }
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

}
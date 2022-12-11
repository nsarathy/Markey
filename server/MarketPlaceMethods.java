import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * MarketPlaceMethods
 * <p>
 * All methods or actions performed by marketplace are not here
 * Especially not stuff like sorting and searching
 * Only those that read and write files as they adhere to the client-server paradox
 *
 * @author nsarathy
 * @version 12/11/2022
 */
public class MarketPlaceMethods {
    private final String username;

    public MarketPlaceMethods(String username) {
        this.username = username;
    }

    public Listing readProductsTxt() throws IOException {

        // Reading products in listings
        FileReader frProducts = new FileReader("Products.txt");
        BufferedReader brProducts = new BufferedReader(frProducts);
        ArrayList<Product> listings = new ArrayList<>();
        ArrayList<String> sellerUsernames = new ArrayList<>();
        String line = brProducts.readLine();
        while (line != null) {
            String[] sellerAndProduct = line.split(";", 2);
            String[] productDetails = sellerAndProduct[1].split("_", -1);
            sellerUsernames.add(sellerAndProduct[0]);
            Store store = new Store(productDetails[1]);
            int quantity = Integer.parseInt(productDetails[2]);
            double price = Double.parseDouble(productDetails[3]);
            listings.add(new Product(productDetails[0], store, quantity, price, productDetails[4]));
            line = brProducts.readLine();
        }
        return new Listing(listings, sellerUsernames);

    }

    public Listing readCart() throws CartNotTrackableException {
        try {
            ArrayList<Product> cartProducts = new ArrayList<>();
            ArrayList<String> cartSellers = new ArrayList<>();
            FileReader fr = new FileReader("carts.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean flag = true;
            String line = br.readLine();
            while (line != null) {
                String[] toGetCustomer = line.split(";", -1);
                if (toGetCustomer[0].equals(username)) {
                    flag = false;
                    String[] products = toGetCustomer[1].split("___", -1);
                    String[] sellers = toGetCustomer[2].split("___", -1);
                    if (!products[0].isEmpty()) {
                        for (String value : products) {
                            String[] productDetails = value.split("_", -1);
                            Store store = new Store(productDetails[1]);
                            int quantity = Integer.parseInt(productDetails[2]);
                            double price = Double.parseDouble(productDetails[3]);
                            cartProducts.add(new Product(productDetails[0], store, quantity, price, productDetails[4]));
                        }
                    }
                    if (!sellers[0].isEmpty()) {
                        cartSellers = new ArrayList<>(Arrays.asList(sellers));
                    }
                    break;
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
            if (flag) {
                throw new CartNotTrackableException("Your cart is empty");
            }
            return new Listing(cartProducts, cartSellers);
        } catch (IOException | NumberFormatException e) {
            throw new CartNotTrackableException("Unable to find cart");
        }
    }

    public void storeCart(ArrayList<Product> currentCart, ArrayList<String> currentSellers)
        throws CartNotTrackableException {
        String finalLine = username + ";";
        for (Product product : currentCart) {
            finalLine = finalLine + product.toString() + "___";
        }
        if (finalLine.endsWith("___")) {
            finalLine = finalLine.substring(0, finalLine.length() - 3);
        }
        finalLine = finalLine + ";";
        for (String seller : currentSellers) {
            finalLine = finalLine + seller + "___";
        }
        if (finalLine.endsWith("___")) {
            finalLine = finalLine.substring(0, finalLine.length() - 3);
        }
        try {
            ArrayList<String> toAppend = new ArrayList<>();
            FileReader fr = new FileReader("carts.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean flag = true;
            String line = br.readLine();
            while (line != null) {
                String[] toGetCustomer = line.split(";", -1);
                if (toGetCustomer[0].equals(username)) {
                    flag = false;
                    toAppend.add(finalLine);
                    line = br.readLine();
                    continue;
                }
                toAppend.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
            if (flag) {
                toAppend.add(finalLine);
            }
            FileOutputStream fos = new FileOutputStream("carts.txt");
            PrintWriter pw = new PrintWriter(fos);
            for (String eachLine : toAppend) {
                pw.println(eachLine);
            }
            pw.close();
            fos.close();
        } catch (IOException e) {
            throw new CartNotTrackableException("Something went wrong - Could not add to cart :(");
        }
    }

    public void checkout(ArrayList<Product> proceedToCheckout, ArrayList<String> sellerUsernames)
        throws IOException {

        // clearing cart

        FileReader frCart = new FileReader("carts.txt");
        BufferedReader brCart = new BufferedReader(frCart);
        ArrayList<String> lines = new ArrayList<>();
        String line = brCart.readLine();
        while (line != null) {
            lines.add(line);
            line = brCart.readLine();
        }
        brCart.close();
        frCart.close();
        FileOutputStream fosCart = new FileOutputStream("carts.txt");
        PrintWriter pwCart = new PrintWriter(fosCart);
        for (String value : lines) {
            String[] splitValue = value.split(";", -1);
            if (splitValue[0].equals(username)) {
                pwCart.println(username + ";;");
            } else {
                pwCart.println(value);
            }
        }
        pwCart.close();
        fosCart.close();

        // updating customer stats

        FileReader frCustomer = new FileReader("CustomerStatistics.txt");
        BufferedReader brCustomer = new BufferedReader(frCustomer);
        lines = new ArrayList<>();
        line = brCustomer.readLine();
        while (line != null) {
            lines.add(line);
            line = brCustomer.readLine();
        }
        brCustomer.close();
        frCustomer.close();
        int i = 0;
        for (String sellerUsername : sellerUsernames) {
            boolean changed = false;
            int j = 0;
            inner:
            for (String eachLine : lines) {
                String[] splitEachLine = eachLine.split(";", -1);
                String finalLine = splitEachLine[0] + ";";
                if (splitEachLine[0].equals(sellerUsername)) {
                    String storeName = proceedToCheckout.get(i).getStore().getName();
                    String[] storeAndSales = splitEachLine[1].split("___");
                    for (String eachStore : storeAndSales) {
                        String[] storeAndSale = eachStore.split("_", -1);
                        if (storeAndSale[0].equals(storeName)) {
                            int oldQuantity = Integer.parseInt(storeAndSale[1]);
                            int newQuantity = oldQuantity + proceedToCheckout.get(i).getQuantity();
                            finalLine = finalLine + storeAndSale[0] + "_" + newQuantity + "___";
                            changed = true;
                        } else {
                            finalLine = finalLine + eachStore + "___";
                        }
                    }
                    if (!changed) {
                        finalLine =
                            finalLine + proceedToCheckout.get(i).getStore().getName() + "_" +
                                proceedToCheckout.get(i).getQuantity() + "___";
                        changed = true;
                    }
                }
                if (finalLine.endsWith("___")) {
                    finalLine = finalLine.substring(0, finalLine.length() - 3);
                }
                if (changed) {
                    lines.set(j, finalLine);
                    break inner;
                }
                j++;
            }
            if (!changed) {
                lines.add(sellerUsername + ";" + proceedToCheckout.get(i).getStore().getName() + "_" +
                    proceedToCheckout.get(i).getQuantity());
            }
            i++;
        }
        FileOutputStream fosCustomer = new FileOutputStream("CustomerStatistics.txt");
        PrintWriter pwCustomer = new PrintWriter(fosCustomer);
        for (String eachLine : lines) {
            pwCustomer.println(eachLine);
        }
        pwCustomer.close();
        fosCustomer.close();


        // updating Products.txt

        FileReader frProducts = new FileReader("Products.txt");
        BufferedReader brProducts = new BufferedReader(frProducts);
        lines = new ArrayList<>();
        line = brProducts.readLine();
        while (line != null) {
            boolean added = false;
            for (int k = 0; k < proceedToCheckout.size(); k++) {
                Product product = proceedToCheckout.get(k);
                String seller = sellerUsernames.get(k);
                String[] sellerAndProduct = line.split(";", -1);
                boolean same = sellerAndProduct[0].equals(seller);
                String[] productString = sellerAndProduct[1].split("_", -1);
                Product newProduct = new Product(productString[0],
                    new Store(productString[1]),
                    Integer.parseInt(productString[2]),
                    Double.parseDouble(productString[3]),
                    productString[4]
                );
                same = same && product.getName().equals(newProduct.getName());
                same = same && product.getStore().getName().equals(newProduct.getStore().getName());
                same = same && product.getPrice() == newProduct.getPrice();
                same = same && product.getDescription().equals(newProduct.getDescription());
                if (same) {
                    newProduct.setQuantity(newProduct.getQuantity() - product.getQuantity());
                    added = true;
                    if (newProduct.getQuantity() > 0) {
                        lines.add(seller + ";" + newProduct.toString());
                        break;
                    }
                }
            }
            if (!added) {
                lines.add(line);
            }
            line = brProducts.readLine();
        }
        brProducts.close();
        frProducts.close();
        FileOutputStream fosProducts = new FileOutputStream("Products.txt");
        PrintWriter pwProducts = new PrintWriter(fosProducts);
        for (String eachLine : lines) {
            pwProducts.println(eachLine);
        }
        pwProducts.close();
        fosProducts.close();


        var cart = new Cart(proceedToCheckout, sellerUsernames, username);
        cart.updateCustomerPurchaseHistory();
        cart.updateSellerStatistics();

    }

    public ArrayList<Product> viewPurchaseHistory() {
        // Use Customer.displayPurchases
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
            return new ArrayList<>();
        }
        if (!exists) {
            return null;
        } else {
            return productList;
        }
    }

    public StringBuilder customerCsvExport() throws IOException {
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
        sb.append("___");

        for (String value : lines) {
            String[] checkUser = value.split(";", -1);
            if (checkUser[0].equals(username)) {
                String[] products = checkUser[1].split("___", -1);
                for (String val : products) {
                    String[] product = val.split("_", -1);
                    product[4] = product[4].split("\\$", -1)[0];
                    for (String eachValue : product) {
                        sb.append(eachValue);
                        sb.append(',');
                    }
                    sb.append("___");
                }
            }
        }
        return sb;
    }

    public ProductsAndStores readSeller() throws IOException {
        Listing reading = readProductsTxt();
        ArrayList<Product> listings = reading.getProducts();
        ArrayList<String> sellerUsernames = reading.getSellers();
        boolean listed = false;
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Store> stores = new ArrayList<>();
        for (int i = 0; i < listings.size(); i++) {
            String sellerUsername = sellerUsernames.get(i);
            if (sellerUsername.equals(username)) {
                listed = true;
                products.add(listings.get(i));
                Store sellerStore = listings.get(i).getStore();
                boolean sameName = false;
                for (Store value : stores) {
                    if (value.getName().equals(sellerStore.getName())) {
                        sameName = true;
                        break;
                    }
                }
                if (!sameName) {
                    stores.add(sellerStore);
                }
            }
        }
        if (listed) {
            return new ProductsAndStores(products, stores);
        } else {
            return null;
        }
    }

    public void createProduct(String name, Store store, int quantity, double price, String description)
        throws IOException {
        // initialize a product
        Product newProduct = new Product(name, store, quantity, price, description);
        // add created product to products
        // update Products.txt
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("Products.txt"));
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        br.close();

        PrintWriter pw = new PrintWriter("Products.txt");
        for (String eachLine : lines) {
            pw.println(eachLine);
        }
        pw.println(username + ";" + newProduct.toString());
        pw.close();
    }

    public void createProduct(Product newProduct)
        throws IOException {
        // update Products.txt
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("Products.txt"));
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        br.close();

        PrintWriter pw = new PrintWriter("Products.txt");
        for (String eachLine : lines) {
            pw.println(eachLine);
        }
        pw.println(username + ";" + newProduct.toString());
        pw.close();
    }

    public void deleteProduct(String checkLine) throws IOException {
        ArrayList<String> theListingsLines = new ArrayList<>();
        FileReader frDelete = new FileReader("Products.txt");
        BufferedReader brDelete = new BufferedReader(frDelete);
        String eachLine = brDelete.readLine();
        while (eachLine != null) {
            if (!eachLine.equals(checkLine)) {
                theListingsLines.add(eachLine);
            }
            eachLine = brDelete.readLine();
        }
        brDelete.close();
        frDelete.close();
        PrintWriter pwDelete = new PrintWriter("Products.txt");
        for (String line : theListingsLines) {
            pwDelete.println(line);
        }
        pwDelete.close();
    }

    public void editProduct(String checkLine, String replaceLine) throws IOException {
        ArrayList<String> theListingsLines = new ArrayList<>();
        FileReader frDelete = new FileReader("Products.txt");
        BufferedReader brDelete = new BufferedReader(frDelete);
        String eachLine = brDelete.readLine();
        while (eachLine != null) {
            if (!eachLine.equals(checkLine)) {
                theListingsLines.add(eachLine);
            } else {
                theListingsLines.add(replaceLine);
            }
            eachLine = brDelete.readLine();
        }
        brDelete.close();
        frDelete.close();
        PrintWriter pwDelete = new PrintWriter("Products.txt");
        for (String line : theListingsLines) {
            pwDelete.println(line);
        }
        pwDelete.close();
    }

    public ArrayList<ArrayList<String>> seeCarts() throws IOException {
        FileReader frCarts = new FileReader("carts.txt");
        BufferedReader brCarts = new BufferedReader(frCarts);
        ArrayList<String> lines = new ArrayList<>();
        String eachLine = brCarts.readLine();
        while (eachLine != null) {
            lines.add(eachLine);
            eachLine = brCarts.readLine();
        }
        brCarts.close();
        frCarts.close();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String theLine : lines) {
            ArrayList<String> element = new ArrayList<>();
            String[] all = theLine.split(";", -1);
            String user = all[0];
            String[] allProds = all[1].split("___", -1);
            element.add("Customer: " + user);
            if (allProds.length > 1) {
                element.add("________________________");
                for (String eachProd : allProds) {
                    String[] prodDetails = eachProd.split("_", -1);
                    element.add(prodDetails[0]);
                    element.add("Store: " + prodDetails[1]);
                    element.add("Quantity in cart: " + prodDetails[2]);
                    element.add("Price: " + prodDetails[3]);
                    element.add("Description:<br>" + prodDetails[4]);
                    element.add("________________________");
                }
            } else {
                element.add("Empty");
            }
            result.add(element);
        }

        return result;
    }

    public void csvImport(ArrayList<Product> products) throws IOException {
        for (Product product : products) {
            createProduct(product);
        }
    }

    public void reviewWrite(Product product, String stars, String review) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"));
        ArrayList<String> lines = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        br.close();
        lines.add(product.toString() + "___" + stars + "___" + review);
        PrintWriter pw = new PrintWriter("Reviews.txt");
        for (String eachLine : lines) {
            pw.println(eachLine);
        }
        pw.close();
    }

    public ArrayList<String> reviewRead(Product p) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Reviews.txt"));
        ArrayList<String> lines = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        br.close();
        ArrayList<String> reviews = new ArrayList<>();
        for (String eachLine : lines) {
            String[] det = eachLine.split("___", -1);
            String[] prodDetails = det[0].split("_", -1);
            if (prodDetails[0].equals(p.getName()) && prodDetails[1].equals(p.getStore().getName()) &&
                prodDetails[3].equals(String.format("%.2f", p.getPrice())) &&
                prodDetails[4].equals(p.getDescription())) {
                reviews.add("<br>" + det[1] + "<br>" + unTruncate(det[2]) + "<br>");
            }
        }
        return reviews;
    }

    public String unTruncate(String name) {
        if (name.length() > 30) {
            name = name.substring(0, 30) + "-<br>" + unTruncate(name.substring(30));
        }
        return name;
    }
}

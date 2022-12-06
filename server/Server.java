import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Server class
public class Server {
    public final static Object gateKeeper = new Object();

    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            // server is listening on port 1234
            server = new ServerSocket(42426);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // create a new thread object
                ClientHandler clientSock
                    = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            class RegexChecker {
                public boolean matchesCreate(String s) { // CAM{username;password___booleanCustomer}
                    boolean startsWith = s.startsWith("CAM");
                    boolean notStartsWith = !s.startsWith("CAM-UN");
                    return startsWith && notStartsWith;
                }

                public boolean matchesCheckUser(String s) { // CAM-UN{username}
                    return s.startsWith("CAM-UN");
                }

                public boolean matchesReadCustomerStats(String s) { // CDRCS
                    return s.equals("CDRCS");
                }

                public boolean matchesReadPurchaseHistory(String s) { // CDRPH
                    return s.equals("CDRPH");
                }

                public boolean matchesLoginRead(String s) { // LN{username;password}
                    return s.startsWith("LN");
                }

                public boolean matchesReadProducts(String s) { // MPM;username;RPT
                    return s.startsWith("MPM") && s.endsWith("RPT");
                }

                public boolean matchesReadCartCustomer(String s) { // MPM;username;RCC
                    return s.startsWith("MPM") && s.endsWith("RCC");
                }

                public boolean matchesStoreCart(String s) { // MPM;username;SC{products(___);sellers(,)}
                    String[] check = s.split(";", -1);
                    return check[0].equals("MPM") && check[2].startsWith("SC");
                }

                public boolean matchesCheckout(String s) { // MPM;username;COUT{products(___);sellers(,)}
                    String[] check = s.split(";", -1);
                    return check[0].equals("MPM") && check[2].startsWith("COUT");
                }

                public boolean matchesViewPurchases(String s) { // MPM;username;VPH
                    return s.startsWith("MPM") && s.endsWith("VPH");
                }

                public boolean matchesReadCustomerCsv(String s) { //MPM;username;CCSV
                    return s.startsWith("MPM") && s.endsWith("CCSV");
                }

                public boolean matchesReadSeller(String s) { // MPM;username;RS
                    return s.startsWith("MPM") && s.endsWith("RS");
                }

                public boolean matchesCreateProduct(String s) { // MPM;username;CP;prod.toString
                    String[] det = s.split(";", -1);
                    return det[0].equals("MPM") && det[2].equals("CP");
                }

                public boolean matchesDeleteProduct(String s) { // MPM___username___RP___checkLine
                    String[] det = s.split("___", -1);
                    return det[0].equals("MPM") && det[2].equals("RP");
                }

                public boolean matchesEditProduct(String s) { // MPM___username___EP___checkLine___replaceLine
                    String[] det = s.split("___", -1);
                    return det[0].equals("MPM") && det[2].equals("EP");
                }

                public boolean matchesSellerImportCsv(String s) { // MPM;username;SelCSVI;products(___)
                    String[] det = s.split(";", -1);
                    return det[0].equals("MPM") && det[2].equals("SelCSVI");
                }

                public boolean matchesSeeCarts(String s) { //MPM;username;SEEC
                    return s.startsWith("MPM") && s.endsWith("SEEC");
                }

                public boolean matchesReviewWrite(String s) { //MPM;username;RW;product(_);stars;review
                    String[] det = s.split(";", -1);
                    return det[0].equals("MPM") && det[2].equals("RW");
                }

                public boolean matchesReviewRead(String s) { // MPM;username;RR;product(_)
                    String[] det = s.split(";", -1);
                    return det[0].equals("MPM") && det[2].equals("RR");
                }

                public boolean matchesSellerStats(String s) { // SDR
                    return s.equals("SDR");
                }

            }
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                    new InputStreamReader(
                        clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // writing the received message from
                    // client

                    String reply = "ERROR404";

                    RegexChecker checker = new RegexChecker();
                    synchronized (gateKeeper) {
                        //customer
                        if (checker.matchesCreate(line)) {
                            String username = line.substring(line.indexOf("{") + 1, line.indexOf(";"));
                            String password = line.substring(line.indexOf(";") + 1, line.indexOf("___"));
                            boolean customer = Boolean.parseBoolean(line.substring(line.indexOf("___") + 3,
                                line.lastIndexOf("}")));
                            try {
                                CreateAccountMethods.writeAccount(new Account(username, password), customer);
                                reply = "SUCCESS";
                            } catch (Exception exception) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesCheckUser(line)) {
                            String username = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));
                            try {
                                reply = String.valueOf(CreateAccountMethods.checkUsername(username));
                            } catch (Exception exception) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReadCustomerStats(line)) {
                            try {
                                List<String> resultList = CustomerDashboardMethods.readCustomerStats();
                                reply = Encoder.encodeList(resultList);
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReadPurchaseHistory(line)) {
                            try {
                                List<String> resultList = CustomerDashboardMethods.readPurchaseHistory();
                                reply = Encoder.encodeList(resultList);
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesLoginRead(line)) {
                            int semiColonIndex = line.indexOf(";");
                            String username = line.substring(line.indexOf("{") + 1, semiColonIndex);
                            String password = line.substring(semiColonIndex + 1, line.lastIndexOf("}"));
                            try {
                                reply = LoginMethods.reader(username, password); // exists;accountType
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReadProducts(line)) {
                            String[] details = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(details[1]);
                            try {
                                Listing listing = methods.readProductsTxt();
                                reply = Encoder.encodeListing(listing);
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReadCartCustomer(line)) {
                            String[] details = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(details[1]);
                            try {
                                Listing listing = methods.readCart();
                                reply = Encoder.encodeListing(listing);
                            } catch (CartNotTrackableException e) {
                                reply = "ERROR-CNT-" + e.getMessage();
                            }
                        } else if (checker.matchesStoreCart(line)) {
                            String[] value = line.split(";");
                            MarketPlaceMethods methods = new MarketPlaceMethods(value[1]);
                            String prods = value[2].substring(value[2].indexOf("{") + 1);
                            String[] sellerUsernames = value[3].substring(0, value[3].length() - 1)
                                .split(",", -1);
                            ArrayList<String> sellers = new ArrayList<>(Arrays.asList(sellerUsernames));
                            ArrayList<Product> products = new ArrayList<>();
                            for (String eachLine : prods.split("___", -1)) {
                                String[] prodDetails = eachLine.split("_", -1);
                                products.add(new Product(prodDetails[0], new Store(prodDetails[1]),
                                    Integer.parseInt(prodDetails[2]), Double.parseDouble(prodDetails[3]),
                                    prodDetails[4]));
                            }
                            try {
                                methods.storeCart(products, sellers);
                                reply = "SUCCESS";
                            } catch (CartNotTrackableException e) {
                                reply = "ERROR-CNT-" + e.getMessage();
                            }
                        } else if (checker.matchesCheckout(line)) {
                            String[] value = line.split(";");
                            MarketPlaceMethods methods = new MarketPlaceMethods(value[1]);
                            String prods = value[2].substring(value[2].indexOf("{") + 1);
                            String[] sellerUsernames = value[3].substring(0, value[3].length() - 1)
                                .split(",", -1);
                            ArrayList<String> sellers = new ArrayList<>(Arrays.asList(sellerUsernames));
                            ArrayList<Product> products = new ArrayList<>();
                            for (String eachLine : prods.split("___", -1)) {
                                String[] prodDetails = eachLine.split("_", -1);
                                products.add(new Product(prodDetails[0], new Store(prodDetails[1]),
                                    Integer.parseInt(prodDetails[2]), Double.parseDouble(prodDetails[3]),
                                    prodDetails[4]));
                            }
                            try {
                                methods.checkout(products, sellers);
                                reply = "SUCCESS";
                            } catch (IOException e) {
                                reply = "ERROR502<br>"+e.getMessage();
                            }
                        } else if (checker.matchesViewPurchases(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            ArrayList<Product> products = methods.viewPurchaseHistory();
                            if (products == null) {
                                reply = "null";
                            } else if (products.isEmpty()) {
                                reply = "";
                            } else {
                                reply = Encoder.encodeProducts(products);
                            }
                        } else if (checker.matchesReadCustomerCsv(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            try {
                                reply = methods.customerCsvExport().toString();
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReadSeller(line)) { // seller
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            try {
                                ProductsAndStores val = methods.readSeller();
                                reply = Encoder.encodeProductsAndStores(val);
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesCreateProduct(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            String[] prod = det[3].split("_", -1);
                            try {
                                methods.createProduct(prod[0], new Store(prod[1]), Integer.parseInt(prod[2]),
                                    Double.parseDouble(prod[3]), prod[4]);
                                reply = "SUCCESS";
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesDeleteProduct(line)) {
                            String[] det = line.split("___", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            try {
                                methods.deleteProduct(det[3]);
                                reply = "SUCCESS";
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesEditProduct(line)) {
                            String[] det = line.split("___", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            try {
                                methods.editProduct(det[3], det[4]);
                                reply = "SUCCESS";
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesSellerImportCsv(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            ArrayList<Product> products = new ArrayList<>();
                            String[] prods = det[3].split("___", -1);
                            for (String value : prods) {
                                String[] prodDetails = value.split("_", -1);
                                products.add(new Product(prodDetails[0], new Store(prodDetails[1]),
                                    Integer.parseInt(prodDetails[2]), Double.parseDouble(prodDetails[3]),
                                    prodDetails[4]));
                            }
                            try {
                                methods.csvImport(products);
                                reply = "SUCCESS";
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesSeeCarts(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            ArrayList<ArrayList<String>> arrayList = methods.seeCarts();
                            reply = Encoder.encodeArrayListOfArrayLists(arrayList);
                        } else if (checker.matchesReviewWrite(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            String[] prod = det[3].split("_", -1);
                            Product product = new Product(prod[0], new Store(prod[1]), Integer.parseInt(prod[2]),
                                Double.parseDouble(prod[3]), prod[4]);
                            try {
                                methods.reviewWrite(product, det[4], det[5]);
                                reply = "SUCCESS";
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesReviewRead(line)) {
                            String[] det = line.split(";", -1);
                            MarketPlaceMethods methods = new MarketPlaceMethods(det[1]);
                            String[] prod = det[3].split("_", -1);
                            Product product = new Product(prod[0], new Store(prod[1]), Integer.parseInt(prod[2]),
                                Double.parseDouble(prod[3]), prod[4]);
                            try {
                                ArrayList<String> arrayList = methods.reviewRead(product);
                                reply = Encoder.encodeReviews(arrayList);
                            } catch (Exception e) {
                                reply = "ERROR502";
                            }
                        } else if (checker.matchesSellerStats(line)) {
                            List<String> list = SellerDashboardMethods.readSellerStats();
                            reply = Encoder.encodeList(list);
                        }
                        out.println(reply);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}

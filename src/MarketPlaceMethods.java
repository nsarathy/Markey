public class MarketPlaceMethods {
    public Listing readProductsTxt() {
        // Reading products in listings
        FileReader frProducts = new FileReader("Products.txt");
        BufferedReader brProducts = new BufferedReader(frProducts);
        ArrayList<Product> listings = new ArrayList<>();
        ArrayList<String> sellerUsernames = new ArrayList<>();
        final ArrayList<Product> originalListings;
        final ArrayList<String> originalSellerUsernames;
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
    }
}

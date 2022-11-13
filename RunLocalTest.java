import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class RunLocalTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Due to file handling nature of this project");
        System.out.println("Please run each test case manually and separately");
    }

    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            // read and write
            FileOutputStream fos = null;
            FileOutputStream fs = null;
            try {
                fos = new FileOutputStream("Products.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println("wantGorOwner;Metal Headband_WantGor_100_3.98_Metal headband for men and women");
            pw.println("jbl;JBL Vibe 200TWs_JBL_30_29.95_Black wireless earbuds");
            pw.println("steveJ;USB-C Wall Charger_INCORIC_200_9.27_iPhone 14 Charger Block 20W PD Power Adaptor");
            pw.println(
                "proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable " +
                    "buttons - Mechanical switches - rubber side grips - Mercury white");
            pw.println(
                "warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
            pw.println("ysenser;Wool Socks 5 pairs_YSense_10_10.99_Thick Wool socks winter warm for men");
            pw.println("firstSeller;gg_Dunsmore_6_5.00_ekwbgew");
            pw.println("firstSeller;Arnav_Dunsmore_99_0.69_weriufheiu uwueth riut");
            pw.println("firstSeller;Colten_Oh_98_10.20_si instructor");

            pw.close();

            try {
                fs = new FileOutputStream("Accounts.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw1 = new PrintWriter(fs);
            pw1.println("seller_firstSeller;password");
            pw1.println("customer_firstCustomer;password");

            pw1.close();

            try {
                FileOutputStream fosCart = new FileOutputStream("carts.txt");
                PrintWriter pwCart = new PrintWriter(fosCart);
                pwCart.println("firstCustomer;;");
                pwCart.println("secondCustomer;;");
                pwCart.close();
                fosCart.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000000)
        public void testExpectedOne() {
            String input = "2" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "sellerUser" + System.lineSeparator() +
                "password" + System.lineSeparator() + "sellerUser" + System.lineSeparator() + "password" +
                System.lineSeparator() + "@" + System.lineSeparator() + "Turkstra" + System.lineSeparator() +
                "cs182" + System.lineSeparator() + "900" + System.lineSeparator() + "420.69" +
                System.lineSeparator() + "A course on foundations of cs" + System.lineSeparator() +
                "++" + System.lineSeparator() + "cs180 blk" + System.lineSeparator() + "1" + System.lineSeparator() +
                "400" + System.lineSeparator() + "1.0" + System.lineSeparator() + "A java course" +
                System.lineSeparator() +
                "*" + System.lineSeparator() + "1" + System.lineSeparator() +
                "2" + System.lineSeparator() + "2.0" + System.lineSeparator() + "--" + System.lineSeparator() + "2" +
                System.lineSeparator() + "?" + System.lineSeparator();
            //    restoreInputAndOutput();
            //   System.out.println(input);
            //  outputStart();
            String expected = "Welcome to the Marketplace!" + System.lineSeparator() +
                "Do you have an existing account?" + System.lineSeparator() +
                "1. Yes" + System.lineSeparator() + "2. No" + System.lineSeparator() +
                "Are you a seller or customer?" + System.lineSeparator() + "1. Seller" +
                System.lineSeparator() + "2. Customer" + System.lineSeparator() + "Enter desired username: " +
                System.lineSeparator() +
                "Enter desired password: " + System.lineSeparator() +
                "Checking validity... ... ... " + System.lineSeparator() + "Account has been created!" +
                System.lineSeparator() +
                System.lineSeparator() + "LOGIN WINDOW:" + System.lineSeparator() + "Enter Username:" +
                System.lineSeparator() +
                "Enter Password:" + System.lineSeparator() + "LOGIN SUCCESSFUL" + System.lineSeparator() + "Markey"
                + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "Enter '@' to open a new Store" + System.lineSeparator() +
                "Enter '++' to list a new item for sale" +
                System.lineSeparator() +
                "Enter '--' to remove" +
                " an " +
                "item from sale" + System.lineSeparator() +
                "Enter '*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "You do not having anything for sale" +
                System.lineSeparator() + "Enter a name for your store" + System.lineSeparator() +
                "It is mandatory to have at least one product on sale when opening a new store" +
                System.lineSeparator() + "Enter name of product" + System.lineSeparator() +
                "Enter the quantity of this item available in stock" +
                System.lineSeparator() + "Enter the price of this item" + System.lineSeparator() +
                "Enter the description of this item" +
                System.lineSeparator() + "Markey" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "10.\tcs182\t\tSold by: Turkstra\t\tPrice: 420.69" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() +
                "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() +
                "Enter '*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Enter name of product" +
                System.lineSeparator() +
                "Choose a store for your item" + System.lineSeparator() + "1. Turkstra" + System.lineSeparator() +
                "Enter the quantity of this item available in stock" + System.lineSeparator() +
                "Enter the price of this item" + System.lineSeparator() + "Enter the description of this item" +
                System.lineSeparator() +
                "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "10.\tcs182\t\tSold by: Turkstra\t\tPrice: 420.69" + System.lineSeparator() + System.lineSeparator() +
                "11.\tcs180 blk\t\tSold by: Turkstra\t\tPrice: 1.00" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() +
                "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() +
                "Enter '*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                "1.\tcs182\t\tSold by: Turkstra\t\tPrice: 420.69" +
                System.lineSeparator() + System.lineSeparator() + "2.\tcs180 blk\t\tSold by: Turkstra\t\tPrice: 1.00" +
                System.lineSeparator() + "Enter 2 to edit 2nd item" + System.lineSeparator() + System.lineSeparator() +
                "cs182\t\tSold by: Turkstra\t\tPrice: 420.69" +
                System.lineSeparator() + "Available in stock: 900" + System.lineSeparator() +
                "Description: A course on foundations of cs" +
                System.lineSeparator() + System.lineSeparator() + "Enter" + System.lineSeparator() +
                "'1' to edit name" +
                System.lineSeparator() + "'2' to edit price" + System.lineSeparator() + "'3' to edit description" +
                System.lineSeparator() + "'4' to edit availability in stock" + System.lineSeparator() +
                "Enter new price" + System.lineSeparator() + "Edit successful :)" + System.lineSeparator() +
                "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "10.\tcs182\t\tSold by: Turkstra\t\tPrice: 2.00" + System.lineSeparator() + System.lineSeparator() +
                "11.\tcs180 blk\t\tSold by: Turkstra\t\tPrice: 1.00" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() +
                "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() +
                "Enter '*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                "1.\tcs182\t\tSold by: Turkstra\t\tPrice: 2.00" +
                System.lineSeparator() + System.lineSeparator() + "2.\tcs180 blk\t\tSold by: Turkstra\t\tPrice: 1.00" +
                System.lineSeparator() +
                System.lineSeparator() + "Enter 2 to remove 2nd item from listings" + System.lineSeparator() +
                "Deletion successful :)" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                System.lineSeparator() +
                System.lineSeparator() + "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" +
                System.lineSeparator() + System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "10.\tcs182\t\tSold by: Turkstra\t\tPrice: 2.00" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() +
                "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() +
                "Enter '*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "Thank you for using Markey! Have a nice day :)"
                + System.lineSeparator();

            // restoreInputAndOutput();
            receiveInput(input);
            Login.main(new String[0]);

            String output = getOutput();

            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("doesn't work ig?",
                expected.trim(), output.trim());


        }


    }

    public static class TestCase1 {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            // read and write
            FileOutputStream fos = null;
            FileOutputStream fs = null;
            try {
                fos = new FileOutputStream("Products.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println("wantGorOwner;Metal Headband_WantGor_100_3.98_Metal headband for men and women");
            pw.println("jbl;JBL Vibe 200TWs_JBL_30_29.95_Black wireless earbuds");
            pw.println("steveJ;USB-C Wall Charger_INCORIC_200_9.27_iPhone 14 Charger Block 20W PD Power Adaptor");
            pw.println(
                "proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable " +
                    "buttons - Mechanical switches - rubber side grips - Mercury white");
            pw.println(
                "warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
            pw.println("ysenser;Wool Socks 5 pairs_YSense_10_10.99_Thick Wool socks winter warm for men");
            pw.println("firstSeller;gg_Dunsmore_6_5.00_ekwbgew");
            pw.println("firstSeller;Arnav_Dunsmore_99_0.69_weriufheiu uwueth riut");
            pw.println("firstSeller;Colten_Oh_98_10.20_si instructor");

            pw.close();

            try {
                fs = new FileOutputStream("Accounts.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw1 = new PrintWriter(fs);
            pw1.println("seller_firstSeller;password");
            pw1.println("customer_firstCustomer;password");

            pw1.close();
            try {
                FileOutputStream fosCart = new FileOutputStream("carts.txt");
                PrintWriter pwCart = new PrintWriter(fosCart);
                pwCart.println("firstCustomer;;");
                pwCart.println("secondCustomer;;");
                pwCart.close();
                fosCart.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000000)
        public void testExpectedOne() {
            String input = "1" + System.lineSeparator() + "firstCustomer" + System.lineSeparator() + "password" +
                System.lineSeparator() + "1" + System.lineSeparator() + "5" + System.lineSeparator() +
                "<" + System.lineSeparator() + ">" + System.lineSeparator() +
                "<>" + System.lineSeparator() + "3" + System.lineSeparator() +
                "5" + System.lineSeparator() + "#" + System.lineSeparator() + "$" + System.lineSeparator() +
                "?" + System.lineSeparator();
            //    restoreInputAndOutput();
            //   System.out.println(input);
            //  outputStart();
            String expected = "Welcome to the Marketplace!" + System.lineSeparator() +
                "Do you have an existing account?" + System.lineSeparator() +
                "1. Yes" + System.lineSeparator() + "2. No" + System.lineSeparator()
                + System.lineSeparator() + "LOGIN WINDOW:" + System.lineSeparator() + "Enter Username:" +
                System.lineSeparator() +
                "Enter Password:" + System.lineSeparator() +
                "LOGIN SUCCESSFUL" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 0 items in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                "Metal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                "Available in stock: 100" + System.lineSeparator() +
                "Description: Metal headband for men and women" +
                System.lineSeparator() + "Enter quantity for the selected item ('?' to exit)" +
                System.lineSeparator() + "Added to your cart :)" + System.lineSeparator() +
                "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 1 item in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Markey" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() +
                System.lineSeparator() + "2.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" +
                System.lineSeparator() + System.lineSeparator() +
                "3.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() +
                System.lineSeparator() + "4.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" +
                System.lineSeparator() + System.lineSeparator() +
                "7.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" +
                System.lineSeparator() + System.lineSeparator() +
                "8.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "9.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "You have 1 item in your cart" + System.lineSeparator() + System.lineSeparator() +
                "Enter '2' to add item number 2 to your cart" + System.lineSeparator() + System.lineSeparator() +
                "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Markey" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "3.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" +
                System.lineSeparator() + System.lineSeparator() +
                "4.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                "6.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() + "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" +
                System.lineSeparator() + System.lineSeparator() +
                "8.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() + "9.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "You have 1 item in your cart" + System.lineSeparator() + System.lineSeparator() +
                "Enter '2' to add item number 2 to your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Markey" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 1 item in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() +
                "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                "USB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                "Available in stock: 200" + System.lineSeparator() +
                "Description: iPhone 14 Charger Block 20W PD Power Adaptor" + System.lineSeparator() +
                "Enter quantity for the selected item ('?' to exit)" + System.lineSeparator() +
                "Added to your cart :)" + System.lineSeparator() + "Markey" +
                System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 2 items in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() +
                "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                "Quantity: 5" + System.lineSeparator() + "2.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27"
                + System.lineSeparator() + "Quantity: 5" + System.lineSeparator() + System.lineSeparator() +
                "Total Price: 66.25" + System.lineSeparator() + System.lineSeparator() + "Enter '$' to checkout" +
                System.lineSeparator() + "Enter 2 to remove item number 2" + System.lineSeparator() +
                "Enter '?' to exit cart" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 0 items in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() +
                "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "Thank you for using Markey! Have a nice day :)" + System.lineSeparator();


            // restoreInputAndOutput();
            receiveInput(input);
            Login.main(new String[0]);

            String output = getOutput();

            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("doesn't work ig?",
                expected.trim(), output.trim());


        }


    }

    public static class TestCase2 {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            // read and write
            FileOutputStream fos = null;
            FileOutputStream fs = null;

            try {
                fos = new FileOutputStream("SellerStatistics.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println(
                "firstSeller;Arnav_Dunsmore_1_0.69_weriufheiu uwueth riut___Colten_Oh_1_0.69_si " +
                    "instructor___gg_Dunsmore_5_5.00_ekwbgew;firstCustomer");
            pw.println("ysenser;Wool Socks 5 pairs_YSense_5_10.99_Thick Wool socks winter warm for men;firstCustomer");
            pw.println("wantGorOwner;Metal Headband_WantGor_5_3.98_Metal headband for men and women;firstCustomer");
            pw.println(
                "steveJ;USB-C Wall Charger_INCORIC_5_9.27_iPhone 14 Charger Block 20W PD Power Adaptor;firstCustomer");
            pw.println("firstSeller;Colten_Oh_8_10.20_si instructor;scannerTester");

            pw.close();

            try {
                fs = new FileOutputStream("Products.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw1 = new PrintWriter(fs);
            pw1.println("wantGorOwner;Metal Headband_WantGor_100_3.98_Metal headband for men and women");
            pw1.println("jbl;JBL Vibe 200TWs_JBL_30_29.95_Black wireless earbuds");
            pw1.println("steveJ;USB-C Wall Charger_INCORIC_200_9.27_iPhone 14 Charger Block 20W PD Power Adaptor");
            pw1.println(
                "proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable " +
                    "buttons - Mechanical switches - rubber side grips - Mercury white");
            pw1.println(
                "warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
            pw1.println("ysenser;Wool Socks 5 pairs_YSense_10_10.99_Thick Wool socks winter warm for men");
            pw1.println("firstSeller;gg_Dunsmore_6_5.00_ekwbgew");
            pw1.println("firstSeller;Arnav_Dunsmore_99_0.69_weriufheiu uwueth riut");
            pw1.println("firstSeller;Colten_Oh_98_10.20_si instructor");

            pw1.close();


        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000000)
        public void testExpectedOne() {
            String input = "1" + System.lineSeparator() + "firstSeller" + System.lineSeparator() + "password" +
                System.lineSeparator() + "db" + System.lineSeparator() + "1" + System.lineSeparator() +
                "3" + System.lineSeparator() + "3" + System.lineSeparator() +
                "?" + System.lineSeparator();
            //    restoreInputAndOutput();
            //   System.out.println(input);
            //  outputStart();
            String expected = "Welcome to the Marketplace!" + System.lineSeparator() +
                "Do you have an existing account?" + System.lineSeparator() +
                "1. Yes" + System.lineSeparator() + "2. No" + System.lineSeparator()
                + System.lineSeparator() + "LOGIN WINDOW:" + System.lineSeparator() + "Enter Username:" +
                System.lineSeparator() +
                "Enter Password:" + System.lineSeparator() +
                "LOGIN SUCCESSFUL" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "Enter '@' to open a new Store" + System.lineSeparator() +
                "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() + "Enter " +
                "'*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "*SELLER DASHBOARD*" + System.lineSeparator() + System.lineSeparator() +
                "1. View Customer List" + System.lineSeparator() + "2. View Product Sales List" +
                System.lineSeparator() + "3. Exit Customer Dashboard" + System.lineSeparator() +
                System.lineSeparator() + "Enter Option Here: " + System.lineSeparator() +
                "********************" + System.lineSeparator() + "Customer List" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                "Customer: firstCustomer" + System.lineSeparator() + "Items Purchased: 7" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                "Customer: scannerTester" + System.lineSeparator() + "Items Purchased: 8" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                System.lineSeparator() + "Would you like to sort this List?" + System.lineSeparator() +
                "1: High to Low" + System.lineSeparator() + "2: Low to High" + System.lineSeparator() +
                "3: Exit" + System.lineSeparator() + System.lineSeparator() + "Enter Here: " + System.lineSeparator() +
                System.lineSeparator() + "*SELLER DASHBOARD*" +
                System.lineSeparator() + System.lineSeparator() + "1. View Customer List" +
                System.lineSeparator() + "2. View Product Sales List" + System.lineSeparator() +
                "3. Exit Customer Dashboard" +
                System.lineSeparator() + System.lineSeparator() + "Enter Option Here: " + System.lineSeparator() +
                "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "Enter '@' to open a new Store" + System.lineSeparator() +
                "Enter '++' to list a new item for sale" +
                System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() + "Enter " +
                "'*' to edit an item that's on sale" + System.lineSeparator() + "Enter '&' to See what's on " +
                "everyone's carts" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() +
                "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "Thank you for using Markey! Have a nice day :)"
                + System.lineSeparator();


            // restoreInputAndOutput();
            receiveInput(input);
            Login.main(new String[0]);

            String output = getOutput();

            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("doesn't work ig?",
                expected.trim(), output.trim());


        }


    }

    public static class TestCase3 {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            // read and write
            FileOutputStream fos = null;
            FileOutputStream fs = null;
            FileOutputStream fs1 = null;

            try {
                fos = new FileOutputStream("CustomerStatistics.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println("warrenBuffet;Puffin Books_210");
            pw.println("testUserSeller;neel's Store_206___lewis'_6___yudon_502");
            pw.println("firstSeller;Dunsmore_21___Oh_27");
            pw.println("ysenser;YSense_5");
            pw.println("wantGorOwner;WantGor_5");
            pw.println("steveJ;INCORIC_5");

            pw.close();

            try {
                fs = new FileOutputStream("CustomerPurchaseHistory.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw1 = new PrintWriter(fs);
            pw1.println(
                "testUser;wantGorOwner Metal Headband_WantGor_2_3.98_Metal headband for men and women___JBL Vibe " +
                    "200TWs_JBL_1_29.95_Black wireless earbuds");
            pw1.println(
                "testuser1;USB-C Wall Charger_INCORIC_1_9.27_iPhone 14 Charger Block 20W PD Power Adaptor___Razer " +
                    "DeathAdder Essential Gaming Mouse_Razer_1_17.85_Gaming mouse - 5 programmable buttons - " +
                    "Mechanical switches - rubber side grips - Mercury white");
            pw1.println(
                "firstCustomer;Arnav_Dunsmore_9_0.69_weriufheiu uwueth riut$firstSeller___Colten_Oh_9_0.69_si " +
                    "instructor$firstSellerfirstCustomer;Arnav_Dunsmore_6_0.69_weriufheiu uwueth " +
                    "riut$firstSeller___Colten_Oh_6_0.69_si instructor$firstSellerfirstCustomer;Arnav_Dunsmore_1_0" +
                    ".69_weriufheiu uwueth riut$firstSeller___Wool Socks 5 pairs_YSense_5_10.99_Thick Wool socks " +
                    "winter warm for men$ysenserfirstCustomer;Colten_Oh_1_0.69_si " +
                    "instructor$firstSellerfirstCustomer;Colten_Oh_1_0.69_si instructor$firstSellerfirstCustomer;" +
                    "Colten_Oh_1_0.69_si instructor$firstSellerfirstCustomer;Colten_Oh_1_0.69_si " +
                    "instructor$firstSellerfirstCustomer;gg_Dunsmore_5_5.00_ekwbgew$firstSellerfirstCustomer;Metal " +
                    "Headband_WantGor_5_3.98_Metal headband for men and women$wantGorOwner___USB-C Wall " +
                    "Charger_INCORIC_5_9.27_iPhone 14 Charger Block 20W PD Power Adaptor$steveJscannerTester;" +
                    "Colten_Oh_8_10.20_si instructor$firstSeller");

            pw1.close();

            try {
                fs1 = new FileOutputStream("Products.txt", false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw2 = new PrintWriter(fs1);
            pw2.println("wantGorOwner;Metal Headband_WantGor_100_3.98_Metal headband for men and women");
            pw2.println("jbl;JBL Vibe 200TWs_JBL_30_29.95_Black wireless earbuds");
            pw2.println("steveJ;USB-C Wall Charger_INCORIC_200_9.27_iPhone 14 Charger Block 20W PD Power Adaptor");
            pw2.println(
                "proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable " +
                    "buttons - Mechanical switches - rubber side grips - Mercury white");
            pw2.println(
                "warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
            pw2.println("ysenser;Wool Socks 5 pairs_YSense_10_10.99_Thick Wool socks winter warm for men");
            pw2.println("firstSeller;gg_Dunsmore_6_5.00_ekwbgew");
            pw2.println("firstSeller;Arnav_Dunsmore_99_0.69_weriufheiu uwueth riut");
            pw2.println("firstSeller;Colten_Oh_98_10.20_si instructor");

            pw2.close();


        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000000)
        public void testExpectedOne() {
            String input = "1" + System.lineSeparator() + "firstCustomer" + System.lineSeparator() +
                "password" + System.lineSeparator() + "db" + System.lineSeparator() + "1" + System.lineSeparator() +
                "3" + System.lineSeparator() + "3" + System.lineSeparator() + "?" + System.lineSeparator();
            //    restoreInputAndOutput();
            //   System.out.println(input);
            //  outputStart();
            String expected = "Welcome to the Marketplace!" + System.lineSeparator() +
                "Do you have an existing account?" + System.lineSeparator() +
                "1. Yes" + System.lineSeparator() + "2. No" + System.lineSeparator()
                + System.lineSeparator() + "LOGIN WINDOW:" + System.lineSeparator() + "Enter Username:" +
                System.lineSeparator() +
                "Enter Password:" + System.lineSeparator() +
                "LOGIN SUCCESSFUL" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 0 items in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "*CUSTOMER DASHBOARD*" + System.lineSeparator() + System.lineSeparator() +
                "1. View All Stores" + System.lineSeparator() + "2. View Stores Purchased From" +
                System.lineSeparator() + "3. Exit Customer Dashboard" + System.lineSeparator() +
                System.lineSeparator() +
                "Enter Option Here: " + System.lineSeparator() + "********************" + System.lineSeparator() +
                "STORE INFORMATION" + System.lineSeparator() + "--------------------" +
                System.lineSeparator() + "Seller: warrenBuffet" + System.lineSeparator() + "Puffin Books: 210 Sales" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                "Seller: testUserSeller" + System.lineSeparator() + "neel's Store: 206 Sales" +
                System.lineSeparator() + "lewis': 6 Sales" + System.lineSeparator() + "yudon: 502 Sales" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                "Seller: firstSeller" + System.lineSeparator() + "Dunsmore: 21 Sales" +
                System.lineSeparator() + "Oh: 27 Sales" + System.lineSeparator() + "--------------------" +
                System.lineSeparator() + "Seller: ysenser" + System.lineSeparator() + "YSense: 5 Sales" +
                System.lineSeparator() + "--------------------" + System.lineSeparator() +
                "Seller: wantGorOwner" + System.lineSeparator() + "WantGor: 5 Sales" + System.lineSeparator() +
                "--------------------" + System.lineSeparator() + "Seller: steveJ" + System.lineSeparator() +
                "INCORIC: 5 Sales" + System.lineSeparator() + "--------------------" +
                System.lineSeparator() + System.lineSeparator() + "Would you like to sort this List?" +
                System.lineSeparator() +
                "1: High to Low" + System.lineSeparator() + "2: Low to High" + System.lineSeparator() +
                "3: Exit" + System.lineSeparator() + System.lineSeparator() + "Enter Here: " + System.lineSeparator() +
                System.lineSeparator() + "*CUSTOMER DASHBOARD*" +
                System.lineSeparator() + System.lineSeparator() + "1. View All Stores" +
                System.lineSeparator() + "2. View Stores Purchased From" + System.lineSeparator() +
                "3. Exit Customer Dashboard" +
                System.lineSeparator() + System.lineSeparator() + "Enter Option Here: " + System.lineSeparator() +
                "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                System.lineSeparator() +
                "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() +
                System.lineSeparator() +
                "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                System.lineSeparator() +
                "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" +
                System.lineSeparator() + System.lineSeparator() +
                "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() +
                System.lineSeparator() +
                "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() +
                System.lineSeparator() +
                "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                System.lineSeparator() + "You have 0 items in your cart" + System.lineSeparator() +
                System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                System.lineSeparator() + "Enter '<>' to de-sort" +
                System.lineSeparator() + "Enter 'q' to sort by availability" +
                System.lineSeparator() + "Enter '@' to view your purchase history" +
                System.lineSeparator() + "Enter '&' to search for something" +
                System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                System.lineSeparator() + "Enter 'db' to go to dashboard" +
                System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                "Thank you for using Markey! Have a nice day :)" + System.lineSeparator();


            // restoreInputAndOutput();
            receiveInput(input);
            Login.main(new String[0]);

            String output = getOutput();

            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("doesn't work ig?",
                expected.trim(), output.trim());


        }


    }

}
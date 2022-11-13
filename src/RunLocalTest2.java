import junit.framework.TestCase;
import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.*;
public class RunLocalTest2 {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
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
                fos = new FileOutputStream("SellerStatistics.txt",false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println("firstSeller;Arnav_Dunsmore_1_0.69_weriufheiu uwueth riut___Colten_Oh_1_0.69_si instructor___gg_Dunsmore_5_5.00_ekwbgew;firstCustomer");
            pw.println("ysenser;Wool Socks 5 pairs_YSense_5_10.99_Thick Wool socks winter warm for men;firstCustomer");
            pw.println("wantGorOwner;Metal Headband_WantGor_5_3.98_Metal headband for men and women;firstCustomer");
            pw.println("steveJ;USB-C Wall Charger_INCORIC_5_9.27_iPhone 14 Charger Block 20W PD Power Adaptor;firstCustomer");
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
            pw1.println("proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable buttons - Mechanical switches - rubber side grips - Mercury white");
            pw1.println("warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
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
                    "3" +System.lineSeparator() + "3" + System.lineSeparator() +
                    "?" + System.lineSeparator();
            //    restoreInputAndOutput();
            //   System.out.println(input);
            //  outputStart();
            String expected = "Welcome to the Marketplace!" + System.lineSeparator() +
                    "Do you have an existing account?" + System.lineSeparator() +
                    "1. Yes" + System.lineSeparator() +"2. No" + System.lineSeparator()
                    + System.lineSeparator() +"LOGIN WINDOW:" + System.lineSeparator() + "Enter Username:" + System.lineSeparator()+
                    "Enter Password:" + System.lineSeparator() +
                    "LOGIN SUCCESSFUL" + System.lineSeparator() +"Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
                    "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                    "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                    "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                    System.lineSeparator() + "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                    System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() + "Enter '*' to edit an item that's on sale" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() + "Enter 'db' to go to dashboard" +
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
                    System.lineSeparator()+ "2. View Product Sales List" + System.lineSeparator() + "3. Exit Customer Dashboard" +
                    System.lineSeparator() + System.lineSeparator() + "Enter Option Here: " + System.lineSeparator() +"Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
                    "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                    "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                    "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                    System.lineSeparator() + "Enter '@' to open a new Store" + System.lineSeparator() + "Enter '++' to list a new item for sale" +
                    System.lineSeparator() + "Enter '--' to remove an item from sale" + System.lineSeparator() + "Enter '*' to edit an item that's on sale" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" + System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Thank you for using Markey! Have a nice day :)"
                    + System.lineSeparator();


            // restoreInputAndOutput();
            receiveInput(input);
            Login.main(new String[0]);

            String output = getOutput();

            expected = expected.replaceAll("\r\n","\n");
            output = output.replaceAll("\r\n","\n");
            assertEquals("doesn't work ig?",
                    expected.trim(), output.trim());











        }





    }

}


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
public class RunLocalTest1 {
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
                fos = new FileOutputStream("Products.txt",false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fos);
            pw.println("wantGorOwner;Metal Headband_WantGor_100_3.98_Metal headband for men and women");
            pw.println("jbl;JBL Vibe 200TWs_JBL_30_29.95_Black wireless earbuds");
            pw.println("steveJ;USB-C Wall Charger_INCORIC_200_9.27_iPhone 14 Charger Block 20W PD Power Adaptor");
            pw.println("proGamer69;Razer DeathAdder Essential Gaming Mouse_Razer_50_17.85_Gaming mouse - 5 programmable buttons - Mechanical switches - rubber side grips - Mercury white");
            pw.println("warrenBuffet;Guide to investing in your 20s_Puffin Books_396_12.99_Book about long term investing");
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
                    "<" +System.lineSeparator() + ">" + System.lineSeparator() +
                    "<>" + System.lineSeparator() + "3" + System.lineSeparator() +
                    "5" + System.lineSeparator() + "#" + System.lineSeparator() + "$" + System.lineSeparator() +
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
                    System.lineSeparator() + "You have 0 items in your cart" + System.lineSeparator() +
                    System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                    System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                    System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                    System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                    System.lineSeparator() + "Enter '<>' to de-sort" +
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                    System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                    "Metal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() +
                    "Available in stock: 100" + System.lineSeparator() +
                    "Description: Metal headband for men and women" +
                    System.lineSeparator() + "Enter quantity for the selected item ('?' to exit)" +
                    System.lineSeparator() + "Added to your cart :)" + System.lineSeparator() +
                    "Markey" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
                    "7.\tgg\t\tSold by: Dunsmore\t\tPrice: 5.00" + System.lineSeparator() + System.lineSeparator() +
                    "8.\tArnav\t\tSold by: Dunsmore\t\tPrice: 0.69" + System.lineSeparator() + System.lineSeparator() +
                    "9.\tColten\t\tSold by: Oh\t\tPrice: 10.20" + System.lineSeparator() + System.lineSeparator() +
                    System.lineSeparator() + "You have 1 item in your cart" + System.lineSeparator() +
                    System.lineSeparator() + "Enter '2' to add item number 2 to your cart" +
                    System.lineSeparator() + System.lineSeparator() + "Enter '#' to view your cart" +
                    System.lineSeparator() + "Enter '<' to sort listings by cost (low to high)" +
                    System.lineSeparator() + "Enter '>' to sort listings by cost (high to low)" +
                    System.lineSeparator() + "Enter '<>' to de-sort" +
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
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
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
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
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                    System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + "Markey" +
                    System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
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
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                    System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                    "USB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() +
                    "Available in stock: 200" + System.lineSeparator() +
                    "Description: iPhone 14 Charger Block 20W PD Power Adaptor" + System.lineSeparator() +
                    "Enter quantity for the selected item ('?' to exit)" + System.lineSeparator() +
                    "Added to your cart :)" + System.lineSeparator() + "Markey" +
                    System.lineSeparator() + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
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
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                    System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator()  +
                    "Quantity: 5" + System.lineSeparator() + "2.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27"
                    + System.lineSeparator() + "Quantity: 5" + System.lineSeparator() + System.lineSeparator() +
                    "Total Price: 66.25" + System.lineSeparator() + System.lineSeparator() + "Enter '$' to checkout" +
                    System.lineSeparator() + "Enter 2 to remove item number 2" + System.lineSeparator() +
                    "Enter '?' to exit cart" + System.lineSeparator() + "Markey" + System.lineSeparator() +
                    System.lineSeparator() + System.lineSeparator() +
                    "1.\tMetal Headband\t\tSold by: WantGor\t\tPrice: 3.98" + System.lineSeparator() + System.lineSeparator() +
                    "2.\tJBL Vibe 200TWs\t\tSold by: JBL\t\tPrice: 29.95" + System.lineSeparator() + System.lineSeparator() +
                    "3.\tUSB-C Wall Charger\t\tSold by: INCORIC\t\tPrice: 9.27" + System.lineSeparator() + System.lineSeparator() +
                    "4.\tRazer DeathAdder Essential Gaming Mouse\t\tSold by: Razer\t\tPrice: 17.85" + System.lineSeparator() + System.lineSeparator() +
                    "5.\tGuide to investing in your 20s\t\tSold by: Puffin Books\t\tPrice: 12.99" + System.lineSeparator() + System.lineSeparator() +
                    "6.\tWool Socks 5 pairs\t\tSold by: YSense\t\tPrice: 10.99" + System.lineSeparator() + System.lineSeparator() +
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
                    System.lineSeparator() + "Enter '@' to view your purchase history" +
                    System.lineSeparator() + "Enter 'csv' to get a csv file with data" +
                    System.lineSeparator() + "Enter 'db' to go to dashboard" +
                    System.lineSeparator() + "Enter '?' to exit" + System.lineSeparator() +
                    "Thank you for using Markey! Have a nice day :)" + System.lineSeparator();



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

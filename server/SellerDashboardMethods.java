import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * SellerDashboardMethods
 * <p>
 * File handling for seller dashboard
 *
 * @author nsarathy
 * @version 12/11/2022
 */
public class SellerDashboardMethods {
    public static List<String> readSellerStats() throws IOException {
        List<String> fullList = new ArrayList<>();

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

    }
}

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Client class
public class Client {

    // driver code
    public static String main(String line) {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket(ServerDetails.SERVER_ADDRESS, 42426)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

            out.println(line);
            out.flush();

            String reply = in.readLine();

            in.close();
            out.close();

            return reply;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server disconnected", "Markey",
                JOptionPane.ERROR_MESSAGE);
            return "Error 404";
        }
    }

}

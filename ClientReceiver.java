import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ClientReceiver {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 2000;

        if (args.length == 1) {
            host = args[0];
            port = 2000;
        } else if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        try {
            try (Socket socket = new Socket(host, port)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                    receiveMessageFromChattServer(reader);
                } catch (IOException e) {
                    System.out.println("Error: Misslyckades med att ta emot meddelande från servern");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att ansluta till servern");
            e.printStackTrace();
        }
    }

    public static void receiveMessageFromChattServer(BufferedReader reader) {

        try {

            String message;

            while ((message = reader.readLine()) != null) {
                showMessageInChattWindow(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showMessageInChattWindow(String message) {
        System.out.println(message);
    }

}
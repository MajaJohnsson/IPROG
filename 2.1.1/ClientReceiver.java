import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ClientReceiver {

    //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 2000;

        try {
            if (args.length == 1) {
                host = args[0];
                port = 2000;
            } else if (args.length == 2) {
                host = args[0];
                port = Integer.parseInt(args[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            try (Socket socket = new Socket(host, port)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                    receiveMessageFromChattServer(reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveMessageFromChattServer(BufferedReader reader) {

        Thread thread = new Thread();

        try {
            thread.start();

            while (true) {
                String message = reader.readLine();
                if (message != null) {
                    showMessageInChattWindow(message);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showMessageInChattWindow(String message) {
        System.out.println(message);
    }

}
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class ClientSender {

    //PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
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

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Ansluten till servern lyckades: " + host + ":" + port);
            
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                sendMessageToChattServer(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att ansluta till servern");
            return;
        }

    }

    public static void sendMessageToChattServer(PrintWriter writer) {
        
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
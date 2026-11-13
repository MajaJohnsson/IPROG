import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientSender {

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

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Ansluten till servern lyckades: " + host + ":" + port);
            
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                sendMessageToChattServer(writer);
            } catch (IOException e) {
                System.out.println("Error: Misslyckades med att skicka meddelande till servern");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att ansluta till servern");
            return;
        }

    }

    public static void sendMessageToChattServer(PrintWriter writer) {
        //AI användes för att förstå hur man kan läsa in från tangentbordet med en bufferedreader
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String message = "";

        try {
            while ((message = keyboardReader.readLine()) != null) {
                try {
                    writer.println(message);
                } catch (Exception e) {
                    System.out.println("Error: Misslyckades med att skicka meddelande till servern");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att läsa från tangentbordet");
            e.printStackTrace();
        }

    }

}
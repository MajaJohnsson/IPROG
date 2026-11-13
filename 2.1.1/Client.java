import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

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
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                    handleMessage(writer, reader);
                } catch (IOException e) {
                    System.out.println("Error: Misslyckades med att ta emot meddelande från servern");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Error: Misslyckades med att skicka meddelande till servern");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att ansluta till servern");
            return;
        }

    }

    public static void handleMessage(PrintWriter writer, BufferedReader reader) {

        //tar emot meddelande från servern i en egen tråd
        Thread messageThread = new Thread(() -> {
            try {
                String receiveMessage;
                while ((receiveMessage = reader.readLine()) != null) {
                    showMessageInChattWindow(receiveMessage);
                }
            } catch (IOException e) {
                System.out.println("Error: Misslyckades med att ta emot meddelande från servern");
                e.printStackTrace();
            }
        });

        messageThread.start();

        //AI användes för att förstå hur man kan läsa in från tangentbordet med en bufferedreader
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        //skickar meddelande till servern
        try {
            String sendMessage;
            while ((sendMessage = keyboardReader.readLine()) != null) {
                writer.println(sendMessage);
            }

        } catch (IOException e) {
            System.out.println("Error: Misslyckades med att skicka meddelandet");
            e.printStackTrace();
        }

    }
    
    public static void showMessageInChattWindow(String message) {
        System.out.println(message);
    }

}
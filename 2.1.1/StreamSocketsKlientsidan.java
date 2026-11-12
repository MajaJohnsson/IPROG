import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class StreamSocketsKlientsidan {

    //PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 2000);

            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                sendMessageToChattServer(writer); //ändra
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                //String message = receiveMessageFromChattServer();
                startThread(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startThread(BufferedReader reader) {

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

    //public void startClient() {}

    //public void endClient() {
        //writer.close();
        //reader.close();
    //}

    public static void sendMessageToChattServer(PrintWriter writer) {
        //PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    
    }

    public static void receiveMessageFromChattServer() {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    
    }

    public static void showMessageInChattWindow(String message) {}

}
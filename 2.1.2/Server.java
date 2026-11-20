import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {
        int port = 2000;
        String host;
        ConcurrentHashMap<Socket, PrintWriter> connectedClients = new ConcurrentHashMap<>();

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
    
        //öppnar serversocket på port som användaren skrev
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            //laddar in host och skriver vart servern har startat
            try {
                host = InetAddress.getLocalHost().getHostName();
                System.out.println("Servern startar på: " + port + ", " + host);
                System.out.println("Antalet anslutna klienter: " + connectedClients.size());
            } catch (IOException e) {
                System.out.println("Fel vid inläsning av host namn");
            }

            //väntar på klienter och för varje som ansluter läggs det till en viruell tråd med socket
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    String ip = socket.getInetAddress().getHostAddress();

                    Thread.ofVirtual().start(() -> {

                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
                            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                                connectedClients.put(socket, writer);
                                System.out.println("Antalet anslutna klienter: " + connectedClients.size());
                                handleClient(socket, ip, reader, connectedClients);
                            } catch (IOException e) {
                                System.out.println("Fel vid start av printwriter");
                            }
                        } catch (IOException e) {
                            System.out.println("Fel vid start av bufferedreader");
                        }
                    });

                } catch (IOException e) {
                    System.out.println("Fel vid start av socket");
                }
            }

        } catch (IOException e) {
            System.out.println("Fel vid start av serversocket");
        }

    }

    //läser in texten som användaren har skrivit, och skickar det tillbaka till klienten
    public static void handleClient(Socket socket, String ip, BufferedReader reader, ConcurrentHashMap<Socket, PrintWriter> clients) {
        try (socket) {
            String line;
            while ((line = reader.readLine()) != null) {
                //skriver ut i servens terminal
                System.out.println("Mottaget: " + ip + " " + line);
                String message = ip + " " + line;
                //använder metod som skriver ut i alla klienters terminal
                printMessageToAll(message, clients);
            }
        } catch (IOException e) {
            System.out.println("Fel vid hantering av klient");
        } finally {
            removeClient(socket, clients);
        }
    }

    //skriver ut meddelandet i terminalen
    public static synchronized void printMessageToAll(String message, ConcurrentHashMap<Socket, PrintWriter> clients) {
        for (PrintWriter writer : clients.values()) {
            writer.println(message);
        }
    }

    //tar bort klienten från listan samt stänger socket
    public static synchronized void removeClient(Socket socket, ConcurrentHashMap<Socket, PrintWriter> clients) {
        try {
            clients.remove(socket);
            socket.close();
            System.out.println("Antalet anslutna klienter: " + clients.size());
        } catch (IOException e) {
            System.out.println("Fel vid stängning av socket");
        }
    }

}
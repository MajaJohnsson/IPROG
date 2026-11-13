
public class Server {
    public static void main(String[] args) {
        int port = 2000;

        if (args.length == 1) {
            port = 2000;
        }
        
        System.out.println("Servern startar på: " + port);

    }
}
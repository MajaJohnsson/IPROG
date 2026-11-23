import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

//AI användes för att förstå hur Ritprogram, Draw och Paper skulle sättas ihop

public class Ritprogram {
    private String myPort;
    private String toAddress;
    private String toPort;
    private Paper paper;

    public Ritprogram (Paper paper) {
        this.paper = paper;
    }

    public static void main (String [] args) {
        Draw draw = new Draw();
        Paper paper = draw.getPaper();

        Ritprogram program = new Ritprogram(paper);
        
        paper.setRitprogram(program);

        program.myPort = args[0];
        program.toAddress = args[1];
        program.toPort = args[2];

        program.recieveDatagram();

    }

    //gör om punkten till en sträng
    public String transformPoint (Point p) {
        String message = p.x + " " + p.y;

        return message;
    }

    //gör om meddelandet till punkter och ritar upp dessa på ritytan
    public void drawPoint (String message) {
        String[] xy = message.split(" ");
        Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));

        //skickar till paper för att rita upp
        paper.recievePoint(p);
    
    }

    //skickar punkterna till mottagaren
    public void sendDatagram (Point p) {
        try (DatagramSocket sendSocket = new DatagramSocket()) {
            
            String message = transformPoint(p);

            byte[] data = message.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            sendSocket.send(new DatagramPacket(data, data.length, InetAddress.getByName(toAddress), Integer.parseInt(toPort)));

        } catch (SocketException e) {
            System.out.println("Felmeddelande: Kunde inte starta socket - skicka");
        } catch (IOException e) {
            System.out.println("Felmeddelande: Kunde inte skicka punkterna");
        } 
    }

    //tar emot paketet av punkter
    public void recieveDatagram () {
        Thread thread = new Thread(() -> {

            try (DatagramSocket socketRecieve = new DatagramSocket(Integer.parseInt(myPort))) {

                while (true) {
                    byte[] buf = new byte[8192];
                    DatagramPacket p = new DatagramPacket(buf, buf.length);
                    socketRecieve.receive(p);
                    String message = new String(p.getData(), 0, p.getLength(), java.nio.charset.StandardCharsets.UTF_8);
                    drawPoint(message);
                }

            } catch (SocketException e) {
                System.out.println("Felmeddelande: Kunde inte starta socket - ta emot");
            } catch (IOException e) {
                System.out.println("Felmeddelande: Kunde inte ta emot punkterna");
            }
        });

        thread.start();

    }

}


/** Ett enkelt, lokalt ritprogram att utgå från. Ingen nätverksdel – bara ritning med musen. */
class Draw extends JFrame {
    private final Paper paper;

    public static void main(String[] args) {
        new Draw();
    }

    public Draw() {
        super("Ritprogram");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        paper = new Paper();
        add(paper, BorderLayout.CENTER);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Paper getPaper() {
        return paper;
    }
}

class Paper extends JPanel {

    private final Set<Point> punkter = new HashSet<>();

    private Ritprogram program;

    public void setRitprogram(Ritprogram program) {
        this.program = program;
    }

    public Paper() {
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) { 
                addPoint(me.getPoint()); 
                sendPoint(me.getPoint());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) { 
                addPoint(me.getPoint()); 
                sendPoint(me.getPoint());
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for (Point p : punkter) g.fillOval(p.x, p.y, 2, 2);
    }

    private void addPoint(Point p) {
        punkter.add(p);
        repaint();
    }

    public void sendPoint(Point p) {
        program.sendDatagram(p);
    }

    public void recievePoint(Point p) {
        javax.swing.SwingUtilities.invokeLater(() -> addPoint(p));
    }
}
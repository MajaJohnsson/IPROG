
public class EpostReceiver {

    private String epostserver;
    private String portnumber;
    private String username;
    private String password;

    public static void main (String[] args) {
        epostserver = args[0];
        portnumber = args[1];
        username = args[2];
        
    }

    //ansluta till servern krypterat med TLS

    //visa lista över senaste meddelanden, med avsändare, ämne och datum

    //visa innehållet i ett meddelande som användaren väljer

    //visa ett begripligt meddelande om inloggning misslyckas eller om servern inte svarar

    //visa meddelande med html - regex för att ta bort taggarna och få fram texten

    public void getMessagesInfo () {
        Properties properties = new Properties();
        properties.put("mail.imaps.connectiontimeout", "10000");
        properties.put("mail.imaps.timeout", "10000");

        Session session = Session.getInstance(properties);
        try (Store store = session.getStore("imaps")) {
            store.connect(epostserver, portnumber, username, password);
            try (Folder inbox = store.getFolder("INBOX")) {
                inbox.open(Folder.READ_ONLY);
                int amount = inbox.getMessageCount();
                Message[] message = inbox.getMessages(Math.max(1, amount - 9), amount);
                for (Message m : message) {
                    InternetAddress from = (InternetAddress) m.getFrom()[0];
                    String text = from.toUnicodeString();
                    System.out.println("Meddelande: " + message[m].getIndex());
                    System.out.println("Datum: " + m.getSentDate());
                    System.out.println("Från: " + m.getFrom()[0]);
                    System.out.println("Ämne: " + m.getSubject());
                }
            }
        }
    }

    public static String getMessage (Part part) {
        try {
            if (part.isMimeType("text/plain")) {
                return (String) part.getContent();
            } else if (part.getContent() instanceof Multipart parts) {
                for (int i = 0; i < parts.getCount(); i++) {
                    String text = getMessage(parts.getBodyPart(i));
                    if (!text.isEmpty()) {
                        return text;
                    }
                }
            }
            return "";
        } catch (MessagingException me) {
            System.out.println("Felmeddelande: ");
        } catch (IOException e) {
            System.out.println("Felmeddelande: ");
        }

    }

}
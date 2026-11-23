
public class Epost {
    private String from;
    private String to;
    private String subject;
    private String message;

    private String epostserver;
    private String portnumber;
    private String username;
    private String password;

    public static void main (String[] args) {
        server = args[0];
        port = args[1];
        username = args[2];
        to = args[3];
        subject = args[4];
        message = args[5];
    }

    //hur gör man med lösenord?
    //Lösenordet anges inte på kommandoraden, 
    // där det skulle synas och sparas i kommandohistoriken, 
    // utan programmet frågar efter det och läser det utan att det 
    // syns på skärmen

    System.console().readPassword();

    public void sendMessage () {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", epostserver);
            properties.put("mail.smtp.port", portnumber);
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.connectiontimeout", "10000");
            properties.put("mail.smtp.timeout", "10000");
            properties.put("mail.smpt.writetimeout", "10000");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message. setSubject(amne, "UTF-8");
            message.setText(text, "UTF.8");
            Transport.send(message);
        } catch (AddressException ad) {
            System.out.Println("Meddelandet kunde inte skickas; felaktig adress");
        } catch (AuthenticationFailedException afe) {
            System.out.Println("Meddelandet kunde inte skickas; felaktigt användarnamn eller lösenord");
        } catch (SendFailedException sfe) {
            System.out.Println("Meddelandet kunde inte skickas; adress mottogs inte av server " + sfe.getNextException());
        } catch (MessagingException me) {
            System.out.Println("Meddelandet kunde inte skickas; " + me.getCause());
        }

    }
}
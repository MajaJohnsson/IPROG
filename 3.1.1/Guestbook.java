//uppgift 3.1.1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Guestbook {
    private String user;
    private String password;
    private String url = "jdbc:mariadb://localhost:3306";

    public static void main (String[] args) {

        Guestbook program = new Guestbook();

        System.out.println("Detta kan göras: \n 1. Lägg till ett inlägg \n 2. Visa alla inlägg \n Ange 1 eller två för vad du vill göra!");

        try (BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String choice = keyboardReader.readLine().trim();

            if (choice == "1") {
                program.addPost();
            } else if (choice == "2") {
                program.showPosts();
            }

        } catch (IOException e) {
            System.out.println("Kunde inte läsa in svaret");
        }

    }

    //läser in info om inlägg från användaren
    public void addPost () {
        //AI användes för att förstå hur man kan läsa in från tangentbordet med en bufferedreader
        try (BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
             System.out.println("Skriv in namn: ");

             String name = keyboardReader.readLine();

             if (name != null) {
                System.out.println("Skriv in e-post: ");

                String epost = keyboardReader.readLine();

                if (epost != null) {
                    System.out.println("Skriv in hemsida: ");

                    String homepage = keyboardReader.readLine();

                    if (homepage != null) {
                        System.out.println("Skriv in kommentar: ");

                        String comment = keyboardReader.readLine();

                        if (comment != null) {
                            System.out.println("Vill du spara inlägget? ja/nej");

                            String anwser = keyboardReader.readLine().toLowerCase().trim();

                            name = checkTextForHTML(name);
                            epost = checkTextForHTML(epost);
                            homepage = checkTextForHTML(homepage);
                            comment = checkTextForHTML(comment);

                            if (anwser == "ja") {
                                savePost(name, epost, homepage, comment);
                            } else {
                                return;
                            }

                        }

                    }
                }
             }
        } catch (IOException e) {
            System.out.println("Kunde inte läsa in svaret");
        }
    }

    //spara inläggen i databasen
    public void savePost (String name, String epost, String page, String comment) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO guestbook (namn, epost, hemsida, kommentar) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, epost);
                ps.setString(3, page);
                ps.setString(4, comment);
                ps.executeUpdate();
            }
        } catch (SQLException sql) {
            System.out.println("Kunde inte spara inlägg");
        }
    }

    //kontrollera för HTML
    public String checkTextForHTML (String text) {
        Pattern html = Pattern.compile("<[a-zA-Z/!]");
        if (html.matcher(text).find()) {
            text = "censur";
        }

        return text;

    }

    //visar inlägg som finns i databasen
    public void showPosts () {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT namn, kommentar FROM guestbook ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("namn") + ": " + rs.getString("kommentar"));
                }
            } catch (SQLException sql) {
                System.out.println("Kunde inte visa inlägg");
            }
        } catch (SQLException sql) {
            System.out.println("Kunde inte spara inlägg");
        }
    }

    //skapa tabeller i databas i terminalen
}
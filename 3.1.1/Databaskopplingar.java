//uppgift 3.1.1
import java.sql;

public class Guestbook {
    private String name;
    private String epost;
    private String page;
    private String comment;

    public static void main (String[] args) {

    }

    //skapa databasserver

    //spara inläggen i databasen
    public void savePost () {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO guestbook (namn, epost, hemsida, kommentar) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, namn);
                ps.setString(2, epost);
                ps.setString(3, hemsida);
                ps.setString(4, kommentar);
                ps.executeUpdate();
            }
        } catch (SQLException sql) {
            System.out.println("Serven gick inte att nå");
        }
    }

    public String checkPostForHTML () {
        Pattern html = Pattern.compile("<[a-zA-Z/!]");
        if (html.matcher(text).find()) {
            text = "censur";
        }

        return text;

    }

    public void errorMessage () {

    }

    public void showPosts () {
        try (PreparedStatement ps = conn.prepareStatement("SELECT namn, kommentar FROM guestbook ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("namn") + ": " + rs.getString("kommentae"));
            }
        }
    }
}
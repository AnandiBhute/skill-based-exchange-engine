import java.sql.*;

public class NotificationDAO {

    public void addNotification(
            int userId,
            String message) {

        String sql = """
                INSERT INTO notifications
                (user_id,message,is_read,created_at)
                VALUES(?,?,?,?)
                """;

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.setString(2, message);
            p.setInt(3, 0);
            p.setString(
                    4,
                    java.time.LocalDateTime
                            .now()
                            .toString()
            );

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Notification error: "
                            + e.getMessage()
            );
        }
    }
}
import java.sql.*;

public class SubscriptionDAO {

    public void addSubscription(
            int userId,
            String plan,
            int points) {

        String sql = """
                INSERT INTO subscriptions
                (user_id,plan_name,points_added,
                 start_date,status)
                VALUES(?,?,?,?,?)
                """;

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.setString(2, plan);
            p.setInt(3, points);
            p.setString(
                    4,
                    java.time.LocalDateTime
                            .now()
                            .toString()
            );
            p.setString(5, "ACTIVE");

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Subscription error: "
                            + e.getMessage()
            );
        }
    }
}
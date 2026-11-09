import java.sql.*;

public class PointTransactionDAO {

    public void addTransaction(
            int userId,
            int bondId,
            int points,
            String description) {

        String sql = """
                INSERT INTO point_transactions
                (user_id,bond_id,transaction_type,
                 points,description,created_at)
                VALUES(?,?,?,?,?,?)
                """;

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.setInt(2, bondId);
            p.setString(
                    3,
                    points < 0 ? "DEBIT" : "CREDIT"
            );
            p.setInt(4, points);
            p.setString(5, description);
            p.setString(
                    6,
                    java.time.LocalDateTime
                            .now()
                            .toString()
            );

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Transaction error: "
                            + e.getMessage()
            );
        }
    }
}
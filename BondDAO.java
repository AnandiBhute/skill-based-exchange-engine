import java.sql.*;

public class BondDAO {

    public int createBond(
            int user1,
            int user2,
            String skill1,
            String skill2,
            int points) {

        String sql = """
                INSERT INTO bonds
                (user1_id,user2_id,user1_skill,user2_skill,
                 points_per_user,status,created_at)
                VALUES(?,?,?,?,?,?,?)
                """;

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            p.setInt(1, user1);
            p.setInt(2, user2);
            p.setString(3, skill1);
            p.setString(4, skill2);
            p.setInt(5, points);
            p.setString(6, "ACTIVE");
            p.setString(
                    7,
                    java.time.LocalDateTime
                            .now()
                            .toString()
            );

            p.executeUpdate();

            ResultSet rs =
                    p.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Bond error: " + e.getMessage()
            );
        }

        return -1;
    }

    public void updateStatus(
            int bondId,
            String status) {

        String sql =
                "UPDATE bonds SET status=? " +
                "WHERE bond_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setString(1, status);
            p.setInt(2, bondId);

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Bond error: " + e.getMessage()
            );
        }
    }
}
import java.sql.*;

public class BondHistoryDAO {

    public void addHistory(Bond bond) {

        String sql = """
                INSERT INTO bond_history
                (bond_id,user1_id,user2_id,user1_skill,
                 user2_skill,points_charged,status,created_at)
                VALUES(?,?,?,?,?,?,?,?)
                """;

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, bond.bondId);
            p.setInt(2, bond.user1.userId);
            p.setInt(3, bond.user2.userId);
            p.setString(4, bond.user1Skill);
            p.setString(5, bond.user2Skill);
            p.setInt(6, bond.pointsPerUser);
            p.setString(7, bond.status);
            p.setString(
                    8,
                    java.time.LocalDateTime
                            .now()
                            .toString()
            );

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "History error: " + e.getMessage()
            );
        }
    }
}
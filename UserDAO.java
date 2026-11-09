import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public int addUser(
            String name,
            String email) {

        String sql =
                "INSERT INTO users(name,email) VALUES(?,?)";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, name);
            p.setString(2, email);

            p.executeUpdate();

            ResultSet rs =
                    p.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "User error: " + e.getMessage()
            );
        }

        return -1;
    }

    public User getUser(int id) {

        String sql =
                "SELECT * FROM users WHERE user_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, id);

            ResultSet rs =
                    p.executeQuery();

            if (rs.next()) {

                User user =
                        new User(
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getInt("free_points"),
                                rs.getInt("is_available") == 1
                        );

                loadSkills(user);
                loadInterests(user);

                return user;
            }

        } catch (SQLException e) {

            System.out.println(
                    "User error: " + e.getMessage()
            );
        }

        return null;
    }

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql =
                "SELECT user_id FROM users";

        try (Connection c =
                     DatabaseConnection.getConnection();
             Statement s =
                     c.createStatement();
             ResultSet rs =
                     s.executeQuery(sql)) {

            while (rs.next()) {

                User user =
                        getUser(
                                rs.getInt("user_id")
                        );

                if (user != null) {
                    users.add(user);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "User error: " + e.getMessage()
            );
        }

        return users;
    }

    private void loadSkills(User user)
            throws SQLException {

        String sql =
                "SELECT skill_name,skill_type " +
                "FROM user_skills WHERE user_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, user.userId);

            ResultSet rs =
                    p.executeQuery();

            while (rs.next()) {

                String skill =
                        rs.getString("skill_name");

                String type =
                        rs.getString("skill_type");

                if (type.equals("TEACH")) {
                    user.teachingSkills.add(skill);
                } else {
                    user.learningSkills.add(skill);
                }
            }
        }
    }

    private void loadInterests(User user)
            throws SQLException {

        String sql =
                "SELECT interest_name " +
                "FROM user_interests WHERE user_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, user.userId);

            ResultSet rs =
                    p.executeQuery();

            while (rs.next()) {

                user.interests.add(
                        rs.getString("interest_name")
                );
            }
        }
    }

    public void addSkill(
            int userId,
            String skill,
            String type) {

        String sql =
                "INSERT INTO user_skills" +
                "(user_id,skill_name,skill_type)" +
                " VALUES(?,?,?)";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.setString(2, skill);
            p.setString(3, type);

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Skill error: " + e.getMessage()
            );
        }
    }

    
public void addInterest(int userId, String interest) {
    if (interest == null || interest.trim().isEmpty()) {
        System.out.println("Interest cannot be empty.");
        return;
    }

    String cleanedInterest = interest.trim();

    String checkSql =
            "SELECT interest_name FROM user_interests WHERE user_id = ?";

    String insertSql =
            "INSERT INTO user_interests (user_id, interest_name) VALUES (?, ?)";

    try (Connection c = DatabaseConnection.getConnection()) {

        // Check duplicates without considering capitalization.
        try (PreparedStatement p = c.prepareStatement(checkSql)) {
            p.setInt(1, userId);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    String existing = rs.getString("interest_name");

                    if (existing != null &&
                            existing.trim().equalsIgnoreCase(cleanedInterest)) {
                        System.out.println("Interest already exists.");
                        return;
                    }
                }
            }
        }

        try (PreparedStatement p = c.prepareStatement(insertSql)) {
            p.setInt(1, userId);
            p.setString(2, cleanedInterest);

            p.executeUpdate();
            System.out.println("Interest added successfully.");
        }

    } catch (SQLException e) {
        System.out.println("Interest error: " + e.getMessage());
    }
}


    /**
     * Restores a user's skill/interest profile from a Memento snapshot.
     * Other user fields are left unchanged.
     */
    
public boolean restoreProfile(UserMemento snapshot) {
    if (snapshot == null || snapshot.getUserId() <= 0) {
        return false;
    }

    try (Connection c = DatabaseConnection.getConnection()) {
        c.setAutoCommit(false);

        try {
            // 1. Verify that the user exists before deleting anything.
            try (PreparedStatement p = c.prepareStatement(
                    "SELECT 1 FROM users WHERE user_id = ?")) {
                p.setInt(1, snapshot.getUserId());

                try (ResultSet rs = p.executeQuery()) {
                    if (!rs.next()) {
                        c.rollback();
                        return false;
                    }
                }
            }

            // 2. Remove the current skills.
            try (PreparedStatement p = c.prepareStatement(
                    "DELETE FROM user_skills WHERE user_id = ?")) {
                p.setInt(1, snapshot.getUserId());
                p.executeUpdate();
            }

            // 3. Remove the current interests.
            try (PreparedStatement p = c.prepareStatement(
                    "DELETE FROM user_interests WHERE user_id = ?")) {
                p.setInt(1, snapshot.getUserId());
                p.executeUpdate();
            }

            // 4. Restore teaching and learning skills.
            try (PreparedStatement p = c.prepareStatement(
                    "INSERT INTO user_skills"
                    + "(user_id, skill_name, skill_type) VALUES (?, ?, ?)")) {

                for (String skill : snapshot.getTeachingSkills()) {
                    p.setInt(1, snapshot.getUserId());
                    p.setString(2, skill);
                    p.setString(3, "TEACH");
                    p.addBatch();
                }

                for (String skill : snapshot.getLearningSkills()) {
                    p.setInt(1, snapshot.getUserId());
                    p.setString(2, skill);
                    p.setString(3, "LEARN");
                    p.addBatch();
                }

                p.executeBatch();
            }

            // 5. Restore interests.
            try (PreparedStatement p = c.prepareStatement(
                    "INSERT INTO user_interests"
                    + "(user_id, interest_name) VALUES (?, ?)")) {

                for (String interest : snapshot.getInterests()) {
                    p.setInt(1, snapshot.getUserId());
                    p.setString(2, interest);
                    p.addBatch();
                }

                p.executeBatch();
            }

            // 6. Commit only when every operation succeeds.
            c.commit();
            return true;

        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            System.out.println(
                    "Profile restore error: " + e.getMessage());
            return false;
        }

    } catch (SQLException e) {
        System.out.println(
                "Profile restore error: " + e.getMessage());
        return false;
    }
}


    public void updatePoints(
            int userId,
            int points) {

        String sql =
                "UPDATE users " +
                "SET free_points = free_points + ? " +
                "WHERE user_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, points);
            p.setInt(2, userId);

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Point error: " + e.getMessage()
            );
        }
    }

    public void updateAvailability(
            int userId,
            boolean available) {

        String sql =
                "UPDATE users SET is_available=? " +
                "WHERE user_id=?";

        try (Connection c =
                     DatabaseConnection.getConnection();
             PreparedStatement p =
                     c.prepareStatement(sql)) {

            p.setInt(1, available ? 1 : 0);
            p.setInt(2, userId);

            p.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Availability error: "
                            + e.getMessage()
            );
        }
    }
}
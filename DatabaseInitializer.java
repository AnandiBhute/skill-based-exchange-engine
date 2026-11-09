import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String users = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    free_points INTEGER DEFAULT 1000,
                    is_available INTEGER DEFAULT 1
                )
                """;

        String skills = """
                CREATE TABLE IF NOT EXISTS user_skills (
                    user_skill_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    skill_name TEXT NOT NULL,
                    skill_type TEXT NOT NULL,
                    FOREIGN KEY(user_id) REFERENCES users(user_id)
                )
                """;

        String interests = """
                CREATE TABLE IF NOT EXISTS user_interests (
                    interest_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    interest_name TEXT NOT NULL,
                    FOREIGN KEY(user_id) REFERENCES users(user_id)
                )
                """;

        String bonds = """
                CREATE TABLE IF NOT EXISTS bonds (
                    bond_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user1_id INTEGER NOT NULL,
                    user2_id INTEGER NOT NULL,
                    user1_skill TEXT NOT NULL,
                    user2_skill TEXT NOT NULL,
                    points_per_user INTEGER NOT NULL,
                    status TEXT NOT NULL,
                    created_at TEXT NOT NULL
                )
                """;

        String history = """
                CREATE TABLE IF NOT EXISTS bond_history (
                    history_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    bond_id INTEGER NOT NULL,
                    user1_id INTEGER NOT NULL,
                    user2_id INTEGER NOT NULL,
                    user1_skill TEXT,
                    user2_skill TEXT,
                    points_charged INTEGER,
                    status TEXT,
                    created_at TEXT
                )
                """;

        String notifications = """
                CREATE TABLE IF NOT EXISTS notifications (
                    notification_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    message TEXT NOT NULL,
                    is_read INTEGER DEFAULT 0,
                    created_at TEXT
                )
                """;

        String transactions = """
                CREATE TABLE IF NOT EXISTS point_transactions (
                    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    bond_id INTEGER,
                    transaction_type TEXT NOT NULL,
                    points INTEGER NOT NULL,
                    description TEXT,
                    created_at TEXT
                )
                """;

        String subscriptions = """
                CREATE TABLE IF NOT EXISTS subscriptions (
                    subscription_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    plan_name TEXT NOT NULL,
                    points_added INTEGER NOT NULL,
                    start_date TEXT,
                    expiry_date TEXT,
                    status TEXT
                )
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             Statement statement =
                     connection.createStatement()) {

            statement.execute(users);
            statement.execute(skills);
            statement.execute(interests);
            statement.execute(bonds);
            statement.execute(history);
            statement.execute(notifications);
            statement.execute(transactions);
            statement.execute(subscriptions);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            System.out.println(
                    "Database initialization failed: "
                            + e.getMessage());
        }
    }
}
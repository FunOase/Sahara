package net.funoase.sahara.common.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class PlayerTable {

    private final static String createTable = "CREATE TABLE IF NOT EXISTS players (" +
            "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
            "language VARCHAR(4) NOT NULL DEFAULT 'en_us'" +
            ");";
    private final static String updatePlayer = "INSERT INTO players (uuid, language) VALUES (?, ?)";
    private static Connection connection;

    public static void initialize(Connection connection) {
        PlayerTable.connection = connection;
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    public static void insertPlayer(UUID uuid) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid) VALUES (?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    public static void updatePlayer(UUID uuid, String language) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET language = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, language);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
    }

    public static Optional<String> getLanguage(UUID uuid) {
        String reason = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT language FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reason = resultSet.getString("language");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table", e);
        }
        return Optional.ofNullable(reason);
    }
}

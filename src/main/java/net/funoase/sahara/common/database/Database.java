package net.funoase.sahara.common.database;

import net.funoase.sahara.common.database.tables.PlayerTable;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Database {

    private final Logger logger;
    private Connection connection;

    public Database(Logger logger) {
        this.logger = logger;
        PlayerTable.initialize(this);
    }

    public void connect(String host, int port, String database, String username, String password) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database,
                    username,
                    password
            );
        } catch (SQLException e) {
            logger.throwing("Database", "connect", e);
        }
    }

    @Nullable
    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (Exception ignored) {}
    }
}

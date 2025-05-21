package main.javalang;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AzureSQLConnector {
    private final String dbName;
    private final String username;
    private final String password;
    private final String serverName;

    private static final Logger log = Logger.getLogger(AzureSQLConnector.class.getName());

    public AzureSQLConnector() {
        log.info("Getting Environment Variables...");
        this.serverName = System.getenv("DB_SERVER");
        this.dbName = System.getenv("DB_NAME");
        this.username = System.getenv("DB_LOGIN");
        this.password = System.getenv("DB_PASS");
    }

    public Connection connect() {
        log.info("Attempting to connect to server...");
        String url = String.format(
            "jdbc:sqlserver://%s.database.windows.net:1433;database=%s;encrypt=true;trustServerCertificate=false;loginTimeout=30;",
            serverName,
            dbName
        );

        try {
            
            Connection conn = DriverManager.getConnection(url, username, password);
            log.info("Connected successfully to Azure SQL DB.");
            return conn;
        } catch (SQLException e) {
            log.log(Level.WARNING, "Connection failed: {0}", e.getMessage());
            return null;
        }
    }
}

package main.javalang.classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AzureSQLConnector {
    private final String dbName;
    private final String username;
    private final String password;
    private final String serverName;

    private static final Logger log = Logger.getLogger(AzureSQLConnector.class.getName());
    private final DotEnv env = new DotEnv(".env");

    public AzureSQLConnector() {
        log.info("Getting Environment Variables...");
        this.serverName = env.Get("DB_SERVER");
        this.dbName = env.Get("DB_NAME");
        this.username = env.Get("DB_LOGIN");
        this.password = env.Get("DB_PASS");
    }

    public void ensureDatabaseExists() {
    String masterUrl = String.format(
        "jdbc:sqlserver://%s.database.windows.net:1433;database=master;encrypt=true;trustServerCertificate=false;loginTimeout=30;",
        serverName
    );

    try (Connection conn = DriverManager.getConnection(masterUrl, username, password);
         Statement stmt = conn.createStatement()) {

        String checkDbQuery = String.format(
            "SELECT COUNT(*) FROM sys.databases WHERE name = '%s'", dbName
        );

        var rs = stmt.executeQuery(checkDbQuery);
        rs.next();
        int count = rs.getInt(1);

        if (count == 0) {
            log.info("Database does not exist. Creating...");
            String createDbQuery = String.format("CREATE DATABASE [%s]", dbName);
            stmt.executeUpdate(createDbQuery);
            log.info("Database created.");
        } else {
            log.info("Database already exists.");
        }

    } catch (SQLException e) {
        log.log(Level.WARNING, "Error checking/creating database: {0}", e.getMessage());
    }
}


    public Connection connect() {
        log.info("Attempting to connect to server...");
        String url = String.format(
            "jdbc:sqlserver://%s.database.windows.net:1433;database=%s;encrypt=true;trustServerCertificate=false;loginTimeout=30;",
            serverName,
            dbName
        );

        try {
            
            Connection conn = DriverManager.getConnection(url, this.username, this.password);
            log.info("Connected successfully to Azure SQL DB.");
            return conn;
        } catch (SQLException e) {
            log.log(Level.WARNING, "Connection failed: {0}", e.getMessage());
            return null;
        }
    }


}

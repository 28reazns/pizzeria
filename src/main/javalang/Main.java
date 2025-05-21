package main.javalang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import main.javalang.classes.AzureSQLConnector;

public class Main {
    public static void runSqlScript(Connection conn, String path){
        try {
            String sql = Files.readString(Paths.get(path));
            java.sql.Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("SQL script executed.");
        } catch (IOException | SQLException e) {
            System.err.println("Error executing SQL script: "+e.getMessage());
        }
    }
    public static void main(String[] args) {
        AzureSQLConnector connector = new AzureSQLConnector();
        connector.ensureDatabaseExists();
        try(Connection conn = connector.connect()){
            runSqlScript(conn,"src/main/sql/schema.sql");
            runSqlScript(conn,"src/main/sql/views.sql");
        }catch(Exception e){
            System.err.println("Failed to connect: "+e.getMessage());
        }
    }
}

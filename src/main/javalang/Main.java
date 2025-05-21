package main.javalang;

import main.javalang.classes.AzureSQLConnector;

public class Main {
    public static void main(String[] args) {
        AzureSQLConnector connector = new AzureSQLConnector();
        connector.connect();
    }
}

package ru.lokincompany.lokengine.network.mysql;

import ru.lokincompany.lokengine.tools.Logger;

import java.sql.*;

public class MySQLConnect {

    protected Connection connection;
    protected Statement statement;
    protected String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public MySQLConnect(String address, int port, String databaseName, String username, String password, String timezone) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + databaseName + "?serverTimezone=" + timezone, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.warning("Fail connect to MySQL database", "LokEngine_MySQLConnect");
            Logger.printException(e);
        }
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            Logger.warning("Fail close MySQL database connect", "LokEngine_MySQLConnect");
            Logger.printException(e);
        }
    }

    public MySQLConnect(String address, String databaseName, String username, String password, String timezone) {
        this(address, 3306, databaseName, username, password, timezone);
    }

    public MySQLConnect(String address, String databaseName, String username, String password) {
        this(address, 3306, databaseName, username, password, "UTC");
    }

    public boolean connected() {
        return statement != null;
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            Logger.warning("Fail execute query", "LokEngine_MySQLConnect");
            Logger.printException(e);
        }
        return null;
    }
}

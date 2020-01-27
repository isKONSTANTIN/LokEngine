package ru.lokincompany.lokengine.network.mysql;

import ru.lokincompany.lokengine.tools.Logger;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnect {
    protected Connection connection;
    protected Statement statement;
    protected String databaseName;

    public MySQLConnect(String address, int port, String databaseName, String username, String password, String timezone) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + databaseName + "?serverTimezone=" + timezone, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.warning("Fail connect to MySQL database", "LokEngine_MySQLConnect");
            Logger.printException(e);
        }
    }

    public MySQLConnect(String address, String databaseName, String username, String password, String timezone) {
        this(address, 3306, databaseName, username, password, timezone);
    }

    public MySQLConnect(String address, String databaseName, String username, String password) {
        this(address, 3306, databaseName, username, password, "UTC");
    }

    public String getDatabaseName() {
        return databaseName;
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

    public ArrayList getAll(ResultSet resultSet, int columnsSize) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            try {
                if (!resultSet.next()) break;

                for (int i = 1; i <= columnsSize; i++) {
                    arrayList.add(resultSet.getObject(i));
                }

            } catch (SQLException e) {
                Logger.warning("Fail add data to array from ResultSet", "LokEngine_MySQLTools");
                Logger.printException(e);
            }

        }
        return arrayList;
    }

    public void setDataToCell(String tableName, String data, String column, String lineNameFilter, String filterValue) {
        executeQuery("UPDATE `" + tableName + "` SET `" + column + "` = " + data + " WHERE `" + lineNameFilter + "` = " + filterValue);
    }

    public void setDataToCell(String tableName, String data, String column, int lineId) {
        setDataToCell(tableName, data, column, "id", String.valueOf(lineId));
    }

    public Object getDataFromCell(String tableName, String column, int lineId) {
        return getDataFromCell(tableName, column, "id", String.valueOf(lineId));
    }

    public Object getDataFromCell(String tableName, String column, String lineNameFilter, String filterValue) {
        ResultSet resultSet = executeQuery("SELECT `" + column + "` FROM `" + tableName + "` WHERE `" + lineNameFilter + "` = " + filterValue);

        try {
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            Logger.warning("Fail get data from cell", "LokEngine_MySQLTools");
            Logger.printException(e);
        }
        return null;
    }

}

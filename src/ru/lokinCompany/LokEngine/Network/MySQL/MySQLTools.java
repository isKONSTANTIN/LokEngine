package ru.lokinCompany.LokEngine.Network.MySQL;

import ru.lokinCompany.LokEngine.Tools.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLTools {

    public static ArrayList getAll(ResultSet resultSet, int columnsSize) {
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

    public static void setDataToCell(MySQLConnect database, String tableName, String data, String column, String lineNameFilter, String line) {
        database.executeQuery("UPDATE `" + tableName + "` SET `" + column + "` = " + data + " WHERE `" + lineNameFilter + "` = " + line);
    }

    public static void setDataToCell(MySQLConnect database, String tableName, String data, String column, int lineId) {
        setDataToCell(database, tableName, data, column, "id", String.valueOf(lineId));
    }

    public static Object getDataFromCell(MySQLConnect database, String tableName, String column, int lineId) {
        return getDataFromCell(database, tableName, column, "id", String.valueOf(lineId));
    }

    public static Object getDataFromCell(MySQLConnect database, String tableName, String column, String lineNameFilter, String line) {
        ResultSet resultSet = database.executeQuery("SELECT `" + column + "` FROM `" + tableName + "` WHERE `" + lineNameFilter + "` = " + line);

        try {
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            Logger.warning("Fail get data from cell", "LokEngine_MySQLTools");
            Logger.printException(e);
        }
        return null;
    }

}

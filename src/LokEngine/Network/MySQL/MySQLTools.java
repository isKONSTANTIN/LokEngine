package LokEngine.Network.MySQL;

import LokEngine.Tools.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLTools {

    public static ArrayList getAll(ResultSet resultSet, int columnsSize){
        ArrayList arrayList = new ArrayList();
        while (true){
            try {
                if (!resultSet.next()) break;

                for (int i = 1; i <= columnsSize; i++){
                    arrayList.add(resultSet.getObject(i));
                }

            } catch (SQLException e) {
                Logger.warning("Fail add data to array from ResultSet", "LokEngine_MySQLTools");
                Logger.printException(e);
            }

        }
        return arrayList;
    }

    public static Object getDataFromCell(MySQLConnect database, String tableName, String column, int lineId){
        ResultSet resultSet = database.executeQuery("SELECT `" + column + "` FROM `" + tableName + "` WHERE `id` = " + lineId);

        try {
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            Logger.warning("Fail get data from cell", "LokEngine_MySQLTools");
            Logger.printException(e);
        }
        return null;
    }

    public static Object getDataFromCell(MySQLConnect database, String tableName, String column, String lineNameFilter, String lineString){
        ResultSet resultSet = database.executeQuery("SELECT `" + column + "` FROM `" + tableName + "` WHERE `" + lineNameFilter + "` = '" + lineString + "'");

        try {
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            Logger.warning("Fail get data from cell", "LokEngine_MySQLTools");
            Logger.printException(e);
        }
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.utils;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

/**
 * Clase util para convertir objetos a json
 *
 * @author Leonardo Cardona : leocardonapiedrahita@gmail.com
 * @author Maicol Camargo : mfcamargoh@gmail.com
 */
public class JsonUtil {

    /**
     * Método que permite convertir un DefaultTableModel a un objeto Json
     *
     * @param rs <code> DefaultTableModel </code>
     * @return <code> JSONArray </code>
     */
    public static JSONArray convertResultSetToJSON(java.sql.ResultSet rs) {

        JSONArray json = new JSONArray();
        try {
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 1; i < numColumns + 1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        obj.put(column_name, rs.getArray(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        obj.put(column_name, rs.getInt(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        obj.put(column_name, rs.getBoolean(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        obj.put(column_name, rs.getBlob(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        obj.put(column_name, rs.getDouble(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        obj.put(column_name, rs.getFloat(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                        obj.put(column_name, rs.getInt(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                        obj.put(column_name, rs.getNString(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                        obj.put(column_name, rs.getString(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                        obj.put(column_name, rs.getInt(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                        obj.put(column_name, rs.getInt(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                        obj.put(column_name, rs.getDate(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                        obj.put(column_name, rs.getTimestamp(column_name));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.OTHER) {
                        Object o = rs.getObject(column_name);
                        if (o instanceof PGobject) {
                            if (((PGobject) o).getType().equals("json")) {
                                String jsonString = ((PGobject) o).getValue();
                                if (jsonString.startsWith("[")) {
                                    JSONArray j = new JSONArray(jsonString);
                                    obj.put(column_name, j);
                                } else if (jsonString.startsWith("{")) {
                                    JSONObject j = new JSONObject(jsonString);
                                    obj.put(column_name, j);
                                }
                            }
                        } else {
                            obj.put(column_name, rs.getObject(column_name));
                        }
                    } else if (rsmd.getColumnType(i) == java.sql.Types.JAVA_OBJECT) {
                        obj.put(column_name, rs.getObject(column_name));
                    } else {
                        obj.put(column_name, rs.getObject(column_name));
                    }
                }//end foreach
                json.put(obj);
            }//end while
        } catch (SQLException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import ut.webservices.utils.JsonUtil;

/**
 *
 * @author felipe
 */
public class Postgres implements Driver {

    private Connection c = null;
    private Statement stmt = null;

    @Override
    public JSONArray query(Properties p, HttpServletRequest request) {
        String sql = p.getProperty("query");
        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String paramName = parameters.nextElement();
            String paramValue = request.getParameter(paramName);
            sql = sql.replaceAll("\\@\\{" + paramName + "\\}", paramValue);
        }
        JSONArray response = new JSONArray();
        try {
            this.stmt = this.c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            response = JsonUtil.convertResultSetToJSON(rs);
            stmt.close();
            c.commit();
            return response;
        } catch (SQLException ex) {
            Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @Override
    public boolean connect(Properties p) {
        this.disconnect();
        try {
            Class.forName(p.getProperty("ClassForName"));
            c = DriverManager.getConnection(
                    p.getProperty("connection.string"),
                    p.getProperty("connection.user"),
                    p.getProperty("connection.password"));
            c.setAutoCommit(false);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
            this.disconnect();
        }
        return false;
    }

    @Override
    public void disconnect() {
        if (this.c != null) {
            try {
                this.c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.c = null;
    }

}

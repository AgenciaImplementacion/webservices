/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.driver;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;

/**
 *
 * @author felipe
 */
public interface Driver {

    boolean connect(Properties p);

    JSONArray query(Properties p, HttpServletRequest request);

    void disconnect();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.service;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ut.webservices.driver.Driver;
import ut.webservices.driver.Postgres;
import ut.webservices.utils.ConfigUtil;

/**
 *
 * @author felipe
 */
@RestController
public class WSController {

    @RequestMapping(value = "ws/{name}", method = RequestMethod.POST, produces = "application/json")
    public String post(HttpServletRequest request, @PathVariable("name") String name) {
        Properties conf = ConfigUtil.getConfig(name);
        Driver d = new Postgres();
        d.connect(conf);
        JSONArray l = d.query(conf, request);
        d.disconnect();
        return l.toString();
    }

    @RequestMapping(value = "ws", method = RequestMethod.GET)
    public String get() {
        return "Web Services";
    }

}

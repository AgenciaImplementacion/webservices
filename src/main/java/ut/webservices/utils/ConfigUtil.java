/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 *
 * @author felipe
 */
public class ConfigUtil {

    private final static Logger LOGGER = Logger.getLogger(ConfigUtil.class.getName());

    public static Properties readConfigFile(InputStream configFile) {
        try {
            Properties prop = new Properties();
            prop.load(configFile);
            return prop;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: (ConfigUtil.readConfigFile.IOException) " + e.getMessage(), e);
        }
        return null;
    }

    public static Properties getConfig(String name) {
        PathMatchingResourcePatternResolver a = new PathMatchingResourcePatternResolver();
        try {
            Resource r = a.getResource("services/" + name + ".properties");
            return ConfigUtil.readConfigFile(r.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: (ConfigUtil.getConfig.IOException) " + e.getMessage(), e);
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;
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
    
    public static Properties readConfigFile(InputStreamReader configFile) {
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
        	InputStream stream = r.getInputStream();
        	
        	// https://stackoverflow.com/questions/11497902/how-to-check-the-charset-of-string-in-java
        	CharsetDetector detector = new CharsetDetector();
            detector.setText(stream);
            
        	CharsetMatch charset = detector.detect();
        	String charsetName = charset.getName();
        	
        	// https://stackoverflow.com/questions/30755014/reading-from-property-file-containing-utf-8-character
        	InputStreamReader isr = new InputStreamReader(stream, Charset.forName(charsetName));
    		return ConfigUtil.readConfigFile(isr);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: (ConfigUtil.getConfig.IOException) " + e.getMessage(), e);
        }
        return null;
    }
}

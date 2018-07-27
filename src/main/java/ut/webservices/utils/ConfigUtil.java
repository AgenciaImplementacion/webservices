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

import org.mozilla.universalchardet.UniversalDetector;
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
        	
        	// https://github.com/albfernandez/juniversalchardet/blob/master/README.md
        	InputStream fis = r.getInputStream();
        	UniversalDetector detector = new UniversalDetector();        	
        	byte[] buf = new byte[4096];        	
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
              detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
        	String charsetName = detector.getDetectedCharset();
        	//System.out.println("charsetName: " + charsetName);
        	
        	// https://stackoverflow.com/questions/30755014/reading-from-property-file-containing-utf-8-character
        	InputStreamReader isr = new InputStreamReader(stream, Charset.forName(charsetName));
    		return ConfigUtil.readConfigFile(isr);
    		//return ConfigUtil.readConfigFile(stream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error: (ConfigUtil.getConfig.IOException) " + e.getMessage(), e);
        }
        return null;
    }
}

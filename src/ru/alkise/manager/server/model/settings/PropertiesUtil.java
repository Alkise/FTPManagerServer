/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author alkise
 */
public class PropertiesUtil {

    private Properties properties;
    private File propertiesFile;

    public PropertiesUtil(String propertiesFilename) {
        propertiesFile = new File(propertiesFilename);
    }

    public void save() {
        try (OutputStream os = new FileOutputStream(propertiesFile)) {
            properties.storeToXML(os, "FTP File Manager Settings");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public void load() {
        try (InputStream is = new FileInputStream(propertiesFile)) {
            properties.loadFromXML(is);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public String getProperty(String propertyName) {
         return properties.getProperty(propertyName);
    }
}

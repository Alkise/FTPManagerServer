/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.settings;

import com.sun.org.glassfish.external.probe.provider.annotations.Probe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

/**
 *
 * @author alkise
 */
public class ManagerSettingsRemote extends UnicastRemoteObject implements ManagerSettingsRemoteIntf {
    private Properties properties;
    private File settingsFile;

    public ManagerSettingsRemote() throws RemoteException {
        this("settings.xml");
    }
    
    public ManagerSettingsRemote(String filename) throws RemoteException {
        this.settingsFile = new File(filename);
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        properties = new Properties();
    }

    @Override
    public void save() throws RemoteException {
        try (OutputStream os = new FileOutputStream(settingsFile)) {
            properties.storeToXML(os, "FTP File Manager Settings");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void load() throws RemoteException {
        try (InputStream is = new FileInputStream(settingsFile)) {
            properties.loadFromXML(is);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void setProperty(String key, String value) throws RemoteException {
        properties.setProperty(key, value);
    }
   
    @Override
    public String getProperty(String key) throws RemoteException {
        return properties.getProperty(key);
    }
    
}

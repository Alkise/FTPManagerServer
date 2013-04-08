/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.settings;

import java.util.Properties;

/**
 *
 * @author alkise
 */
public class ManagerSettingsRemote implements ManagerSettingsRemoteIntf {
    private Properties properties;
    
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
    
}

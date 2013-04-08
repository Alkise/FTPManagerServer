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
public interface ManagerSettingsRemoteIntf {
    void setProperties(Properties properties);
    Properties getProperties();
}

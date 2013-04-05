/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 *
 * @author alkise
 */
public interface ManagerModelRemoteIntf extends Remote {

    void setProperties(Properties properties) throws RemoteException;

    Properties getProperties() throws RemoteException;

    void copyDifferentItemsToRightList() throws RemoteException;

    void deleteDifferentItemsFromRightList() throws RemoteException;

    String[] getLeftListItems() throws RemoteException;

    String[] getRightListItems() throws RemoteException;
}

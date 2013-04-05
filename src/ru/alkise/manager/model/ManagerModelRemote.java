/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import ru.alkise.manager.model.itemlist.ItemListIntf;
import ru.alkise.manager.model.itemlist.locallist.FileItemList;

/**
 *
 * @author alkise
 */
public class ManagerModelRemote extends UnicastRemoteObject implements ManagerModelRemoteIntf {
    private ItemListIntf fromList;
    private ItemListIntf toList;
    
    public ManagerModelRemote(ItemListIntf fromList, ItemListIntf toList) throws RemoteException {
        this.fromList = fromList;
        this.toList = toList;
    }
    
    @Override
    public void setProperties(Properties properties) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Properties getProperties() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void copyDifferentItemsToRightList() throws RemoteException {
        try {
        toList.addItems(fromList.getWorkingDirectory(), fromList.getDifferentItems(toList)); 
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteDifferentItemsFromRightList() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getLeftListItems() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getRightListItems() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        try {
            ItemListIntf fromList = new FileItemList("/home/alkise/Pictures");
            ItemListIntf toList = new FileItemList("/home/alkise/Documents");
            ManagerModelRemoteIntf model = new ManagerModelRemote(fromList, toList);
            Naming.rebind("ManagerModel", model);
            System.out.println("Server started");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}

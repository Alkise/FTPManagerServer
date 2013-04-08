/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ru.alkise.manager.server.model.itemlist.ItemListIntf;

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
    public void copyDifferentItemsToRightList() throws RemoteException {
        try {
            toList.addItems(fromList.getWorkingDirectory(), fromList.getDifferentItems(toList));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteDifferentItemsFromRightList() throws RemoteException {
        toList.removeItems(fromList.getDifferentItems(toList));
    }

    @Override
    public String[] getLeftListItems() throws RemoteException {
        String[] itemArray = new String[fromList.getItems().size()];
        return fromList.getItems().toArray(itemArray);
    }

    @Override
    public String[] getRightListItems() throws RemoteException {
        String[] itemArray = new String[toList.getItems().size()];
        return toList.getItems().toArray(itemArray);
    }
}

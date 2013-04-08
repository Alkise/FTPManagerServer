/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import ru.alkise.manager.server.model.ManagerModelRemote;
import ru.alkise.manager.server.model.ManagerModelRemoteIntf;
import ru.alkise.manager.server.model.itemlist.ItemListIntf;
import ru.alkise.manager.server.model.itemlist.fileitemlist.FileItemList;
import ru.alkise.manager.server.model.settings.ManagerSettingsRemote;
import ru.alkise.manager.server.model.settings.ManagerSettingsRemoteIntf;

/**
 *
 * @author alkise
 */
public class FTPManagerServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ManagerSettingsRemoteIntf settings = new ManagerSettingsRemote();
            ItemListIntf fromList = new FileItemList(settings.getProperties().getProperty("localDirectory"));
            ItemListIntf toList = new FileItemList("/home/alkise/Documents");
            ManagerModelRemoteIntf model = new ManagerModelRemote(fromList, toList);
            Naming.rebind("ManagerModel", model);
            System.out.println("Server started");
        } catch (RemoteException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

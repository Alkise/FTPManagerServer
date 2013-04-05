/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import ru.alkise.manager.model.ManagerModelRemote;
import ru.alkise.manager.model.ManagerModelRemoteIntf;
import ru.alkise.manager.model.itemlist.ItemListIntf;
import ru.alkise.manager.model.itemlist.locallist.FileItemList;

/**
 *
 * @author alkise
 */
public class FTPManagerServer {
    public static void main(String[] args) {
        try {
            ItemListIntf fromList = new FileItemList("/home/alkise/Pictures");
            ItemListIntf toList = new FileItemList("/home/alkise/Documents");
            ManagerModelRemoteIntf model = new ManagerModelRemote(fromList, toList);
            Naming.rebind("ManagerModel", model);
            System.out.println("Server started");
        } catch (RemoteException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

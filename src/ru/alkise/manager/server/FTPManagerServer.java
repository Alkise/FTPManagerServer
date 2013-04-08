/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import ru.alkise.manager.server.model.ManagerModelRemote;
import ru.alkise.manager.server.model.ManagerModelRemoteIntf;
import ru.alkise.manager.server.model.encryptor.AESEncryptor;
import ru.alkise.manager.server.model.itemlist.ItemListIntf;
import ru.alkise.manager.server.model.itemlist.fileitemlist.FileItemList;
import ru.alkise.manager.server.model.itemlist.ftpitemlist.FTPItemList;
import ru.alkise.manager.server.model.settings.ManagerSettingsRemote;

/**
 *
 * @author alkise
 */
public class FTPManagerServer {
    public static void main(String[] args) {
        try {
            AESEncryptor encryptor = new AESEncryptor("TheSupaSecretKey");
            ManagerSettingsRemote settings = new ManagerSettingsRemote();
//            settings.setProperty("localDirectory", "/home/alkise/Pictures");
//            settings.setProperty("hostname", "127.0.0.1");
//            settings.setProperty("username", "Anonymous");
//            settings.setProperty("password", encryptor.encrypt("secretpassword"));
//            settings.setProperty("workingDirectory", "");
//            settings.save();
            settings.load();
            ItemListIntf fromList = new FileItemList(settings.getProperty("localDirectory"));
            ItemListIntf toList = new FTPItemList(settings.getProperty("hostname"), 
                    settings.getProperty("username"), 
                    encryptor.encrypt(settings.getProperty("password")), 
                    settings.getProperty("workingDirectory"));
            ManagerModelRemoteIntf model = new ManagerModelRemote(fromList, toList);
            
            LocateRegistry.createRegistry(1099);
            Naming.rebind("ManagerModel", model);
            Naming.rebind("ManagerSettings", settings);
            System.out.println("Server started");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

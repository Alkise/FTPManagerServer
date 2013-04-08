/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.itemlist.ftpitemlist;

import java.io.IOException;
import java.util.Collection;
import ru.alkise.manager.server.model.itemlist.AbstractItemList;

/**
 *
 * @author alkise
 */
public class FTPItemList extends AbstractItemList {

    private FTPConnector fTPConnector;

    public FTPItemList(String hostname, String username, String password, String workingDirectory) throws IOException {
        super(workingDirectory);
        fTPConnector = new FTPConnector(hostname, username, password, workingDirectory);
        fTPConnector.connect();
        this.workingDirectory = workingDirectory;
        for (String item : fTPConnector.getNames()) {
            if (item.endsWith(".jpg") || item.endsWith(".jpeg")) {
                items.add(item);
            }
        }
    }

    @Override
    public void addItem(String fromDirectory, String item) throws IOException {
        fTPConnector.uploadFile(fromDirectory, item);
        items.add(item);
    }

    @Override
    public void addItems(String fromDirectory, Collection<String> items) throws IOException {
        fTPConnector.uploadFiles(fromDirectory, items);
        items.addAll(items);
    }

    @Override
    public void removeItem(String item) {
        try {
            fTPConnector.deleteFile(item);
            items.remove(item);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void removeItems(Collection<String> items) {
        try {
            fTPConnector.deleteFiles(items);
            items.removeAll(items);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public void disconnect() {
        try {
        fTPConnector.disconnect();
        } catch (IOException ioe) {
            System.out.println("Exception: " + ioe.getMessage());
        }
    }
}

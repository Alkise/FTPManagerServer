/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.itemlist;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author alkise
 */
public interface ItemListIntf {

    String getWorkingDirectory();

    void addItem(String fromDirectory, String item) throws IOException;

    void addItems(String fromDirectory, Collection<String> items) throws IOException;

    void removeItem(String item);

    void removeItems(Collection<String> items);

    Collection<String> getItems();

    Collection<String> getDifferentItems(Collection<String> items);
    
    Collection<String> getDifferentItems(ItemListIntf itemList);
}

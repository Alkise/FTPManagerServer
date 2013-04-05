/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model.itemlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author alkise
 */
public abstract class AbstractItemList implements ItemListIntf {
    protected Collection<String> items;
    protected String workingDirectory;

    public AbstractItemList() {
        this(null);
    }
    
    public AbstractItemList(String workingDirectory) {
        this(new ArrayList<String>(), workingDirectory);
    }
    
    public AbstractItemList(Collection<String> items, String workingDirectory) {
        this.items = items;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public abstract void addItem(String fromDirectory, String item) throws IOException;

    @Override
    public void addItems(String fromDirectory, Collection<String> items) throws IOException {
        for (String item : items) {
            addItem(fromDirectory, item);
        }
    }

    @Override
    public abstract void removeItem(String item);

    @Override
    public void removeItems(Collection<String> items) {
        for (String item: items) {
            removeItem(item);
        }
    }

    @Override
    public Collection<String> getItems() {
        return items;
    }

    @Override
    public Collection<String> getDifferentItems(Collection<String> items) {
        Collection<String> different = new HashSet<>(this.items);
        different.removeAll(items);
        return different;
    }
    
    @Override
    public Collection<String> getDifferentItems(ItemListIntf itemList) {
        return getDifferentItems(itemList.getItems());
    }
}

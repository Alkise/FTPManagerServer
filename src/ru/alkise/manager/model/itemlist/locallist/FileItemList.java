/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model.itemlist.locallist;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import ru.alkise.manager.model.itemlist.AbstractItemList;

/**
 *
 * @author alkise
 */
public class FileItemList extends AbstractItemList {
    private File directory;

    public FileItemList(String workingDirectory) {
        super(workingDirectory);
        directory = new File(workingDirectory);
        if (directory.exists() && directory.isDirectory()) {
            items.addAll(Arrays.asList(directory.list()));
        }
    }
    
    @Override
    public void addItem(String fromDirectory, String item) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(String item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

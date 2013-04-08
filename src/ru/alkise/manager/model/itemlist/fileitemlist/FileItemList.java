/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model.itemlist.fileitemlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public void addItem(final String fromDirectory, final String item) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream is = new FileInputStream(fromDirectory + File.separator + item);
                        OutputStream os = new FileOutputStream(workingDirectory + File.separator + item)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    items.add(item);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }

    @Override
    public void removeItem(final String item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File removedFile = new File(workingDirectory + File.separator + item);
                if (removedFile.exists()) {
                    removedFile.delete();
                }
                items.remove(item);
            }
        }).start();
    }
}

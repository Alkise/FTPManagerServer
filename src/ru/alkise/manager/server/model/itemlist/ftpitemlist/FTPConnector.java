/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.server.model.itemlist.ftpitemlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author alkise
 */
public class FTPConnector {

    private FTPClient client;
    private String hostname;
    private String username;
    private String password;
    private String workingDirectory;

    public FTPConnector(String hostname, String username, String password) {
        this(hostname, username, password, null);
    }

    public FTPConnector(String hostname, String username, String password, String workingDirectory) {
        client = new FTPClient();
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.workingDirectory = workingDirectory;
    }

    private void connect() throws IOException {
        client.connect(hostname);
        login();
    }

    private void login() throws IOException {
        client.login(username, password);
        if (workingDirectory != null) {
            client.changeWorkingDirectory(workingDirectory);
        }
    }

    private void logout() throws IOException {
        client.logout();
    }

    private void disconnect() throws IOException {
        logout();
        client.disconnect();
    }

    public void setWorkingDirectory(String workingDirectory) throws IOException {
        this.workingDirectory = workingDirectory;
        client.changeWorkingDirectory(workingDirectory);
    }

    public void deleteFile(String filename) throws IOException {
        connect();
        client.deleteFile(filename);
        disconnect();
    }
    
    public void deleteFiles(Collection<String> filenames) throws IOException {
        connect();
        for (String filename : filenames) {
            client.deleteFile(filename);
        }
        disconnect();
    }

    public Collection<String> getNames() throws IOException {
        Collection<String> names;
        connect();
        names = Arrays.asList(client.listNames());
        disconnect();
        return names;
    }

    public void uploadFile(final String localDirectory, final String filename) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream is = new FileInputStream(localDirectory + File.separator + filename);) {
                    connect();
                    client.storeFile(filename, is);
                    disconnect();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }).start();
    }

    public void uploadFiles(final String localDirectory, final Collection<String> filenames) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connect();
                    InputStream is;
                    for (String filename : filenames) {
                        is = new FileInputStream(localDirectory + File.separator + filename);
                        client.storeFile(filename, is);
                        is.close();
                    }
                    disconnect();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }).start();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alkise.manager.model.itemlist.ftpitemlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
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

    public void connect() throws IOException {
        client.connect(hostname);
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

    public void disconnect() throws IOException {
        client.disconnect();
    }

    public void setWorkingDirectory(String workingDirectory) throws IOException {
        this.workingDirectory = workingDirectory;
    }

    public void deleteFile(String filename) throws IOException {
        login();
        client.deleteFile(filename);
        logout();
    }
    
    public void deleteFiles(Collection<String> filenames) throws IOException {
        login();
        for (String filename : filenames) {
            client.deleteFile(filename);
        }
        logout();
    }

    public Collection<String> getNames() throws IOException {
        Collection<String> names;
        login();
        names = Arrays.asList(client.listNames());
        logout();
        return names;
    }

    public void uploadFile(final String localDirectory, final String filename) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream is = new FileInputStream(localDirectory + File.separator + filename);) {
                    login();
                    client.storeFile(filename, is);
                    logout();
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
                    login();
                    InputStream is;
                    for (String filename : filenames) {
                        is = new FileInputStream(localDirectory + File.separator + filename);
                        client.storeFile(filename, is);
                        is.close();
                    }
                    logout();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }).start();
    }
}

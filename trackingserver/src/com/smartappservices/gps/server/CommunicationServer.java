package com.smartappservices.gps.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import snaq.db.DBPoolDataSource;

public class CommunicationServer implements Runnable {

    private static DBPoolDataSource dBPoolDataSource = null;
    private final Socket connection;
    private static final int port = 4014;//Listening Port No.

    public static void main(String[] args) throws SQLException {
     dBPoolDataSource = DBConnectionPoolManager.getDataSource();
        Socket listeningSocket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(port);// Open socket
            System.out.println("Mobile Server Initialized");

            while (true) {
                listeningSocket = serverSocket.accept();
                Thread newVehicleThread = new Thread(new CommunicationServer(listeningSocket));
                newVehicleThread.setName("New Vehicle");
                newVehicleThread.start();

            }
        } catch (IOException ioException) {
            System.out.println(" Exception main" + ioException.getMessage());
        }
    }

    CommunicationServer(Socket socket) {
        this.connection = socket;//Store serverSocketConnection
    }

    @Override
     public void run() {
        int character, inCount;
        Thread thisThread = Thread.currentThread();
        try {
            StringBuilder deviceData = new StringBuilder();
            while (true) {
                deviceData.delete(0, deviceData.length());
                //System.out.println("Current thread Id :: " + thisThread.getId() + "# Current Thread Name::" + thisThread.getName());
                if (thisThread.isAlive()) {// Check this thread is alive or not

                    InputStreamReader inputStreamReader = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));

                    if (inputStreamReader.ready()) {
                        if ((character = inputStreamReader.read()) == -1) {//Check that this is not end of stream
                            System.out.println("## End of Stream ");
                            break;
                        } else {
                            deviceData.append((char) character);// Add this character in data 
                            inCount = 1;
                            while (inCount == 1) {
                                if (inputStreamReader.ready()) {
                                    
                                    deviceData.append((char) inputStreamReader.read());
                                } else {
                                    inCount = -1;
                                }
                            }//while
                        }
                    }
                    //System.out.println("Socket: " + connection + "count: " + thisThread.getId() + " " + deviceData.toString());
                   
                    if ((deviceData.length()) >= 92) {
                        System.out.println("Data from Vehicle Socket: " + connection + "count: " + thisThread.getId() + " " + deviceData.toString());
                        Parser parse = new Parser();
                         parse.parseData(deviceData.toString(),dBPoolDataSource);
                    } else {
                       // System.out.println("Socket: " + connection + "count: " + thisThread.getId() + " Data Length is less than 92 " + deviceData.toString());
                    }

                } else {
                    System.out.println("Thread is not alive. ");
                }
                Thread.sleep(100);
            }//while
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
            System.out.println(exception.getMessage());

        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("I am Done. ");
        }
    }//synchronized run method end here
}

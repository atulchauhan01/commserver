/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smartappservices.gps.server;

import com.smartappservices.gps.database.DataSourceProvider;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;

public class ServerExecutor {
    
    private static final DataSource dBPoolDataSource = DataSourceProvider.getDataSource();
    private final Socket connection;
    private static final int port = 4014;//Listening Port No.

    public ServerExecutor(Socket connection) {
        this.connection = connection;
    }

    
    public static void main(String[] args) throws SQLException {
    
        Socket clientSocket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(port);// Open socket
            System.out.println("Mobile Server Initialized");

            while (true) {
                clientSocket = serverSocket.accept();
                Thread newVehicleThread = new Thread(new CommunicationServer(clientSocket, dBPoolDataSource));
                newVehicleThread.setName("New Vehicle");
                newVehicleThread.start();

            }
        } catch (IOException ioException) {
            System.out.println(" Exception main" + ioException.getMessage());
        }
    }
}

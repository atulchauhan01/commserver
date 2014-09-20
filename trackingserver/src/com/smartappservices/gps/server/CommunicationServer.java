package com.smartappservices.gps.server;

import com.smartappservices.gps.parser.Parser;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;

public class CommunicationServer implements Runnable {

    private final DataSource dBPoolDataSource;
    private final Socket clientSocketRef;
    

    CommunicationServer(Socket clientSocket, DataSource dBPoolDataSource) {
        this.clientSocketRef = clientSocket;//Store serverSocketConnection
        this.dBPoolDataSource = dBPoolDataSource;
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

                    InputStreamReader inputStreamReader = new InputStreamReader(new BufferedInputStream(clientSocketRef.getInputStream()));

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
                            
                            
                            inputStreamReader = null;
                        }
                    }
                    //System.out.println("Socket: " + connection + "count: " + thisThread.getId() + " " + deviceData.toString());
                   
                    if ((deviceData.length()) >= 92) {
                        System.out.println("Data from Vehicle Socket: " + clientSocketRef + "count: " + thisThread.getId() + " " + deviceData.toString());
                         Parser parse = new Parser(dBPoolDataSource);
                        try{
                         parse.parseData(deviceData.toString());
                        }catch(Exception exception){
                            System.out.println("Message Corrupted");
                        }
                    } else {
                       // System.out.println("Socket: " + connection + "count: " + thisThread.getId() + " Data Length is less than 92 " + deviceData.toString());
                    }
                    
                } else {
                    System.out.println("Thread is not alive. ");
                }
              Thread.sleep(1000); 
            }//while
              this.clientSocketRef.close();
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

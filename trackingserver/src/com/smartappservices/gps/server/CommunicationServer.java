package com.smartappservices.gps.server;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.smartappservices.gps.server.Parser;




public class CommunicationServer implements Runnable {

    private Socket connection;
    private int ID;
    public static Map receiveMaster = new HashMap();       //Store Thread Id and last time to receive data
     
    int character = 0;//Variable for store character
    String ICId = "";//Variable for store IC
    int byteCount = 1;//Variable for byte counter
    boolean crcStatus = false;//Variable for store CRC status
    boolean isData = false;//Variable for store CRC status
    char c, crcByte = 0;//Variable for store CRC byte
    int helloByte = 0;//Variable for hello byte

    public static void main(String[] args) throws SQLException {

        int port = 4014;//Listening Port No.
        int count = 0;
        Socket conn = null;
        try {
            ServerSocket serverSocket = new ServerSocket(port);// Open socket
            System.out.println("Mobile Server Initialized");
            Runnable monitorThread = new Runnable() {

                public void run() {
                    try {
                        while (true) {
                        System.out.print("Inside Monitor thread");
                            Thread.sleep(400);
                        }
                        
                    } catch (Exception exception) {
                        System.out.println("Exception- Monitor Thread: " + exception.getMessage());
                    }
                }
            };
            
            Thread receiveObserver = new Thread(monitorThread);// Create monitor thread
            receiveObserver.setName("Recieve Observer");
            receiveObserver.start();
          
            while (true) {
                if (conn == null) {
                    
                    Socket serverSocketConnection = serverSocket.accept();//Connection accept
                    conn = serverSocketConnection;
                    
                    Runnable runnable = new CommunicationServer(conn, count);
                    
                    Thread thread = new Thread(runnable);// Create new serverSocketConnection or thread
                    conn = null;
                    thread.start();
                    count++;
                }else{
                  System.out.println(" Conn not null" );        
                }
            }
        } catch (Exception e) {
            System.out.println(" Exception main" + e.getMessage());
        }
    }//main ends here

    CommunicationServer(Socket socket, int count) {
        
        this.connection = socket;//Store serverSocketConnection
        this.ID = count;//Store id

    }

    
    synchronized public void run() { // Modify on 07-08-2008

        Thread thisThread = Thread.currentThread();
        try {
            while (true) {
                System.out.println("Current thread: " + thisThread.getId());
                System.out.println("Current Name: " + thisThread.getName());
                

                if (thisThread.isAlive()) {// Check this thread is alive or not

                    BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader isr = new InputStreamReader(is);
                    character = 0;//Variable for store character

                    ICId = "";//Variable for store IC
                    byteCount = 1;//Variable for byte counter
                    crcStatus = false;//Variable for store CRC status
                    isData = false;//Variable for store CRC status
                    char c = 0;
                    crcByte = 0;//Variable for store CRC byte
                    helloByte = 0;//Variable for hello byte

                    StringBuffer data = new StringBuffer();
                    /**
                     * ******** Start to Send Data*********
                     */
                    if (isr.ready()) {
                        if ((character = isr.read()) == -1) {//Check that this is not end of stream

                            break;
                        } else {
                            c = (char) character;//Read data and store in c

                            data.append(c);// Add this character in data 

                            helloByte = character;
                            crcByte = (char) (crcByte ^ c);// Calculate CRC

                            byteCount++;
                            receiveMaster.put(thisThread.getId(), Calendar.getInstance().getTimeInMillis());// Store in running table

                        }
                    }
                    try {
                        int inCount = 1;
                        while (inCount == 1) {
                            if (isr.ready() == true) {//Check that stream is not empty

                                character = isr.read();
                                c = (char) character;//Read data and store in c

                                data.append(c);// Add this character in data 

                                if (byteCount == 2 && helloByte == 165) {//Check that first byte is hello byte and byte count is second

                                    ICId = String.valueOf((char) character);// This read char is IC Id

                                }
                                /**
                                 * *Verify Packet**
                                 */
                                crcByte = (char) (crcByte ^ c);// Calculate CRC

                                if (crcByte == 0) {//verify tha packet 

                                    crcStatus = true;
                                }
                                /**
                                 * *End Verify Packet**
                                 */
                                byteCount++;
                                isData = true;
                            } else {
                                inCount = -1;
                            }
                        }//while

                    } //try
                    catch (Exception e) {
                        System.out.println("Read" + e.getMessage());
                    }
                    System.out.println("Socket: " + connection + "count: " + thisThread.getId() + " " + data);
                    String data1 = new String(data);
                    Date date1 = new Date();//date

                    String date2 = date1.toString();
                    if ((data1.length()) >= 92) {
                        //for seperating data
                        Parser parse = new Parser();
                        parse.parseData(data1);

                    }

                }//if

            }//while
            Thread.sleep(400);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            String AGVServerlog = "*************************Stop Server*******************************";
        }

    }//synchronized run method end here
}

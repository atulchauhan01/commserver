package com.smartappservices.gps.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client43 extends Thread {

    //String of device tk106
    //private static String data ="(020000000043BP05000020000000034111214A2606.6947N09145.9674E000.0121212155.3200000000L02AE630C)";
    //String of device SkyTrack
    private static String data = "(119996096676BP05000009996096676111107A3020.8757N07650.2781E000.0090120245.310001000000L0000001F)";
    /**
     * 
     */
    private static Socket socket;
    /**
     * 
     */
    private static PrintWriter printWriter;
    /**
     * 
     */
    private static DataOutputStream dataOutputStream;
    /**
     * 
     */
    private static BufferedOutputStream bufferedOutputStream;
    /**
     * 
     */
    private static InetAddress inetAddress = null;

    /**
     * 
     */
    // private static  byte[] ipAddr = new byte[] {(byte)8,(byte)22,(byte)201,(byte)39};
    static void publishData() {

        try {

            inetAddress = InetAddress.getByName("68.67.69.9");
            //inetAddress = InetAddress.getLocalHost();

            System.err.println(inetAddress);            
            socket = new Socket(inetAddress, 4014);
            System.err.println("socket" + socket);
            
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 
     */
    public void run() {

        try {
            for (int i = 0; i < 500; i++) {

                System.out.println("Thread executed!" + data);
                dataOutputStream.writeBytes(data);

                Thread.sleep(10000L);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    public static void main(String[] args) {

        Client43 client = new Client43();
        Client43.publishData();
        Thread clientThread = new Client43();
        clientThread.start();

    }
}

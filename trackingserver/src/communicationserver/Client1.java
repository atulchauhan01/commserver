/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communicationserver;

/**
 *
 * @author technolife
 */
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client1 extends Thread {

    /**
     * 
     */
    private static String data = "(020000000034BP05000020000000034110930A2606.6947N09145.9674E000.0131116155.3200000000L02AE630C)";
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

            inetAddress = InetAddress.getLocalHost();
            System.err.println(inetAddress);
            System.err.println("socket  1-->>" + socket);
            socket = new Socket(inetAddress, 1888);
            System.err.println("socket" + socket);
            //socket = new Socket(inetAddress, 4014);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.err.println("*************");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 
     */
    public void run() {

        try {
            for (int i = 0; i < 5; i++) {

                System.out.println("Thread executed!" + data);
                dataOutputStream.writeBytes(data);

                Thread.sleep(1000L);

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

        //Client1 client = new Client1();
        Client1.publishData();
        Thread clientThread = new Client1();
        clientThread.start();

    }
}

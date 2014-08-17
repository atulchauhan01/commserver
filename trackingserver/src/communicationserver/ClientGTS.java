package communicationserver;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGTS extends Thread {

    /**
     * 
     */
    //private static String data ="(imei:020000000035,BP05000020000000034,211011,A,2606.6947,N,09145.9674,E,000.0,122020,155.32,0,0,000000,L,02AE630C)";
//private static String data ="demo,demo,1268425875,61714,38.4465157985687,-121.857991218567,0,106,224.470588235294,19,,0,Dixon,CA,,,0,1049.17509440264,0,,1275153794,,Dixon,,,,US/CA,0,0,0,0,0,\\N,\\N,\\N,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,,,,,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,,,,,,,";
//private static String data="(imei:123451042191239,tracker ,1107090553,9735551234,F,225314.000,A,4103.7641,N,14244.9450,W,0.08,)";
    //private static String data="(imei:123451042191239,tracker ,1107090553,9735551234,F,194428.000,A,2838.9635,N,7720.4376,E,0.08,)";
//private static String data="(imei:123451042191239,tracker ,1107090553,9735551234,F,194438.000,A,2838.9635,N,7720.43761,E,0.08,)";//for rajiv chowk
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

            //inetAddress = InetAddress.getByName("8.22.201.39");
            inetAddress = InetAddress.getLocalHost();
            System.err.println(inetAddress);
            System.err.println("socket  1-->>" + socket);
            socket = new Socket(inetAddress, 31272);
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
            for (int i = 0; i < 10; i++) {

                SimpleDateFormat df = new SimpleDateFormat("HHmmss");
                Date t = new Date();
                String time = df.format(t);
                //System.out.println(time);
                String data = "(BP05000010000000144111103A2838.9635N07720.4376E000.0"+time+"191.5000000000L0075564C)";
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

        ClientGTS client = new ClientGTS();
        ClientGTS.publishData();
        Thread clientThread = new ClientGTS();
        clientThread.start();

    }
}

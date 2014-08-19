package communicationServer;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Communicationserver
        implements Runnable {

    private Socket connection;
    private int ID;
    public static Map HRSMapping = new HashMap();
    public static Map HRSICMaster = new HashMap();
    public static Map HRSICMapping = new HashMap();
    public static Map HRSAddressMapping = new HashMap();
    public static Map receiveMaster = new HashMap();
    public static Map intelliCartList = new HashMap();
    public static Connection sqlConn;
    int character = 0;
    String ICId = "";
    int byteCount = 1;
    boolean crcStatus = false;
    boolean isData = false;
    char c;
    char crcByte = '\000';
    int helloByte = 0;

    public static void main(String[] args) throws SQLException {
        int port = 1888;

        int count = 0;
        Socket conn = null;
        try {
            ServerSocket socket1 = new ServerSocket(port);

            // String AGVServerlog = "*************************Start Server*******************************";
            System.out.println("Mobile Server Initialized");

            Runnable ro = new Runnable() {

                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(400L);
                        }
                    } catch (Exception iex) {
                        System.out.println("Exception- Monitor Thread: "+ iex.getMessage());
                    }
                }
            };
            Thread receiveObserver = new Thread(ro);

            receiveObserver.start();
            while (true) {
                if (conn == null) {
                    Socket connection = socket1.accept();

                    conn = connection;

                    Runnable runnable = new Communicationserver(conn, count);
                    Thread thread = new Thread(runnable);

                    conn = null;
                    thread.start();
                    count++;

                    continue;
                }

            }

        } catch (Exception e) {
            System.out.println(" Exception main" + e.getMessage());
        }
    }

    Communicationserver(Socket s, int i) {
        this.connection = s;

        this.ID = i;
    }

    public synchronized void run() {
        Thread thisThread = Thread.currentThread();
        String str1;
        try {
            while (true) {
                System.out.println("Current thread: " + thisThread.getId());

                if (!thisThread.isAlive()) {
                    continue;
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(this.connection.getInputStream());
                InputStreamReader isr = new InputStreamReader(bufferedInputStream);
                this.character = 0;

                this.ICId = "";
                this.byteCount = 1;
                this.crcStatus = false;
                this.isData = false;
                char c = '\000';
                this.crcByte = '\000';
                this.helloByte = 0;

                StringBuffer data = new StringBuffer();

                if (isr.ready()) {
                    if ((this.character = isr.read()) == -1) {
                        break;
                    }

                    c = (char) this.character;

                    data.append(c);

                    this.helloByte = this.character;
                    this.crcByte = (char) (this.crcByte ^ c);

                    this.byteCount += 1;
                    receiveMaster.put(Long.valueOf(thisThread.getId()),
                            Long.valueOf(Calendar.getInstance().getTimeInMillis()));
                } else {
                    try {
                        int inCount = 1;
                        while (inCount == 1) {
                            if (isr.ready()) {
                                this.character = isr.read();
                                c = (char) this.character;

                                data.append(c);

                                if ((this.byteCount == 2) && (this.helloByte == 165)) {
                                    this.ICId = String.valueOf((char) this.character);
                                }

                                this.crcByte = (char) (this.crcByte ^ c);

                                if (this.crcByte == 0) {
                                    this.crcStatus = true;
                                }

                                this.byteCount += 1;
                                this.isData = true;
                            } else {
                                inCount = -1;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Read" + e.getMessage());
                    }

                    System.out.println("Socket: " + this.connection + "count: " + thisThread.getId() + " " + data);
                    String data1 = new String(data);
                    //Date date1 = new Date();

                    //String date2 = date1.toString();
                    if (data1.length() < 92) {
                        System.out.println("data1.length() :::::"+data1.length());
                        continue;                        
                    }
                    Parser parse = new Parser();
                    synchronized (parse) {
                        System.out.println("parse : "+data1);
                        parse.parseData(data1);
                    }

                }

            }

            Thread.sleep(400L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            str1 = "*************************Stop Server*******************************";
        }
    }
}

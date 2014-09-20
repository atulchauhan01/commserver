/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartappservices.gps.thread;

import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {

    private Socket socket = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(
                            socket.getInputStream()));
            String inputLine;

            boolean stop = false;
            int count = 1;
            while ((inputLine = in.readLine()) != null) {
               System.out.println(inputLine);
               if(++count >10)
                   stop = true;
                if (stop) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

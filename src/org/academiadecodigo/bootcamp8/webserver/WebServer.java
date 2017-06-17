package org.academiadecodigo.bootcamp8.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Prashanta on 12/06/17.
 */
public class WebServer {
    private static int port;
    private static ServerSocket serverSocket = null;


    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java Webserver <port>");
            System.exit(1);
        }

        WebServer webServer = new WebServer(Integer.parseInt(args[0]));
        try {
            serverSocket = new ServerSocket(port);
            ExecutorService cachedPool = Executors.newCachedThreadPool();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                cachedPool.submit(new RequestHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                webServer.closeServerSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private WebServer(int port){
        this.port = port;
    }

    private void closeServerSocket() throws IOException {
        serverSocket.close();
    }
}

package com.tsoft.civilization.web;

import com.tsoft.civilization.util.DefaultLogger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class GameServer {
    private int port;

    public GameServer(int port) throws Throwable {
        this.port = port;

        listNetworkInterfaces();
    }

    public void start() throws Throwable {
        DefaultLogger.createLogger("server");

        // Start listening sockets
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            Socket socket = ss.accept();
            long tick = System.currentTimeMillis();
            ClientServiceThread clientThread = new ClientServiceThread(socket, tick);
            Thread thread = new Thread(clientThread);

            DefaultLogger.info("[" + tick + " " + thread.getName() + "] is started to serve a request from " + clientThread.getLogInfo());
            thread.start();
        }
    }

    private void listNetworkInterfaces() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();

            if (ni.isLoopback()) {
                continue;
            }

            for (InterfaceAddress addr : ni.getInterfaceAddresses()) {
                InetAddress inet_addr = addr.getAddress();

                if (!(inet_addr instanceof Inet4Address)) {
                    continue;
                }

                System.out.println("Found address (" + ni.getName() + "): " + inet_addr.getHostAddress());
            }
        }
    }
}

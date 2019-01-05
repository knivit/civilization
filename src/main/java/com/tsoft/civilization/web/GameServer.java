package com.tsoft.civilization.web;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
public class GameServer {
    private int port;

    public GameServer(String port) throws Throwable {
        this.port = Integer.parseInt(port);

        listNetworkInterfaces();
    }

    public void start() throws Throwable {
        // Start listening sockets
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            Socket socket = ss.accept();

            ClientServiceThread clientThread = new ClientServiceThread(socket);
            Thread thread = new Thread(clientThread);

            log.info("{} started to serve a request", thread.getName());
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

package com.tsoft.civilization.web;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
public class Server {
    private final int port;

    public Server(String port) throws SocketException {
        this.port = Integer.parseInt(port);

        listNetworkInterfaces();
    }

    public void start() throws IOException {
        // Start listening sockets
        ServerSocket ss = new ServerSocket(port);

        log.info("Server started on port " + port);
        while (true) {
            Socket socket = ss.accept();

            ClientServiceThread clientThread = new ClientServiceThread(socket);
            Thread thread = new Thread(clientThread);
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
                InetAddress inetAddr = addr.getAddress();

                if (!(inetAddr instanceof Inet4Address)) {
                    continue;
                }

                log.info("Found address '{}': {}", ni.getName(), inetAddr.getHostAddress());
            }
        }
    }
}

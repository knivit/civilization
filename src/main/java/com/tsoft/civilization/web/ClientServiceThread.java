package com.tsoft.civilization.web;

import com.tsoft.civilization.util.DefaultLogger;

import java.net.Socket;

public class ClientServiceThread implements Runnable {
    private Socket socket;
    private ServerClient client;
    private long tick;

    public ClientServiceThread(Socket socket, long tick) throws Throwable {
        this.socket = socket;
        this.tick = tick;

        client = new ServerClient();
        client.readRequest(socket);
    }

    @Override
    public void run() {
        // Logger must be created here (not in the constructor) as it must be in a different thread with GameServer
        DefaultLogger.createLogger("client-" + client.getRequest().getClientIP(), tick);

        try {
            try {
                DefaultLogger.info("Started to serve a request from " + client.getRequest().toString());
                client.processRequest();
            } catch (Throwable ex) {
                DefaultLogger.severe("Error during request processing", ex);
            } finally {
                try {
                    socket.close();
                } catch (Throwable ex) {
                    DefaultLogger.severe("Can't close a socket", ex);
                }
            }
        } finally {
            long time = System.currentTimeMillis() - tick;
            DefaultLogger.fine("Work is done, took " + time + " (ms)\n");
            DefaultLogger.close();
        }
    }

    public String getLogInfo() {
        return client.getRequest().toString();
    }
}
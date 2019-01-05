package com.tsoft.civilization.web;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

@Slf4j
public class ClientServiceThread implements Runnable {
    private Socket socket;
    private ServerClient client;
    private Instant tick;

    public ClientServiceThread(Socket socket, long tick) throws Throwable {
        this.socket = socket;
        this.tick = Instant.now();

        client = new ServerClient();
        client.readRequest(socket);
    }

    @Override
    public void run() {
        try {
            try {
                log.info("Started to serve a request from {}", client.getRequest().toString());
                client.processRequest();
            } catch (Throwable ex) {
                log.error("Error during request processing", ex);
            } finally {
                try {
                    socket.close();
                } catch (Throwable ex) {
                    log.error("Can't close a socket", ex);
                }
            }
        } finally {
            log.debug("Work is done, took {} (ms)\n", Duration.between(tick, Instant.now()));
        }
    }

    public String getLogInfo() {
        return client.getRequest().toString();
    }
}
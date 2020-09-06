package com.tsoft.civilization.web;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

@Slf4j
public class ClientServiceThread implements Runnable {
    private Socket socket;
    private ServerClient client;

    public ClientServiceThread(Socket socket) throws Throwable {
        this.socket = socket;

        client = new ServerClient();
        client.readRequest(socket);
    }

    @Override
    public void run() {
        Instant tick = Instant.now();
        try {
            try {
                log.info("Started to serve {}", client.getRequest());
                client.processRequest();
            } catch (Throwable ex) {
                log.error("Error during {} processing", client.getRequest(), ex);
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

}
package com.tsoft.civilization.web;

import com.tsoft.civilization.web.request.Request;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class ClientServiceThread implements Runnable {
    private final Socket socket;

    public ClientServiceThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Client client = new Client();
            Request request = client.readRequest(socket);
            client.processRequest(request);
        } catch (Throwable ex) {
            log.error("Error during a request processing", ex);
        } finally {
            try {
                socket.close();
            } catch (Throwable ex) {
                log.error("Can't close a socket", ex);
            }
        }
    }
}
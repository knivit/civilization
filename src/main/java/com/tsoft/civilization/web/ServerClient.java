package com.tsoft.civilization.web;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.RequestReader;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ServerClient {
    private final RequestReader requestReader = new RequestReader();

    private OutputStream outputStream;

    private Request request;

    public void readRequest(Socket socket) throws IOException {
        // Get the client's address
        InetSocketAddress clientAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
        String clientIp = clientAddress.getAddress().toString();
        int clientPort = clientAddress.getPort();
        if (clientIp != null) {
            clientIp = clientIp.substring(1);
        }

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();

        // Create a Request
        request = requestReader.readRequest(clientIp, clientPort, inputStream);

        // Set properties as a client is needed
        if ("keep-alive".equals(request.getHeader("Connection"))) {
            socket.setKeepAlive(true);
        }
    }

    public void processRequest() {
        log.info("Started to serve {}", request);

        if (request.getRequestType() == null) {
            return;
        }

        switch (request.getRequestType()) {
            case GET -> {
                if ("GetNotifications".equals(request.getRequestUrl())) {
                    Sessions.setCurrent(request.getSessionId());
                    NotificationRequestProcessor.processRequest(this);
                } else {
                    GetRequestProcessor.processRequest(this);
                }
            }
            case POST -> {
                Sessions.setCurrent(request.getSessionId());
                Response response = PostRequestProcessor.processRequest(request);
                sendResponse(response);
            }
            default -> log.info("Unknown request type {}", request.getRequestType());
        }
    }

    public Request getRequest() {
        return request;
    }

    public boolean sendBytes(byte[] bytes) {
        try {
            outputStream.write(bytes);
            outputStream.flush();
            return true;
        } catch (SocketException se) {
            // java.net.SocketException: Broken pipe - client disconnected
            return false;
        } catch (IOException ex) {
            log.error("An error occurred during sending a response. Request: {}", request.getParamsAsString(), ex);
        }
        return false;
    }

    public boolean sendText(String text) {
        return sendBytes(text.getBytes(StandardCharsets.UTF_8));
    }

    public boolean sendResponse(Response response) {
        StringBuilder buf = Format.text("""
            HTTP/1.1 $errorCode\r
            Content-Type: $contentType\r
            Content-Length: $contentLength\r
            $additionalHeadersConnection: keep-alive\r
            \r
            """,

            "$errorCode", response.getResponseCode(),
            "$contentType", response.getContentType(),
            "$contentLength", response.getContentLength(),
            "$additionalHeaders", response.getAdditionalHeaders()
        );

        if (sendText(buf.toString())) {
            return sendBytes(response.getContent());
        }

        return false;
    }

    /** Send a error to the client */
    public void sendError(L10nMap messages) {
        sendResponse(Response.newErrorInstance(messages));
    }
}

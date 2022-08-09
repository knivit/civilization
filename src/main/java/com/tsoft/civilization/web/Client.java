package com.tsoft.civilization.web;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.request.RequestReader;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Client {
    private final RequestReader requestReader = new RequestReader();

    private OutputStream outputStream;

    public Request readRequest(Socket socket) throws IOException {
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
        Request request = requestReader.readRequest(clientIp, clientPort, inputStream);

        // Set properties as a client is needed
        if ("keep-alive".equalsIgnoreCase(request.getHeader("Connection"))) {
            socket.setKeepAlive(true);
        }

        return request;
    }

    public void processRequest(Request request) {
        if (request.getRequestType() == null) {
            log.debug("Invalid request {}", request);
            return;
        }

        switch (request.getRequestType()) {
            case GET -> {
                if ("GetNotifications".equals(request.getRequestUrl())) {
                    if (!Sessions.setCurrent(request.getSessionId())) {
                        log.debug("Invalid request {}", request);
                        return;
                    }

                    NotificationRequestProcessor.processRequest(this, request);
                } else {
                    Response response = GetRequestProcessor.processRequest(request);
                    sendResponse(response);
                }
            }

            case POST -> {
                if (!Sessions.setCurrent(request.getSessionId())) {
                    log.debug("Invalid request {}", request);
                    return;
                }

                Response response = PostRequestProcessor.processRequest(request);
                sendResponse(response);
            }

            default -> log.debug("Invalid request type {}", request.getRequestType());
        }
    }

    private void sendResponse(Response response) {
        StringBuilder buf = Format.text("""
            HTTP/1.1 $errorCode\r
            Content-Type: $contentType\r
            Content-Length: $contentLength\r
            Connection: keep-alive\r
            $headers\r
            """,

            "$errorCode", response.getResponseCode(),
            "$contentType", response.getContentType(),
            "$contentLength", response.getContentLength(),
            "$headers", response.getHeaders()
        );

        if (sendText(buf.toString())) {
            sendBytes(response.getContent());
        }
    }

    /** Send a error message */
    public void sendError(L10n messages) {
        sendResponse(JsonResponse.badRequest(messages));
    }

    public boolean sendText(String text) {
        return sendBytes(text.getBytes(StandardCharsets.UTF_8));
    }

    private boolean sendBytes(byte[] bytes) {
        try {
            outputStream.write(bytes);
            outputStream.flush();
            return true;
        } catch (IOException ex) {
            log.warn("An error occurred during sending a response", ex);
        }
        return false;
    }
}

package com.tsoft.civilization.web;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ServerClient {
    private BufferedReader inputStream;
    private OutputStream outputStream;

    private Request request;

    public void readRequest(Socket socket) throws IOException {
        // Get the client's address
        InetSocketAddress clientAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
        String clientIP = clientAddress.getAddress().toString();
        int clientPort = clientAddress.getPort();
        if (clientIP != null) {
            clientIP = clientIP.substring(1);
        }

        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = socket.getOutputStream();

        // Create a Request
        request = new Request(clientIP, clientPort);
        request.readHeaders(inputStream);
        request.readPostData(inputStream);

        // Set properties as a client is needed
        if ("keep-alive".equals(request.getHeader("Connection"))) {
            socket.setKeepAlive(true);
        }
    }

    public void processRequest() {
        switch (request.getRequestType()) {
            case GET: {
                if ("GetNotifications".equals(request.getRequestUrl())) {
                    NotificationRequestProcessor.processRequest(this);
                } else {
                    GetRequestProcessor.processRequest(this);
                }
                break;
            }

            case POST: {
                PostRequestProcessor.processRequest(this);
                break;
            }

            default: {
                DefaultLogger.info("Unknown request type " + request.getRequestType());
                break;
            }
        }
    }

    public Request getRequest() {
        return request;
    }

    public ClientSession getSession() {
        String sessionId = request.getSessionId();
        Sessions.setCurrent(sessionId);
        return Sessions.getCurrent();
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
            DefaultLogger.severe("An error occurred during sending a response. Request: " + request.getParamsAsString(), ex);
        }
        return false;
    }

    public boolean sendText(String text) {
        return sendBytes(text.getBytes(StandardCharsets.UTF_8));
    }

    public boolean sendResponse(Response response) {
        StringBuilder buf = Format.text(
            "HTTP/1.1 $errorCode\r\n" +
            "Content-Type: $contentType\r\n" +
            "Content-Length: $contentLength\r\n" +
            "$additionalHeaders" +
            "Connection: keep-alive\r\n" +
             "\r\n",

            "$errorCode", response.getErrorCode(),
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

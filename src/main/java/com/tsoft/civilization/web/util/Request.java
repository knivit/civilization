package com.tsoft.civilization.web.util;

import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.NumberUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final int MAX_POST_DATA = 8192;

    private String clientIP;
    private int clientPort;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    private RequestType requestType;
    private String requestUrl;
    private String sessionId;
    private String userAgent;

    public Request(String clientIP, int clientPort) {
        this.clientIP = clientIP;
        this.clientPort = clientPort;
        this.requestType = RequestType.GET;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getClientIP() {
        return clientIP;
    }

    public int getClientPort() {
        return clientPort;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    // Read headers until an empty line
    // For GET requests, that's the end of a request
    // For POST requests, the post data will be after that
    public void readHeaders(BufferedReader inputStream) {
        while (true) {
            try {
                String line = inputStream.readLine();
                if (line == null) {
                    break;
                }

                line = line.trim();
                if (line.length() == 0) {
                    break;
                }

                // header name: header value
                int n = line.indexOf(':');
                if (n == -1) {
                    // name value
                    n = line.indexOf(' ');
                }

                if (n != -1) {
                    String name = line.substring(0, n).trim();
                    String value = line.substring(n + 1).trim();
                    headers.put(name, value);
                }
            } catch (IOException ex) {
                DefaultLogger.severe("Can't read headers", ex);
            }
        }

        readRequestTypeAndUrl();
        readSessionId();
        readUserAgent();
    }

    private void readRequestTypeAndUrl() {
        requestUrl = headers.get("GET");
        if (requestUrl != null) {
            requestType = RequestType.GET;
        } else {
            requestUrl = headers.get("POST");
            if (requestUrl != null) {
                requestType = RequestType.POST;
            } else {
                requestType = null;
            }
        }

        if (requestUrl != null) {
            requestUrl = requestUrl.trim();
            int n = requestUrl.indexOf(' ');
            if (n != -1) {
                requestUrl = requestUrl.substring(0, n);
            }

            if (requestUrl.startsWith("/")) {
                requestUrl = requestUrl.substring(1);
            }
        }
    }

    private void readSessionId() {
        String cookies = headers.get("Cookie");
        if (cookies == null) {
            return;
        }

        int n = cookies.indexOf("sessionId=");
        if (n != -1) {
            n += 10; // skip 'sessionId'

            int m = cookies.indexOf(';', n);
            if (m > 0) {
                sessionId = cookies.substring(n, m);
            } else {
                sessionId = cookies.substring(n);
            }
        }
    }

    private void readUserAgent() {
        userAgent = headers.get("User-Agent");
    }

    public String get(String paramName) {
        return params.get(paramName);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    /** Read parameters from the headers */
    public void readPostData(BufferedReader inputStream) {
        int contentLength = NumberUtil.parseInt(headers.get("Content-Length"), -1);
        if (contentLength <= 0 || contentLength >= MAX_POST_DATA) {
            return;
        }

        char[] buf = new char[contentLength];
        try {
            int n = inputStream.read(buf);
            if (n != contentLength) {
                DefaultLogger.warning("Not all data received (" + n + " bytes instead of " + contentLength + "): " + toString());
                return;
            }
        } catch (IOException ex) {
            DefaultLogger.severe("Can't read POST data", ex);
            return;
        }

        try {
            int pos = 0;
            String paramName = null;
            for (int i = 0; i < buf.length; i ++) {
                if (buf[i] == '=') {
                    paramName = new String(buf, pos,  i - pos);
                    pos = i + 1;
                    continue;
                }

                if ((buf[i] == '&') && (paramName != null)) {
                    String paramValue = new String(buf, pos, i - pos);
                    getParams().put(paramName, paramValue);
                    pos = i + 1;
                    paramName = null;
                }
            }

            if (paramName != null) {
                String paramValue = new String(buf, pos, buf.length - pos);
                getParams().put(paramName, paramValue);
            }
        } catch (RuntimeException ex) {
            DefaultLogger.severe("Invalid request: " + toString() + "\nPOST data:\n" + new String(buf), ex);
        }
    }

    public String getParamsAsString() {
        StringBuilder buf = new StringBuilder();
        String requestUrl = getRequestUrl();
        buf.append(": ").append((requestUrl == null) || (requestUrl.length() == 0) ? '/' : requestUrl);
        if (params.size() > 0) {
            buf.append(", ");
            boolean isFirst = true;
            for (String paramName : params.keySet()) {
                if (!isFirst) {
                    buf.append(", ");
                }
                isFirst = false;

                buf.append(paramName);
                buf.append("=");
                buf.append(params.get(paramName));
            }
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        return "Request{" +
            "requestType=" + requestType +
            ", requestUrl='" + requestUrl + '\'' +
            ", clientIP='" + clientIP + '\'' +
            ", clientPort=" + clientPort +
            ", sessionId='" + sessionId + '\'' +
        '}';
    }
}

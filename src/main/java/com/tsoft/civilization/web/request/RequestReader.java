package com.tsoft.civilization.web.request;

import com.tsoft.civilization.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestReader {
    private static final int MAX_POST_DATA = 8192;

    public Request readRequest(String clientIp, int clientPort, BufferedReader inputStream) {
        Map<String, String> headers = readHeaders(inputStream);

        String requestUrl = getRequestUrl(headers);
        RequestType requestType = getRequestType(headers);
        String sessionId = getSessionId(headers);
        String userAgent = getUserAgent(headers);
        Map<String, String> params = readPostData(headers, inputStream);

        return Request.builder()
            .requestUrl(requestUrl)
            .requestType(requestType)
            .sessionId(sessionId)
            .userAgent(userAgent)
            .clientIp(clientIp)
            .clientPort(clientPort)
            .headers(headers)
            .params(params)
            .build();
    }

    // Read headers until an empty line
    // For GET requests, that's the end of a request
    // For POST requests, the post data will be after that
    private Map<String, String> readHeaders(BufferedReader inputStream) {
        Map<String, String> headers = new HashMap<>();

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
                log.error("Can't read headers", ex);
            }
        }
        return headers;
    }

    private String getRequestUrl(Map<String, String> headers) {
        String requestUrl = headers.get("GET");
        if (requestUrl == null) {
            requestUrl = headers.get("POST");
        }
        if (requestUrl == null) {
            return null;
        }

        requestUrl = requestUrl.trim();
        int n = requestUrl.indexOf(' ');
        if (n != -1) {
            requestUrl = requestUrl.substring(0, n);
        }

        if (requestUrl.startsWith("/")) {
            requestUrl = requestUrl.substring(1);
        }

        return requestUrl;
    }

    private RequestType getRequestType(Map<String, String> headers) {
        if (headers.containsKey("GET")) {
            return RequestType.GET;
        }

        if (headers.containsKey("POST")) {
            return RequestType.POST;
        }

        return null;
    }

    private String getSessionId(Map<String, String> headers) {
        String cookies = headers.get("Cookie");
        if (cookies == null) {
            return null;
        }

        int n = cookies.indexOf("sessionId=");
        if (n != -1) {
            n += 10; // skip 'sessionId'

            int m = cookies.indexOf(';', n);
            if (m > 0) {
                return cookies.substring(n, m);
            } else {
                return cookies.substring(n);
            }
        }
        return null;
    }

    private String getUserAgent(Map<String, String> headers) {
        return headers.get("User-Agent");
    }

    /** Read parameters from the headers */
    private Map<String, String> readPostData(Map<String, String> headers, BufferedReader inputStream) {
        int contentLength = NumberUtil.parseInt(headers.get("Content-Length"), -1);
        if (contentLength <= 0 || contentLength >= MAX_POST_DATA) {
            return Collections.emptyMap();
        }

        char[] buf = new char[contentLength];
        try {
            int n = inputStream.read(buf);
            if (n != contentLength) {
                log.warn("Not all data received ({} bytes instead of {})", n, contentLength);
                return Collections.emptyMap();
            }
        } catch (IOException ex) {
            log.error("Can't read POST data", ex);
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<>();
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
                    params.put(paramName, paramValue);
                    pos = i + 1;
                    paramName = null;
                }
            }

            if (paramName != null) {
                String paramValue = new String(buf, pos, buf.length - pos);
                params.put(paramName, paramValue);
            }
        } catch (RuntimeException ex) {
            log.error("Invalid request: {}\nPOST data:{}\n", toString(), new String(buf), ex);
        }

        return params;
    }
}

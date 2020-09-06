package com.tsoft.civilization.web.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class Request {
    @Getter
    private final String clientIp;
    private final int clientPort;

    @Getter
    private final RequestType requestType;

    @Getter
    private final String requestUrl;

    @Getter
    private final String sessionId;

    @Getter
    private final String userAgent;

    private final Map<String, String> headers;
    private final Map<String, String> params;

    public String get(String paramName) {
        return params.get(paramName);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public String getParamsAsString() {
        StringBuilder buf = new StringBuilder();
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
            ", clientIp='" + clientIp + '\'' +
            ", clientPort=" + clientPort +
            ", sessionId='" + sessionId + '\'' +
        '}';
    }
}

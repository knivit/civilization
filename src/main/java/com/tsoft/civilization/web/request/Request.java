package com.tsoft.civilization.web.request;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "RequestBuilder")
public final class Request {

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

    private final RequestHeadersMap headers;
    private final Map<String, String> params;

    public static class RequestBuilder {
        public RequestBuilder param(String key, Object value) {
            if (params == null) {
                params = new HashMap<>();
            }

            params.put(key, (value == null) ? null : value.toString());
            return this;
        }
    }

    public String get(String paramName) {
        return params.get(paramName);
    }

    public String get(String paramName, String defaultValue) {
        String value = params.get(paramName);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        return "Request{" +
            requestType + " " + requestUrl +
            ", client='" + clientIp + ':' + clientPort + '\'' +
            ", sessionId='" + sessionId + '\'' +
            (params.isEmpty() ? "" : ", params=" + params) +
        '}';
    }
}

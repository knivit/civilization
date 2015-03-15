package com.tsoft.civilization.web;

import com.tsoft.civilization.web.util.Request;

public class MockRequest extends Request {
    public static MockRequest newInstance() {
        return new MockRequest("127.0.0.1", 80);
    }

    public MockRequest(String clientIP, int clientPort) {
        super(clientIP, clientPort);
    }

    public void put(String paramName, String paramValue) {
        getParams().put(paramName, paramValue);
    }
}

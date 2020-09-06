package com.tsoft.civilization.web;

import com.tsoft.civilization.web.request.Request;

import java.util.HashMap;
import java.util.Map;

public class MockRequest {
    public static Request newInstance(String ... params) {
        Map<String, String> paramsMap = new HashMap<>();
        for (int i = 0; i < params.length; i += 2) {
            paramsMap.put(params[i], params[i + 1]);
        }

        return Request.builder()
            .clientIp("127.0.0.1")
            .clientPort(80)
            .params(paramsMap)
            .build();
    }
}

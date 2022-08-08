package com.tsoft.civilization.web.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeadersMap {

    // header name is case-insensitive

    private final Map<String, String> map = new HashMap<>();

    public void add(String name, String value) {
        if (name != null && value != null) {
            map.put(name.toLowerCase(), value);
        }
    }

    public String get(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }

        return map.get(name.toLowerCase());
    }

    public boolean contains(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }

        return map.containsKey(name.toLowerCase());
    }
}

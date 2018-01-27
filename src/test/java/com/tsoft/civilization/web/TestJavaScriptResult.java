package com.tsoft.civilization.web;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class TestJavaScriptResult {
    private ScriptObjectMirror mirror;

    public TestJavaScriptResult(ScriptObjectMirror mirror) {
        this.mirror = mirror;
    }

    public int size() {
        return mirror.size();
    }

    public String getString(String key) {
        Object val = mirror.get(key);

        if (val == null) return null;

        return val.toString();
    }

    public Integer getInt(String key) {
        Object val = mirror.get(key);

        if (val == null) return null;

        if (val instanceof Number) {
            return ((Number)val).intValue();
        }

        if (val instanceof String) {
            return Integer.parseInt((String)val);
        }

        throw new IllegalArgumentException("For key=" + key + " the value's class is unknown: " + val.getClass().getName());
    }

    public TestJavaScriptResult getChild(String key) {
        Object val = mirror.get(key);

        if (val == null) return null;

        if (val instanceof ScriptObjectMirror) {
            return new TestJavaScriptResult((ScriptObjectMirror)val);
        }

        throw new IllegalArgumentException("For key=" + key + " the value's class is unknown: " + val.getClass().getName());
    }
}

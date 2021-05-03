package com.tsoft.civilization.web.view;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonBlock {
    private int paramCount;
    private int elementCount;
    private boolean isArray;

    private final StringBuilder buf = new StringBuilder();
    private final char quoteChar;

    public JsonBlock() {
        this('"');
    }

    public JsonBlock(char quoteChar) {
        this.quoteChar = quoteChar;
    }

    // DO NOT use a new line character '\n' here
    // As it breaks Server Push Notifications

    public void addParam(String name, Object value) {
        if (isArray) {
            log.error("Param name = {} can't be inside an array", name);
            throw new IllegalStateException("A param can't be inside an array");
        }

        if (paramCount > 0) {
            buf.append(",");
        }
        buf.append(quoteChar).append(name).append(quoteChar).append(":");

        String valueStr = null;
        if (value != null) {
            valueStr = value.toString();
            if (valueStr.contains("\n")) {
                log.error("A new line character (\\n) can not be used as it breaks Server push notifications, name={}, value={}", name, value);
                throw new IllegalArgumentException("A new line character (\\n) can not be used");
            }
        }

        if ((valueStr != null) && (valueStr.length() > 1) && (valueStr.charAt(0) == '{')) {
            buf.append(valueStr);
        } else {
            buf.append(quoteChar);
            buf.append(valueStr == null ? "" : valueStr);
            buf.append(quoteChar);
        }

        paramCount ++;
    }

    public void startArray(String name) {
        if (paramCount > 0) {
            buf.append(",");
        }

        buf.append(quoteChar).append(name).append(quoteChar).append(":[");
        paramCount ++;
        elementCount = 0;
        isArray = true;
    }

    public void addElement(String value) {
        if (elementCount > 0) {
            buf.append(",");
        }

        buf.append(value);
        elementCount ++;
    }

    public void stopArray() {
        buf.append("]");
        isArray = false;
    }

    public String getText() {
        return "{" + buf + "}";
    }

    public String getValue() {
        return buf.toString();
    }
}

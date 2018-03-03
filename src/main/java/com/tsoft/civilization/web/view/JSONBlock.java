package com.tsoft.civilization.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONBlock {
    private static final Logger log = LoggerFactory.getLogger(JSONBlock.class);

    private int paramCount;
    private int elementCount;
    private boolean isArray;

    private StringBuilder buf = new StringBuilder();
    private char quoteChar;

    public JSONBlock() {
        this('"');
    }

    public JSONBlock(char quoteChar) {
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
        }
        if ((valueStr != null) && (valueStr.length() > 1) && (valueStr.charAt(0) == '{')) {
            buf.append(valueStr);
        } else {
            buf.append(quoteChar);
            buf.append(value == null ? "" : value.toString());
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
        return "{" + buf.toString() + "}";
    }

    public String getValue() {
        return buf.toString();
    }
}

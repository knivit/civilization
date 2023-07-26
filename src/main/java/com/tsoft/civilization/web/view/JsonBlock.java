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

    public JsonBlock addParam(String name, Object value) {
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

        addElementValue(valueStr);

        paramCount ++;

        return this;
    }

    public JsonBlock startArray(String name) {
        if (paramCount > 0) {
            buf.append(",");
        }

        buf.append(quoteChar).append(name).append(quoteChar).append(":[");
        paramCount ++;
        elementCount = 0;
        isArray = true;

        return this;
    }

    public JsonBlock addElement(String value) {
        if (elementCount > 0) {
            buf.append(",");
        }

        addElementValue(value);
        elementCount ++;

        return this;
    }

    public JsonBlock add(JsonBlock block) {
        return addElement(block.getText());
    }

    public JsonBlock stopArray() {
        buf.append("]");
        isArray = false;

        return this;
    }

    public String getText() {
        return "{" + buf + "}";
    }

    public String getValue() {
        return buf.toString();
    }

    private void addElementValue(String value) {
        if ((value != null) && (value.length() > 1) && (value.charAt(0) == '{')) {
            buf.append(value);
        } else {
            buf.append(quoteChar);
            buf.append(value == null ? "" : value);
            buf.append(quoteChar);
        }
    }
}

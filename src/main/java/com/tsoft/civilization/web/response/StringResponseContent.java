package com.tsoft.civilization.web.response;

import java.nio.charset.StandardCharsets;

public class StringResponseContent extends AbstractResponseContent {

    private final byte[] content;

    public StringResponseContent(String value) {
        content = value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public int getLength() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }
}

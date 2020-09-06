package com.tsoft.civilization.web.response;

import com.tsoft.civilization.web.response.AbstractResponseContent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ResourceResponseContent extends AbstractResponseContent {
    private byte[] content;

    public ResourceResponseContent(Class resourceLoaderClass, String fileName) {
        try {
            content = Thread.currentThread().getContextClassLoader().getResourceAsStream("web/" + fileName).readAllBytes();
        } catch (IOException | NullPointerException ex) {
            log.error("A error during reading of {}/{} is occurred",  resourceLoaderClass.getName(), fileName, ex);
            content = new byte[] { };
        }
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

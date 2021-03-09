package com.tsoft.civilization.web.response;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ResourceResponseContent extends AbstractResponseContent {
    private static final byte[] EMPTY_CONTENT = new byte[] { };

    private byte[] content = EMPTY_CONTENT;

    public ResourceResponseContent(Class<?> resourceLoaderClass, String fileName) {
        String resourceName = fileName;
        int pos = fileName.indexOf('?');
        if (pos != -1) {
            resourceName = resourceName.substring(0, pos);
        }

        try (InputStream is = getResourceAsStream("web/" + resourceName)) {
            if (is != null) {
                content = is.readAllBytes();
            }
        } catch (IOException | NullPointerException ex) {
            log.error("A error during reading {}/{} is occurred",  resourceLoaderClass.getName(), fileName, ex);
        }
    }

    private InputStream getResourceAsStream(String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
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

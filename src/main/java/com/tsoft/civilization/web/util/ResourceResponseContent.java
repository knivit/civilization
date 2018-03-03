package com.tsoft.civilization.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ResourceResponseContent extends AbstractResponseContent {
    private static final Logger log = LoggerFactory.getLogger(ResourceResponseContent.class);

    private byte[] content;

    public ResourceResponseContent(Class resourceLoaderClass, String fileName) {
        try (InputStream is = resourceLoaderClass.getResourceAsStream(fileName)) {
            if (is == null) {
                log.warn("File {}/{} not found", resourceLoaderClass.getName(), fileName);
                content = new byte[] { };
                return;
            }

            content = new byte[is.available()];
            int n = is.read(content);
            if (n != content.length) {
                log.warn("Got {} byte(s) from {}/{}, but {} is available", n, resourceLoaderClass.getName(), fileName, content.length);
                content = new byte[] { };
                return;
            }
        } catch (IOException ex) {
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

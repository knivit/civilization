package com.tsoft.civilization.web.response;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ResourceResponseContent extends AbstractResponseContent {
    private byte[] content;
see redraw: function() in DrawMap.js
    public ResourceResponseContent(Class<?> resourceLoaderClass, String fileName) {
        String resourceName = fileName;
        int pos = fileName.indexOf('?');
        if (pos != -1) {
            resourceName = resourceName.substring(0, pos);
        }

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("web/" + resourceName)) {
            content = is.readAllBytes();
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

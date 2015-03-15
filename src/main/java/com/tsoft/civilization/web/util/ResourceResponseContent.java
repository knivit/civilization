package com.tsoft.civilization.web.util;

import com.tsoft.civilization.util.DefaultLogger;

import java.io.IOException;
import java.io.InputStream;

public class ResourceResponseContent extends AbstractResponseContent {
    private byte[] content;

    public ResourceResponseContent(Class resourceLoaderClass, String fileName) {
        try (InputStream is = resourceLoaderClass.getResourceAsStream(fileName)) {
            if (is == null) {
                DefaultLogger.warning("File " + resourceLoaderClass.getName() + "/" + fileName + " not found");
                content = new byte[] { };
                return;
            }

            content = new byte[is.available()];
            int n = is.read(content);
            if (n != content.length) {
                DefaultLogger.warning("Got " + n + " byte(s) from " + resourceLoaderClass.getName() + "/" + fileName + ", but " + content.length + " is available");
                content = new byte[] { };
                return;
            }
        } catch (IOException ex) {
            DefaultLogger.severe("A error during reading of " + resourceLoaderClass.getName() + "/" + fileName + " is occured", ex);
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

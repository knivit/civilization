package com.tsoft.civilization.web.response;

import java.util.HashMap;
import java.util.Map;

final class MimeType {
    private static final Map<String, String> mimeTypes = new HashMap<>();

    static {
        mimeTypes.put(".html", ContentType.TEXT_HTML);
        mimeTypes.put(".js", ContentType.JAVASCRIPT);
        mimeTypes.put(".jpg", ContentType.JPEG);
        mimeTypes.put(".png", ContentType.PNG);
        mimeTypes.put(".css", ContentType.CSS);
        mimeTypes.put(".ico", ContentType.ICON);
    }

    private MimeType() { }

    public static String get(String fileName) {
        int pos = fileName.lastIndexOf('.');
        return (pos > 0) ? mimeTypes.get(fileName.substring(pos)) : ContentType.TEXT_HTML;
    }
}

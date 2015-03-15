package com.tsoft.civilization.web.util;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JSONBlock;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private static final Map<String, String> mimeTypes = new HashMap<>();
    static {
        mimeTypes.put(".html", ContentType.TEXT_HTML);
        mimeTypes.put(".js", ContentType.JAVASCRIPT);
        mimeTypes.put(".jpg", ContentType.JPEG);
        mimeTypes.put(".png", ContentType.PNG);
        mimeTypes.put(".css", ContentType.CSS);
    }

    private String errorCode;
    private String contentType;
    private AbstractResponseContent content;

    private StringBuilder additionalHeaders = new StringBuilder();

    public static Response newErrorInstance(L10nMap messages) {
        String enText = messages.getEnglish();

        ClientSession session = Sessions.getCurrent();
        JSONBlock block = new JSONBlock();
        block.addParam("error", session == null ? enText : messages.getLocalized());

        return new Response(ResponseCode.BAD_REQUEST, block.getText(), ContentType.APPLICATION_JSON);
    }

    public Response(String errorCode, String value, String contentType) {
        this.errorCode = errorCode;
        this.contentType = contentType;

        content = new StringResponseContent(value);
    }

    public Response(String errorCode, Class resourceLoaderClass, String fileName) {
        this.errorCode = errorCode;

        contentType = null;
        int pos = fileName.lastIndexOf('.');
        if (pos > 0) {
            contentType = mimeTypes.get(fileName.substring(pos));
        }
        if (contentType == null) {
            contentType = ContentType.TEXT_HTML;
        }

        content = new ResourceResponseContent(resourceLoaderClass, fileName);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getContentLength() {
        return content.getLength();
    }

    public byte[] getContent() {
        return content.getBytes();
    }

    public String getContentType() {
        return contentType;
    }

    public void addHeader(String header) {
        if (header != null) {
            additionalHeaders.append(header);
            additionalHeaders.append("\r\n");
        }
    }

    public StringBuilder getAdditionalHeaders() {
        return additionalHeaders;
    }
}

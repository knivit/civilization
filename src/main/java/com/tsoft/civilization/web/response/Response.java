package com.tsoft.civilization.web.response;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JsonBlock;

public class Response {
    private final String responseCode;
    private final String contentType;
    private final AbstractResponseContent content;

    private final StringBuilder additionalHeaders = new StringBuilder();

    public static Response newErrorInstance(L10n messages) {
        String enText = messages.getEnglish();

        ClientSession session = Sessions.getCurrent();
        JsonBlock block = new JsonBlock();
        block.addParam("error", session == null ? enText : messages.getLocalized());

        return new Response(ResponseCode.BAD_REQUEST, block.getText(), ContentType.APPLICATION_JSON);
    }

    public Response(String responseCode, String value, String contentType) {
        this.responseCode = responseCode;
        this.contentType = contentType;
        content = new StringResponseContent(value);
    }

    public Response(String responseCode, Class<?> resourceLoaderClass, String fileName) {
        this.responseCode = responseCode;
        contentType = MimeType.get(fileName);
        content = new ResourceResponseContent(resourceLoaderClass, fileName);
    }

    public String getResponseCode() {
        return responseCode;
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

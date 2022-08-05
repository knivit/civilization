package com.tsoft.civilization.web.response;

public class Response {
    private final String responseCode;
    private final String contentType;
    private final AbstractResponseContent content;

    private final StringBuilder headers = new StringBuilder();

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
            headers.append(header);
            headers.append("\r\n");
        }
    }

    public StringBuilder getHeaders() {
        return headers;
    }
}

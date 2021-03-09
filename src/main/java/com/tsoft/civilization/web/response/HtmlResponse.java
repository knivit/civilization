package com.tsoft.civilization.web.response;

public class HtmlResponse extends Response {

    public static HtmlResponse ok(StringBuilder value) {
        return new HtmlResponse(ResponseCode.OK, value.toString());
    }

    public HtmlResponse(String responseCode, String value) {
        super(responseCode, value, ContentType.TEXT_HTML);
    }
}

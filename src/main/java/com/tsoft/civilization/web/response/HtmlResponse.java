package com.tsoft.civilization.web.response;

public class HtmlResponse extends Response {

    public HtmlResponse(String errorCode, String value) {
        super(errorCode, value, ContentType.TEXT_HTML);
    }
}

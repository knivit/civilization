package com.tsoft.civilization.web.response;

public class JsonResponse extends Response {

    public JsonResponse(String errorCode, String value) {
        super(errorCode, value, ContentType.APPLICATION_JSON);
    }
}

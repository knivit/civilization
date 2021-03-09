package com.tsoft.civilization.web.response;

import com.tsoft.civilization.web.view.JsonBlock;

public class JsonResponse extends Response {

    public JsonResponse(String errorCode, String value) {
        super(errorCode, value, ContentType.APPLICATION_JSON);
    }

    public JsonResponse(String errorCode, JsonBlock json) {
        super(errorCode, json.getText(), ContentType.APPLICATION_JSON);
    }
}

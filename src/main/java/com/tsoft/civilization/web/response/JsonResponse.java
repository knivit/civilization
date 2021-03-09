package com.tsoft.civilization.web.response;

import com.tsoft.civilization.web.view.JsonBlock;

public class JsonResponse extends Response {

    public static JsonResponse ok(JsonBlock json) {
        return new JsonResponse(ResponseCode.OK, json);
    }

    public JsonResponse(String code, String value) {
        super(code, value, ContentType.APPLICATION_JSON);
    }

    public JsonResponse(String code, JsonBlock json) {
        super(code, json.getText(), ContentType.APPLICATION_JSON);
    }
}

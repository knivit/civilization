package com.tsoft.civilization.web.response;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JsonBlock;

public class JsonResponse extends Response {

    public static JsonResponse ok(JsonBlock json) {
        return new JsonResponse(ResponseCode.OK, json);
    }

    public static JsonResponse accepted(L10n message) {
        return new JsonResponse(ResponseCode.ACCEPTED, getText("message", message));
    }

    public static JsonResponse badRequest(L10n message) {
        return new JsonResponse(ResponseCode.BAD_REQUEST, getText("error", message));
    }

    public JsonResponse(String code, String value) {
        super(code, value, ContentType.APPLICATION_JSON);
    }

    public JsonResponse(String code, JsonBlock json) {
        super(code, json.getText(), ContentType.APPLICATION_JSON);
    }

    private static String getText(String param, L10n message) {
        String eng = message.getEnglish();

        ClientSession session = Sessions.getCurrent();
        JsonBlock block = new JsonBlock();
        block.addParam(param, session == null ? eng : message.getLocalized());

        return block.getText();
    }
}

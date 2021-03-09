package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JsonBlock;

public class JoinWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        ActionAbstractResult result = JoinWorldAction.join(request.get("world"));

        if (result.isFail()) {
            Sessions.getCurrent().setLanguage(request.get("language"));

            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response);
        }

        JsonBlock activeLocation = new JsonBlock();
        activeLocation.addParam("col", 0);
        activeLocation.addParam("row", 0);
        return new JsonResponse(ResponseCode.OK, "");
    }
}

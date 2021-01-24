package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JsonBlock;

public class CreateWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        CreateWorldAction.Request req = CreateWorldAction.Request.builder()
            .worldName(request.get("worldName"))
            .mapWidth(NumberUtil.parseInt(request.get("mapWidth"), 20))
            .mapHeight(NumberUtil.parseInt(request.get("mapHeight"), 20))
            .worldType(NumberUtil.parseInt(request.get("worldType"), 0))
            .climate(NumberUtil.parseInt(request.get("climate"), 1))
            .maxNumberOfCivilizations(NumberUtil.parseInt(request.get("maxNumberOfCivilizations"), 4))
            .build();

        ActionAbstractResult result = CreateWorldAction.create(req);
        if (result.isFail()) {
            Sessions.getCurrent().setLanguage(request.get("language"));

            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response.getText());
        }

        return new JsonResponse(ResponseCode.OK, "");
    }
}

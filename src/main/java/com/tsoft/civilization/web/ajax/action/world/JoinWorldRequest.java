package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.world.action.JoinWorldAction;

public class JoinWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        JoinWorldAction.Request req = JoinWorldAction.Request.builder()
            .worldId(request.get("world"))
            .name(request.get("name"))
            .ai(Boolean.getBoolean(request.get("ai", "false")))
            .build();

        ActionAbstractResult result = JoinWorldAction.join(req);

        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response);
        }

        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point settlersLocation = null;
        UnitList settlers = myCivilization.units().findByClassUuid(Settlers.CLASS_UUID);
        if (!settlers.isEmpty()) {
            settlersLocation = settlers.getAny().getLocation();
        }

        JsonBlock activeLocation = new JsonBlock();
        activeLocation.addParam("col", (settlersLocation == null) ? 0 : settlersLocation.getX());
        activeLocation.addParam("row", (settlersLocation == null) ? 0 : settlersLocation.getY());
        return JsonResponse.ok(activeLocation);
    }
}

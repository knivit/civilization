package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.UUID;

public class CreateWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        CreateWorldAction.Request req = CreateWorldAction.Request.builder()
            .worldName(request.get("worldName", "World " + UUID.randomUUID().toString()))
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
        return new JsonResponse(ResponseCode.OK, activeLocation);
    }
}
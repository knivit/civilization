package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.MoveUnitAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class MoveUnitActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.units().getUnitById(unitId);
        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));

        ActionAbstractResult result = MoveUnitAction.move(unit, location);
        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response);
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson();
        return JsonResponse.ok(response);
    }
}
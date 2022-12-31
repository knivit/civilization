package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.MoveUnitAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class MoveUnitActionRequest extends AbstractAjaxRequest {

    private static final MoveUnitService moveUnitService = new MoveUnitService();
    private static final MoveUnitAction moveUnitAction = new MoveUnitAction(moveUnitService);

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.getUnitService().getUnitById(unitId);
        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));

        ActionAbstractResult result = moveUnitAction.move(unit, location);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}
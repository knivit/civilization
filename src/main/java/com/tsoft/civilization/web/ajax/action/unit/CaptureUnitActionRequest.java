package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.combat.service.CaptureUnitService;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.action.CaptureUnitAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class CaptureUnitActionRequest extends AbstractAjaxRequest {

    private final CaptureUnitService captureUnitService = new CaptureUnitService();
    private final CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureUnitService);

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String attackerId = request.get("attacker");
        AbstractUnit attacker = myCivilization.getUnitService().getUnitById(attackerId);
        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));

        ActionAbstractResult result = captureUnitAction.capture(attacker, location);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}
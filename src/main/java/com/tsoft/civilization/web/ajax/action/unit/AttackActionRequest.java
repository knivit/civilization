package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.AttackAction;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class AttackActionRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String attackerId = request.get("attacker");
        HasCombatStrength attacker = myCivilization.units().getAttackerById(attackerId);
        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));

        ActionAbstractResult result = AttackAction.attack(attacker, location);
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
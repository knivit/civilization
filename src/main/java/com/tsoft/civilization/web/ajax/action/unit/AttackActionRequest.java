package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.combat.service.AttackService;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.action.AttackAction;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class AttackActionRequest extends AbstractAjaxRequest {

    private final AttackService attackService = new AttackService();
    private final AttackAction attackAction = new AttackAction(attackService);

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String attackerId = request.get("attacker");
        HasCombatStrength attacker = myCivilization.getUnitService().getAttackerById(attackerId);
        Point location = myCivilization.getTilesMap().getLocation(request.get("col"), request.get("row"));

        ActionAbstractResult result = attackAction.attack(attacker, location);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}
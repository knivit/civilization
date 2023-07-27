package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.action.PillageAction;
import com.tsoft.civilization.combat.service.PillageService;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.view.JsonBlock;

public class PillageActionRequest extends AbstractAjaxRequest {

    private final PillageService pillageService = new PillageService();
    private final PillageAction pillageAction = new PillageAction(pillageService);

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String attackerId = request.get("attacker");
        AbstractUnit attacker = myCivilization.getUnitService().getUnitById(attackerId);
        String targetId = request.get("target");
        HasCombatStrength target = myCivilization.getWorld().getWorldObjectService().get(targetId);

        ActionAbstractResult result = pillageAction.pillage(attacker, target);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}

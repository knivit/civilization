package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.unit.service.destroy.DestroyUnitService;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.DestroyUnitAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class DestroyUnitActionRequest extends AbstractAjaxRequest {

    private static final DestroyUnitService destroyService = new DestroyUnitService();
    private static final DestroyUnitAction destroyAction = new DestroyUnitAction(destroyService);

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.getUnitService().getUnitById(unitId);

        ActionAbstractResult result = destroyAction.destroyUnit(unit);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson();
        return JsonResponse.ok(response);
    }
}
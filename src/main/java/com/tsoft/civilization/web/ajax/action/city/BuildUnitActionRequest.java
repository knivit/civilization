package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.action.BuildUnitAction;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuildUnitActionRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        City city = myCivilization.getCityService().getCityById(request.get("city"));
        String unitUuid = request.get("unitUuid");
        ActionAbstractResult result = BuildUnitAction.buildUnit(city, unitUuid);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson();
        return JsonResponse.ok(response);
    }
}
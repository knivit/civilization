package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.civilization.city.action.BuildUnitAction;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuildUnitActionRequest extends AbstractAjaxRequest {

    private final BuildUnitAction buildUnitAction;

    public static BuildUnitActionRequest newInstance() {
        return new BuildUnitActionRequest(new BuildUnitAction());
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        City city = myCivilization.getCityService().getCityById(request.get("city"));
        String unitUuid = request.get("unitUuid");
        ActionAbstractResult result = buildUnitAction.buildUnit(city, unitUuid);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}
package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.civilization.city.action.BuildBuildingAction;
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
public class BuildBuildingActionRequest extends AbstractAjaxRequest {

    private final BuildBuildingAction buildBuildingAction;

    public static BuildBuildingActionRequest newInstance() {
        return new BuildBuildingActionRequest(new BuildBuildingAction());
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        City city;
        String cityId = request.get("city");
        if (cityId == null || (city = myCivilization.getCityService().getCityById(cityId)) == null) {
            return JsonResponse.badRequest(L10nServer.CITY_NOT_FOUND);
        }

        String buildingUuid = request.get("buildingUuid");
        ActionAbstractResult result = buildBuildingAction.buildBuilding(city, buildingUuid);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}

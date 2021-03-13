package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.action.BuildBuildingAction;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuildBuildingActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        City city = myCivilization.cities().getCityById(request.get("city"));
        String buildingUuid = request.get("buildingUuid");
        ActionAbstractResult result = BuildBuildingAction.buildBuilding(city, buildingUuid);
        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("error", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response);
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson();
        return JsonResponse.ok(response);
    }
}

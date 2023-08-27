package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.civilization.city.action.DestroyBuildingAction;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DestroyBuildingActionRequest extends AbstractAjaxRequest {

    private final DestroyBuildingAction destroyBuildingAction;

    public static DestroyBuildingActionRequest newInstance() {
        return new DestroyBuildingActionRequest(new DestroyBuildingAction());
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        AbstractBuilding building = myCivilization.getCityService().getBuildingById(request.get("building"));
        ActionAbstractResult result = destroyBuildingAction.destroyBuilding(building);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}

package com.tsoft.civilization.web.ajax.action.unit.settlers;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.settlers.action.BuildCityAction;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuildCityActionRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String settlersId = request.get("settlers");
        Settlers settlers = (Settlers)myCivilization.getUnitService().getUnitById(settlersId);

        ActionAbstractResult result = BuildCityAction.buildCity(settlers);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);
        return JsonResponse.ok(response);
    }
}

package com.tsoft.civilization.web.ajax.action.unit.settlers;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.settlers.action.BuildCityAction;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuildCityActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String settlersId = request.get("settlers");
        Settlers settlers = (Settlers)myCivilization.units().getUnitById(settlersId);

        ActionAbstractResult result = BuildCityAction.buildCity(settlers);
        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new JsonResponse(ResponseCode.ACCEPTED, response);
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJSON();
        return new JsonResponse(ResponseCode.OK, response);
    }
}

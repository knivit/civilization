package com.tsoft.civilization.web.ajax.action.unit.settlers;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.settlers.BuildCityAction;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuildCityActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String settlersId = request.get("settlers");
        Settlers settlers = (Settlers)myCivilization.getUnitById(settlersId);

        ActionAbstractResult result = BuildCityAction.buildCity(settlers);
        if (result.isFail()) {
            JSONBlock response = new JSONBlock();
            response.addParam("message", result.getLocalized());
            return new Response(ResponseCode.ACCEPTED, response.getText(), ContentType.APPLICATION_JSON);
        }

        // return the map
        JSONBlock response = myCivilization.getWorld().getView().getJSON();
        return new Response(ResponseCode.OK, response.getText(), ContentType.APPLICATION_JSON);
    }
}

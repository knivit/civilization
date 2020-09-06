package com.tsoft.civilization.web.ajax.action.city;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.city.BuyUnitAction;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.civilization.Civilization;

public class BuyUnitActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        City city = myCivilization.getCityById(request.get("city"));
        String unitUuid = request.get("unitUuid");
        ActionAbstractResult result = BuyUnitAction.buyUnit(city, unitUuid);
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

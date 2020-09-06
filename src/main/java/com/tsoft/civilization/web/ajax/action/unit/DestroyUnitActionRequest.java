package com.tsoft.civilization.web.ajax.action.unit;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.DestroyUnitAction;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.civilization.Civilization;

public class DestroyUnitActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String unitId = request.get("unit");
        AbstractUnit unit = myCivilization.getUnitById(unitId);

        ActionAbstractResult result = DestroyUnitAction.destroyUnit(unit);
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
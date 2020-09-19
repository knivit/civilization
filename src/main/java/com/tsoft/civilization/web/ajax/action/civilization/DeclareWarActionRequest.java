package com.tsoft.civilization.web.ajax.action.civilization;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.action.DeclareWarAction;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class DeclareWarActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String otherCivilizationId = request.get("otherCivilization");
        Civilization otherCivilization = myCivilization.getWorld().getCivilizationById(otherCivilizationId);
        if (otherCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        ActionAbstractResult result = DeclareWarAction.declareWar(myCivilization, otherCivilization);
        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new Response(ResponseCode.ACCEPTED, response.getText(), ContentType.APPLICATION_JSON);
        }

        // nothing to return
        return new Response(ResponseCode.OK, "", ContentType.APPLICATION_JSON);
    }
}

package com.tsoft.civilization.web.ajax.action.civilization;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.action.NextMoveAction;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class NextMoveActionRequest extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null || myCivilization.isDestroyed()) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        myCivilization.nextMove();

        ActionAbstractResult result = NextMoveAction.nextMove(myCivilization.getWorld());

        // if there is a wait for others, then send an message to client
        if (result.isFail()) {
            JsonBlock response = new JsonBlock();
            response.addParam("message", result.getLocalized());
            return new Response(ResponseCode.ACCEPTED, response.getText(), ContentType.APPLICATION_JSON);
        }

        // return the map
        JsonBlock response = myCivilization.getWorld().getView().getJSON();
        return new Response(ResponseCode.OK, response.getText(), ContentType.APPLICATION_JSON);
    }
}

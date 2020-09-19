package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class LoadWorld extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return Response.newErrorInstance(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // map
        JsonBlock response = myCivilization.getWorld().getView().getJSON();

        // starting point
        Point startPoint = myCivilization.getStartPoint();
        response.addParam("selectedCol", startPoint.getX());
        response.addParam("selectedRow", startPoint.getY());

        return new Response(ResponseCode.OK, response.getText(), ContentType.APPLICATION_JSON);
    }
}

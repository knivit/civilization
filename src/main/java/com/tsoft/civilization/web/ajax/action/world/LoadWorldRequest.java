package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;

public class LoadWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        // map
        JsonBlock response = myCivilization.getWorld().getView().getJson(myCivilization);

        // starting point
        Point startPoint = myCivilization.getStartPoint();
        response.addParam("selectedCol", startPoint.getX());
        response.addParam("selectedRow", startPoint.getY());

        return JsonResponse.ok(response);
    }
}

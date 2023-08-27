package com.tsoft.civilization.web.ajax.action.civilization;

import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.action.DeclareWarAction;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.civilization.Civilization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeclareWarActionRequest extends AbstractAjaxRequest {

    private final DeclareWarAction declareWarAction;

    public static DeclareWarActionRequest newInstance() {
        return new DeclareWarActionRequest(new DeclareWarAction());
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        String otherCivilizationId = request.get("otherCivilization");
        Civilization otherCivilization = myCivilization.getWorld().getCivilizationById(otherCivilizationId);
        if (otherCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        ActionAbstractResult result = declareWarAction.declareWar(myCivilization, otherCivilization);
        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        // nothing to return
        return new JsonResponse(ResponseCode.OK, "");
    }
}

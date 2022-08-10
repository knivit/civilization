package com.tsoft.civilization.web.ajax.action.status;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.construction.Construction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.HtmlResponse;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;

public class GetConstructionStatus extends AbstractAjaxRequest {

    public static StringBuilder getAjax(Construction construction) {
        return Format.text("server.sendAsyncAjax('ajax/GetConstructionStatus', { construction:'$construction' })",
            "$construction", construction.getId()
        );
    }

    @Override
    public Response getJson(Request request) {
        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        StringBuilder value = Format.text("""
        """);

        return HtmlResponse.ok(value);
    }
}

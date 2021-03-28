package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.action.GetWorldsAction;

public class GetWorldsRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        return GetWorldsAction.getWorlds();
    }
}

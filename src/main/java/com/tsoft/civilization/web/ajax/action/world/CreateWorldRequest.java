package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.action.CreateWorldAction;
import com.tsoft.civilization.world.action.GetWorldsAction;

import java.util.UUID;

public class CreateWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        CreateWorldAction.Request req = CreateWorldAction.Request.builder()
            .worldName(request.get("worldName", "World " + UUID.randomUUID()))
            .mapWidth(NumberUtil.parseInt(request.get("mapWidth"), 20))
            .mapHeight(NumberUtil.parseInt(request.get("mapHeight"), 20))
            .worldType(NumberUtil.parseInt(request.get("worldType"), 0))
            .climate(NumberUtil.parseInt(request.get("climate"), 1))
            .maxNumberOfCivilizations(NumberUtil.parseInt(request.get("maxNumberOfCivilizations"), 4))
            .difficultyLevel(request.get("difficultyLevel"))
            .build();

        ActionAbstractResult result = CreateWorldAction.create(req);

        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        return GetWorldsAction.getWorlds();
    }
}

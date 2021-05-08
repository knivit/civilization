package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.world.action.CreateWorldAction;
import com.tsoft.civilization.world.action.GetWorldsAction;
import com.tsoft.civilization.world.service.CreateWorldParams;
import com.tsoft.civilization.world.service.CreateWorldService;

import java.util.UUID;

public class CreateWorldRequest extends AbstractAjaxRequest {

    private final CreateWorldService createWorldService = new CreateWorldService();
    private final CreateWorldAction createWorldAction = new CreateWorldAction(createWorldService);

    @Override
    public Response getJson(Request request) {
        CreateWorldParams params = CreateWorldParams.builder()
            .worldName(request.get("worldName", "World " + UUID.randomUUID()))
            .mapSize(request.get("mapSize"))
            .mapConfiguration(request.get("mapConfiguration"))
            .climate(request.get("climate"))
            .difficultyLevel(request.get("difficultyLevel"))
            .build();

        ActionAbstractResult result = createWorldAction.create(params);

        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        return GetWorldsAction.getWorlds();
    }
}

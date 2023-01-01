package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.world.action.GetWorldsAction;
import com.tsoft.civilization.world.action.JoinWorldAction;

public class JoinWorldRequest extends AbstractAjaxRequest {

    @Override
    public Response getJson(Request request) {
        PlayerType playerType = PlayerType.valueOf(request.get("playerType", PlayerType.HUMAN.name()));

        JoinWorldAction.Request joinWorldRequest = JoinWorldAction.Request.builder()
            .worldId(request.get("world"))
            .civilization(request.get("civilization"))
            .playerType(playerType)
            .build();

        ActionAbstractResult result = JoinWorldAction.join(joinWorldRequest);

        if (result.isFail()) {
            return JsonResponse.accepted(result.getMessage());
        }

        if (PlayerType.BOT.equals(playerType)) {
            return GetWorldsAction.getWorlds();
        }

        Civilization myCivilization = getMyCivilization();
        if (myCivilization == null) {
            return JsonResponse.badRequest(L10nServer.CIVILIZATION_NOT_FOUND);
        }

        Point settlersLocation = null;
        UnitList settlers = myCivilization.getUnitService().findByClassUuid(Settlers.CLASS_UUID);
        if (!settlers.isEmpty()) {
            settlersLocation = settlers.getAny().getLocation();
        }

        JsonBlock activeLocation = new JsonBlock();
        activeLocation.addParam("col", (settlersLocation == null) ? 0 : settlersLocation.getX());
        activeLocation.addParam("row", (settlersLocation == null) ? 0 : settlersLocation.getY());
        return JsonResponse.ok(activeLocation);
    }
}

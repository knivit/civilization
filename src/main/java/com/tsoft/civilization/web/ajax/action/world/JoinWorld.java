package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nServer;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.JsonResponse;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;

public class JoinWorld extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        World world = Worlds.getWorld(request.get("world"));
        if (world == null) {
            return Response.newErrorInstance(L10nServer.WORLD_NOT_FOUND);
        }

        Civilization civilization = new Civilization(world, world.getCivilizations().size());
        Sessions.getCurrent().setWorldAndCivilizationIds(civilization);
        civilization.addFirstUnits();

        return new JsonResponse(ResponseCode.OK, "");
    }
}

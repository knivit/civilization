package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nMap;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.tsoft.civilization.L10n.L10nCivilization.CIVILIZATIONS;

public class JoinWorld extends AbstractAjaxRequest {
    @Override
    public Response getJson(Request request) {
        World world = Worlds.getWorld(request.get("world"));
        if (world == null) {
            return Response.newErrorInstance(L10nServer.WORLD_NOT_FOUND);
        }

        int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
        if (slotsAvailable == 0) {
            return Response.newErrorInstance((L10nServer.WORLD_IS_FULL));
        }

        List<L10nMap> names = new ArrayList<>(CIVILIZATIONS);
        List<L10nMap> notUsed = names.stream()
            .filter(name -> world.getCivilizations().filter(c -> c.getName().equals(name)).isEmpty())
            .collect(Collectors.toList());
        if (notUsed.isEmpty()) {
            return Response.newErrorInstance(L10nServer.NO_CIVILIZATION_AVAILABLE);
        }

        int random = ThreadLocalRandom.current().nextInt(notUsed.size());
        Civilization civilization = world.createCivilization(notUsed.get(random));
        civilization.units().addFirstUnits();

        Sessions.getCurrent().setActiveCivilization(civilization);
        return new JsonResponse(ResponseCode.OK, "");
    }
}

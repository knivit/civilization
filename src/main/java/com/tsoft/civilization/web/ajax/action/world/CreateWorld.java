package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.util.NumberUtil;
import com.tsoft.civilization.web.util.ContentType;
import com.tsoft.civilization.web.util.Request;
import com.tsoft.civilization.web.util.Response;
import com.tsoft.civilization.web.util.ResponseCode;
import com.tsoft.civilization.web.ajax.AbstractAjaxRequest;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.world.generator.WorldGenerator;

public class CreateWorld extends AbstractAjaxRequest {
    @Override
    public Response getJSON(Request request) {
        String worldName = request.get("worldName");
        int mapWidth = NumberUtil.parseInt(request.get("mapWidth"), 20);
        int mapHeight = NumberUtil.parseInt(request.get("mapHeight"), 20);
        int worldType = NumberUtil.parseInt(request.get("worldType"), 0);
        int climate = NumberUtil.parseInt(request.get("climate"), 1);
        int maxNumberOfCivilizations = NumberUtil.parseInt(request.get("maxNumberOfCivilizations"), 4);
        Sessions.getCurrent().setLanguage(request.get("language"));

        // create a world and put it into a store for the given client's session
        World world = new World(MapType.SIX_TILES, mapWidth, mapHeight);
        world.setName(worldName);
        world.setMaxNumberOfCivilizations(maxNumberOfCivilizations);
        Worlds.add(world);

        // generate landscape
        WorldGenerator generator = WorldGeneratorService.getGenerator(worldType);
        generator.generate(world.getTilesMap(), Climate.getClimateByNo(climate));

        // create civilizations
        Civilization civilization = new Civilization(world, world.getCivilizations().size());
        civilization.addFirstUnits();

        Sessions.getCurrent().setWorldAndCivilizationIds(civilization);

        return new Response(ResponseCode.OK, "", ContentType.APPLICATION_JSON);
    }
}

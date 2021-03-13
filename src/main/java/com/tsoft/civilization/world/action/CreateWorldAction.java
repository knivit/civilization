package com.tsoft.civilization.world.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import lombok.Builder;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

public class CreateWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CREATED = new ActionSuccessResult(L10nWorld.WORLD_CREATED);

    public static final ActionFailureResult CANT_CREATE_CIVILIZATION = new ActionFailureResult(L10nServer.CANT_CREATE_CIVILIZATION);
    public static final ActionFailureResult INVALID_MAP_SIZE = new ActionFailureResult(L10nWorld.INVALID_MAP_SIZE);
    public static final ActionFailureResult INVALID_WORLD_NAME = new ActionFailureResult(L10nWorld.INVALID_WORLD_NAME);
    public static final ActionFailureResult INVALID_CIVILIZATIONS_NUMBER = new ActionFailureResult(L10nWorld.INVALID_CIVILIZATIONS_NUMBER);

    @Builder
    public static class Request {
        final String worldName;
        final int mapWidth;
        final int mapHeight;
        final int worldType;
        final int climate;
        final int maxNumberOfCivilizations;
    }

    public static ActionAbstractResult create(Request request) {
        if (request.mapWidth < TilesMap.MIN_WIDTH || request.mapWidth > TilesMap.MAX_WIDTH ||
                request.mapHeight < TilesMap.MIN_HEIGHT || request.mapHeight > TilesMap.MAX_HEIGHT) {
            return INVALID_MAP_SIZE;
        }

        if (request.worldName == null || request.worldName.isBlank()) {
            return INVALID_WORLD_NAME;
        }

        if (request.maxNumberOfCivilizations < 1 || request.maxNumberOfCivilizations > 16) {
            return INVALID_CIVILIZATIONS_NUMBER;
        }

        // create a world and put it into a store for the given client's session
        World world = new World(request.worldName, MapType.SIX_TILES, request.mapWidth, request.mapHeight);
        world.setMaxNumberOfCivilizations(request.maxNumberOfCivilizations);

        if (!Worlds.add(world)) {
            return INVALID_WORLD_NAME;
        }

        // generate landscape
        WorldGenerator generator = WorldGeneratorService.getGenerator(request.worldType);
        generator.generate(world.getTilesMap(), Climate.getClimateByNo(request.climate));

        // create civilizations
        int random = ThreadLocalRandom.current().nextInt(CIVILIZATIONS.size());
        Civilization civilization = world.createCivilization(CIVILIZATIONS.get(random));

        if (!civilization.units().addFirstUnits()) {
            return CANT_CREATE_CIVILIZATION;
        }

        Sessions.setActiveCivilization(civilization);
        return CREATED;
    }
}

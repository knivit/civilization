package com.tsoft.civilization.world.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import com.tsoft.civilization.world.scenario.BarbariansScenario;
import lombok.Builder;

import java.util.UUID;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

public class CreateWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CREATED = new ActionSuccessResult(L10nWorld.WORLD_CREATED);

    public static final ActionFailureResult INVALID_MAP_SIZE = new ActionFailureResult(L10nWorld.INVALID_MAP_SIZE);
    public static final ActionFailureResult INVALID_WORLD_NAME = new ActionFailureResult(L10nWorld.INVALID_WORLD_NAME);
    public static final ActionFailureResult INVALID_MAX_NUMBER_OF_CIVILIZATIONS = new ActionFailureResult(L10nWorld.INVALID_MAX_NUMBER_OF_CIVILIZATIONS);
    public static final ActionFailureResult CANT_CREATE_WORLD = new ActionFailureResult(L10nWorld.CANT_CREATE_WORLD);
    public static final ActionFailureResult CANT_CREATE_BARBARIANS = new ActionFailureResult(L10nWorld.CANT_CREATE_BARBARIANS);

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
            return INVALID_MAX_NUMBER_OF_CIVILIZATIONS;
        }

        // create a world
        TilesMap tilesMap = new TilesMap(MapType.SIX_TILES, request.mapWidth, request.mapHeight);
        World world = new World(request.worldName, tilesMap);
        world.setMaxNumberOfCivilizations(Math.min(CIVILIZATIONS.size(), request.maxNumberOfCivilizations));

        // generate landscape
        WorldGenerator generator = WorldGeneratorService.getGenerator(request.worldType);
        generator.generate(tilesMap, Climate.getClimateByNo(request.climate));

        // start history
        world.startYear();

        // add Barbarians civilization
        Civilization barbarians = world.createCivilization(PlayerType.BOT, BARBARIANS, new BarbariansScenario());
        if (barbarians == null) {
            return CANT_CREATE_BARBARIANS;
        }

        if (!Worlds.add(world)) {
            return CANT_CREATE_WORLD;
        }

        return CREATED;
    }
}

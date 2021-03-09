package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.generator.Climate;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.WorldGeneratorService;
import lombok.Builder;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.tsoft.civilization.L10n.L10nCivilization.CIVILIZATIONS;

public class CreateWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

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
            return CreateWorldActionResults.INVALID_MAP_SIZE;
        }

        if (request.worldName == null || request.worldName.isBlank()) {
            return CreateWorldActionResults.INVALID_WORLD_NAME;
        }

        if (request.maxNumberOfCivilizations < 1 || request.maxNumberOfCivilizations > 16) {
            return CreateWorldActionResults.INVALID_CIVILIZATIONS_NUMBER;
        }

        // create a world and put it into a store for the given client's session
        World world = new World(MapType.SIX_TILES, request.mapWidth, request.mapHeight);
        world.setName(request.worldName);
        world.setMaxNumberOfCivilizations(request.maxNumberOfCivilizations);

        if (!Worlds.add(world)) {
            return CreateWorldActionResults.INVALID_WORLD_NAME;
        }

        // generate landscape
        WorldGenerator generator = WorldGeneratorService.getGenerator(request.worldType);
        generator.generate(world.getTilesMap(), Climate.getClimateByNo(request.climate));

        // create civilizations
        int random = ThreadLocalRandom.current().nextInt(CIVILIZATIONS.size());
        Civilization civilization = world.createCivilization(CIVILIZATIONS.get(random));

        if (!civilization.units().addFirstUnits()) {
            return CreateWorldActionResults.CANT_CREATE_CIVILIZATION;
        }

        Sessions.setActiveCivilization(civilization);
        return CreateWorldActionResults.CREATED;
    }
}

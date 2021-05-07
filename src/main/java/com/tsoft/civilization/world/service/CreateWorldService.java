package com.tsoft.civilization.world.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.*;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.WorldGeneratorFactory;
import com.tsoft.civilization.world.scenario.BarbariansScenario;
import lombok.extern.slf4j.Slf4j;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

@Slf4j
public class CreateWorldService {

    public static final ActionSuccessResult CREATED = new ActionSuccessResult(L10nWorld.WORLD_CREATED);

    public static final ActionFailureResult INVALID_MAP_SIZE = new ActionFailureResult(L10nWorld.INVALID_MAP_SIZE);
    public static final ActionFailureResult INVALID_WORLD_NAME = new ActionFailureResult(L10nWorld.INVALID_WORLD_NAME);
    public static final ActionFailureResult INVALID_DIFFICULTY_LEVEL = new ActionFailureResult(L10nWorld.INVALID_DIFFICULTY_LEVEL);
    public static final ActionFailureResult INVALID_CLIMATE = new ActionFailureResult(L10nWorld.INVALID_CLIMATE);
    public static final ActionFailureResult INVALID_MAP_CONFIGURATION = new ActionFailureResult(L10nWorld.INVALID_MAP_CONFIGURATION);
    public static final ActionFailureResult INVALID_MAX_NUMBER_OF_CIVILIZATIONS = new ActionFailureResult(L10nWorld.INVALID_MAX_NUMBER_OF_CIVILIZATIONS);
    public static final ActionFailureResult CANT_CREATE_WORLD = new ActionFailureResult(L10nWorld.CANT_CREATE_WORLD);
    public static final ActionFailureResult CANT_CREATE_BARBARIANS = new ActionFailureResult(L10nWorld.CANT_CREATE_BARBARIANS);

    public ActionAbstractResult create(CreateWorldParams request) {
        if (request.getWorldName() == null || request.getWorldName().isBlank()) {
            log.debug("World name is blank");
            return INVALID_WORLD_NAME;
        }

        if (request.getMaxNumberOfCivilizations() < 1 || request.getMaxNumberOfCivilizations() > 16) {
            log.debug("Number of civilization = {} is invalid", request.getMaxNumberOfCivilizations());
            return INVALID_MAX_NUMBER_OF_CIVILIZATIONS;
        }

        DifficultyLevel difficultyLevel = DifficultyLevel.find(request.getDifficultyLevel());
        if (difficultyLevel == null) {
            log.debug("Difficulty level = {} is invalid", request.getDifficultyLevel());
            return INVALID_DIFFICULTY_LEVEL;
        }

        Climate climate = Climate.find(request.getClimate());
        if (climate == null) {
            log.debug("Climate = {} is invalid", request.getClimate());
            return INVALID_CLIMATE;
        }

        MapConfiguration mapConfiguration = MapConfiguration.find(request.getMapConfiguration());
        if (mapConfiguration == null) {
            log.debug("Map configuration = {} is invalid", request.getMapConfiguration());
            return INVALID_MAP_CONFIGURATION;
        }

        // create a map
        TilesMap tilesMap = createTileMap(request.getMapSize(), request.getMapWidth(), request.getMapHeight());
        if (tilesMap == null) {
            log.debug("Tiles map = {} ({}x{}) is invalid", request.getMapSize(), request.getMapWidth(), request.getMapHeight());
            return INVALID_MAP_SIZE;
        }
        tilesMap.setMapConfiguration(mapConfiguration);

        // create a world
        World world = new World(request.getWorldName(), tilesMap, climate);
        world.setMaxNumberOfCivilizations(Math.min(CIVILIZATIONS.size(), request.getMaxNumberOfCivilizations()));
        world.setDifficultyLevel(difficultyLevel);

        // generate landscape
        WorldGenerator generator = WorldGeneratorFactory.getGenerator(mapConfiguration);
        generator.generate(tilesMap, climate);

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

    private TilesMap createTileMap(String mapSizeName, int width, int height) {
        MapSize mapSize = MapSize.find(mapSizeName);
        if (mapSize == null) {
            return null;
        }

        if (MapSize.CUSTOM.equals(mapSize)) {
            if (width < TilesMap.MIN_WIDTH || width > TilesMap.MAX_WIDTH ||
                height < TilesMap.MIN_HEIGHT || height > TilesMap.MAX_HEIGHT) {
                return null;
            }
            return new TilesMap(width, height);
        }

        return new TilesMap(mapSize);
    }
}

package com.tsoft.civilization.world.service;

import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.MapConfiguration;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.World;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.world.DifficultyLevel.WARLORD;
import static com.tsoft.civilization.world.service.CreateWorldService.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateWorldServiceTest {

    private final CreateWorldService createWorldService = new CreateWorldService();

    @Test
    public void created() {
        String worldName = UUID.randomUUID().toString();

        CreateWorldParams params = CreateWorldParams.builder()
            .worldName(worldName)
            .mapConfiguration(MapConfiguration.EARTH.name())
            .mapSize(MapSize.STANDARD.name())
            .maxNumberOfCivilizations(8)
            .difficultyLevel(WARLORD.name())
            .build();

        assertThat(createWorldService.create(params))
            .isEqualTo(CREATED);

        assertThat(Worlds.getWorlds())
            .filteredOn(e -> worldName.equals(e.getName()))
            .hasSize(1)
            .first()

            // check for Barbarians
            .extracting(World::getCivilizations)
            .returns(1, CivilizationList::size)
            .returns(BARBARIANS, e -> e.get(0).getName());
    }

    @Test
    public void invalid_map_size() {
        CreateWorldParams params = CreateWorldParams.builder()
            .worldName("World 1")
            .build();

        assertThat(createWorldService.create(params))
            .isEqualTo(INVALID_MAP_SIZE);
    }

    @Test
    public void invalid_max_number_of_civilizations() {
        CreateWorldParams params = CreateWorldParams.builder()
            .worldName("World 1")
            .mapWidth(10)
            .mapHeight(10)
            .build();

        assertThat(createWorldService.create(params))
            .isEqualTo(INVALID_MAX_NUMBER_OF_CIVILIZATIONS);
    }
}

package com.tsoft.civilization.world.action;

import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.world.action.CreateWorldAction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateWorldActionTest {

    @Test
    public void created() {
        CreateWorldAction.Request request = CreateWorldAction.Request.builder()
            .worldName("World 1")
            .mapWidth(10)
            .mapHeight(10)
            .maxNumberOfCivilizations(8)
            .build();

        assertThat(CreateWorldAction.create(request))
            .isEqualTo(CREATED);

        assertThat(Worlds.getWorlds())
            .hasSize(1)
            .element(0)
            .returns("World 1", World::getName)

            // check for Barbarians
            .extracting(World::getCivilizations)
            .returns(1, CivilizationList::size)
            .returns(BARBARIANS, e -> e.get(0).getName());
    }

    @Test
    public void invalid_map_size() {
        CreateWorldAction.Request request = CreateWorldAction.Request.builder()
            .worldName("World 1")
            .build();

        assertThat(CreateWorldAction.create(request))
            .isEqualTo(INVALID_MAP_SIZE);
    }

    @Test
    public void invalid_max_number_of_civilizations() {
        CreateWorldAction.Request request = CreateWorldAction.Request.builder()
            .worldName("World 1")
            .mapWidth(10)
            .mapHeight(10)
            .build();

        assertThat(CreateWorldAction.create(request))
            .isEqualTo(INVALID_MAX_NUMBER_OF_CIVILIZATIONS);
    }
}

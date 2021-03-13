package com.tsoft.civilization.world.action;

import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.world.action.CreateWorldAction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateWorldActionTest {

    @BeforeEach
    public void before() {
        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
    }

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

        assertThat(Sessions.getCurrent())
            .isNotNull()
            .extracting(ClientSession::getCivilization)
            .isNotNull();
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
            .isEqualTo(INVALID_CIVILIZATIONS_NUMBER);
    }
}

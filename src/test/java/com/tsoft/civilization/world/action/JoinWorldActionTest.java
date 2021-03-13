package com.tsoft.civilization.world.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.world.action.JoinWorldAction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class JoinWorldActionTest {

    @BeforeEach
    public void before() {
        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
    }

    @Test
    public void joined() {
        MockWorld world = MockWorld.newSimpleWorld();
        world.setMaxNumberOfCivilizations(1);

        assertThat(JoinWorldAction.join(world.getId()))
            .isEqualTo(JOINED);

        assertThat(Sessions.getCurrent())
            .isNotNull()
            .extracting(ClientSession::getCivilization)
            .isNotNull();
    }

    @Test
    public void world_not_found() {
        assertThat(JoinWorldAction.join("<<<invalid_world_id>>>"))
            .isEqualTo(WORLD_NOT_FOUND);
    }

    @Test
    public void world_is_full() {
        MockWorld world = MockWorld.newSimpleWorld();
        world.setMaxNumberOfCivilizations(1);

        assertThat(JoinWorldAction.join(world.getId()))
            .isEqualTo(JOINED);

        assertThat(JoinWorldAction.join(world.getId()))
            .isEqualTo(WORLD_IS_FULL);
    }
}

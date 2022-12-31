package com.tsoft.civilization.world.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.world.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.MockWorld.GRASSLAND_MAP_10x10;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.world.action.JoinWorldAction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class JoinWorldActionTest {

    @BeforeEach
    public void before() {
        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
    }

    @Test
    public void join_as_human_with_random_civilization() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);
        world.setMaxNumberOfCivilizations(1);

        JoinWorldAction.Request request = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .playerType(PlayerType.HUMAN)
            .build();

        assertThat(JoinWorldAction.join(request))
            .isEqualTo(JOINED);

        assertThat(Sessions.getCurrent()).isNotNull()
            .extracting(ClientSession::getCivilization).isNotNull()
            .extracting(Civilization::getPlayerType).isEqualTo(PlayerType.HUMAN);

        assertThat(world.getYear()).isNotEqualTo(Year.NOT_STARTED);
    }

    @Test
    public void join_two_random_civilizations() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);
        world.setMaxNumberOfCivilizations(2);

        for (int i = 0; i < 2; i ++) {
            JoinWorldAction.Request request = JoinWorldAction.Request.builder()
                .worldId(world.getId())
                .playerType(PlayerType.HUMAN)
                .build();

            assertThat(JoinWorldAction.join(request))
                .isEqualTo(JOINED);

            assertThat(Sessions.getCurrent()).isNotNull()
                .extracting(ClientSession::getCivilization).isNotNull()
                .extracting(Civilization::getPlayerType).isEqualTo(PlayerType.HUMAN);
        }

        assertThat(world.getYear()).isNotEqualTo(Year.NOT_STARTED);
    }

    @Test
    public void join_as_bot_random_civilization() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);
        world.setMaxNumberOfCivilizations(1);

        JoinWorldAction.Request request = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .playerType(PlayerType.BOT)
            .build();

        assertThat(JoinWorldAction.join(request))
            .isEqualTo(JOINED);

        // A bot doesn't have a session
        assertThat(Sessions.getCurrent()).isNotNull()
            .extracting(ClientSession::getCivilization).isNull();

        assertThat(world.getYear()).isEqualTo(Year.NOT_STARTED);
    }

    @Test
    public void join_as_spectator() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);
        world.setMaxNumberOfCivilizations(1);

        JoinWorldAction.Request human = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .civilization(RUSSIA.getEnglish())
            .playerType(PlayerType.HUMAN)
            .build();

        assertThat(JoinWorldAction.join(human))
            .isEqualTo(JOINED);

        JoinWorldAction.Request spectator = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .civilization(RUSSIA.getEnglish())
            .playerType(PlayerType.SPECTATOR)
            .build();

        assertThat(JoinWorldAction.join(spectator))
            .isEqualTo(JOINED);

        assertThat(Sessions.getCurrent()).isNotNull()
            .extracting(ClientSession::getCivilization).isNotNull()
            .extracting(Civilization::getName).isEqualTo(RUSSIA);

        assertThat(world.getYear()).isNotEqualTo(Year.NOT_STARTED);
    }

    @Test
    public void join_as_spectator_fails_as_civilization_not_found() {
        MockWorld world = MockWorld.newSimpleWorld();
        world.setMaxNumberOfCivilizations(1);

        // there are no any civilizations (human or AI) to spectate
        JoinWorldAction.Request spectator = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .playerType(PlayerType.SPECTATOR)
            .build();

        assertThat(JoinWorldAction.join(spectator))
            .isEqualTo(CIVILIZATION_NOT_FOUND);
    }

    @Test
    public void world_not_found() {
        JoinWorldAction.Request request = JoinWorldAction.Request.builder()
            .worldId("<<<invalid_world_id>>>")
            .build();

        assertThat(JoinWorldAction.join(request))
            .isEqualTo(WORLD_NOT_FOUND);
    }

    @Test
    public void world_is_full() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);
        world.setMaxNumberOfCivilizations(1);

        JoinWorldAction.Request request = JoinWorldAction.Request.builder()
            .worldId(world.getId())
            .playerType(PlayerType.HUMAN)
            .build();

        assertThat(JoinWorldAction.join(request))
            .isEqualTo(JOINED);

        assertThat(JoinWorldAction.join(request))
            .isEqualTo(WORLD_IS_FULL);

        assertThat(world.getYear()).isNotEqualTo(Year.NOT_STARTED);
    }
}

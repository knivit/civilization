package com.tsoft.civilization.world.action;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.scenario.DefaultScenario;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.tsoft.civilization.civilization.L10nCivilization.*;

public class JoinWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult JOINED = new ActionSuccessResult(L10nWorld.CIVILIZATION_JOINED);

    public static final ActionFailureResult WORLD_NOT_FOUND = new ActionFailureResult(L10nServer.WORLD_NOT_FOUND);
    public static final ActionFailureResult WORLD_IS_FULL = new ActionFailureResult(L10nServer.WORLD_IS_FULL);
    public static final ActionFailureResult PLAYER_TYPE_MUST_BE_HUMAN_OR_BOT = new ActionFailureResult(L10nServer.PLAYER_TYPE_MUST_BE_HUMAN_OR_BOT);
    public static final ActionFailureResult CANT_CREATE_CIVILIZATION = new ActionFailureResult(L10nServer.CANT_CREATE_CIVILIZATION);
    public static final ActionFailureResult NO_CIVILIZATION_AVAILABLE = new ActionFailureResult(L10nServer.NO_CIVILIZATION_AVAILABLE);
    public static final ActionFailureResult CIVILIZATION_NOT_FOUND = new ActionFailureResult(L10nServer.CIVILIZATION_NOT_FOUND);

    @Builder
    public final static class Request {
        private final String worldId;
        private final String civilization;
        private final PlayerType playerType;
    }

    public static ActionAbstractResult join(Request request) {
        return PlayerType.SPECTATOR.equals(request.playerType) ?
            joinSpectator(request) : joinPlayer(request);
    }

    // A human or a bot
    private static ActionAbstractResult joinPlayer(Request request) {
        World world = Worlds.getWorld(request.worldId);
        if (world == null) {
            return WORLD_NOT_FOUND;
        }

        if (!Arrays.asList(PlayerType.HUMAN, PlayerType.BOT).contains(request.playerType)) {
            return PLAYER_TYPE_MUST_BE_HUMAN_OR_BOT;
        }

        int slotsUsed = world.getCivilizations().size();
        if (world.getCivilizations().getCivilizationByName(BARBARIANS) != null) {
            slotsUsed --;
        }

        int slotsAvailable = world.getMaxNumberOfCivilizations() - slotsUsed;
        if (slotsAvailable == 0) {
            return WORLD_IS_FULL;
        }

        List<L10n> notUsed = CIVILIZATIONS.stream()
            .filter(name -> world.getCivilizations().stream().noneMatch(c -> c.getName().equals(name)))
            .collect(Collectors.toList());

        if (notUsed.isEmpty()) {
            return NO_CIVILIZATION_AVAILABLE;
        }

        L10n civilizationName = findName(request.civilization, notUsed);
        if (civilizationName == null) {
            return NO_CIVILIZATION_AVAILABLE;
        }

        Civilization civilization = world.createCivilization(request.playerType, civilizationName, new DefaultScenario());

        if (civilization == null) {
            return CANT_CREATE_CIVILIZATION;
        }

        if (!PlayerType.BOT.equals(request.playerType)) {
            Sessions.getCurrent().setActiveCivilization(civilization);

            // Start the game
            world.startGame();
        }

        return JOINED;
    }

    private static ActionAbstractResult joinSpectator(Request request) {
        World world = Worlds.getWorld(request.worldId);
        if (world == null) {
            return WORLD_NOT_FOUND;
        }

        if (world.getCivilizations().isEmpty()) {
            return CIVILIZATION_NOT_FOUND;
        }

        Civilization civilization;

        if (request.civilization == null || RANDOM.getEnglish().equals(request.civilization)) {
            int random = ThreadLocalRandom.current().nextInt(world.getCivilizations().size());
            civilization = world.getCivilizations().get(random);
        } else {
            civilization = world.getCivilizations().stream()
                .filter(c -> c.getName().getEnglish().equals(request.civilization))
                .findAny()
                .orElse(null);
        }

        if (civilization == null) {
            return CIVILIZATION_NOT_FOUND;
        }

        Sessions.getCurrent().setActiveCivilization(civilization);
        return JOINED;
    }

    private static L10n findName(String civilization, List<L10n> notUsed) {
        if (civilization == null || RANDOM.getEnglish().equals(civilization)) {
            int random = ThreadLocalRandom.current().nextInt(notUsed.size());
            return notUsed.get(random);
        }

        return notUsed.stream()
            .filter(n -> civilization.equalsIgnoreCase(n.getEnglish()))
            .findAny()
            .orElse(null);
    }
}

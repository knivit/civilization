package com.tsoft.civilization.world.action;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.L10nServer;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.world.World;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

public class JoinWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult JOINED = new ActionSuccessResult(L10nWorld.CIVILIZATION_JOINED);

    public static final ActionFailureResult CANT_CREATE_CIVILIZATION = new ActionFailureResult(L10nServer.CANT_CREATE_CIVILIZATION);
    public static final ActionFailureResult WORLD_NOT_FOUND = new ActionFailureResult(L10nServer.WORLD_NOT_FOUND);
    public static final ActionFailureResult WORLD_IS_FULL = new ActionFailureResult(L10nServer.WORLD_IS_FULL);
    public static final ActionFailureResult NO_CIVILIZATION_AVAILABLE = new ActionFailureResult(L10nServer.NO_CIVILIZATION_AVAILABLE);

    public static ActionAbstractResult join(String worldId) {
        World world = Worlds.getWorld(worldId);
        if (world == null) {
            return WORLD_NOT_FOUND;
        }

        int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
        if (slotsAvailable == 0) {
            return WORLD_IS_FULL;
        }

        List<L10n> notUsed = CIVILIZATIONS.stream()
            .filter(name -> world.getCivilizations().filter(c -> c.getName().equals(name)).isEmpty())
            .collect(Collectors.toList());

        if (notUsed.isEmpty()) {
            return NO_CIVILIZATION_AVAILABLE;
        }

        int random = ThreadLocalRandom.current().nextInt(notUsed.size());
        Civilization civilization = world.createCivilization(notUsed.get(random));

        if (!civilization.units().addFirstUnits()) {
            return CANT_CREATE_CIVILIZATION;
        }

        Sessions.setActiveCivilization(civilization);
        return JOINED;
    }
}

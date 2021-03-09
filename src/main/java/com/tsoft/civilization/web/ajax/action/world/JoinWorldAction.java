package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.tsoft.civilization.L10n.L10nCivilization.CIVILIZATIONS;

public class JoinWorldAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult join(String worldId) {
        World world = Worlds.getWorld(worldId);
        if (world == null) {
            return JoinWorldActionResults.WORLD_NOT_FOUND;
        }

        int slotsAvailable = world.getMaxNumberOfCivilizations() - world.getCivilizations().size();
        if (slotsAvailable == 0) {
            return JoinWorldActionResults.WORLD_IS_FULL;
        }

        List<L10nMap> names = new ArrayList<>(CIVILIZATIONS);
        List<L10nMap> notUsed = names.stream()
            .filter(name -> world.getCivilizations().filter(c -> c.getName().equals(name)).isEmpty())
            .collect(Collectors.toList());

        if (notUsed.isEmpty()) {
            return JoinWorldActionResults.NO_CIVILIZATION_AVAILABLE;
        }

        int random = ThreadLocalRandom.current().nextInt(notUsed.size());
        Civilization civilization = world.createCivilization(notUsed.get(random));
        if (!civilization.units().addFirstUnits()) {
            return JoinWorldActionResults.CANT_CREATE_CIVILIZATION;
        }

        return JoinWorldActionResults.JOINED;
    }
}

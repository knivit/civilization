package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.america.America;
import com.tsoft.civilization.civilization.japan.Japan;
import com.tsoft.civilization.civilization.russia.Russia;
import com.tsoft.civilization.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static com.tsoft.civilization.civilization.L10nCivilization.*;

public class CivilizationFactory {

    private static final Map<L10n, BiFunction<World, PlayerType, Civilization>> CIVILIZATION_CATALOG = new HashMap<>();

    static {
        CIVILIZATION_CATALOG.put(AMERICA, America::new);
        CIVILIZATION_CATALOG.put(JAPAN, Japan::new);
        CIVILIZATION_CATALOG.put(RUSSIA, Russia::new);
    }

    public static Civilization newInstance(L10n name, World world, PlayerType playerType) {
        BiFunction<World, PlayerType, Civilization> supplier = CIVILIZATION_CATALOG.get(name);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown civilization name = " + name.getEnglish());
        }

        return supplier.apply(world, playerType);
    }
}

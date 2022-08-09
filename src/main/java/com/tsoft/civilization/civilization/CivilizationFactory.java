package com.tsoft.civilization.civilization;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.nation.america.America;
import com.tsoft.civilization.civilization.nation.barbarians.Barbarians;
import com.tsoft.civilization.civilization.nation.japan.Japan;
import com.tsoft.civilization.civilization.nation.russia.Russia;
import com.tsoft.civilization.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static com.tsoft.civilization.civilization.L10nCivilization.*;

public class CivilizationFactory {

    private static final Map<L10n, BiFunction<World, PlayerType, Civilization>> CATALOG = new HashMap<>();

    static {
        CATALOG.put(AMERICA, America::new);
        CATALOG.put(BARBARIANS, Barbarians::new);
        CATALOG.put(JAPAN, Japan::new);
        CATALOG.put(RUSSIA, Russia::new);
    }

    public static Civilization newInstance(L10n name, World world, PlayerType playerType) {
        BiFunction<World, PlayerType, Civilization> supplier = CATALOG.get(name);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown civilization name = " + name.getEnglish());
        }

        return supplier.apply(world, playerType);
    }
}

package com.tsoft.civilization.unit;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.DifficultyLevel;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.world.DifficultyLevel.*;
import static com.tsoft.civilization.world.DifficultyLevel.DEITY;

public final class UnitBaseModifiers {

    private UnitBaseModifiers() { }

    private static final Map<DifficultyLevel, Double> BASE_ECONOMIC_MODIFIERS = new HashMap<>() {{
        put(SETTLER, 1.5);
        put(CHIEFTAIN, 1.3);
        put(WARLORD, 1.1);
        put(PRINCE, 1.0);
        put(KING, 0.9);
        put(EMPEROR, 0.85);
        put(IMMORTAL, 0.8);
        put(DEITY, 0.75);
    }};

    private static final Map<DifficultyLevel, Double> BASE_COMBAT_STRENGTH_MODIFIERS = new HashMap<>() {{
        put(SETTLER, 1.5);
        put(CHIEFTAIN, 1.3);
        put(WARLORD, 1.1);
        put(PRINCE, 1.0);
        put(KING, 0.9);
        put(EMPEROR, 0.85);
        put(IMMORTAL, 0.8);
        put(DEITY, 0.75);
    }};

    private static final Map<DifficultyLevel, Double> UNIT_PRODUCTION_COST_MODIFIERS = Map.of(
        SETTLER, 0.5,
        CHIEFTAIN, 0.67,
        WARLORD, 0.85,
        PRINCE, 1.0,
        KING, 1.0,
        EMPEROR, 1.0,
        IMMORTAL, 1.0,
        DEITY, 1.0
    );

    public static double getProductionCostModifier(Civilization civilization) {
        DifficultyLevel level = civilization.getWorld().getDifficultyLevel();
        return UNIT_PRODUCTION_COST_MODIFIERS.get(level);
    }

    public static double getCombatStrengthModifier(Civilization civilization) {
        DifficultyLevel level = civilization.getWorld().getDifficultyLevel();
        return BASE_COMBAT_STRENGTH_MODIFIERS.get(level);
    }

    public static double getEconomicModifier(Civilization civilization) {
        DifficultyLevel level = civilization.getWorld().getDifficultyLevel();
        return BASE_ECONOMIC_MODIFIERS.get(level);
    }
}

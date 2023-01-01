package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.DifficultyLevel;

import java.util.Map;

import static com.tsoft.civilization.world.DifficultyLevel.*;
import static com.tsoft.civilization.world.DifficultyLevel.DEITY;

public final class BuildingBaseModifiers {

    private static final Map<DifficultyLevel, Double> ECONOMIC_MODIFIERS = Map.of(
        SETTLER, 0.5,
        CHIEFTAIN, 0.67,
        WARLORD, 0.85,
        PRINCE, 1.0,
        KING, 1.0,
        EMPEROR, 1.0,
        IMMORTAL, 1.0,
        DEITY, 1.0
    );

    private BuildingBaseModifiers() { }

    public static double getModifier(Civilization civilization) {
        DifficultyLevel level = civilization.getWorld().getDifficultyLevel();
        return ECONOMIC_MODIFIERS.get(level);
    }
}

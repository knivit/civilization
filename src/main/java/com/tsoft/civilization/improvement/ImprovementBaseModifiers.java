package com.tsoft.civilization.improvement;

import com.tsoft.civilization.civilization.Civilization;

public final class ImprovementBaseModifiers {

    private ImprovementBaseModifiers() { }

    public static double getModifier(Civilization civilization) {
        return 1.0;
    }
}

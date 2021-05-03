package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.L10nWorld;
import lombok.EqualsAndHashCode;

// immutable
@EqualsAndHashCode(of = "state")
public class CivilizationsRelations {
    private static final int WAR_STATE = -50;
    private static final int NEUTRAL_STATE = 0;
    private static final int FRIENDS_STATE = 50;

    private final int state;

    public static CivilizationsRelations war() {
        return new CivilizationsRelations(WAR_STATE);
    }

    public static CivilizationsRelations neutral() {
        return new CivilizationsRelations(NEUTRAL_STATE);
    }

    public static CivilizationsRelations friends() {
        return new CivilizationsRelations(FRIENDS_STATE);
    }

    private CivilizationsRelations(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public boolean isWar() {
        return state == WAR_STATE;
    }

    public boolean isNeutral() {
        return state == NEUTRAL_STATE;
    }

    public boolean isFriends() {
        return state == FRIENDS_STATE;
    }

    public L10n getDescription() {
        if (state == WAR_STATE) return L10nWorld.WAR_RELATIONS_DESCRIPTION;
        if (state < NEUTRAL_STATE) return L10nWorld.BAD_RELATIONS_DESCRIPTION;
        if (state == NEUTRAL_STATE) return L10nWorld.NEUTRAL_RELATIONS_DESCRIPTION;
        if (state < FRIENDS_STATE) return L10nWorld.GOOD_RELATIONS_DESCRIPTION;
        return L10nWorld.FRIENDS_RELATIONS_DESCRIPTION;
    }

    @Override
    public String toString() {
        return "CivilizationsRelations" +
            "{state=" + state +
            '}';
    }
}

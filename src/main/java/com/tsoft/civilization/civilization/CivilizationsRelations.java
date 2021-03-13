package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.L10nWorld;

public class CivilizationsRelations {
    public static final int WAR_STATE = -50;
    public static final CivilizationsRelations WAR = new CivilizationsRelations(WAR_STATE);

    public static final CivilizationsRelations NEUTRAL = new CivilizationsRelations(0);

    public static final int FRIENDS_STATE = 50;
    public static final CivilizationsRelations FRIENDS = new CivilizationsRelations(FRIENDS_STATE);

    private int state;

    public CivilizationsRelations(int state) {
        assert (state >= WAR_STATE && state <= FRIENDS_STATE) : "Wrong state = " + state + ". It must be in [-50..50]";
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setState(CivilizationsRelations relations) {
        this.state = relations.getState();
    }

    public boolean isWar() {
        return WAR.equals(this);
    }

    public L10n getDescription() {
        if (state == WAR_STATE) return L10nWorld.WAR_RELATIONS_DESCRIPTION;
        if (state < NEUTRAL.state) return L10nWorld.BAD_RELATIONS_DESCRIPTION;
        if (state == NEUTRAL.state) return L10nWorld.NEUTRAL_RELATIONS_DESCRIPTION;
        if (state < FRIENDS_STATE) return L10nWorld.GOOD_RELATIONS_DESCRIPTION;
        return L10nWorld.FRIENDS_RELATIONS_DESCRIPTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CivilizationsRelations that = (CivilizationsRelations) o;

        return state == that.state;
    }

    @Override
    public int hashCode() {
        return state;
    }

    @Override
    public String toString() {
        return "CivilizationsRelations" +
            "{state=" + state +
            '}';
    }
}

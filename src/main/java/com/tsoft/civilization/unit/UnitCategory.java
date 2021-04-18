package com.tsoft.civilization.unit;

import java.util.Arrays;

public enum UnitCategory {

    MILITARY_MELEE,
    MILITARY_RANGED,
    MILITARY_RANGED_CITY,
    MILITARY_SIEGE,
    MILITARY_MOUNTED,
    MILITARY_NAVAL,
    MILITARY_AIR,

    CIVIL;

    public boolean isMilitary() {
        return !CIVIL.equals(this);
    }

    public boolean isLand() {
        return this.in(CIVIL, MILITARY_MELEE, MILITARY_RANGED, MILITARY_RANGED_CITY, MILITARY_SIEGE, MILITARY_MOUNTED);
    }

    public boolean isAir() {
        return this.equals(MILITARY_AIR);
    }

    public boolean isNaval() {
        return this.equals(MILITARY_NAVAL);
    }

    public boolean isMelee() {
        return (MILITARY_MELEE.equals(this) || MILITARY_MOUNTED.equals(this));
    }

    public boolean isRanged() {
        return (MILITARY_RANGED.equals(this) || MILITARY_RANGED_CITY.equals(this));
    }

    public boolean isCity() {
        return MILITARY_RANGED_CITY.equals(this);
    }

    private boolean in(UnitCategory ... list) {
        return Arrays.asList(list).contains(this);
    }
}

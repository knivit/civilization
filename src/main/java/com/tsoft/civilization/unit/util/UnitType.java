package com.tsoft.civilization.unit.util;

public enum UnitType {
    MILITARY_MELEE(true),
    MILITARY_RANGED(true),
    MILITARY_RANGED_CITY(true),
    MILITARY_SIEGE(true),
    MILITARY_MOUNTED(true),
    MILITARY_NAVAL(true),
    MILITARY_AIR(true),
    MILITARY_MISSILE(true),

    CIVIL(false);

    private boolean isMilitary;

    UnitType(boolean isMilitary) {
        this.isMilitary = isMilitary;
    }

    public boolean isMilitary() {
        return isMilitary;
    }

    public boolean isRanged() {
        return (MILITARY_RANGED.equals(this) || MILITARY_RANGED_CITY.equals(this));
    }

    public boolean isCity() {
        return MILITARY_RANGED_CITY.equals(this);
    }
}

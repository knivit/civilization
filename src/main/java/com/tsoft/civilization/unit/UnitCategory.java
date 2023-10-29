package com.tsoft.civilization.unit;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum UnitCategory {

    AIRCRAFT_CARRIER("Aircraft Carrier"),
    ARCHERY("Archery"),
    ARMORED("Armored"),
    ATOMIC_BOMBER("Atomic Bomber"),
    BOMBER("Bomber"),
    CIVILIAN_WATER("Civilian Water"),
    CIVILIAN("Civilian"),
    FIGHTER("Fighter"),
    GUNPOWDER("Gunpowder"),
    HELICOPTER("Helicopter"),
    MELEE_WATER("Melee Water"),
    MISSILE("Missile"),
    MOUNTED("Mounted"),
    RANGED_GUNPOWDER("Ranged Gunpowder"),
    RANGED_WATER("Ranged Water"),
    SCOUT("Scout"),
    SIEGE("Siege"),
    SUBMARINE("Submarine"),
    SWORD("Sword");

    private final String code;

    public static UnitCategory find(String code) {
        for (UnitCategory category : UnitCategory.values()) {
            if (code.equals(category.code)) {
                return category;
            }
        }
        return null;
    }

    public boolean isMilitary() {
        return !CIVILIAN.equals(this);
    }

    public boolean isLand() {
        return this.in(CIVILIAN, ARCHERY, GUNPOWDER, MOUNTED, RANGED_GUNPOWDER, SCOUT, SIEGE, SWORD);
    }

    public boolean isAir() {
        return this.in(ATOMIC_BOMBER, BOMBER, FIGHTER, HELICOPTER, MISSILE);
    }

    public boolean isNaval() {
        return this.in(AIRCRAFT_CARRIER, CIVILIAN_WATER, MELEE_WATER, RANGED_WATER, SUBMARINE);
    }

    public boolean isMelee() {
        return this.in(ARMORED, GUNPOWDER, MELEE_WATER, MOUNTED, SWORD);
    }

    public boolean isRanged() {
        return this.in(AIRCRAFT_CARRIER, ARCHERY, ATOMIC_BOMBER, BOMBER, FIGHTER, HELICOPTER, MISSILE,
            RANGED_GUNPOWDER, RANGED_WATER, SIEGE, SUBMARINE);
    }

    private boolean in(UnitCategory ... list) {
        return Arrays.asList(list).contains(this);
    }
}

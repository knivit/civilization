package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitCategory;

public class CityCombatService {

    private final City city;
    private CombatStrength combatStrength;

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
        .setTargetBackFireStrength(5)
        .setStrength(50)
        .setRangedAttackStrength(10)
        .setRangedAttackRadius(2);

    public CityCombatService(City city) {
        this.city = city;
        combatStrength = new CombatStrength(city, COMBAT_STRENGTH);
    }

    public CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    public boolean isDestroyed() {
        return combatStrength.isDestroyed();
    }

    public CombatStrength getCombatStrength() {
        return combatStrength;
    }

    public UnitCategory getUnitCategory() {
        return com.tsoft.civilization.unit.UnitCategory.MILITARY_RANGED_CITY;
    }

    // Check is this building adds defense strength
    public void addBuilding(AbstractBuilding building) {
        int strength = combatStrength.getStrength();
        strength += building.getStrength();
        combatStrength.setStrength(strength);
    }

    public void destroyBuilding(AbstractBuilding building) {
        int strength = combatStrength.getStrength();
        strength -= building.getStrength();
        combatStrength.setStrength(strength);
    }

    public void startYear() {

    }

    public void stopYear() {

    }
}

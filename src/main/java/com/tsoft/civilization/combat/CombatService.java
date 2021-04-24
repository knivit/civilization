package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

public class CombatService {

    private static final MeleeCombatService meleeCombatService = new MeleeCombatService();
    private static final RangedCombatService rangedCombatService = new RangedCombatService();

    // Find out all targets to attack
    public HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        if (attacker.getUnitCategory().isRanged()) {
            return rangedCombatService.getTargetsToAttack(attacker);
        }

        // All the foreign units, cities are targets to attack
        // For a melee attack targets must be located next to the attacker
        return meleeCombatService.getTargetsToAttack(attacker);
    }

    public HasCombatStrength getTargetToAttackAtLocation(HasCombatStrength attacker, Point location) {
        Civilization civilization = attacker.getCivilization();

        // first, if there is a city then attack it
        City city = civilization.getWorld().getCityAtLocation(location);
        if (city != null) {
            return city;
        }

        // second, see is there a military unit
        UnitList foreignUnits = civilization.getWorld().getUnitsAtLocation(location, civilization);
        AbstractUnit militaryUnit = foreignUnits.findFirstMilitaryUnit();
        if (militaryUnit != null) {
            return militaryUnit;
        }

        return foreignUnits.getAny();
    }

    public CombatResult attack(HasCombatStrength attacker, HasCombatStrength target) {
        if (attacker.getUnitCategory().isRanged()) {
            return rangedCombatService.attack(attacker, target);
        }

        // For a melee unit, move to the target first, then attack
        return meleeCombatService.attack(attacker, target);

    }
}

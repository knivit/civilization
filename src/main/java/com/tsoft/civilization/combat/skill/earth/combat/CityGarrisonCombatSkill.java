package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.UnitList;
import lombok.Getter;

import java.util.stream.Collectors;

public class CityGarrisonCombatSkill implements AbstractCombatSkill {

    public static AbstractCombatSkill CITY_GARRISON_COMBAT_SKILL = new CityGarrisonCombatSkill();

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    @Getter final L10n localizedName = L10nSkill.CITY_GARRISON_COMBAT_SKILL;

    private CityGarrisonCombatSkill() { }

    // A portion of the garrisoned unit's combat strength is added to the city's total strength
    // Only Land units may form a Garrison
    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        if (unit.getUnitCategory().isCity()) {
            City city = (City) unit;
            int garrisonMeleeAttackStrength = calcGarrisonedMeleeAttackStrength(city);
            int garrisonRangedAttackStrength = calcGarrisonedRangedAttackStrength(city);
            int garrisonDefenseStrength = calcGarrisonedDefenseStrength(city);

            return CombatStrength.builder()
                .meleeAttackStrength(garrisonMeleeAttackStrength)
                .rangedAttackStrength(garrisonRangedAttackStrength)
                .defenseStrength(garrisonDefenseStrength)
                .build();
        }

        return CombatStrength.ZERO;
    }

    private int calcGarrisonedMeleeAttackStrength(City city) {
        return getGarrison(city).stream()
            .filter(e -> e.getUnitCategory().isLand())
            .mapToInt(e -> (int)Math.round((double)e.calcCombatStrength().getMeleeAttackStrength() * 0.4))
            .sum();
    }

    private int calcGarrisonedRangedAttackStrength(City city) {
        return getGarrison(city).stream()
            .filter(e -> e.getUnitCategory().isRanged())
            .mapToInt(e -> (int)Math.round((double)e.calcCombatStrength().getRangedAttackStrength() * 0.6))
            .sum();
    }

    private int calcGarrisonedDefenseStrength(City city) {
        return getGarrison(city).stream()
            .mapToInt(e -> (int)Math.round((double)e.calcCombatStrength().getDefenseStrength() * 0.2))
            .sum();
    }

    // Garrison - military land units stationed in the city
    private UnitList getGarrison(City city) {
        UnitList unitsAround = city.getCivilization().getUnitService()
            .getUnitsAtLocation(city.getLocation());

        return new UnitList(unitsAround.stream()
            .filter(e -> e.getUnitCategory().isMilitary())
            .filter(e -> e.getUnitCategory().isLand())
            .collect(Collectors.toList()));
    }
}


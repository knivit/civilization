package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillType;
import com.tsoft.civilization.improvement.city.City;
import lombok.Getter;

public class CityBuildingsCombatSkill implements AbstractCombatSkill {

    public static AbstractCombatSkill CITY_BUILDINGS_COMBAT_SKILL = new CityBuildingsCombatSkill();

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    @Getter final L10n localizedName = L10nSkill.CITY_BUILDINGS_COMBAT_SKILL;

    private CityBuildingsCombatSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        if (unit.getUnitCategory().isCity()) {
            City city = (City) unit;
            int defenseStrength = city.getBuildings().stream()
                .mapToInt(AbstractBuilding::getCityDefenseStrength)
                .sum();

            return CombatStrength.builder()
                .defenseStrength(defenseStrength)
                .build();
        }

        return CombatStrength.ZERO;
    }
}

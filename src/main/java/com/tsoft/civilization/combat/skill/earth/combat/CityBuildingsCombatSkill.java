package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.civilization.city.City;
import lombok.Getter;

public class CityBuildingsCombatSkill implements AbstractCombatSkill {

    public static AbstractCombatSkill CITY_BUILDINGS_COMBAT_SKILL = new CityBuildingsCombatSkill();

    @Getter
    private final SkillName skillName = SkillName.CITY_BUILDINGS_COMBAT_SKILL;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    @Getter
    final L10n localizedName = L10nSkill.CITY_BUILDINGS_COMBAT_SKILL;

    private CityBuildingsCombatSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit) {
        if ("City".equals(unit.getClassUuid())) {
            City city = (City) unit;
            int defenseStrength = city.getBuildings().stream()
                .mapToInt(e -> e.getDefenseStrength(unit.getCivilization()))
                .sum();

            return CombatStrength.builder()
                .defenseStrength(defenseStrength)
                .build();
        }

        return CombatStrength.ZERO;
    }
}

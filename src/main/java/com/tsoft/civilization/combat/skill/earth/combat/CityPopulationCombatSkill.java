package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.civilization.city.City;
import lombok.Getter;

public class CityPopulationCombatSkill implements AbstractCombatSkill {

    public static AbstractCombatSkill CITY_POPULATION_COMBAT_SKILL = new CityPopulationCombatSkill();

    @Getter
    private final SkillName skillName = SkillName.CITY_POPULATION_COMBAT_SKILL;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    @Getter final L10n localizedName = L10nSkill.CITY_POPULATION_COMBAT_SKILL;

    private CityPopulationCombatSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit) {
        if ("City".equals(unit.getClassUuid())) {
            City city = (City) unit;
            return CombatStrength.builder()
                .defenseStrength(city.getCitizenCount())
                .build();
        }

        return CombatStrength.ZERO;
    }
}

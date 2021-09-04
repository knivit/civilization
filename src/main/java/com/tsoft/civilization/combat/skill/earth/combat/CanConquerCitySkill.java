package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillType;
import lombok.Getter;

public class CanConquerCitySkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill CAN_CONQUER_CITY_SKILL = new CanConquerCitySkill();

    @Getter
    private final L10n localizedName = L10nSkill.CAN_CONQUER_CITY;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    private CanConquerCitySkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        return CombatStrength.ZERO;
    }
}

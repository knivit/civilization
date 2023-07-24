package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import lombok.Getter;

public class CanConquerCitySkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill CAN_CONQUER_CITY_SKILL = new CanConquerCitySkill();

    @Getter
    private final SkillName skillName = SkillName.CAN_CONQUER_CITY;

    @Getter
    private final L10n localizedName = L10nSkill.CAN_CONQUER_CITY;

    @Getter
    private final SkillType skillType = SkillType.ACCUMULATOR;

    private CanConquerCitySkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength unit) {
        return CombatStrength.ZERO;
    }
}

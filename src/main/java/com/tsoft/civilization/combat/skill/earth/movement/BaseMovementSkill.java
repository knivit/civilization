package com.tsoft.civilization.combat.skill.earth.movement;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.skill.AbstractMovementSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.unit.AbstractUnit;
import lombok.Getter;

public class BaseMovementSkill implements AbstractMovementSkill {

    public static final AbstractMovementSkill BASE_MOVEMENT_SKILL = new BaseMovementSkill();

    @Getter
    private final L10n localizedName = L10nSkill.BASE_MOVEMENT_SKILL;

    @Override
    public int getPassScore(AbstractUnit unit, SkillLevel level) {
        return level.getValue() - 1;
    }
}

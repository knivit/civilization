package com.tsoft.civilization.combat.skill.earth.movement;

import com.tsoft.civilization.combat.skill.SkillName;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.skill.AbstractMovementSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.unit.AbstractUnit;
import lombok.Getter;

public class BaseMovementSkill implements AbstractMovementSkill {

    public static final AbstractMovementSkill BASE_MOVEMENT_SKILL = new BaseMovementSkill();

    @Getter
    private final SkillName skillName = SkillName.BASE_MOVEMENT_SKILL;

    @Getter
    private final L10n localizedName = L10nSkill.BASE_MOVEMENT_SKILL;

    @Override
    public int getPassScore(AbstractUnit unit) {
        return 1;
    }
}

package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.unit.AbstractUnit;

public interface AbstractMovementSkill extends AbstractSkill {

    int getPassScore(AbstractUnit unit, SkillLevel level);
}

package com.tsoft.civilization.unit.service;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import lombok.Getter;
import lombok.Setter;

public class UnitMovementService {

    private final SkillService skillService = new SkillService();

    private final AbstractUnit unit;

    @Getter
    @Setter
    private Point location;

    @Getter
    @Setter
    private int passScore;

    private final SkillMap<AbstractMovementSkill> movementSkills;

    public UnitMovementService(AbstractUnit unit) {
        this.unit = unit;

        movementSkills = unit.getBaseMovementSkills();
    }

    public SkillMap<AbstractMovementSkill> getHealingSkills() {
        return movementSkills.unmodifiable();
    }

    public void addCombatSkill(AbstractMovementSkill skill) {
        movementSkills.put(skill, SkillLevel.ONE);
    }

    public void startYear() {
        passScore = calcPassScore();
    }

    public void startEra() {

    }

    public void stopYear() {

    }

    private int calcPassScore() {
        return skillService.calcPassScore(unit, movementSkills);
    }
}

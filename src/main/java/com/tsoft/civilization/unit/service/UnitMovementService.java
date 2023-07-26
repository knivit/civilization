package com.tsoft.civilization.unit.service;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

public class UnitMovementService {

    private final SkillService skillService = new SkillService();

    private final AbstractUnit unit;

    @Getter
    private Point location;

    @Getter
    private int passScore;

    @Getter
    private final SkillList<AbstractMovementSkill> movementSkills;

    public UnitMovementService(AbstractUnit unit) {
        this.unit = unit;
        movementSkills = unit.getBaseMovementSkills(unit.getCivilization());
    }

    public void moveTo(Point location) {
        if (location == null) {
            throw new IllegalArgumentException("location can't be null");
        }

        this.location = location;
    }

    public void updatePassScore(int passScore) {
        this.passScore = Math.max(0, passScore);
    }

    public void startYear() {
        passScore = skillService.calcPassScore(unit, movementSkills);
    }
}

package com.tsoft.civilization.unit.service.movement;

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

        movementSkills = unit.getBaseMovementSkills(unit.getCivilization());
    }

    public SkillMap<AbstractMovementSkill> getMovementSkills() {
        return movementSkills.unmodifiable();
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

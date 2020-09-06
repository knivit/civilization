package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;

import java.util.List;

public interface HasCombatStrength {
    String getId();

    Civilization getCivilization();

    Point getLocation();

    UnitCategory getUnitCategory();

    UnitCollection getUnitsAround(int radius);

    List<AbstractSkill> getSkills();

    CombatStrength getCombatStrength();

    void destroyedBy(HasCombatStrength target, boolean destroyOtherUnitsAtLocation);

    void setPassScore(int passScore);

    String getClassUuid();

    boolean isDestroyed();
}

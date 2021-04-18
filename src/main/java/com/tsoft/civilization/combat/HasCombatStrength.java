package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;

import java.util.List;

public interface HasCombatStrength {
    String getId();

    Civilization getCivilization();

    Point getLocation();

    UnitCategory getUnitCategory();

    UnitList getUnitsAround(int radius);

    List<AbstractSkill> getSkills();

    CombatStrength getBaseCombatStrength();

    CombatStrength getCombatStrength();

    void setCombatStrength(CombatStrength combatStrength);

    CombatStrength calcCombatStrength();

    void destroyedBy(HasCombatStrength target, boolean destroyOtherUnitsAtLocation);

    void setPassScore(int passScore);

    String getClassUuid();

    boolean isDestroyed();

    default void addCombatStrength(CombatStrength other) {
        setCombatStrength(getCombatStrength().add(other));
    }
}

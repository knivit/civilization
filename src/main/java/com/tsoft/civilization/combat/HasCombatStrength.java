package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.world.AbstractView;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;

import java.util.List;

public interface HasCombatStrength {

    String getId();

    AbstractView getView();

    Civilization getCivilization();

    Point getLocation();

    UnitCategory getUnitCategory();

    List<AbstractSkill> getSkills();

    CombatStrength getBaseCombatStrength();

    CombatStrength getCombatStrength();

    void setCombatStrength(CombatStrength combatStrength);

    CombatStrength calcCombatStrength();

    void setPassScore(int passScore);

    String getClassUuid();

    boolean isDestroyed();

    void destroy();
}

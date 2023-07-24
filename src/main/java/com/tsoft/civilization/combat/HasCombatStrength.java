package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.SkillList;
import com.tsoft.civilization.world.AbstractView;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;

public interface HasCombatStrength {

    String getId();

    AbstractView getView();

    Civilization getCivilization();

    Point getLocation();

    UnitCategory getUnitCategory();

    CombatStrength getBaseCombatStrength(Civilization civilization);

    SkillList<AbstractCombatSkill> getBaseCombatSkills(Civilization civilization);

    CombatStrength calcCombatStrength();

    SkillList<AbstractHealingSkill> getBaseHealingSkills(Civilization civilization);

    CombatDamage getCombatDamage();

    void addCombatDamage(CombatDamage damage);

    void setCombatExperience(CombatExperience experience);

    void setPassScore(int passScore);

    String getClassUuid();

    boolean isDestroyed();

    void destroy();
}

package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.SkillMap;
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

    CombatStrength getBaseCombatStrength(int era);

    SkillMap<AbstractCombatSkill> getBaseCombatSkills(int era);

    CombatStrength calcCombatStrength();

    SkillMap<AbstractHealingSkill> getBaseHealingSkills(int era);

    CombatDamage getCombatDamage();

    void addCombatDamage(CombatDamage damage);

    void setPassScore(int passScore);

    String getClassUuid();

    boolean isDestroyed();

    void destroy();
}

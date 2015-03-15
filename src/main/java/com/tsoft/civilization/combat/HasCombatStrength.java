package com.tsoft.civilization.combat;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitType;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;

import java.util.List;

public interface HasCombatStrength {
    public String getId();

    public Civilization getCivilization();

    public Point getLocation();

    public UnitType getUnitType();

    public UnitCollection getUnitsAround(int radius);

    public List<AbstractSkill> getSkills();

    public CombatStrength getCombatStrength();

    public void destroyBy(HasCombatStrength target, boolean destroyOtherUnitsAtLocation);

    public void setPassScore(int passScore);

    public String getClassUuid();

    public boolean isDestroyed();
}

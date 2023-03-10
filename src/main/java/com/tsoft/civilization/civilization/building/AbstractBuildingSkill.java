package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.economic.Supply;

public interface AbstractBuildingSkill extends AbstractSkill {

    Supply calcSupply(AbstractBuilding building);
}

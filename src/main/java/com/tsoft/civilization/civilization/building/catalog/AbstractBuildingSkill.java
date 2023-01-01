package com.tsoft.civilization.civilization.building.catalog;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.economic.Supply;

public interface AbstractBuildingSkill extends AbstractSkill {

    Supply calcSupply(AbstractBuilding building);
}

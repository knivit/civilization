package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.civilization.building.catalog.AbstractBuildingSkill;
import com.tsoft.civilization.economic.Supply;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BuildingBaseState {

    private final BuildingCategory category;
    private final int goldCost;
    private final int productionCost;
    private final int defenseStrength;
    private final Supply incomeSupply;
    private final Supply outcomeSupply;
    private final int localHappiness;
    private final int globalHappiness;
    private final List<AbstractBuildingSkill> supplySkills;
}

package com.tsoft.civilization.civilization.building.catalog.granary;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.BUILDING;
import static com.tsoft.civilization.civilization.building.catalog.granary.GranaryIncomeSkill.GRANARY_INCOME_SKILL;
import static java.util.Arrays.asList;

public class GranaryBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
            .category(BUILDING)
            .productionCost(60)
            .goldCost(340)
            .incomeSupply(Supply.builder().food(2).build())
            .outcomeSupply(Supply.builder().gold(-1).build())
            .supplySkills(asList(GRANARY_INCOME_SKILL))
            .build();
    }
}

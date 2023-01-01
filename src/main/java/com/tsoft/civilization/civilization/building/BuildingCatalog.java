package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.economic.Supply;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.civilization.building.BuildingCategory.*;
import static com.tsoft.civilization.civilization.building.BuildingType.*;
import static com.tsoft.civilization.civilization.building.granary.GranaryIncomeSkill.GRANARY_INCOME_SKILL;
import static java.util.Arrays.asList;

public final class BuildingCatalog {

    private static final Map<BuildingType, BuildingBaseState> BUILDINGS = new HashMap<>() {{
        put(GRANARY, BuildingBaseState.builder()
            .category(BUILDING)
            .productionCost(60)
            .goldCost(340)
            .incomeSupply(Supply.builder().food(2).build())
            .outcomeSupply(Supply.builder().gold(-1).build())
            .supplySkills(asList(GRANARY_INCOME_SKILL))
            .build());

        put(MARKET, BuildingBaseState.builder()
            .category(BUILDING)
            .productionCost(120)
            .goldCost(580)
            .incomeSupply(Supply.builder().food(2).build())
            .outcomeSupply(Supply.EMPTY)
            .build());

        put(MONUMENT, BuildingBaseState.builder()
            .category(BUILDING)
            .productionCost(40)
            .goldCost(280)
            .incomeSupply(Supply.builder().culture(2).build())
            .outcomeSupply(Supply.builder().gold(-1).build())
            .build());

        put(PALACE, BuildingBaseState.builder()
            .category(NATIONAL_WONDER)
            .defenseStrength(25)
            .incomeSupply(Supply.builder().production(3).gold(3).science(3).culture(1).build())
            .build());

        put(SETTLEMENT, BuildingBaseState.builder()
            .category(NATIONAL_WONDER)
            .defenseStrength(10)
            .incomeSupply(Supply.builder().production(1).gold(1).culture(1).build())
            .build());

        put(WALLS, BuildingBaseState.builder()
            .category(BUILDING)
            .productionCost(200)
            .goldCost(400)
            .defenseStrength(40)
            .outcomeSupply(Supply.builder().gold(-1).build())
            .build());
    }};


    private BuildingCatalog() { }

    public static BuildingBaseState getBaseState(BuildingType type) {
        return BUILDINGS.get(type);
    }
}

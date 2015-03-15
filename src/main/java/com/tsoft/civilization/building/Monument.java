package com.tsoft.civilization.building;

import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.economic.BuildingScore;
import com.tsoft.civilization.world.economic.BuildingSupply;
import com.tsoft.civilization.web.view.building.MonumentView;

import java.util.UUID;

public class Monument extends AbstractBuilding<MonumentView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MonumentView VIEW = new MonumentView();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    /**
     * The Monument increases the Culture of a city speeding the growth of the city's territory
     * and the civilization's acquisition of Social Policies.
     */
    @Override
    public BuildingScore getSupply(City city) {
        BuildingScore score = new BuildingScore(null);
        score.add(new BuildingSupply(0, 0, -1, 0, 0, 0), L10nBuilding.BUILDING_EXPENSES_SUPPLY);
        score.add(new BuildingSupply(0, 0, 0, 0, 2, 0), L10nBuilding.BUILDING_SUPPLY);
        return score;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getProductionCost() {
        return 40;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost() {
        return 280;
    }

    @Override
    public MonumentView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}

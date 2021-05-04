package com.tsoft.civilization.improvement.city.population;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.population.Happiness;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.resource.luxury.AbstractLuxuryResource;
import com.tsoft.civilization.world.DifficultyLevel;

import static com.tsoft.civilization.civilization.population.CivilizationHappinessService.EXTRA_HAPPINESS_PER_LUXURY;

public class CityHappinessService {

    private final City city;

    public CityHappinessService(City city) {
        this.city = city;
    }

    public Happiness calcHappiness() {
        int buildings = calcBuildingsHappiness();
        int garrisonedUnits = calcGarrisonedUnitsHappiness();
        int luxuryResources = calcLuxuryResourcesHappiness();

        return Happiness.builder()
            .luxuryResources(luxuryResources)
            .buildings(buildings)
            .garrisonedUnits(garrisonedUnits)
            .build();
    }

    // TODO depends on a Social Policy
    private int calcGarrisonedUnitsHappiness() {
        return 0;
    }

    // Their practical effect is to provide additional Happiness: +4 for each luxury
    // resource which your empire has secured at least one source of. Unlike strategic resources, each improved luxury resource
    // tile provides only one unit of the resource. If an empire has secured more than one unit of the same luxury resource,
    // the Happiness effect is the same as possessing only one, so you should consider using the surplus to trade with other empires.
    private int calcLuxuryResourcesHappiness() {
        DifficultyLevel difficultyLevel = city.getCivilization().getWorld().getDifficultyLevel();

        int luxuryResourcesCount = (int)city.getTileService().getLuxuryResources().stream()
            .map(AbstractLuxuryResource::getClass)
            .distinct()
            .count();

        return luxuryResourcesCount * 4 + luxuryResourcesCount * EXTRA_HAPPINESS_PER_LUXURY.get(difficultyLevel);
    }

    // This means that if a city has 5 Population and sources of local happiness adding up to 8 Happiness, the actual amount
    // that will be contributed to your empire's Happiness will be 5, not 8.
    private int calcBuildingsHappiness() {
        int localHappiness = city.getBuildings().stream()
            .mapToInt(AbstractBuilding::getLocalHappiness)
            .sum();

        int globalHappiness = city.getBuildings().stream()
            .mapToInt(AbstractBuilding::getGlobalHappiness)
            .sum();

        int citizenCount = city.getCitizenCount();

        return Math.min(citizenCount, localHappiness) + globalHappiness;
    }
}

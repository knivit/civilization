package com.tsoft.civilization.civilization.population;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.City;

public class CivilizationPopulationService {

    private final Civilization civilization;

    public CivilizationPopulationService(Civilization civilization) {
        this.civilization = civilization;
    }

    public int getCitizenCount() {
        return civilization.getCityService().getCities().stream()
            .mapToInt(City::getCitizenCount)
            .sum();
    }
}

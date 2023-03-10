package com.tsoft.civilization.civilization.city.happiness;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.happiness.Unhappiness;
import com.tsoft.civilization.world.DifficultyLevel;

import java.util.Map;

import static com.tsoft.civilization.world.DifficultyLevel.*;
import static com.tsoft.civilization.world.DifficultyLevel.DEITY;

public class CityUnhappinessService {

    private static final Map<DifficultyLevel, Double> UNHAPPINESS_PER_POPULATION = Map.of(
            SETTLER, 0.4,
            CHIEFTAIN, 0.6,
            WARLORD, 0.75,
            PRINCE, 1.0,
            KING, 1.0,
            EMPEROR, 1.0,
            IMMORTAL, 1.0,
            DEITY, 1.0
    );

    private final City city;

    private Unhappiness unhappiness;

    public CityUnhappinessService(City city) {
        this.city = city;
    }

    public Unhappiness getUnhappiness() {
        if (unhappiness == null) {
            unhappiness = calc();
        }
        return unhappiness;
    }

    public void recalculate() {
        unhappiness = null;
        city.getCivilization().getUnhappinessService().recalculate();
    }

    private Unhappiness calc() {
        DifficultyLevel difficultyLevel = city.getDifficultyLevel();

        // Population
        int populationCount = city.getCitizenCount();
        int population = (int)Math.round(populationCount * UNHAPPINESS_PER_POPULATION.get(difficultyLevel));

        return Unhappiness.builder()
                .population(population)
                .build();
    }
}

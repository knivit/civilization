package com.tsoft.civilization.world.economic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Supply {
    public static final Supply EMPTY_SUPPLY = Supply.builder().build();

    private final int food;
    private final int production;
    private final int gold;
    private final int science;
    private final int culture;
    private final int happiness;
    private final int population;

    public Supply add(Supply other) {
        return Supply.builder()
            .food(food + other.food)
            .production(production + other.production)
            .gold(gold + other.gold)
            .science(science + other.science)
            .culture(culture + other.culture)
            .happiness(happiness + other.happiness)
            .population(population + other.population)
            .build();
    }

    @Override
    public String toString() {
        return "{" +
                "food=" + food +
                ", production=" + production +
                ", gold=" + gold +
                ", science=" + science +
                ", culture=" + culture +
                ", happiness=" + happiness +
                ", population=" + population +
                '}';
    }
}
